package com.exam.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.exam.vo.MemberVO;

public class MemberDao {

	private static final MemberDao instance = new MemberDao();

	public static MemberDao getInstance() {
		return instance;
	}
	
	private MemberDao() {
	}

	
	public boolean isIdDuplicated(String id) {
		//중복이면 true, 아니면 false
		boolean isIdDuplicated = false;
		
		int count =0;//id값이 일치하는 행의개수
		Connection con = null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		String sql ="";
		
		try {
			con=DBManager.getConnection();
			sql="SELECT COUNT(*) AS cnt FROM jspdb.member WHERE id = ?";
			pstmt= con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs=pstmt.executeQuery();
			rs.next();
			count=rs.getInt(1);
			if(count==1) {
				isIdDuplicated = true;//중복이다
			}else {
				isIdDuplicated = false;//중복이아니다
			}	
				
				
		
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt, rs);
		}
		return isIdDuplicated;
	} //0827_아이디중복체크
	
	
	
	
	
	
	
	
	public int insertMember(MemberVO vo) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		try {
			con = DBManager.getConnection();
			// 3단계: sql문 준비
			String sql = "INSERT INTO jspdb.member (id,passwd,name,email,address,tel,mtel,reg_date)";
			sql += " VALUES (?,?,?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPasswd());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getAddress());
			pstmt.setString(6, vo.getTel());
			pstmt.setString(7, vo.getMtel());
			pstmt.setTimestamp(8, vo.getRegDate());
			// 4단계: sql문 실행
			rowCount = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, pstmt);
		}
		return rowCount;
	} // insertMember method
	
	
	public MemberVO getMember(String id) {
		MemberVO memberVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			con = DBManager.getConnection();
			sql = "SELECT * FROM jspdb.member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			//4단계: sql문 실행
			rs = pstmt.executeQuery();
			//5단계: rs 데이터 사용
			if (rs.next()) {
				memberVO = new MemberVO();

				memberVO.setId(rs.getString("id"));
				memberVO.setPasswd(rs.getString("passwd"));
				memberVO.setName(rs.getString("name"));
				
				// rs.getInt("age");  숫자값이 null이 아니고 항상 존재할때.
				
				memberVO.setEmail(rs.getString("email"));
				memberVO.setRegDate(rs.getTimestamp("reg_date"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, pstmt, rs);
		}
		return memberVO;
	} // getMember method
	
	
	public int userCheck(String id, String passwd) {
		int check = -1; // -1 아이디 불일치. 0 패스워드 불일치. 1 일치함

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = DBManager.getConnection();
			
			//3단계: sql문 준비
			String sql = "SELECT passwd FROM jspdb.member WHERE id = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			//4단계: sql문 실행
			rs = pstmt.executeQuery();
			//5단계: rs 데이터 사용

			if (rs.next()) { 
				// 아이디 있음
				if (passwd.equals(rs.getString("passwd"))) {
					check = 1;  // 아이디,패스워드 일치
				} else {
					check = 0; // 패스워드 불일치
				}
			} else { // 아이디 없음
				check = -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, pstmt, rs);
		}
		return check;
	} // userCheck method
	
	
	// 전체회원정보 가져오기
	public List<MemberVO> getMembers() {
		List<MemberVO> list = new ArrayList<MemberVO>();
		
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "";
		try {
			//1단계: DB 드라이버 로딩
			//2단계: DB연결
			con = DBManager.getConnection();
			//3단계: sql문 준비
			sql = "SELECT * FROM jspdb.member ORDER BY id ASC";
			stmt = con.createStatement();
			//4단계: sql문 실행 -> rs 생성
			rs = stmt.executeQuery(sql);
			//5단계: while문  rs.next() 다음행이 있으면
			//       자바빈객체 MemberVO 생성 <- rs 행1개 저장
			//       자바빈 한개 -> 배열리스트에 추가
			while (rs.next()) {
				MemberVO memberVO = new MemberVO();
				memberVO.setId(rs.getString("id"));
				memberVO.setPasswd(rs.getString("passwd"));
				memberVO.setName(rs.getString("name"));
				
				// rs.getInt("age");  숫자값이 null이 아니고 항상 존재할때.
			
				memberVO.setEmail(rs.getString("email"));
				memberVO.setRegDate(rs.getTimestamp("reg_date"));
				
				list.add(memberVO);
			} // while
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, stmt, rs);
		}
		return list;
	} // getMembers method
	
	
	// 회원정보 수정하기 메소드
	// 매개변수 memberVO에 passwd필드는 수정의 대상이 아니라
	// 본인 확인 용도로 사용
	public int updateMember(MemberVO memberVO) {
		int rowCount = 0;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		String sql = "";
		
		try {
			//1단계: DB 드라이버 로딩
			//2단계: DB연결
			con = DBManager.getConnection();
			//3단계: sql문 준비
			sql = "UPDATE jspdb.member SET name=?, age=?, gender=?, email=? WHERE id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, memberVO.getName());
			
			
			pstmt.setString(4, memberVO.getEmail());
			pstmt.setString(5, memberVO.getId());
			// 실행
			rowCount = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBManager.close(con, pstmt);
		}
		return rowCount;
	} // updateMember method
	
	
	
	//회원정보 삭제
	public int deleteMember(String id) {
		int rowCount = 0; // 삭제된 행의 갯수
		//jdbc 참조변수 준비
		Connection con = null;
		PreparedStatement pstmt = null;
		String sql="";
		
		try {
		con = DBManager.getConnection();
		sql="DELETE FROM jspdb.member WHERE id =?";
		pstmt=con.prepareStatement(sql);
		pstmt.setString(1, id);
		
		rowCount=pstmt.executeUpdate();
		
		
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(con, pstmt);
		}
		return rowCount;
	}// deletemember
	
	

} // class MemberDao



