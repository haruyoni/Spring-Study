<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인</title>
</head>
<body>
	<jsp:include page="${contextPath}/WEB-INF/views/header.jsp"></jsp:include>
	<div class="container">
		<h1>로그인</h1>
		<form action="login" method="post">
			<div class="mb-3 mt-3">
				<label for="userId" class="form-label">아이디</label> <input
					type="text" class="form-control" id="userId"
					placeholder="아이디를 입력해주세요" name="userId">
			</div>

			<div class="mb-3">
				<label for="userPwd" class="form-label">비밀번호</label> <input
					type="password" class="form-control" id="userPwd"
					placeholder="비밀번호를 입력해주세요" name="userPwd">
			</div>

			<button type="submit" class="btn btn-secondary">로그인</button>

		</form>


	</div>
	<jsp:include page="${contextPath}/WEB-INF/views/footer.jsp"></jsp:include>
</body>
</html>