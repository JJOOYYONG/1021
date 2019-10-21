<%@page import="com.exam.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function useID(){
	//현재창을 생성한 부모창 참조
	//window.opener
	opener.document.frm.id.value=wfrm.userid.value;
	//현재 창 닫기 window.close();
	close();
	
}
</script>





</head>
<body>
<%
	//"userid" 중복확인할 아이디 파라미터값 가져오기
	String userid = request.getParameter("userid");
	//DAO객체 준비
	MemberDao memberDao = MemberDao.getInstance();
	//아이디중복확인메소드
	boolean isIdDup = memberDao.isIdDuplicated(userid);
	if(isIdDup){//isIdDup==true 중복
		
			%>
		아이디중복,사용중인 아이디입니다.

			<%
	}else{ //isIdDup==false 아이디중복 아님
		%>
		
		사용가능한 아이디입니다.<br>
		<button type="button" onclick="useID();">사용</button>
		<%
	}
%>

<form action="joinIdDupCheck.jsp" method="get" name="wfrm">
<input type="text" name="userid" value="<%=userid%>">
<button type="submit">중복확인</button>
</form>


</body>
</html>