package com.exam.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.controller.member.MemberJoinFormAction;


@WebServlet("*.do")
public class FrontController extends HttpServlet {
	//몇가지 자동생성된 생성자 와 메소드가 있었지만 필요없어서 지움
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		//*프론트 컨트롤러 역할 및 단계
		
		//1) 요청정보(명령어) 추출
		//http://localhost:80/webmvc/MemberJoin.do
		String requestURI = request.getRequestURI();
		System.out.println("요청URL주소:" + requestURI);
		
		//요청URL주소:/webmvc/MemberJoin.do
		
		String contextPath =request.getContextPath();
		System.out.println("contextPath:"+contextPath);
		//contextPath:/webmvc
		
		String command = requestURI.substring(contextPath.length());
		System.out.println("command: " + command);
		//command: /MemberJoin.do

		//2) 요청정보(명령어)에 대흥하는 코드 실행
		
		Action action = null;
		ActionForward forward = null;
		
		if(command.equals("/memberJoinForm.do")){
			//회원가입폼 제공
			
			
			action =new MemberJoinFormAction();
			try {
				
				forward = action.execute(request, response);
			
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
		}else if(command.equals("/memberJoin.do")) {
			//회원 로그인 처리
		}
		
		
		
		//3) 화면정보를 가진 뷰 (jsp)를 선택해서 실행
		//URL주소(sendRedirect 방식 이동.*.do 명령어로 재요청할때) 
		//또는 jsp 파일명(dispatch 방식 이동) 
		
		if(forward != null) {
			//이동정보가 있으면
			//redirect 방식 이동
			if(forward.isRedirect()) {
				response.sendRedirect(forward.getPath());
			}else {
			//dispathch방식 이동
				RequestDispatcher dispatcher = request.getRequestDispatcher(forward.getPath());
				dispatcher.forward(request, response);
				
			}
		}//if
		
		
		
		
	}//doget

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		//post 요청정보 한글처리
		request.setCharacterEncoding("utf-8");
		//doGet() 메소드 호출
		doGet(request, response);
	}//do post

}//front
