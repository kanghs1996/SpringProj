<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->
<title>List.jsp</title>
<!-- 부트스트랩 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">

<!-- IE8 에서 HTML5 요소와 미디어 쿼리를 위한 HTML5 shim 와 Respond.js -->
<!-- WARNING: Respond.js 는 당신이 file:// 을 통해 페이지를 볼 때는 동작하지 않습니다. -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	<style>
		body{padding-top:60px}
	</style>
	<!-- jQuery (부트스트랩의 자바스크립트 플러그인을 위해 필요합니다) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
	
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<script>
		//모든 NodeList의 요소의 class속성 제거하는 함수
		function allClear(nodes) {
			nodes.forEach(function(node) {
				if (node.getAttribute('class') === 'active')//dropup 및 divider적용키 위함(다른 class는 지우면 안됨)
					node.removeAttribute('class');
			});
		}
	
		var gnb = document.querySelectorAll("#collapse-menu > ul > li");
		//각 NodeList의 요소에 click이벤트 설정
		gnb.forEach(function(nb) {
			nb.onclick = function() { //특정 li(요소) 클릭시   			
				allClear(gnb);//모든 class속성 제거
				if (!this.getAttribute('class'))//클릭한 해당 li에만 class속성 추가
					this.setAttribute('class', 'active');
	
			};
		});
		
		function logout(){
			$('#logoutForm').submit();//스프링 씨큐리티 사용- csrf를 활성화 시킬때
		}
	</script>
</head>
<body>
	<!-- 로그아웃 GET방식에서 POST방식으로 변경하기 위한 폼태그 추가 -->
	<form id="logoutForm" method="post" action="<c:url value="/onememo/auth/Logout.do"/>">
			<!-- 씨큐리티 적용:csrf취약점 방어용 -->
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	
	</form>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<!--화면 크기가 작을때 보여지는 네비게이션바(모바일용)  -->
			<div class="navbar-header">
				<button class="navbar-toggle collapsed" data-toggle="collapse"
					data-target="#collapse-menu">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<!-- CI표시 -->
				
				<a class="navbar-brand" href="<c:url value="/"/>">
					<span class="glyphicon glyphicon-education"></span>
					<strong>KOSMO</strong>
				</a>
			</div>
	
			<!-- 화면 크기가 클때 상단에 보여지는 네비게이션바(데스크탑용) -->
			<div class="collapse navbar-collapse" id="collapse-menu">
				<!-- 네비게이션바에 폼 추가 -->
				<form class="navbar-form navbar-right">
					<div class="form-group">
						<input type="text" class="form-control" placeholder="검색">
					</div>
					<input type="button" class="btn btn-info" value="확인" onclick="alert('검색어를 입력하세요')"/>
				</form>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="<c:url value="/"/>">HOME</a></li>
					<!-- 스프링 씨큐러티 사용하지 않을때 -->
					<%-- 
					<c:if test="${empty sessionScope.id }" var="isLogin">
						<li><a href="<c:url value="/onememo/auth/Login.do"/>">로그인</a></li>
					</c:if>
					<c:if test="${not isLogin }">
						<li><a href="<c:url value="/onememo/auth/Logout.do"/>">로그아웃</a></li>
					</c:if>
					--%>
					<!-- 
				     스프링 씨큐리티 사용시 :단,<security:csrf disabled="false" />
				     설정시 로그아웃을 GET방식이 아닌 POST방식으로 해야한다
				     true로 지정시에는 GET방식이어도 상관없다(CSRF공격은 막지 못한다)
				     -->
				     <sec:authorize access="isAuthenticated()">
				     	<!--  
				     	<li><a href="<c:url value="/onememo/auth/Logout.do"/>">로그아웃</a></li>
				     	-->
				     	<li><a href="javascript:logout()">로그아웃</a></li>
				     </sec:authorize>
				     <sec:authorize access="isAnonymous()">
				     	<li><a href="<c:url value="/onememo/auth/Login.do"/>">로그인</a></li>
				     </sec:authorize>
					
					<li><a href="<c:url value="/onememo/bbs/List.do"/>">한줄 댓글 게시판</a></li>
					<li><a href="#">공지사항</a></li>
				</ul>
			</div>
			<!-- /.navbar-collapse -->
		</div>
		<!-- /.container-fluid -->
	</nav>