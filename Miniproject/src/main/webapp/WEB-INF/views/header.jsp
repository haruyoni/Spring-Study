<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	rel="stylesheet">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script> -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Diphylleia&display=swap" rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">
<meta charset="UTF-8">
<title>Insert title here</title>
<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>
<link rel="stylesheet" href="${contextPath}/resources/style.css" type="text/css" />
<script>
	
</script>
<style>


.userImg{
	width : 24px;
	height : 24px;
	border-radius : 24px;
}
.navbar-nav{
	align-items : center;
	display : flex;
	margin : 0 auto;
	flex-direction : row;
	justify-content : center;
}
</style>
</head>
<body>
	<div class="p-5 text-black text-center homebar">
		<a class="nav-link" href="/"><h1>짱존사</h1></a>
		<p style="color:rgb(245, 192, 194);">짱이는 존재자체가 사랑스럽다</p>
	

	<nav class="navbar navbar-expand-sm navbar-light">
		<div class="container-fluid">
			<ul class="navbar-nav">
				<li class="nav-item"><a class="nav-link active"
					href="/">홈</a></li>
				<li class="nav-item"><a class="nav-link" href="/board/listAll">게시판</a></li>

				<c:choose>
					<c:when test="${sessionScope.loginUser == null}">
						<li class="nav-item">
							<a class="nav-link" href="${contextPath}/member/register">회원가입</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="${contextPath}/member/login">로그인</a>
						</li>
					</c:when>
					<c:otherwise>
						<li class="nav-item">
							<a class="nav-link" href="${contextPath}/member/mypage">${sessionScope.loginUser.userId}
								<img src="${contextPath}/${sessionScope.loginUser.memberImg}" class="userImg">
							</a>
						</li>
						<li class="nav-item">
							<a class="nav-link" href="${contextPath}/member/logout">로그아웃</a>
						</li>
					</c:otherwise>
				</c:choose>
				<c:if test="${sessionScope.loginUser.isAdmin =='Y'}">
					<li class="nav-item">
						<a class="nav-link" href="${contextPath}/admin/admin.jsp">관리자 페이지</a>
					</li>
				</c:if>

			</ul>
		</div>
	</nav>
	</div>
</body>
</html>