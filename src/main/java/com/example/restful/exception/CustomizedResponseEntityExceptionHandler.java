package com.example.restful.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestController
@ControllerAdvice // 모든 컨트롤러가 이곳을 거침
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request){

    }
}
