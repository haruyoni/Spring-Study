<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<meta charset="UTF-8">

<c:set var="now" value="<%=new java.util.Date()%>" />
<title>게시판</title>
<script>
window.addEventListener('pageshow', function(event) {
	  if(event.persisted || (window.performance && window.performance.navigation.type == 2)) {
	      // 웹 페이지가 캐시에서 로드된 경우 처리할 내용 작성
	      location.reload();
	  }
	});
	$(function(){
		$(".isNewDpTarget").each((idx, item) => {
			if(getHowLong($(item).next().next().text())<5){
				item.innerHTML = `<span class="badge bg-danger">New</span>`+ " " +item.innerHTML
			}
		});
		
		$(".board").each(function(){
			console.log($(this).children().eq(3).html());
			let curDate = new Date();
	         let postDate = new Date($(this).children().eq(3).html());
	         let diff = (curDate - postDate) / 1000 / 60 / 60; // 시간단위
	         
	         console.log(diff);
	         
	         let title = $(this).children().eq(1).html();
	         
	         if(diff <5){
	            let output = "<span><img src='${pageContext.request.contextPath}/resources/images/new.png'></span>";
	            $(this).children().eq(1).html(title + output);
	         }

		});
	});
	
	function searchValid(){
		let sw = $("#searchWord").val();
		if(sw.length==0){
			alert("검색어를 입력하세요");
			return false;
		}
		
		let expText = /[%=><]/; // 데이터베이스에서 조건 연산자
		if(expText.test(sw) == true) {
			alert("특수문자를 입력할 수 없습니다.");
			return false;
		}
		
		let sql = new Array("or", "select", "insert", "update", "delete", "create", 
				"alter", "drop", "exec", "union", "fetch", "declare", "truncate");
		for(let i=0; i<sql.length; i++){
			regEx = new RegExp(sql[i], "gi");
			
			if(regEx.test(sw)==true){
				alert("특정 문자로 검색할 수 없습니다.");
				return false;
			}
		}
		
	}
</script>
<style>
input:focus {
	outline: none !important;
}

.pagination { -
	-bs-pagination-focus-box-shadow: none !important; -
	-bs-pagination-border-color: none !important; -
	-bs-pagination-disabled-bg: none !important; -
	-bs-pagination-disabled-color: gray !important; -
	-bs-pagination-color: black !important;
	-bs-pagination-hover-color: none !important;
}

.page-item {
	margin-left: 6px;
	margin-right: 6px;
}

.page-link {
	border-radius: 5px;
	border:none !important;
 	color: gray !important; 
}

.active>.page-link, .page-link.active {
	background-color: rgb(245, 192, 194) !important;
	border-color: rgb(245, 192, 194) !important;
	color: white !important; 
}


.pageNav {
	width: 450px;
}

.boardFooter {
	display: flex;
	justify-content: space-between;
}
.searchArea {
	display: flex;
	justify-content: flex-end;
}

.btns {
	width: 350px;
}
</style>
</head>
<body>
	<c:set var="contextPath" value="<%=request.getContextPath()%>"></c:set>
	<jsp:include page="${contextPath}/WEB-INF/views/header.jsp"></jsp:include>
	<div class="container">
		<div class="boardList">
			<c:choose>
				<c:when test="${boardList != null}">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>글번호</th>
								<th>제목</th>
								<th>작성자</th>
								<th>작성일</th>
								<th>조회수</th>
								<th>좋아요</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="board" items="${boardList}">
								<%-- 								<c:url value="viewBoard" var="detailUri"> --%>
								<%-- 									<c:param name="boardNo" value="${board.no}" /> --%>
								<%-- 									<c:param name="page" value="detail" /> --%>
								<%-- 								</c:url> --%>
								<tr class="board"
									onclick="location.href='viewBoard?no=${board.no}';">
									<td>${board.no}</td>
									<td><c:if test="${board.step > 0 }">
											<c:forEach var="i" begin="1" end="${board.step}"
												varStatus="status">
												<c:if test="${status.last}">
													<!-- 답글 이미지 여백 -->
													<img width="15" alt=""
														src="${pageContext.request.contextPath}/resources/images/reply.png"
														style="margin-left: calc(15px * ${i})">
												</c:if>
											</c:forEach>
										</c:if> ${board.title}</td>
									<td>${board.writer}</td>
									<td><fmt:formatDate value="${board.postDate}"
											pattern="yy.MM.dd hh:mm" /></td>
									<td>${board.readCount}</td>
									<td>${board.likeCount}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:when>
				<c:otherwise>
					게시글이 없습니다.
				</c:otherwise>
			</c:choose>
		</div>

		<div class="boardFooter">
			<div class="btns mb-3 mt-3">
				<button class="btn btn-secondary btn-sm"
					onclick="location.href='writeBoard'">글쓰기</button>
			</div>
			<!-- 		<div> -->
			<%-- 			${pagingInfo} --%>
			<!-- 		</div> -->
			<c:if test="${pagingInfo.totalPagingBlockCnt > 0 }">
	
				<div class="mb-3 mt-3 pageNav">
					<nav aria-label="Page navigation example">
						<ul class="pagination justify-content-center pagination-sm">

							<!-- 이전 블락 버튼 -->
							<c:if test="${pagingInfo.pageBlockOfCurrentPage > 1}">
								<li class="page-item"><a class="page-link"
									href="listAll?pageNo=${pagingInfo.startNumOfCurrentPagingBlock - 1}&searchType=${param.searchType}&searchWord=${param.searchWord}">&lt;&lt;</a></li>
							</c:if>
							<%-- 			<c:if test="${pagingInfo.pageBlockOfCurrentPage > 1}"> --%>
							<%-- 				<li class="page-item"><a class="page-link" href="listAll.bo?pageNo=${pagingInfo.startNumOfCurrentPagingBlock - 1}">&lt;&lt;</a></li>	 --%>
							<%-- 			</c:if> --%>

							<!-- 이전 버튼 -->
							<c:if test="${param.pageNo != null && param.pageNo > 1}">
								<li class="page-item"><a class="page-link"
									href="listAll?pageNo=${param.pageNo - 1}&searchType=${param.searchType}&searchWord=${param.searchWord}">&lt;</a></li>
							</c:if>

							<!-- 페이지 블럭 -->
							<c:forEach var="i"
								begin="${requestScope.pagingInfo.startNumOfCurrentPagingBlock }"
								end="${requestScope.pagingInfo.endNumOfCurrentPagingBlock}">
								<c:choose>
									<c:when test="${requestScope.pagingInfo.pageNo == i }">
										<li class="page-item active">
									</c:when>
									<c:otherwise>
										<li class="page-item">
									</c:otherwise>
								</c:choose>
								<a class="page-link"
									href="listAll?pageNo=${i}&searchType=${param.searchType}&searchWord=${param.searchWord}"
									tabindex="-1">${i}</a>
								</li>
							</c:forEach>

							<!-- 다음 버튼 -->
							<c:if test="${pagingInfo.totalPageCnt>1}">
								<c:if test="${param.pageNo==null}">
									<li class="page-item"><a class="page-link"
										href="list.bo?pageNo=${2}&searchType=${param.searchType}&searchWord=${param.searchWord}">&gt;</a></li>
								</c:if>
								<c:if test="${param.pageNo < pagingInfo.totalPageCnt}">
									<li class="page-item"><a class="page-link"
										href="list.bo?pageNo=${param.pageNo + 1}&searchType=${param.searchType}&searchWord=${param.searchWord}">&gt;</a></li>
								</c:if>
							</c:if>

							<!-- 다음 블락 버튼 -->
							<c:if
								test="${pagingInfo.pageBlockOfCurrentPage < pagingInfo.totalPagingBlockCnt}">
								<li class="page-item"><a class="page-link"
									href="list.bo?pageNo=${pagingInfo.endNumOfCurrentPagingBlock + 1}&searchType=${param.searchType}&searchWord=${param.searchWord}">&gt;&gt;</a></li>
							</c:if>
							<%-- 			<c:if test="${pagingInfo.pageBlockOfCurrentPage < pagingInfo.totalPagingBlockCnt}"> --%>
							<%-- 				<li class="page-item"><a class="page-link" href="listAll.bo?pageNo=${pagingInfo.endNumOfCurrentPagingBlock + 1}">&gt;&gt;</a></li>	 --%>
							<%-- 			</c:if> --%>

						</ul>
					</nav>
				</div>
				<!-- 검색 타입(작성자, 제목, 본문)과 검색어 입력 -->
				<div class="mb-3 mt-3 searchItem">
					<form action="listAll" class="searchArea">
						<div style="width: 100px;">
							<select class="form-select" name="searchType">
								<option value="writer">작성자</option>
								<option value="title">제목</option>
								<option value="content">본문</option>
							</select>
						</div>
						<div>
							<div class="input-group mb-3" style="width: 250px;">
								<input type="text" class="form-control" placeholder="검색하세요."
									name="searchWord" id="searchWord">
								<button class="btn btn-secondary" type="submit" onclick="searchValid()">검색</button>

							</div>
					</form>

				</div>
		</div>
		</c:if>
	</div>
	<jsp:include page="../footer.jsp"></jsp:include>
</body>
</html>