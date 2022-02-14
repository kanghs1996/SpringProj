<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<!-- 상단메뉴 시작 -->	
<jsp:include page="/WEB-INF/views/template/Top_.jsp"/>
<!-- 상단메뉴 끝 -->

<!-- 실제 내용 시작 -->
<div class="container">
	
	<div class="page-header">
		<h1>스프링 <small>예외처리</small></h1>
	</div>
	<fieldset>
			<legend>예외처리 ${message}</legend>
			<form method="post" action="<c:url value='/Exception/Exception.do'/>">
				나이를 입력하세요?
				<input type="text" name="years"/>
				<input type="submit" value="확인"/>
			</form>
			${errorMsg }
		</fieldset>
	
	
</div>
<!-- 실제 내용 끝-->
<!-- 푸터 시작 -->
<jsp:include page="/WEB-INF/views/template/Footer_.jsp"/>
<!-- 푸터 끝 -->

    

