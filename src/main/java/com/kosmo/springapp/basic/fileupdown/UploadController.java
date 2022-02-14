package com.kosmo.springapp.basic.fileupdown;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@ControllerAdvice
@Controller
public class UploadController {

	
	/*
	 * 파일 업로드시 MultipartFile API사용
	 * 주요 메소드:
	 * getOriginalFilename()-사용자가 올린 파일명
	 * getSize()-파일 크기(바이트)
	 * getContentType()-파일 컨텐트 타입
	 * getName()-input type="file"의 name속성에 지정한 값
	 * ※MultipartFile객체의 transferTo(File f)메소드 호출만으로
		 업로드 처리됨.
	 */
	
	
	//방법1]MultipartFile을 매개변수로 사용
	//※먼저 빈 설정파일에 CommonsMultipartResolver빈 등록
	/* 파라미터가 여러개 일때 아래 어노테이션 추가해서
	 * input type="file"을 제외한 파라미터를 받자
	 * @RequestParam Map map
	 * 
	 * ※enctype="multipart/form-data" 일때
	 *   Map은 input type="file"을 받지 못한다.
	 *   또한 checkbox(여러개 선택해도) 는 하나만 받는다.
	 */
	
	/*
	아래 어노이션으로는 MaxUploadSizeExceededException예외 처리 불가 
	@ControllerAdvice가 붙은 com/kosmo/springapp/basic/exception/ExceptionControllerAdvice.java
	클래스에서 예외 처리하거나
	현재 클래스에 @ControllerAdvice를 추가한다
	*/
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public String error(Model model,HttpServletRequest req) {
		model.addAttribute("maxError", "파일 업로드 용량 초과 했어요");
		return "fileupdown14/Upload";
	}
	/*
	@RequestMapping("/FileUpDown/Upload.do")
	public String upload(@RequestParam Map map,//기타 파라미터 받기용
			             @RequestParam MultipartFile upload,//type="file" 받기용
			             HttpServletRequest req//서버의 물리적 경로 얻기용
			             ) throws IllegalStateException, IOException{
		System.out.println("제목(Map):"+map.get("title"));
		System.out.println("제목(HttpServletRequest):"+req.getParameter("title"));
		
		//1]서버의 물리적 경로 얻기		
		String path=req.getServletContext().getRealPath("/upload");
		//2]File객체 생성	
		File dest = new File(path+File.separator+upload.getOriginalFilename());
		//3]업로드 처리
		upload.transferTo(dest);
		//4]리퀘스트 영역에 데이타 저장
		req.setAttribute("original", upload.getOriginalFilename());
		req.setAttribute("real", upload.getOriginalFilename());
		req.setAttribute("type", upload.getContentType());
		req.setAttribute("size", (int)Math.ceil(upload.getSize()/1024.0));
		return "fileupdown14/UploadComplete";
	}*/
	/*방법2]MultipartHttpServletRequest사용
	MultipartHttpServletRequest는 MultipartFile과
	HttpServletRequest가 합쳐진 클래스 
	※input type="file" name="파라미터명"을 받을때는 
	  getFile("파라미터명")
	    기타 파라미터 받을때는 getParameter("파라미터명")로 
	   받는다.
	*/	
	/*
	@RequestMapping("/FileUpDown/Upload.do")
	public String upload(@RequestParam Map map,MultipartHttpServletRequest mhsr) throws IllegalStateException, IOException{
		//1]서버의 물리적 경로 얻기		
		String path=mhsr.getServletContext().getRealPath("/upload");
		//1-1]MultipartHttpServletRequest객체의 getFile("파라미터명")메소드로 MultipartFile객체 얻기
		MultipartFile upload= mhsr.getFile("upload");
		//2]File객체 생성	]
		//2-1] 파일 중복시 이름 변경
		String rename=FileUpDownUtils.getNewFileName(path, upload.getOriginalFilename());
		File dest = new File(path+File.separator+rename);
		//3]업로드 처리
		upload.transferTo(dest);
		//4]리퀘스트 영역에 데이타 저장
		mhsr.setAttribute("original", upload.getOriginalFilename());
		mhsr.setAttribute("real", rename);
		mhsr.setAttribute("type", upload.getContentType());
		mhsr.setAttribute("size", (int)Math.ceil(upload.getSize()/1024.0));
		return "fileupdown14/UploadComplete";
	}*/
	//방법3]커맨드 객체 사용
	@RequestMapping(value="/FileUpDown/Upload.do",method=RequestMethod.POST)
	public String upload(UploadCommand cmd,HttpServletRequest req) throws IllegalStateException, IOException{
		//1]서버의 물리적 경로 얻기		
		String path=req.getServletContext().getRealPath("/upload");
		//1-1]MultipartHttpServletRequest객체의 getFile("파라미터명")메소드로 MultipartFile객체 얻기
		MultipartFile upload= cmd.getUpload();
		//2]File객체 생성	]
		//2-1] 파일 중복시 이름 변경
		String rename=FileUpDownUtils.getNewFileName(path, upload.getOriginalFilename());
		File dest = new File(path+File.separator+rename);
		//3]업로드 처리
		upload.transferTo(dest);
		//4]리퀘스트 영역에 데이타 저장
		req.setAttribute("original", upload.getOriginalFilename());
		req.setAttribute("real", rename);
		req.setAttribute("type", upload.getContentType());
		req.setAttribute("size", (int)Math.ceil(upload.getSize()/1024.0));
		return "fileupdown14/UploadComplete";
	}
	
	//목록 이동용 컨트롤러 메소드
	@RequestMapping("/FileUpDown/List.do")
	public String list(HttpServletRequest req) {
		//1]서버의 물리적 경로 얻기		
		String path=req.getServletContext().getRealPath("/upload");
		//2]File객체 생성	
		File f = new File(path);
		File[] files=f.listFiles();
		//방법1]컬렉션 저장
		List<Map> list = new Vector<Map>();
		for(File file:files) {
			Map map = new HashMap();
			map.put("name", file.getName());
			map.put("size", (int)Math.ceil(file.length()/1024.0));
			list.add(map);
		}
		
		req.setAttribute("files", files);
		req.setAttribute("list", list);
		return "fileupdown14/List";
	}
}
