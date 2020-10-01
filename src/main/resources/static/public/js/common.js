/**
 * 全局js异常处理。
 */
$(document).ajaxError(function (event, jqxhr, settings, thrownError) {
    alert(JSON.parse(jqxhr.responseText)['message']);
});