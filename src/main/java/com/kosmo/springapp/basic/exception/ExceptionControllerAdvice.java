package com.kosmo.springapp.basic.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/*
 * @ControllerAdvice 를 통해 모든 컨트롤러에서 발생하는 예외 처리
 * @ExceptionHandler 를 통해 발생하는 예외 종류에 따른 메소드 정의
 */

//개발시는 아래 주석
@ControllerAdvice
public class ExceptionControllerAdvice {
	@ExceptionHandler(Exception.class)
	public String error(Model model,Exception e) {
		
		model.addAttribute("errors",e.getMessage());
		//뷰정보 반환]
		if(e instanceof MaxUploadSizeExceededException) {
			
			model.addAttribute("maxError", "업로드 최대 용량 초과!!!");
			return "fileupdown14/Upload";
		}
		return "exception13/Error";
	}
}
