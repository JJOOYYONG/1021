<%@page import="com.exam.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	
	<%--세션값 가져오기 --%>
	<%String id = (String) session.getAttribute("id"); %>
	<%
	//post 파라미터값 한글처리
	request.setCharacterEncoding("utf-8");
	
	//파라미터값 가져오기
	String pageNum = request.getParameter("pageNum");
	int num = Integer.parseInt(request.getParameter("num"));
	//passwd 파라미터값은 로그인 사용자일 경우는 null
	String passwd = request.getParameter("passwd");
	//DAO객체 준비
	BoardDao boardDao=BoardDao.getInstance();
	
	
	
	//로그인 안한 사용자는 입력한 패스워드와 글패스워드를 비교하여
	//같을때는 삭제처리
	//다를때는"글 패스워드가 다릅니다.", 뒤로가기
	
	if(id==null){//로그인 안한 사용자
		if(!boardDao.isPasswdEqual(num, passwd)){
			%>
			
			<Script>
			alert('글패스워드가 다릅니다.');
			history.back();
			</Script>
			
			<%
			return;
		}//if2
		
	}
	
	//게시글 삭제하기 메소드 호출
	//[로그인한 사용자] 또는 [로그인안한 사용자인데 패스워드 일치한자]
	boardDao.deleteBoard(num);
	
	//삭제처리 후 글 목록 notice.jsp로 이동
	response.sendRedirect("notice.jsp?pageNum="+pageNum);
	
	%>
	<script>
	//alert('글 삭제되었습니다!');
	//location.href='notice.jsp?pageNum=<%=pageNum%>';
	</script>
	
  