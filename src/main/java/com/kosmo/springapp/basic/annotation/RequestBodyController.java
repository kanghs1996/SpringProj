package com.kosmo.springapp.basic.annotation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/*
@RestController :주로 데이타만 보낼때.@Controller + @ResponseBody와 같다
@Controller : 주로 페이지를 보낼때

@RequestBody : JSON형식 데이타 받을 때 사용 .스프링 부트는 내장되어 있다(Jackson라이브러리)
			   스프링 메이븐(레거시)는 내장되어 있지 않다
Jackson라이브러리 : 자바 객체(DTO계열)를 JSON형식(자스 객체)으로
                  JSON형식(자스 객체)을 자바객체로 변환시켜주는 라이브러리
*/
//@Controller
@RestController
public class RequestBodyController {
	
	
	/*
	  ※Form데이타를 받을때는 @ModelAttribute나 혹은 생략(단,@RequestBody는 415에러:
	   Content type 'application/x-www-form-urlencoded;charset=UTF-8' not supported)
	   JSON데이타를 받을때는 @RequestBody 사용.
	 */
	
	@RequestMapping("/Annotation/RequestBody.do")
	//@ResponseBody //@RestController사용시는 주석처리. POSTMAN툴로 테스트하거나 자바스크립트로 POST요청
	public LoginCommand exec(@RequestBody LoginCommand cmd) {//Jackson:JSON->자바타입(LoginCommand)
		System.out.println("아이디:"+cmd.getUser());
		System.out.println("비번:"+cmd.getPass());
		return cmd;//Jackson:자바타입(LoginCommand)->JSON으로 변환
	}
	
}
