package com.example.wage.controller;

import com.example.wage.pojo.Position;
import com.example.wage.service.PositionService;
import com.example.wage.util.ResultUtil;
import com.example.wage.vo.PageVo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 职位管理控制器
 */
@RestController
@RequestMapping("/api/v1/position")
public class PositionRestController {

    @Resource private PositionService positionService;

    /**
     * 保存职位
     * @param position 职位
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/save")
    public ResultUtil savePosition(@RequestBody Position position) {
        positionService.savePosition(position);
        return ResultUtil.success();
    }

    /**
     * 根据职位id删除职位
     * @param id 职位id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/{id}")
    public ResultUtil deletePosition(@PathVariable("id") String id) {
        positionService.removeById(id);
        return ResultUtil.success();
    }

    /**
     * 分页查询职位
     * @param pageVo 参数
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/web/admin")
    public ResultUtil selectPage(PageVo<Position> pageVo) {
        return ResultUtil.success(positionService.selectPage(pageVo));
    }

    /**
     * 查询所有职位
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/all")
    public ResultUtil all() {
        return ResultUtil.success(positionService.list());
    }

}
