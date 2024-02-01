<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
input:focus{
 	outline : none !important; 
}
textarea{
	outline : none;
	border : var(--bs-border-width) solid var(--bs-border-color);
	border-radius: var(--bs-border-radius);
}
</style>
</head>
<body>
<jsp:include page="${contextPath}/WEB-INF/views/header.jsp"></jsp:include>
	<div class="container content">
	<form action="editBoard.bo" method="post" enctype="multipart/form-data">
			<input type="hidden" name="no" value="${boardDetail.no}">
	
			<div class="mb-3 mt-3">
				<label for="writer" class="form-label">작성자</label> 
				<input type="text" class="form-control-plaintext" id="writer" name="writer" value="${sessionScope.loginUser.userId}" readonly>
			</div>

			<div class="mb-3 mt-3">
				<label for="title" class="form-label">제목</label> <input
					type="text" class="form-control shadow-none" id="title" name="title" value="${boardDetail.title}">
			</div>	
			
			<div class="mb-3 mt-3">
				<label for="content" class="form-label">내용</label>
				<textarea rows="20" style="width:100%" id="content" name="content">${boardDetail.content}</textarea>
			</div>

			
			<div class="mb-3 mt-3">
				<label for="upFile" class="form-label">첨부 이미지</label> 
				<c:if test="${boardDetail.upFileName!=null}">
				<div>
					<img style="width: 300px;"
						src="${pageContext.request.contextPath}/${boardDetail.upFileName}" />
				</div>
			</c:if>
				<input type="file" class="form-control" id="upFile" name="upFile">
			</div>

			<div>
			<button type="submit" class="btn btn-primary" style="text-align: center;">저장</button>	
			</div>

		</form>

	</div>
	
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>