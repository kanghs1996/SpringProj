<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<!-- 상단메뉴 시작 -->	
<jsp:include page="/WEB-INF/views/template/Top_.jsp"/>
<!-- 상단메뉴 끝 -->

<!-- 실제 내용 시작 -->
<div class="container">
	
	<div class="page-header">
		<h1>스프링 <small>파일 업로드/다운로드</small></h1>
	</div>
	<fieldset>
		<legend>파일 업로드 결과</legend>		
		<ul style="list-style: upper-roman;">			
			<li>올린이 : ${param.writer}</li>
			<li>제목 : ${param.title}</li>
			<li>원본파일명 : ${original}</li>
			<li>실제 서버에 저장된 파일명 : ${real}</li>
			<li>컨텐츠 타입 : ${type}</li>
			<li>파일크기 : ${size}KB</li>			
		</ul>	
	</fieldset>
	
	
</div>
<!-- 실제 내용 끝-->
<!-- 푸터 시작 -->
<jsp:include page="/WEB-INF/views/template/Footer_.jsp"/>
<!-- 푸터 끝 -->

    

