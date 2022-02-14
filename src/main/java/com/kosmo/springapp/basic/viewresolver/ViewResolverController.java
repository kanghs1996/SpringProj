package com.kosmo.springapp.basic.viewresolver;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

//컨트롤러 클래스]
@Controller
public class ViewResolverController {
	//컨트롤러 메소드]
	/*
	@RequestMapping("/ViewResolver/ViewResolver.do")
	public String execute(Model model) {
		//데이타 저장]
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
		model.addAttribute("message", dateFormat.format(new Date()));
		//뷰 정보 반환]
		//접두어(/WEB-INF/views/)와 접미어(.jsp)를 뺀 논리적인 이름만 반환
		//1. .jsp페이지로 forward
		//return "viewresolver03/ViewResolver";
		//2. .do로 forward
		//아래 처럼 뷰정보 반환시 접두어 접미어가 붙어서 404에러 발생
	    // /WEB-INF/views/ViewResolver/NotJSP.do.jsp
		//return "/ViewResolver/NotJSP.do";
		//※InternalResourceViewResolver를 통한 접두어/접미어에 
		// 영향을 안 받으려면 return 시 "forward:" 이나
		//"redirect:"를 앞에 붙인다.
		//forward:이 디폴트 이다 즉 포워드방식으로 이동이 기본이다
		
		//[----접두어/접미어 영향 받지 않기-주로 .do로 이동시에 적용한다----]
		//[forward 로 이동시]
		//1. .jsp페이지로 forward
		//return "forward:/WEB-INF/views/viewresolver03/ViewResolver.jsp";
		//2. .do로 forward
		//return "forward:/ViewResolver/NotJSP.do?message=Hello Spring!!";
		//[redirect로 이동시]
		//※리다이렉트시  모델객체에 저장된 데이타는 쿼리스트링으로 전달된다
		//1. .jsp페이로 redirect-/WEB-INF밑에 있는 JSP파일을 직접 URL로 요청한거와 같다 그래서 404에러
		//return "redirect:/WEB-INF/views/viewresolver03/ViewResolver.jsp";		
		//2. .do로 redirect
		return "redirect:/ViewResolver/NotJSP.do";
	}*/
	
	
	//[RedirectView API로 리다이렉트하기]	
	@RequestMapping("/ViewResolver/ViewResolver.do")
	public ModelAndView execute() {		
		//.jsp로 리다이렉트-아래는 404에러
		//리다이렉트는 .jsp를 URL로 직접 요청한 거와 같다(즉 run on server)
		//WEB-INF 밑에 있는 뷰인 JSP는 리다이렉트로 이동 불가(404에러)
		//RedirectView redirectView = new RedirectView("/WEB-INF/views/viewresolver03/ViewResolver.jsp",true);
		//.do로 리다이렉트
		RedirectView redirectView = new RedirectView("/ViewResolver/NotJSP.do");
		redirectView.setContextRelative(true);		
		ModelAndView mav = new ModelAndView();
		//데이타 저장]
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
		mav.addObject("message", dateFormat.format(new Date()))	;
		//뷰 정보 설정
		mav.setView(redirectView);
		
		return mav;
	}///////////////	
	//컨트롤러 메소드(execute()메소드에서 포워드로 같은 요청을 받는 메소드]
	@RequestMapping("/ViewResolver/NotJSP.do")
	public String notjsp(@RequestParam String message) {
		
		return "viewresolver03/ViewResolver";
	}
	
}
