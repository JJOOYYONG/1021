package com.exam.controller.member;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.exam.controller.Action;
import com.exam.controller.ActionForward;

public class MemberJoinFormAction implements Action {
	//생성할때 add 에서 해당 action.java를 상속받음
	
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		//3단계에서 사용할 이동정보 객체 준비하고 리턴
		ActionForward forward = new ActionForward();
		forward.setPath("./WEB-INF/views/member/join.jsp");
		forward.setRedirect(false);// dispatch 방식 이동 : 해당경로 jsp를 바로 실행
		
		
		return forward;
	}

}
