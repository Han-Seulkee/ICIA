package backend.services;

import java.util.ArrayList;


import beans.Information;
import beans.MovieInfo;


public class ViewCtl {


	public String showMovieTitle(ArrayList<MovieInfo> movieList, String mb_id) {   // 로그인 되었을 때
		// TODO Auto-generated method stub
		StringBuffer genres = new StringBuffer();
		StringBuffer list = new StringBuffer();
		String number = null;
		
		int index = -1;
		int j = 1;
		list.append("                                     " + mb_id + " 님 \n");
		list.append(" ---------------------  영화 메뉴 --------------------\n");
				
		for(MovieInfo mi : movieList) {
			index++;
			number = mi.getGN_NUMBER();
			//System.out.println(mi.getGN_NUMBER()+  " showMovies 영화 타이틀 출력");
			if(index == 0 || !movieList.get(index - 1).getGN_NUMBER().equals(number)) {
				genres.append("    " + mi.getGN_NUMBER() + "." + mi.getGN_NAME());
				list.append("\n      "+ addSpace(mi.getGN_NAME() , 10)+ "\n");
				j = 1;
			}
			if(j % 3 == 0) {
				list.append("       "+ addSpace(mi.getMV_TITLE(), 10) + "\n");
			}else {
				list.append("       "+ addSpace(mi.getMV_TITLE(), 10) + "");
			}
			j++;

		}
				
		list.append(" \n--------------------------------------------------\n");

		list.append("#   " + genres.toString() + "    0. 뒤로가기\n\n");
		list.append("        장르를 선택하세요 \n");
		return list.toString();
		
	}
	
	public String showMovies(ArrayList<MovieInfo> movieList) {                // 장르를 선택했을 때
		// TODO Auto-generated method stub
	
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
	
	public String showComment(ArrayList<Information> recordList) {              // 댓글 보여주기
	
		StringBuffer list = new StringBuffer();

		list.append("     " + recordList.get(0).getMv_Title()  + "\n");
		list.append("          " + recordList.get(0).getMv_Plot() + "\n");
		list.append("    시청자 ID    평점    댓글 \n");  // orderList.get(orderList.size()-1).getNow()
		
		//list.append("     영화를 선택하세요.  (코드 입력) \n");
		for(Information mi : recordList) {
			list.append("      " + addSpace(mi.getMb_Id(),10));
			list.append("      " + addSpace(mi.getIf_Gpa(), 10));
			list.append("      " + mi.getMv_Comment() + "\n\n");
		
		}
				
		list.append(" \n--------------------------------------------------\n");
		

		
		return list.toString();
		
	}
	
	private String addSpace(String s, int length) {
		int s_length = 0;
		StringBuffer sb = new StringBuffer();
		if(s != null) {
			s_length = s.length();
			sb.append(s);
		}
				
		for(int i = 0; i < length - s_length; i++) {
			sb.append(" ");
		}
		
		return sb.toString();
	}
}
