package backend.services;

import java.util.ArrayList;

import beans.MovieInfo;
import database.DataAccessObject;

public class MovieManagements {
	public MovieManagements() {

	}

	public String controller(String clientData) {
		String message = null;
		String[] data;
		data = clientData.split("\\?");

		switch (data[0]) {
		case "mgLogin": message = this.loginCtl(data[1]);
			break;
		case "checkMovie": //message = this.checkMvCtl();
			break;
		case "regMovie": message = this.addMvCtl(data[1]);
			break;
		case "modMovie": message = this.modMvCtl(data[1]);
			break;
		}

		return message;
	}

	/* 로그인 */
	private String loginCtl(String clientData) {
		/*
		 * MANAGERS managerId, managerPw (MA_ID, MA_PASSWORD) 
		 * SELECT MA_ID FROM MANAGERS
		 * WHERE MA_ID = ‘managerId’ AND MA_PASSWORD = ‘managerPw’; 
		 * client data : managerId, managerPw 
		 * DB: 0 -> ‘로그인 실패’ 1 -> ‘성공’ 
		 * server data : message = '성공'
		 */
		String message = "로그인 실패";
		String[] loginInfo = clientData.split("&");
		String id, pw;
		DataAccessObject dao = null;
		id = loginInfo[0].substring(loginInfo[0].indexOf("=")+1);
		pw = loginInfo[1].substring(loginInfo[1].indexOf("=")+1);
		
		dao = new DataAccessObject();
		//로그인 메소드 호출
		
		return message;
	}

	/* 영화조회 */

	private String checkMvCtl() {	
//		 * MOVIES (GN_NUMBER, MV_NUMBER, MV_TITLE, MV_PLOT, MV_GPA) 
//		 * SELECT * FROM MOVIES;
//		 * client data : X 
//		 * DB: 0 -> ‘조회 실패’ 1 -> 영화정보 데이터 
//		 * server data : GN_NUMBER, MV_NUMBER, MV_TITLE, MV_PLOT, MV_GPA
		ArrayList<MovieInfo> movieList = null;
		String serverData = null;
		DataAccessObject dao = null;
		
		dao = new DataAccessObject();
		//movieList = this.getMovieList(dao); 영화 전체 리스트 가져오기
		//movieList = this.getGenMovieList(dao); 특정 장르 리스트 가져오기
		
		//serverData = makeMovieList(movieList);
		
		//return this.transferServerData(movieInfo);
	}
	
	public String makeMovieList(ArrayList<MovieInfo> movieList) {
		StringBuffer list = new StringBuffer();

		list.append("\n    " + movieList.get(0).getGN_NAME()  + "\n");
		list.append("       영화 제목         영화 코드        평점     \n");  // orderList.get(orderList.size()-1).getNow()
		
		//list.append("     영화를 선택하세요.  (코드 입력) \n");
		for(MovieInfo mi : movieList) {
			list.append("      " + addSpace(mi.getMV_TITLE(), 10));
			list.append("      " + addSpace(mi.getMV_NUMBER(), 10));
			list.append("      " + mi.getMV_GPA() + "\n");
			//list.append("      " + mi.getMV_PLOT() + "\n\n");
		}				
		list.append(" \n--------------------------------------------------\n");

		return list.toString();
	}
	
	
	/*
	private String getMovieInfo(DataAccessObject dao) {
		dao.getMovieInfo(dao.getMovieCount());
		return null;
	}*/
	
	/*
	private String transferServerData(String[][] movieInfo) {
		StringBuffer sb = new StringBuffer();
		for(int idx = 0;idx<movieInfo.length;idx++) {
			for(int idx2 = 0;idx2<movieInfo[idx].length;idx2++) {
				sb.append(movieInfo[idx][idx2]);
				if(idx2<movieInfo[idx].length-1&&movieInfo[idx][idx2+1]!=null)
					sb.append(",");
				if(idx2<movieInfo[idx].length-1)
					sb.append("&");
			}
		}
		return sb.toString();
	}
	
	/* 영화추가 */
	private String addMvCtl(String clientData) {
		/*
		 * MOVIES genreNumber, mvNumber, mvTitle, mvPlot, mvGPA 
		 * (GN_NUMBER, MV_NUMBER, MV_TITLE, MV_PLOT, MV_GPA) 
		 *******************mvNumber = (select max(mv_number) from movies) + 1;*************************
		 * INSERT INTO MOVIES(GN_NUMBER, MV_NUMBER, MV_TITLE, MV_PLOT, MV_GPA)
		 * VALUES(genreNumber, mvNumber, mvTitle, mvPlot, mvGPA);
		 * client data : genreNumber, mvNumber, mvTitle, mvPlot, mvGPA 
		 * DB : 0 -> ‘저장 실패’ 1 -> ‘성공’ 
		 * server data : message = '성공'
		 */
		String message = "저장 실패";
		String[] movieInfo = clientData.split("&");
		String gnNumber = movieInfo[0].substring(movieInfo[0].indexOf("=")+1);
		DataAccessObject dao;
		
		dao = new DataAccessObject();
		this.getMovieNumber(dao, gnNumber);
		
		return message;
	}
	
	/* 영화번호자동생성 */
	private String getMovieNumber(DataAccessObject dao, String gnNumber) {
//		String.valueOf(dao.getMovieNumber(gnNumber));
		return null;
	}

	/* 영화수정 */
	private String modMvCtl(String clientData) {
		/*
		 * MOVIES genreNumber, mvNumber, mvPlot (GN_NUMBER, MV_NUMBER, MV_TITLE, MV_PLOT, MV_GPA)
		 * UPDATE MOVIES SET MV_PLOT = ‘mvPlot’ WHERE GN_NUMBER = ‘genreNumber’ AND MV_NUMBER = ‘mvNumber’;
		 * client data : genreNumber, mvNumber, mvTitle, mvPlot 
		 * DB -> 0 -> ‘수정 실패’ 1 -> ‘성공’ 
		 * server data : message = '성공'
		 */
		String message = "수정 실패";
		
		return message;
	}
	
	//private String transferData() {}
	
}
