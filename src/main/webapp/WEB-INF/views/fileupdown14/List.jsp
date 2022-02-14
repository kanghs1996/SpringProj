<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!-- 상단메뉴 시작 -->	
<jsp:include page="/WEB-INF/views/template/Top_.jsp"/>
<!-- 상단메뉴 끝 -->

<!-- 실제 내용 시작 -->
<div class="container">
	
	<div class="page-header">
		<h1>스프링 <small>파일 업로드/다운로드</small></h1>
	</div>
	<fieldset>
		<legend>업로드된 파일목록</legend>
		<h2>컬렉션일때</h2>
		<ul style="list-style:decimal">
			<c:forEach var="map" items="${list}">
			
				<li><a href="<c:url value="/FileUpDown/Download.do?filename=${map.name}"/>">파일명 : ${map.name} ,파일 크기 : ${map.size }KB</a></li>
			</c:forEach>
		</ul>
		<h2>File[]일때</h2>	
		<ul style="list-style:decimal">
			<c:forEach var="file" items="${files}">
				<li>파일명 : ${file.name} ,파일 크기 : ${fn:substringBefore(Math.ceil(file.length()/1024),".")}KB</li>
			</c:forEach>
		</ul>
	</fieldset>
	
	
</div>
<!-- 실제 내용 끝-->
<!-- 푸터 시작 -->
<jsp:include page="/WEB-INF/views/template/Footer_.jsp"/>
<!-- 푸터 끝 -->

    

