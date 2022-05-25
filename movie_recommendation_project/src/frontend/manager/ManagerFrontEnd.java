package frontend.manager;

import java.util.Scanner;

import beans.*;
import backend.ctl.Controller;

import java.util.ArrayList;

public class ManagerFrontEnd 
{
	Scanner sc;

	/*---------------------------생성자-----------------------------*/

	public ManagerFrontEnd(Scanner sc) 
	{
		this.sc = sc;
		this.login();
		sc.close();
	}

	/*-----------------------------로그인----------------------------*/

	//로그인 _ 아이디 비밀번호 
	private void login()
	{
		String id = null;
		String pw = null;
		Controller ctl = null;
		String message = null;

		while (true)
		{	
			this.output(this.title("로그인"));
			this.output("  [ ID ] : ");
			id = this.input();

			this.output("\n  [ PW ] : ");
			pw = this.input();
			ctl = new Controller();
			message = ctl.entrance("mgLogin?id="+id+"&password="+pw);
			if(true) {		
				this.mgrMovieCtl();
			}
			break;
		}
	}

	/*----------------------------관리자단---------------------------*/	

	//관리자 _ 메인컨트롤
	private void mgrMovieCtl()
	{
		String ui = new String();
		String jobCode = new String();

		while (true)
		{
			this.output(this.title("관리자 모드"));
			this.output("  [ 메뉴 선택 ]\n");
			this.output("--------------------------------------------------------------\n");
			this.output("      1. 영화추가            2. 영화수정            3. 영화조회            0. 이전화면\n");
			this.output("--------------------------------------------------------------\n");
			this.output("-------------------------------------------------------선택 : ");

			ui = this.input();

			if(ui.equals("1"))
			{
				jobCode = "regMovie";
				this.regMovie();
				break;
			}
			else if(ui.equals("2"))
			{
				jobCode = "modMovie";
				this.modMovie();
				break;
			}
			else if(ui.equals("3"))
			{
				jobCode = "checkMovie";
				this.view();
				break;
			}
			else if(ui.equals("0"))
			{
				this.login();
				break;
			}
			
			else
			{
				this.output("다시 입력해주세요.");
			}
		}
	}

	//관리자 _ 영화추가 _ 영화코드,장르코드,영화제목,영화줄거리
	// 확인작업 장르체크 후 입력목록 출력
	private String regMovie(/*ArrayList*/)
	{
		String ui = new String();

		this.output(this.title("영화추가"));
		this.output(this.output1("장르선택"));
		this.output("      1. 액션       2. 호러       3. 코미디       4. 판타지 ");
		this.output("-------------------------------------------------------선택 : ");
		ui = this.input();
		//ArrayList.장르코드(ui);

		this.output(this.output1("입력사항"));

		/*
		for(ArrayList) 
		{
			this.display("  [ 영화코드 ] : ");
			ui = this.input();
			ArrayList.add영화코드(ui);

			this.display("  [ 영화제목 ] : ");
			ui = this.input();
			ArrayList.add영화제목(ui);

			this.display("  [ 영화줄거리 ] : ");
			ui = this.input();
			ArrayList.add영화줄거리(ui);

			this.display("  [ 영화평점 ] : ");
			ui = this.input();
			ArrayList.add영화평점(ui);

			break;
		}
		 */

		return ui;
	}

	//관리자 _ 영화수정 _ 줄거리
	private String modMovie(/*ArrayList*/)
	{
		String ui = new String();
		Controller ctl = new Controller();
		this.output(this.title("영화추가"));
		this.output(this.output1("장르선택"));
		this.output("      1. 액션       2. 호러       3. 코미디       4. 판타지 ");
		this.output("-------------------------------------------------------선택 : ");
		ui = this.input();
		
		//
		
		/*
		//장르코드 확인
		ArrayList.get장르코드(ui);
		
		//영화코드 확인
		for (int idx = 0; idx < ArrayList.size; idx++)
		{
		this.output(idx + ArrayList.get(idx)영화코드 + ArrayList.get(idx)영화제목);
		}
		ui = this.input();
		ArrayList.get영화코드(ui);

		this.display("  [ 영화줄거리 ] : ");
		ui = this.input();
		ArrayList.set영화줄거리(ui);
		*/
		
		return ui;
	}

	/*----------------------------전체조회---------------------------*/

	//전체조회
	private String view(/*ArrayList*/)
	{
		StringBuffer sb = new StringBuffer();
		MovieInfo mi = null;
		ArrayList<MovieInfo> movieList = null;
		String movieData;
		Controller ctl = new Controller();

		sb.append("--------------------------------------------------------------\n");
		sb.append(" 장르코드       영화코드       영화제목                     영화평점\n");
		sb.append("--------------------------------------------------------------\n");

		//serverRec = 전체 영화정보
		movieData = ctl.entrance("check?");
		sb.append(movieData);
		
//		movieData = new String[serverRec.length][];
//		for(int idx = 0;idx<serverRec.length;idx++) {
//			movieData[idx] = serverRec[idx].split(",");
//		}
//		
//		movieList = new ArrayList<MovieInfo>();
//		mi = new MovieInfo();
		
//		mi.setGnNumber(Integer.parseInt(movieData[0][0]));
//		mi.setMvNumber(Integer.parseInt(movieData[0][1]));
//		mi.setMvTitle(movieData[0][2]);
//		mi.setMvPlot(movieData[0][3]);
//		mi.setMvGPA(Integer.parseInt(movieData[0][4]));
		
		
//		for(int idx=0;idx<movieData.length;idx++) {
//			for(int idx2=0;idx2<movieData[idx].length;idx2++) {
//				System.out.print(movieData[idx][idx2]);
//				if(idx2==movieData[idx].length-1) 
//					System.out.print("\n");
//			}
//		}
		
		
		/*
		for(ArrayList)
		{
			sb.append(ArrayList.get장르코드);
			sb.append(ArrayList.get영화코드);
			sb.append(ArrayList.get영화제목);
			sb.append(ArrayList.get영화줄거리);
			sb.append(ArrayList.get영화평점 + "\n");
		}
		 */

		sb.append("--------------------------------------------------------------\n");

		return sb.toString();
	}

	/*--------------------------기본메소드----------------------------*/

	//타이틀 _
	private String title (String title)
	{
		StringBuffer sb = new StringBuffer();

		sb.append("\n\n\n\n");
		sb.append("--------------------------------------------------------------\n\n");
		sb.append("                      영화   추천   list   \n\n");
		sb.append("--------------------------------------------------------------\n");
		sb.append("  [ " + title + " ]\n");
		sb.append("--------------------------------------------------------------\n");

		return sb.toString();
	}
	//입력 - 사용자 입력값 받기 
	private String input()
	{		
		return sc.next();
	}

	//출력 - 
	private void output(String text)
	{
		System.out.print(text);
	}

	//입력항목 _ 추가, 수정 
	private String output1(String pm)
	{
		StringBuffer sb = new StringBuffer();

		sb.append("  [ " + pm + " ] -------------------------------------------------\n");

		return sb.toString();
	}

	//jobCode
	/*
	private String makeTrd(String jobCode, String name, ArrayList) { 
		return jobCode + "?" + "&장르코드" + "=" + ArrayList.get장르코드 + "&영화코드" + ArrayList.get영화코드; 
	}
	*/
	
}