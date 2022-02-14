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
			<legend>파일업로드 폼</legend>
			<a href="<c:url value='/FileUpDown/List.do'/>">파일 목록</a>
			<form id="frm" action="<c:url value='/FileUpDown/Upload.do'/>" 
			      method="post"	
			      enctype="multipart/form-data">
				<table cellspacing="1" bgcolor="gray">
					<tr bgcolor="white">
						<td>올린이</td>
						<td><input type="text" name="writer" value="${param.writer }"/></td>
					</tr>
					<tr bgcolor="white">
						<td>제목</td>
						<td><input type="text" name="title" size="50" value="${param.title}"/></td>
					</tr>
					<tr bgcolor="white">
						<td>첨부파일</td>
						<td><input type="file" name="upload" size="30" /></td>
					</tr>
					<tr bgcolor="white" align="center">
						<td colspan="2"><input type="button" value="업로드" /></td>
					</tr>
				</table>
			</form>
			<span style="color: red; font-size: 1.8em" id="error">${maxError}</span>
		</fieldset>		
	
	
</div>
<!-- 실제 내용 끝-->
<!-- 푸터 시작 -->
<jsp:include page="/WEB-INF/views/template/Footer_.jsp"/>
<!-- 푸터 끝 -->
<script>
	$(function(){		
		$('input[type=button]').click(function(e){
			var file=$('input[type=file]').get(0);
			if(file.files[0].size > 1024*1024){
				$('#error').html('업로드 최대 용량(1MB)을 초과했어요');			
				return;					
			}
			$('#frm').submit();
		});		
	});
</script>
    

