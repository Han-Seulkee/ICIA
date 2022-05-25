package backend.ctl;

import java.util.ArrayList;


import beans.Information;
import beans.MovieInfo;
import backend.services.MovieCtl;
import backend.services.MovieManagements;
import backend.services.ViewCtl;


public class Controller {
	public Controller() {}
	
	public String entrance(String clientData) {
		ArrayList<MovieInfo> movieList = null;
		ArrayList<Information> recordList = null;
		
		String message = null;		
		String jobCode = null;
		
		MovieCtl mv = null;             // 데이터베이스와 연결하는 클래스 
		ViewCtl vw = null;              // user.java 로 보낼 문자열을 만들어 주는 클래스
		MovieManagements mmg = null;
		jobCode = clientData.substring(0, clientData.indexOf("?"));
		
		switch(jobCode) {

		case "joinMb":             //  회원 가입 
			mv = new MovieCtl();
			message = mv.backController(clientData);
			break;
		case "isMb":               // 로그인 
			mv = new MovieCtl();
			message = mv.backController(clientData);                    // id 값 반환
			if(!message.equals("-1") && !message.equals("0")) {         // 반환값이 0 과 -1 이 아니면 로그인 성공 
				
				movieList = mv.returnMovieTitle();                      // 성공하면 장르  영화 ArrayList , 실패하면  아무것도 없는 ArrayList<MovieInfo> 반환
				if(movieList.size() > 0) {                                                  
					vw = new ViewCtl();
					message = vw.showMovieTitle(movieList, message);    // ArrayList 를 보여줄 문자열으로 만들어 주는 함수 ( 영화 전체 출력 )   message  (  id )	
				}else {
					message = message + "#0";
				}
			}

			break;
		case "selectGenre":           //  장르에 속한 영화정보 가져오기 
			mv = new MovieCtl();			
			movieList = mv.getMovieInfo(clientData);
			
			if(movieList.size() > 0) {                                //  실패하면 빈 리스트를 반환 
				//System.out.println(movieList + " selectGenre ,   controller 12");
				vw = new ViewCtl();
				message = vw.showMovies(movieList);		              // ArrayList 를 사용자에게 보여줄 문자열으로 		( 장르에 속한 영화들만 )	
			}
			break;
		case "returnMovie":          // 시청 기록 가져오기 ( 댓글 , userId ..  )
			mv = new MovieCtl();
			recordList = mv.getComment(clientData); 
	
			if(recordList.size() > 0) {                                //  실패하면 빈 리스트를 반환 
				vw = new ViewCtl();
				message = vw.showComment(recordList);		              // ArrayList 를 사용자에게 보여줄 문자열으로 		( 장르에 속한 영화들만 )	
			}
			break;
		case "comment":
			mv = new MovieCtl();
			message = mv.backController(clientData);
			break;
			
		case "mgLogin": case "checkMovie": case "regMovie": case "modMovie":
			mmg = new MovieManagements();
			message = mmg.controller(clientData);
			break;
		
		default:
		}
		return message;
	}
}

//	String serverMessage = ctl.entrance("comment?" + "INFO_MB_ID=" + id + "&INFO_MV_NUMBER=" + mv_Number + 
//"&INFO_COMMENT=" + comment + "&INFO_GPA=" + info_Gpa);
//
