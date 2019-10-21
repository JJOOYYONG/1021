<%@page import="com.exam.dao.MemberDao"%>
<%@page import="com.exam.vo.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% //포스트값 한글처리
	request.setCharacterEncoding("utf-8");

	//파라미터 값 가져오기(id/passwd/rememberMe)
	String id = request.getParameter("id");
	String passwd = request.getParameter("passwd");
	//체크박스, 라디오 박스처럼 선택하지 않으면 null을 리턴함
	String rememberMe = request.getParameter("rememberMe");
	
	
	
	//DAO객체 준비
	MemberDao memberDao = MemberDao.getInstance();
	//사용자 확인 메소드 호출
	int check = memberDao.userCheck(id,passwd);
	//chek==1이면 로그인 인증(세션값생성 id). index.jsp로 이동)
	//chek==0이면 패스워드가 틀림뒤로 이동
	//chek==-1이면 아이디없음 뒤로 이동

	if(check==1){
		//로그인 인증
		session.setAttribute("id", id);
		
		//로그인 상태유지 여부 확인
		if(rememberMe !=null && rememberMe.equals("true")){
			//쿠키객체 생성해서 응답시 보내기
			Cookie cookie = new Cookie("id",id);
			cookie.setMaxAge(60*10);//초단위 10분 = 60*10 = 600초
			
			cookie.setPath("/"); // 쿠키 최상위 경로 강제 설정
			
			response.addCookie(cookie); // 응답객체에 추가		
		}
		
		//index.jsp로 이동
		response.sendRedirect("../index.jsp");
		
		
		
	}else if(check==0){
		%>
		<script>
		
		alert('패스워드가 틀립니다');
		history.back();
		
		</script>
		
		<%
		
	}else{
		//check==-1일때
	%>
	<script>
	
	alert('존재하지않는 아이디 입니다.');
	history.back();
	
	</script>
	
	<% }

%>

