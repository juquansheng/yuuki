package com.malaxiaoyugan.yuukicore.aop;



import com.malaxiaoyugan.yuukicore.utils.TTBFResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class TTBFExceptionHandler {

    @ExceptionHandler(value = TTBFException.class)
    @ResponseBody
    public TTBFResult exceptionHandler(TTBFException e) throws Exception {
        TTBFResult tjbResult = new TTBFResult();
        tjbResult.setMsg(e.getMessage());
        tjbResult.setStatus(e.getStatus());
        tjbResult.setData("");
        return tjbResult;
    }
}