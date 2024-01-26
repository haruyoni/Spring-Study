<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

<title>${board.title}</title>
<script>

	$(function(){
		getAllReplies();
		
		
		// 좋아요 버튼 클릭시
		$(".likeBtn").on("click",function(){
			console.log("붐업 클릭")
			if(${sessionScope.loginUser != null}){
				console.log("로그인 ")
				$.ajax({
					url : "like.bo", // 데이터가 송수신될 서버의 주소
					type : "GET", // 통신 방식 (GET, POST, PUT, DELETE)
					data : {
						"boardNo" : "${board.no}",
						"userId" : "${sessionScope.loginUser.userId}"
					},
					dataType : "json", // 수신 받을 데이터 타입 (MINE TYPE)
					async : false, // 동기 통신 방식으로 하곘다. (default : true 비동기)
					success : function(data) {
						console.log(data);
						if(data.status=="success"){
							$(".likeCount").html(data.likeCount);
							if(data.likeStatus == "like"){
								$(".likeBtn img").attr("src", "${pageContext.request.contextPath}/resources/images/like2.png");
							} else if(data.likeStatus == "unlike"){
								$(".likeBtn img").attr("src", "${pageContext.request.contextPath}/resources/images/like1.png");
							}
						}
					},
					error : function() {},
					complete : function() {},
				});
			} else{
				alert("로그인 후에 추천 가능합니다.");
				window.location.href="${pageContext.request.contextPath}/member/login.jsp";
			}
		});
		
		// 모달 아니오 버튼 
		$(".closeModal").click(function(){
			$("#delModal").hide();
		});
		
	});
	
	function getAllReplies(){
		let boardNo = "${board.no}";
		$.ajax({
			url : "/reply/all/"+boardNo, 
			type : "GET", 
			dataType : "json", 
			async : false, 
			success : function(data) {
				console.log(data);
				outputReplies(data);
			},
			error : function() {},
			complete : function() {},
		});
	}
	function outputReplies(json){
		let output = "";
		if(json.length>0){
			output += "<hr>";
		}
		$.each(json, function(i, item){
			output += `<div class="reply">`;
			
			output += `<div class="replyHeader">
							<div><b>\${item.replier}</b></div>
							<div>\${procPostDate(item.postDate)}</div>
						</div>`;
						
			output += `<div class="replyText">\${item.replyText}</div>`;
			
			output += `<div class="replyBtns">
							<button class="btns" type="button" onclick="">수정</button>
							<button class="btns" type="button" onclick="">삭제</button>
						</div>`
						
			output += `</div>`
			output += `<hr>`;
		});
		$("#repliesArea").html(output);
	}
	
	function procPostDate(data){
		let postDate = new Date(data);
		let now = new Date(); // 현재 날짜시간
		console.log(postDate);
		console.log(now);
		
		let diff = (now - postDate) / 1000;
		console.log(diff);
		
		let times = [
			{name:"일", time: 24*60*60},
			{name:"시간", time: 60*60},
			{name:"분", time: 60},
			
		];
		
		for(let val of times){
			let tmp = Math.floor(diff/val.time);
			console.log(tmp, val.name);
		
			console.log(val.time, diff, tmp);
			
			if(tmp > 0){
				if(diff > 24 * 60 * 60){ // 1일 이상 지났다
					return postDate.toLocaleString();
				} else {
					
				}
				
				return tmp + val.name + "전";
			}
		}
		
		return "방금전";
	}
	
	function saveReply(){
		let parentNo = "${board.no}";
		let replier = "hihi";
		let replyText = $("#replyText").val();
		
		let newReply =  {
				"parentNo" : parentNo,
				"replier" : replier,
				"replyText" : replyText
			};
		console.log(JSON.stringify(newReply));
		
		$.ajax({
			url : "/reply/", 
			type : "POST", 
			data : JSON.stringify(newReply),
			headers : {
				// 송신하는 데이터의 Mime-type
				"Content-Type":"application/json",
				
				// PUT, DELETE, PATCH 등의 REST에서 사용되는 http-method가 동작하지 않는
				// 과거의 웹브라우저에서 POST 방식으로 동작하도록 한다.
				"X-HTTP-Method-Override" : "POST"
			},
			dataType : "text", // 수신 받을 데이터 타입 (MIME TYPE)
			success : function(data) {
				console.log(data);
				getAllReplies();
			},
			error : function() {
				alert("error 발생");
			},
			complete : function() {},
		});
	}
	
	
	function showDeleteModal(){
		$("#delModal").show();
	}
	
	function deleteBoard(){
		$.ajax({
			url : "delete.bo", // 데이터가 송수신될 서버의 주소
			type : "GET", // 통신 방식 (GET, POST, PUT, DELETE)
			data : {
				"boardNo" : "${board.no}",
				"writer" : "${board.writer}"
			},
			dataType : "json", // 수신 받을 데이터 타입 (MINE TYPE)
			async : false, // 동기 통신 방식으로 하곘다. (default : true 비동기)
			success : function(data) {
				console.log(data);
				if(data.status=="success"){
					location.href="listAll.bo";
				}
				$("#delModal").hide();
			},
			error : function() {},
			complete : function() {},
		});
	}
</script>
<style>
.reply {
	padding: 10px;
}

.replyHeader {
	display: flex;
	justify-content: space-between;
	margin-bottom: 5px;
}

.replyBtns {
	display: flex;
	justify-content: flex-end;
}

.btns {
	border-radius: 5px;
	border: none;
}

.content {
	margin: 50px;
}

.titleContainer {
	margin-top: 50px;
}

.likeBtn {
	border-radius: 10px;
}

.likeBtnContainer {
	width: 64px;
	margin: auto;
}

.detailContainer {
	margin-top: 10px;
	margin-bottom: 15px;
	color: gray;
}

.detailContainer span {
	margin-right: 20px;
}

.likeCount {
	text-align: center;
}

.writerImg {
	width: 30px;
	border-radius: 30px;
}

.boardContent {
	padding: 10px;
}

.footerBtns{
	display: flex;
	justify-content: space-between;
}
</style>
</head>
<body>
	<jsp:include page="${contextPath}/WEB-INF/views/header.jsp"></jsp:include>
	<div class="container content">
		<div class="titleContainer">
			<span><h4>${board.title}</h1>
					<span></span>
		</div>
		<div class="detailContainer">
			<span><img class="writerImg"
				src="${pageContext.request.contextPath}/${board.writerImg}" />
				${board.writer}</span> <span><img style="filter: opacity(0.5);"
				src="${pageContext.request.contextPath}/resources/images/clock.png">
				<fmt:formatDate value="${board.postDate}" pattern="yy.MM.dd" /></span> <span><img
				style="filter: opacity(0.5);"
				src="${pageContext.request.contextPath}/resources/images/view.png">
				${board.readCount}</span> <span><img style="filter: opacity(0.7);"
				src="${pageContext.request.contextPath}/resources/images/thumbs-up.png">
				<span class="likeCount">${board.likeCount}</span></span>

			<button class="btns" type="button"
				onclick="location.href='replyBoard.jsp?ref=${board.ref}&step=${board.step}&reforder=${board.reforder}'">답글쓰기</button>

			<%-- 			<c:if test="${board.writer == sessionScope.loginUser.userId}"> --%>
			<c:url value="board.bo" var="detailUri">
				<c:param name="boardNo" value="${board.no}" />
				<c:param name="page" value="edit" />
			</c:url>
			<button class="btns" type="button"
				onclick="location.href='${detailUri}'">수정</button>
			<button class="btns" type="button" onclick="showDeleteModal();">삭제</button>
			<%-- 			</c:if> --%>
		</div>
		<hr>
		<div class="boardContent">${board.content}</div>
		<c:if test="${upFileList!=null}">
			<c:forEach var="file" items="${upFileList}"> 
			<div>
					<c:choose>
						<c:when test="${file.thumbFileName!=null }">
							<img style="width: 500px;"
								src="${pageContext.request.contextPath}/resources/uploads/${file.newFileName}" />
						</c:when>
						<c:otherwise>
							<a
								href="${pageContext.request.contextPath}/resources/uploads/${file.newFileName}">${file.originFileName}</a>
						</c:otherwise>
					</c:choose>
				</div>
			</c:forEach>


		</c:if>
		<hr>

		<div class="likeBtnContainer">
			<button class="likeBtn" onclick="">
				<c:choose>
					<c:when test="${!board.likeExist}">
						<img
							src="${pageContext.request.contextPath}/resources/images/like1.png">
					</c:when>
					<c:otherwise>
						<img
							src="${pageContext.request.contextPath}/resources/images/like2.png">
					</c:otherwise>
				</c:choose>

				<div class="likeCount">${board.likeCount}</div>
			</button>
		</div>

		<hr>


		<div class="replyDiv">
			<label>댓글</label>
			<div class="allReplies mb-3 mt-3" id="repliesArea"></div>
			<div class="replyInput mb-3 mt-3">
				<textarea class="form-control" rows="5" id="replyText"
					style="width: 100%"></textarea>
				<div class=replyBtns mb-3 mt-3>
					<button class="btns" type="button"
						style="height: 50px; width: 100%" onclick="saveReply();">댓글 등록</button>
				</div>
			</div>
		</div>


		<div class="footerBtns">
			<button class="btns" type="button" onclick="location.href='listAll'">
				<img src="${pageContext.request.contextPath}/resources/images/list.png">목록
			</button>
			
			<button class="btns" type="button" onclick="location.href='#'">
				<img src="${pageContext.request.contextPath}/resources/images/up-arrows.png">맨위로
			</button>
		</div>
	</div>




	<!-- 삭제 Modal -->
	<div class="modal" id="delModal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content">
				<div class="modal-body">정말 삭제하시겠습니까?</div>

				<!-- Modal footer -->
				<div class="modal-footer">
					<button type="button" class="btn btn-primary closeModal"
						data-bs-dismiss="modal">아니오</button>
					<button type="button" class="btn btn-danger delBtn"
						data-bs-dismiss="modal" onclick="deleteBoard();">예</button>
				</div>

			</div>
		</div>
	</div>


	<jsp:include page="../footer.jsp"></jsp:include>

</body>
</html>