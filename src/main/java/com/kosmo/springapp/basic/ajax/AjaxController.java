package com.kosmo.springapp.basic.ajax;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
/*
 * 	사전준비:pom.xml에 jackson-databind라이브러리 의존성 추가
 * 
	[Jackson]
	Java 객체(Map혹은 DTO)를 JSON으로 변환하거나 
	JSON을 Java 객체(Map혹은 DTO)로 변환하는 라이브러리
	List계열 컬렉션 즉 List<Map> 혹은 List<DTO>는
	자바스크립트로 변환시 JSON배열로 변환된다
	예] [ {key1:value2,key2:value2,},{},{},{}...]
	
	Spring 3.0 이후로부터, Jacskon과 관련된 API를 제공
	Jackson라이브러리를 사용하면 자동화 처리 가능
	
	Jackson라이브러리 pom.xml에 설정시
	MessageConverter API인 MappingJacksonHttpMessageConverter API가
	작동하여 컨트롤러가 리턴하는 객체를 후킹하여 ObjectMapper API로 JSON
	형태의 문자열로 자동으로 변환하여 반환한다.	
	1.자바객체를 JSON으로 변환시
	writeValue(File객체,DTO혹은 MAP) :객체를 JSON파일(.json)로 변환	
	writeValueAsString(DTO혹은 MAP):객체를 JSON형식의 문자열로 변환
	writerWithDefaultPrettyPrinter().writeValueAsString(DTO혹은 MAP)
	2.JSON을 자바객체로 변환시
	readValue(File객체,DTO혹은 MAP):JSON파일(.json)에 있는 내용을 자바객체로 읽어들일때
	readValue(JSON형식 문자열,DTO혹은 MAP):JSON형식의 문자열을 자바객체로 읽어 들일때
 */
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosmo.springapp.onememo.service.ListPagingData;
import com.kosmo.springapp.onememo.service.OneMemoDTO;
import com.kosmo.springapp.onememo.service.OneMemoService;
@Controller
public class AjaxController {
	
	@Autowired
	private OneMemoService service;
	
	//[ajax 미 사용]-새로고침이 됨.
	/* 반환타입 void:직접 웹브라우저에 출력 스트림으로 결과값 출력
	*  반환타입 String:디스패처서블릿에게 뷰 반환, 결과값은 모델에 저장(JSP로 포워드)
	*/
	/*
	@RequestMapping("/Ajax/NoAjax.do")
	public void noajax(@RequestParam Map map,HttpServletResponse resp) throws IOException {
		//1]컨텐츠 타입 설정
		resp.setContentType("text/html; charset=UTF-8");
		//2]웹브라우저에 출력하기위한 출력스트림 얻기
		PrintWriter out= resp.getWriter();
		//3]비지니스 로직 호출
		boolean isLogin=service.isLogin(map);
		if(isLogin)
			out.println("<h2>"+map.get("id")+"님 즐감하세요</h2>");
		else {
			out.println("<script>");
			out.println("alert('아뒤와 비번이 틀려요');");
			out.println("history.back();");
			out.println("</script>");
		}
		//4]웹브라우저와 연결된 스트림 닫기
		out.close();
	}*/
	@RequestMapping("/Ajax/NoAjax.do")
	public String noajax(@RequestParam Map map,Model model) {
		//1]서비스 호출
		boolean isLogin=service.isLogin(map);
		//2]데이타 저장
		model.addAttribute("isLogin",isLogin?map.get("id")+"님 반갑습니다":"회원정보 불일치");
		//3]뷰 정보 반환
		return "ajax12/Ajax";
	}
	//[AJAX 사용]-페이지를 전송하지 않고 데이타만 전송
	//[TEXT로 응답할때]	
	/*
	 * 반환타입은 void이거나 
	 * 반환타입이 String인 경우는 @ResponseBody()어노테이션 사용
	 */
	/*
	@RequestMapping("/Ajax/AjaxText.do")
	public void ajaxText(@RequestParam Map map,HttpServletResponse resp) throws IOException {
		//1]컨텐츠 타입 설정
		resp.setContentType("text/html; charset=UTF-8");
		//2]웹브라우저에 출력하기위한 출력스트림 얻기
		PrintWriter out= resp.getWriter();
		//3]비지니스 로직 호출
		boolean isLogin=service.isLogin(map);
		//Y 혹은 N 값으로 응답할때-반드시 print()메소드로 안그러면 println은 줄바꿈이 추가됨
		//out.print(isLogin? "Y" : "N");
		//일반 메시지로 응답할때
		out.print(isLogin? map.get("id")+"님 즐감!": "회원 가입해...");
		//4]웹브라우저와 연결된 스트림 닫기
		out.close();
	}///////////
	*/
	@RequestMapping(value="/Ajax/AjaxText.do",produces = "text/plain; charset=UTF-8")	
	public @ResponseBody String ajaxText(@RequestParam Map map) {
		//1]서비스 호출
		boolean isLogin=service.isLogin(map);
		//Y 혹은 N 값으로 응답할때
		//return isLogin? "Y" : "N";
		//일반 메시지로 응답할때
		return isLogin? map.get("id")+"님 즐감하삼!": "회원 가입 하삼";
	}
	
	@Autowired
	private ObjectMapper mapper;	
	//[JSON으로 응답할때]
	@RequestMapping(value="/Ajax/AjaxJson.do",produces = "application/json; charset=UTF-8")
	public @ResponseBody String ajaxJson(@RequestParam Map map) throws JsonProcessingException {
		//1]서비스 호출
		boolean isLogin=service.isLogin(map);	
		//[ObjectMapper 미 사용]  직접 json형식의 문자열 생성해서 반환.
		//반환 : 회원인 경우- {"isLogin":"방가방가"} 비회원인 경우- {"isLogin":"다음기회에"}
		//return String.format("{\"isLogin\":\"%s\",\"id\":\"%s\",\"pwd\":\"%s\"}", 
		//		isLogin?"방가방가":"다음기회에",
		//		map.get("id"),
		//		map.get("pwd")
		//		);
		//[ObjectMapper 사용]
		//Map을 JSON형식의 문자열로 변경 -writeValueAsString()
		//{"id":"KIM","pwd":"1234","isLogin":"방가방가"}
		map.put("isLogin", isLogin?"방가방가":"다음기회에");
		System.out.println(mapper.writeValueAsString(map));
		return mapper.writeValueAsString(map);
		
	}////////////////
	@RequestMapping(value="/Ajax/AjaxJsonArray.do",produces = "application/json; charset=UTF-8")
	public @ResponseBody String ajaxJsonArray(@RequestParam Map map,HttpServletRequest req) throws JsonProcessingException {
		//1]서비스 호출		
		ListPagingData<OneMemoDTO> datas= service.selectList(map, req, 1);
		List<OneMemoDTO> lists= datas.getLists();
		
		//System.out.println(mapper.writeValueAsString(lists));
		/*[
		{"no":"130","title":"폴링 적용전 글등록","content":"11111111111\r\n2222222222","postDate":1638198000000,"id":"KIM","name":"김길동","commentCount":"0","comments":null},
		{"no":"129","title":"단위 테스트입니다","content":"입력 테스트중 입니다","postDate":1637766000000,"id":"KIM","name":"김길동","commentCount":"0","comments":null},
		{"no":"128","title":"단위 테스트입니다","content":"입력 테스트중 입니다","postDate":1637766000000,"id":"KIM","name":"김길동","commentCount":"0","comments":null},
		{"no":"127","title":"단위 테스트입니다","content":"입력 테스트중 입니다","postDate":1637766000000,"id":"KIM","name":"김길동","commentCount":"0","comments":null}
		]
		*/
		
		return mapper.writeValueAsString(lists);
	}
	
	@RequestMapping(value="/Ajax/AjaxCourse.do",produces = "application/json; charset=UTF-8")
	public @ResponseBody String ajaxCourse(@RequestParam String course) throws JsonProcessingException {
		Map map = new TreeMap();
		switch(course) {
			case "java":
				map.put("j01","자바");
				map.put("j02","JSP");
				map.put("j03","스프링");
				break;
			case "dotnet":
				map.put("d01","C#");
				map.put("d02","ASP.NET");
				map.put("d03","WPF4");
				break;
			default:
				map.put("i01","라즈베리 파이");
				map.put("i02","파이썬");
		}
		
		return mapper.writeValueAsString(map);
	}///////////////////////////
	//1.자바객체를 JSON으로 변환
	//가.KEY=VALUE쌍으로 데이타 받기
	@RequestMapping("/Ajax/form.do")
	public String form(OneMemoDTO dto,Model model,HttpServletRequest req) throws StreamWriteException, DatabindException, IOException{
		//[자바객체를 JSON으로 변환]
		
		//1.JSON파일로 저장
		//mapper.writeValue(new File("onememo.json"), dto);//IDE도구 실행파일이 있는 곳에 저장됨	
		String res=req.getServletContext().getRealPath("/resources");
		mapper.writerWithDefaultPrettyPrinter().writeValue(
				new File(res+File.separator+"onememo.json"), dto);		
		System.out.println(res+File.separator+"onememo.json");
		
		//2.JSON형태의 문자열로 변환
		//String json=mapper.writeValueAsString(dto);	
		String json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);
		System.out.println(json);
		
		model.addAttribute("formRequestResult", json);
		return "ajax12/Ajax";
	}/////////////
	//위 요청후 브라우저에서 http://localhost:8080/springapp/resources/onememo.json로 요청하면
	//json형태로 잘 나옴
	
	@RequestMapping("/Ajax/AjaxDataKeyValue.do")	
	//1.자바빈 반환하는 경우
	/*
	public @ResponseBody OneMemoDTO ajaxKeyValue(OneMemoDTO dto) {
		return dto;//JSON형식의 문자열로 변환되서 반환됨
	}*/
	//2.Map으로 반환하는 경우 : 값을 받지 않은 속성인 경우 null인 속성 제거 효과
	public @ResponseBody Map ajaxKeyValue(@RequestParam Map  map) {
		return map;//JSON형식의 문자열로 변환되서 반환됨
	}
	@RequestMapping(value="/Ajax/AjaxDataJson.do",produces = "application/json;charset=UTF-8")
	//나.json으로 데이타 받기
	//※@RequestBody어노테이션 사용해야 한다
	//Map이나 혹은 자바빈에 받을 수 있다.
	/*
	public @ResponseBody OneMemoDTO ajaxDataJson(@RequestBody OneMemoDTO dto) {
		return dto;//JSON형식의 문자열로 변환되서 반환됨
	}*/
	public @ResponseBody Map ajaxDataJson(@RequestBody Map map) {
		return map;//JSON형식의 문자열로 변환되서 반환됨
	}
	
	@RequestMapping(value="/Ajax/AjaxDataArray.do",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Map> ajaxDataArray() {//String으로 반환시에는 ObjectMapper로 문자열로 변경
		List<Map> lists = new Vector<>();
		Map map = new HashMap();
		map.put("id","KIM");
		map.put("name","김길동");
		lists.add(map);
		map = new HashMap();
		map.put("id","LEE");
		map.put("name","이길동");
		lists.add(map);
		return lists;
	}
	//2.JSON을 자바객체로 변환시
	//json구조를 자바빈으로 만들어주는 사이트:
	//https://www.jsonschema2pojo.org/
	@RequestMapping(value="/Ajax/JsonToJava.do",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public Map jsonToJava(HttpServletRequest req) throws StreamReadException, DatabindException, IOException {
		//onememo.json파일에서 읽기
		/*
		 
		 {
			  "no" : null,
			  "title" : "form태그로 요청",
			  "content" : "폼태그로 요청합니다\r\n감사합니다",
			  "postDate" : null,
			  "id" : null,
			  "name" : "코스모",
			  "commentCount" : null,
			  "comments" : null
			}
		 */
		String res=req.getServletContext().getRealPath("/resources");
		//1.읽어온 데이타를 자바빈에 저장 -반환타입을 OneMemoDTO로
		//OneMemoDTO dto = mapper.readValue(new File(res+File.separator+"onememo.json"), OneMemoDTO.class);
		//String json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dto);//자바빈을 JSON형식의 문자열로 변환
		//System.out.println(json);
		//2.읽어온 데이타를 맵에 저장 -반환타입을 Map으로
		Map map= mapper.readValue(new File(res+File.separator+"onememo.json"), Map.class);
		String json=mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);//맵을 JSON형식의 문자열로 변환
		System.out.println(json);
		return map;
	}
	
}
