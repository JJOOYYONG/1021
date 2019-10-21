<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//쿠키삭제하기(로그인상태유지용)
	Cookie[] cookies = request.getCookies();
	if (cookies != null) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("id")) {
				//유효기간 0으로 설정 ->브라우저 해당쿠키 삭제
				cookie.setMaxAge(0);
				//삭제할 쿠키경로도 동일해야함
				cookie.setPath("/");
				response.addCookie(cookie);
			} //if
		} //for
	} //if

	// 세션값 초기화
	session.invalidate();
	// "로그아웃됨"  index.jsp로 이동
%>  
<script>
	alert('로그아웃 되었습니다.');
	location.href = '../index.jsp';
</script>