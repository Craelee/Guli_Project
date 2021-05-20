package uestc.lj.servicebase.exceptionhandler;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import uestc.lj.commonutils.R;
import uestc.lj.servicebase.exception.GuliException;

/**
 * 统一异常处理
 *
 * @author crazlee
 */
@ControllerAdvice
@Slf4j
public class MyGlobalExceptionHandler {
    /**
     * 全局异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(Exception.class)
    public R error(Exception e) {
        e.printStackTrace();
        return R.error().message("出现了异常错误，请及时处理！");
    }

    /**
     * 自定义异常处理
     *
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(GuliException.class)
    public R error(GuliException e) {
        //将错误信息写入日志文件
        log.error(e.getMessage());

        e.printStackTrace();
        return R.error().code(e.getCode()).message("出现了自定义异常错误，请及时处理！" + e.getMsg());
    }
}
