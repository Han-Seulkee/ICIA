package backend.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import beans.Information;
import beans.MovieInfo;
import database.DataAccessObject;



public class MovieCtl {
    // 1. DB접속
    // -Connection 클래스
	public String backController(String clientData) {
		
		String serverData = null;
		String jobCode = clientData.substring(0, clientData.indexOf("?"));
		
		switch(jobCode) {
		case "joinMb":      //  회원 가입
			serverData = this.insertMemberCode_2(clientData.substring(clientData.indexOf("?")+1));			
			break;
		case "isMb":        // 로그인 ( 회원이 있는가 확인 )  
			
			serverData = this.selectMemberCode(clientData.substring(clientData.indexOf("?")+1));

			break;
		case "comment":
			serverData = this.setComment(clientData.substring(clientData.indexOf("?")+1));
		default:
		}
		
		return serverData;
	}
//	String serverMessage = ctl.entrance("comment?" + "INFO_MB_ID=" + id + "&INFO_MV_NUMBER=" + mv_Number + 
//"&INFO_COMMENT=" + comment + "&INFO_GPA=" + info_Gpa);
	
	public String selectMemberCode(String code) {                      //  로그인
		String mv_Id = "0";
		
		String[] itemValue = code.split("&");
		for(int idx = 0; idx < itemValue.length; idx++) {
			itemValue[idx] = itemValue[idx].substring(itemValue[idx].indexOf("=") + 1);
		}
	    Connection conn;
	    Statement stmt;
	    ResultSet rs;
	    // 2. 연결 문자열 생성
	    // -접속에 필요한 정보로 구성된 문자열, Connection String
	    String url = "jdbc:oracle:thin:@192.168.0.137:1521:xe"; //localhost대신 ip주소가 들어갈수도
	    String id = "TEST1";
	    String pw = "3333";
	
	    // DB작업 > 외부 입출력 > try-catch 필수
	
	    try {
	        // 3. JDBC 드라이버 로딩
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	
	        // 4. 접속
	        // - Connection 객체 생성 + 접속 작업.
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	
	        String query = "select MB_ID from MEMBERS WHERE MB_ID = " + itemValue[0] +" AND MB_PASSWORD = "  + itemValue[1] + "";
	        
	        rs = stmt.executeQuery(query);
	        
	
	        while(rs.next()) {
	        	//movieList = this.showMovieTitle(rs.getString("MB_ID"));
	        	mv_Id =  itemValue[0];
	        }
	
	        // 6. 접속종료
	        rs.close();
	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");     // 접속중(false), 접속종료(true)
	
	    } catch (Exception e) {
	        e.printStackTrace();
	    	System.out.println("에러 발생");
	    	mv_Id = "-1";
	    }
	    
	    return mv_Id;
	}
/* ------------------ 	전체 영화 목록 보여 주기 , controller 에서 직접 호출함,  반환값이 ArrayList */
	
	public ArrayList<MovieInfo> returnMovieTitle(){            
		
		MovieInfo mi = null;

		ArrayList<MovieInfo> movieList = null;		
	
	    Connection conn;
	    Statement stmt;
	    ResultSet rs;
	    // 2. 연결 문자열 생성
	    // -접속에 필요한 정보로 구성된 문자열, Connection String
	    String url = "jdbc:oracle:thin:@192.168.0.137:1521:xe"; //localhost대신 ip주소가 들어갈수도
	    String id = "TEST1";
	    String pw = "3333";
	
	    // DB작업 > 외부 입출력 > try-catch 필수
	
	    try {
	        // 3. JDBC 드라이버 로딩
	    	
	    	
	    	movieList = new ArrayList<MovieInfo>();
	    	
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	    
	        //String query = "SELECT GN_NUMBER, MV_TITLE  FROM MOVIES  ORDER BY GN_NUMBER ,MV_GPA DESC";
	        String query = "SELECT A.MV_GN_NUMBER, A.MV_TITLE , B.GN_NAME FROM MOVIES A INNER JOIN  GENRES B ON A.MV_GN_NUMBER = B.GN_NUMBER ORDER BY MV_GN_NUMBER ,MV_GPA DESC";
	        		
	        rs = stmt.executeQuery(query);
	        
	        while(rs.next()) {
	           mi = new MovieInfo();
	    	   mi.setGN_NUMBER(Integer.toString(rs.getInt("MV_GN_NUMBER")));    	   
	    	   mi.setMV_TITLE(rs.getString("MV_TITLE"));
	    	   mi.setGN_NAME(rs.getString("GN_NAME"));	    	   
	    	   movieList.add(mi);
	        }
	        

	        rs.close();	 
	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println("에러 발생");
	    }
		

    	return movieList;
	 	
	}
	
  /* -------------------  장르에 속한 영화 정보 보여 주기 	--------------------- */ 
	
	public ArrayList<MovieInfo> getMovieInfo(String clientData){            
		String gn_number = clientData.substring(clientData.indexOf("?")+1).split("=")[1];
	
		MovieInfo mi = null;

		ArrayList<MovieInfo> movieList = null;		
	
	    Connection conn;
	    Statement stmt;
	    ResultSet rs;
	    // 2. 연결 문자열 생성
	    // -접속에 필요한 정보로 구성된 문자열, Connection String
	    String url = "jdbc:oracle:thin:@192.168.0.137:1521:xe"; //localhost대신 ip주소가 들어갈수도
	    String id = "TEST1";
	    String pw = "3333";
	
	    // DB작업 > 외부 입출력 > try-catch 필수
	
	    try {
	        // 3. JDBC 드라이버 로딩
	    	
	    	
	    	movieList = new ArrayList<MovieInfo>();
	    	
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	    
	        String query = "SELECT  A.MV_NUMBER, A.MV_TITLE, A.MV_PLOT, A.MV_GPA, B.GN_NAME  FROM MOVIES A INNER JOIN GENRES B ON A.MV_GN_NUMBER = B.GN_NUMBER WHERE A.MV_GN_NUMBER = "+ gn_number + " ORDER BY MV_GPA DESC";
	        //String query = "SELECT A.GN_NUMBER, A.MV_TITLE , B.GN_NAME FROM MOVIES A INNER JOIN  GENRES B ON A.GN_NUMBER = B.GN_NUMBER ORDER BY GN_NUMBER ,MV_GPA DESC";
	        		
	        rs = stmt.executeQuery(query);
	        
	        while(rs.next()) {
	           mi = new MovieInfo();
	    	   mi.setMV_NUMBER(rs.getString("MV_NUMBER"));    	   
	    	   mi.setMV_TITLE(rs.getString("MV_TITLE"));
	    	   mi.setMV_PLOT(rs.getString("MV_PLOT"));
	    	   mi.setMV_GPA(rs.getString("MV_GPA"));
	    	   mi.setGN_NAME(rs.getString("GN_NAME"));
	    	   movieList.add(mi);
	        }
	        

	        rs.close();	 
	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println(" returnMovieInfo   에러 발생");
	    }

    	return movieList;
	 	
	}
	/* ----------------------    시청기록과  댓글 보여 주기   -------------------*/
	
	public ArrayList<Information> getComment(String clientData){            
		String mv_Number = clientData.substring(clientData.indexOf("?")+1).split("=")[1];   // 영화 번호 (코드) 값 추출
		
		Information record = null;

		ArrayList<Information> recordList = null;		
	
	    Connection conn;
	    Statement stmt;
	    ResultSet rs;
	    ResultSet rs2;
	    // 2. 연결 문자열 생성
	    // -접속에 필요한 정보로 구성된 문자열, Connection String
	    String url = "jdbc:oracle:thin:@192.168.0.137:1521:xe"; //localhost대신 ip주소가 들어갈수도
	    String id = "TEST1";
	    String pw = "3333";
	
	    try {
	        // 3. JDBC 드라이버 로딩
	    		    	
	    	recordList = new ArrayList<Information>();
	    	
	        Class.forName("oracle.jdbc.driver.OracleDriver");
	
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	    
	        //String query = "SELECT A.MV_CODE, A.MB_ID, A.MV_COMMENT, A.IF_GPA, B.MV_TITLE FROM INFORMATION A INNER JOIN  MOVIES B ON A.MV_CODE = B.MV_NUMBER  WHERE MV_CODE = " + mv_Number + " ORDER BY IF_GPA DESC";
	        String query = "SELECT A.MV_PLOT ,A.MV_NUMBER ,A.MV_TITLE, B.INFO_MB_ID, B.INFO_COMMENT, B.INFO_GPA "
	        		+ "FROM MOVIES A LEFT OUTER JOIN INFORMATION B ON A.MV_NUMBER = B.INFO_MV_NUMBER  WHERE MV_NUMBER = " + mv_Number + " ORDER BY INFO_GPA DESC";
	        		
	        		
	        				        
	        rs = stmt.executeQuery(query);
	        
	        
	        while(rs.next()) {
	           
	           record = new Information();
	           record.setMv_Plot(rs.getString("MV_PLOT"));
	           record.setMv_Code(rs.getString("MV_NUMBER"));
	           record.setMv_Title(rs.getString("MV_TITLE"));   	   
	           record.setMb_Id(rs.getString("INFO_MB_ID"));
	           record.setMv_Comment(rs.getString("INFO_COMMENT"));
	           record.setIf_Gpa(rs.getString("INFO_GPA"));	          
	           recordList.add(record);
	        }
	        

	        rs.close();	 
	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	    } catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println(" returnMovieInfo   에러 발생");
	    }

    	return recordList;		
	}
	
	/*  --------------------       회원 가입,  이 함수에서 직접 처리    -------------------*/
	
	public String insertMemberCode(String code) {           // 회원 가입 
		String mv_Id = "0";
		String[] itemValue = code.split("&");
		for(int idx = 0; idx < itemValue.length; idx++) {
			itemValue[idx] = itemValue[idx].substring(itemValue[idx].indexOf("=") + 1);
		}
		
	    Connection conn;
	    Statement stmt;
	    ResultSet rs;
	    // 2. 연결 문자열 생성
	    // -접속에 필요한 정보로 구성된 문자열, Connection String
	    String url = "jdbc:oracle:thin:@192.168.0.137:1521:xe"; //localhost대신 ip주소가 들어갈수도
	    String id = "TEST1";
	    String pw = "3333";

	    // DB작업 > 외부 입출력 > try-catch 필수

	    try {
	        // 3. JDBC 드라이버 로딩
	        Class.forName("oracle.jdbc.driver.OracleDriver");

	        // 4. 접속
	        // - Connection 객체 생성 + 접속 작업.
	        conn = DriverManager.getConnection(url, id, pw);
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)
	        
	        stmt = conn.createStatement();
	        
	        
	        String query = "select * from MEMBERS WHERE MB_ID = " + itemValue[0] + "";
	        rs = stmt.executeQuery(query);
	       
	        String query2 = "INSERT INTO MEMBERS(MB_ID, MB_PASSWORD) VALUES(" +itemValue[0]   +" , " + itemValue[1] + ")";
	        
	        

	        if(rs.next()) {      //  값이 있으면 
	        	System.out.println("여기 까지 됨 ㅎ, 회원 가입 movieCtl.java");
	
	        	mv_Id = "0";
	        }else {              // 값이 업으면 입력
	        	stmt.executeUpdate(query2);
	        }
	        
	        rs.close();	
	        stmt.close();
	        conn.close();
	        System.out.println(conn.isClosed()?"접속종료":"접속중");// 접속중(false), 접속종료(true)

	    } catch (Exception e) {
	        e.printStackTrace(); 
	        //mv_Id = "-1";                 //  에러 나면 -1이 입력 안됨
	    	System.out.println("에러 발생  , ,  회원 가입");
	    }
	    
	    return mv_Id;
	}
	/*  ----------------     댓글 달기  ,  이전 댓글이 있으면 update, 없으면 insert    ------------------- */
//	String serverMessage = ctl.entrance("comment?" + "INFO_MB_ID=" + id + "&INFO_MV_NUMBER=" + mv_Number + 
//"&INFO_COMMENT=" + comment + "&INFO_GPA=" + info_Gpa);
	public String setComment(String code) {           // 회원 가입 
		System.out.println(code);
		DataAccessObject dao = new DataAccessObject();
		String mv_Id = "0";
		String[] itemValue = code.split("&");
		for(int idx = 0; idx < itemValue.length; idx++) {
			itemValue[idx] = itemValue[idx].substring(itemValue[idx].indexOf("=") + 1);
		}
		ResultSet rs;

	        
	    String query = "select * from INFORMATION WHERE INFO_MB_ID = " + itemValue[0] + " AND INFO_MV_NUMBER=" + itemValue[1];
	    rs = dao.connect(query);         // 연결

	    try {
			if(rs.next()) {
				//  값이 있으면 
			    System.out.println("여기 까지 됨 ㅎ, 회원 가입 movieCtl.java");
			    String[] query2 = {"UPDATE INFORMATION INFO_COMMENT =" + itemValue[2] + ", INFO_GPA=" + Integer.parseInt(itemValue[3]) + "WHERE INFO_MB_ID = " + Integer.parseInt(itemValue[0]) + " AND INFO_MV_NUMBER=" + itemValue[1] };
			    dao.executeUpdate(query2);
			}else {              // 값이 업으면 입력
				String[] query3 = {"INSERT INTO INFORMATION(INFO_MB_ID, INFO_MV_NUMBER, INFO_COMMENT, INFO_GPA) VALUES(" +itemValue[0]  + " , " + Integer.parseInt(itemValue[1]) + ", " + itemValue[2] + "," + Integer.parseInt(itemValue[3]) +")"};	
				dao.executeUpdate(query3);
			    mv_Id = "1";
			}
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}

	    dao.close();                     // 끊기
	    
	    return mv_Id;
	}
	
	/*  --------------   회원 가입,  데이터 베이스 의 함수를 이용하여 만든 것    ---------------*/	
	  
	
	public String insertMemberCode_2(String code) {           // 회원 가입 
		DataAccessObject dao = new DataAccessObject();
		String mv_Id = "0";
		String[] itemValue = code.split("&");
		for(int idx = 0; idx < itemValue.length; idx++) {
			itemValue[idx] = itemValue[idx].substring(itemValue[idx].indexOf("=") + 1);
		}
		ResultSet rs;

	        
	    String query = "select * from MEMBERS WHERE MB_ID = " + itemValue[0] + "";
	    rs = dao.connect(query);         // 연결

	    try {
			if(rs.next()) {
				//  값이 있으면 
			    System.out.println("여기 까지 됨 ㅎ, 회원 가입 movieCtl.java");
	 
			}else {              // 값이 업으면 입력
				String[] query2 = {"INSERT INTO MEMBERS(MB_ID, MB_PASSWORD) VALUES(" +itemValue[0]   +" , " + itemValue[1] + ")"};	
				dao.executeUpdate(query2);
			    mv_Id = "1";
			}
		} catch (SQLException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}

	    dao.close();                     // 끊기
	    
	    return mv_Id;
	}

}
