package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataAccessObject {

    Connection conn;
    Statement stmt;
    ResultSet rs ;
    // 2. 연결 문자열 생성
    // -접속에 필요한 정보로 구성된 문자열, Connection String
    String url = "jdbc:oracle:thin:@192.168.0.137:1521:xe"; //localhost대신 ip주소가 들어갈수도
    String id = "TEST1";
    String pw = "3333";

	public boolean executeUpdate(String[] query) {           // 회원 가입 insert, update, delete  
		boolean result = false;


	    try {
	        // 3. JDBC 드라이버 로딩
	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        // 4. 접속
	        // - Connection 객체 생성 + 접속 작업.
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	        
	        for(int idx = 0; idx < query.length; idx++) {
	        	stmt.executeUpdate(query[idx]);
	        }

	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        result = true;
	    } catch (Exception e) {
	        e.printStackTrace();
	    	System.out.println("에러 발생  , ,  회원 가입");
	    }
	    
	    return result;
	}
	
	public ResultSet connect(String query) {           // 연결해서 쿼리문 실행후 값 반환 


	    // DB작업 > 외부 입출력 > try-catch 필수

	    try {
	        // 3. JDBC 드라이버 로딩
	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        // 4. 접속
	        // - Connection 객체 생성 + 접속 작업.
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	        
	        rs = stmt.executeQuery(query);
	        

	    } catch (Exception e) {
	        e.printStackTrace();
	    	System.out.println("에러 발생  , ,  DataAccessObject.java  connect");
	    }
	    
	    return rs;
	}
	
	public void close() {           // 연결 끊기

	    try {
	        rs.close();
	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    	System.out.println("에러 발생  , ,  DataAccessObject.java    close ");
	    }
	}
	
	/* 관리자 로그인 */
	public boolean mgrLogin(String id, String pw) {
		boolean check = false;
		String sql = null;
		try {
			// JDBC드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.err.println("드라이버를 정상적으로 로드했습니다.");
		} catch (ClassNotFoundException e) {
			System.err.println("드라이버 로드에 실패했습니다.");
		}
		try {
			// 오라클 db에 연결 & 드라이버 연결
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@192.168.0.137:1521:xe", "movie", "3333");
			System.out.println("DB연결에 성공했습니다.");
			Statement dbSt = con.createStatement();
			System.out.println("JDBC 드라이버가 정상적으로 연결되었습니다.");

			sql = "SELECT * FROM MANAGERS WHERE MA_ID = '" + id + "' AND MA_PW = '" + pw + "';";
			ResultSet result = dbSt.executeQuery(sql);
			if (result.next()) {
				check = true;
			}
			dbSt.close();
			con.close();
		} catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		}
		return check;
	}
	
	/* 영화수정 */
	public boolean modMovie(String data) {
		boolean check = false;
		//String str = "update movie set mv_plot = '"+mvplot+"' where gn_code = '"+gnum+"' and mv_code = '"+mvcode+"';";
		return check;
	}

	/* 영화코드 존재여부 */
	public boolean isMovieNumber(String mvNumber) {
		boolean check = false;

		return check;
	}
}
