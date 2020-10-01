package com.example.wage.security.config;

import com.example.wage.common.bean.AjaxAuthFailureHandler;
import com.example.wage.enumeration.ErrorCode;
import com.example.wage.util.JsonUtil;
import com.example.wage.util.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户页面认证、授权
 */
@Configuration
@Order(2)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {

    private static Map<String, String> ADMIN_MAP = new HashMap();

    /*
     * 模拟数据库用户名和密码(使用md5加密)
     */
    static {
        ADMIN_MAP.put("admin", "123456");
    }

    /**
     * json 格式装换类
     */
    private ObjectMapper objectMapper;

    /**
     * ajax请求失败处理器。
     */
    private AjaxAuthFailureHandler ajaxAuthFailureHandler;

    @Autowired
    public UserSecurityConfig(ObjectMapper objectMapper,
                              AjaxAuthFailureHandler ajaxAuthFailureHandler) {
        this.objectMapper = objectMapper;
        this.ajaxAuthFailureHandler = ajaxAuthFailureHandler;

    }

    /**
     * 配置以MD5验证密码。
     *
     * @return
     */
    @Bean
    @Order(1)
    public PasswordEncoder md5PasswordEncoderForTenancyUser() {
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return rawPassword.toString();
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(encode(rawPassword));
            }
        };
    }

    /**
     * 验证用户名、密码和授权。
     *
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetailsService userDetailsService() throws UsernameNotFoundException {
        return (username) -> {
            if (ADMIN_MAP.get(username) == null) {
                throw new UsernameNotFoundException("User Not Found: " + username);
            }
            /**
             * 用户授权，用户名为lisi的拥有访问用户列表的权限
             */
            List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ADMIN"));
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("USER"));
            return User.withUsername(username)
                    .password(ADMIN_MAP.get(username))
                    .authorities(simpleGrantedAuthorities)
                    .build();
        };
    }

    /**
     * 配置自定义验证用户名、密码和授权的服务。
     *
     * @param authenticationManagerBuilder
     * @throws Exception
     */
    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder)
            throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService());
    }

    /**
     * http请求配置：
     * 1.开启权限拦截路径。
     * 2.释放资源配置。
     * 3.登录请求配置。
     * 4.退出登录配置。
     * 5.默认开启csrf防护。
     *
     * @param http
     * @throws Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(unauthorizedEntryPoint())
                .accessDeniedHandler(handleAccessDeniedForUser())
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/public/**"
                )
                .permitAll()
                .antMatchers(
                        "/login"
                )
                .permitAll()
                .antMatchers(
                        "/api/v1/login"
                )
                .permitAll()
                .anyRequest()
                .hasAuthority("USER")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/v1/login")
                .permitAll()
                .defaultSuccessUrl("/")
                .successHandler(ajaxAuthSuccessHandler())
                .failureHandler(ajaxAuthFailureHandler)
                .and()
                .logout()
                .logoutUrl("/api/v1/logout")
                .logoutSuccessHandler(ajaxLogoutSuccessHandler())
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .and()
                .csrf()
                .disable();
    }

    /**
     * 自定义 “未登入系统，直接请求资源” 处理器。
     * 判断是否ajax请求，是ajax请求则返回json，否则跳转至登录页面。
     *
     * @return
     */
    private AuthenticationEntryPoint unauthorizedEntryPoint() {
        return (HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) -> {
            String requestedWithHeader = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestedWithHeader)) {
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                ResultUtil result = ResultUtil.fail(ErrorCode.UNAUTHORIZED);
                response.getOutputStream().write(JsonUtil.OBJECT_MAPPER.writeValueAsBytes(result));
            } else {
                response.sendRedirect("/login");
            }
        };
    }

    /**
     * 自定义登录成功处理器。
     *
     * @return
     */
    private AuthenticationSuccessHandler ajaxAuthSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");

            ObjectNode root = objectMapper.createObjectNode();
            root.put("redirect",
                    request.getRequestURI().equals("/api/v1/login") ? "/" : request.getRequestURI());

            response.getOutputStream().write(root.toString().getBytes());
        };
    }

    /**
     * 自定义退出成功处理器。
     *
     * @return
     */
    private LogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return (HttpServletRequest request, HttpServletResponse response, Authentication authentication) -> {
            response.setStatus(HttpServletResponse.SC_OK);
            response.setContentType("application/json");
            ObjectNode root = objectMapper.createObjectNode();
            root.put("redirect", "/login");

            response.getOutputStream().write(root.toString().getBytes());
        };
    }

    /**
     * 自定义 “无权请求的资源” 处理器。
     *
     * @return
     */
    private AccessDeniedHandler handleAccessDeniedForUser() {
        return (HttpServletRequest request,
                HttpServletResponse response,
                AccessDeniedException accessDeniedException) -> {
            String requestedWithHeader = request.getHeader("X-Requested-With");
            if ("XMLHttpRequest".equals(requestedWithHeader)) {
                response.setCharacterEncoding(StandardCharsets.UTF_8.displayName());
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                ResultUtil result = ResultUtil.fail(ErrorCode.FORBIDDEN);
                response.getOutputStream().write(JsonUtil.OBJECT_MAPPER.writeValueAsBytes(result));
            } else {
                response.sendRedirect("/login");
            }
        };
    }
}

