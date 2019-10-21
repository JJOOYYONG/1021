<%@page import="com.exam.dao.MemberDao"%>
<%@ page language="java" contentType="application/json; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
     //DAO객체 준비
	MemberDao memberDao = MemberDao.getInstance();
	//아이디중복확인메소드
	boolean isIdDup = memberDao.isIdDuplicated(id);

	//json응답 가로형
	//객체일 경우[]리스트 {}맵
    //기본자료형일 경우 : 숫자, 불리언, 문자열 
    
    
%>

	<%=isIdDup%>