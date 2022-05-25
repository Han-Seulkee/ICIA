package frontend.member;

import java.util.Scanner;

import backend.ctl.Controller;

public class User {
	Scanner scanner;
	public User(Scanner scanner) {
		// TODO Auto-generated constructor stub
		this.scanner = scanner;
		this.start();
	}
	


	private void start() {          // 프로그램 시작
		String ui = new String();
		while(true) {
			
			this.display("[ Select Menu ] ----------------------------\n");
			this.display("  1. 회원 가입         2.  로그인        0. 뒤로 가기\n");
			this.display("___________________________________ Select : ");
			ui = this.userInput();
			/* 유효성 검사 */

			if(ui.equals("1")) {			
				this.joinMember();        //  회원 가입
			}else if(ui.equals("2")) {
				this.rogMember();         //  로그인
			}else if(ui.equals("0")) {
				
				break;
			}else {
				this.display(" [ 올바른 값을 입력 하세요. ] \nu");
			}
		}

	}
	
	private void joinMember() {                            // 회원 가입
		Controller ctl = new Controller();
		this.display(" [ 아이디를 입력하세요. ] : ");
		String id, password;
		id = this.userInput();
		this.display(" [ 비밀번호를 입력하세요. ] : ");
		password = this.userInput();
		if(ctl.entrance("joinMb?" + "id=" + id + "&password=" + password).equals("1")) {
			this.display("  회원가입 성공  ");
		}else {
			this.display("  회원가입 실패 ");
		}
	}
	
	private void rogMember() {                             //  로그인 
		String genre = null; 
		Controller ctl = new Controller();
		this.display(" [ 아이디를 입력하세요. ] : ");
		String id, password;
		String mv_Number;                                  // 선택한 영화 코드
		id = this.userInput();
		this.display(" [ 비밀번호를 입력하세요. ] : ");
		password = this.userInput();
		
		String bData = ctl.entrance("isMb?" + "id=" + id + "&password=" + password);       // ViewCtl 에서 영화리스트와 id 를 문자열로 반환
		                                                                                      
		if(bData.equals("-1") || bData.equals("0")) {
			this.display("    로그인 실패   ");
			return;
		}else {
			this.display("    로그인 성공   ");
		}
		String[] backendData = 	bData.split("\\#");
		
		if(backendData[1].equals("0")) {
			this.display("  [ 영화 없음 ]  ");
			return;
		}

		this.display(backendData[0]);
		while(true) {
			this.display(backendData[1]);
			
			genre = this.userInput();                                 // 숫자만 입력 , 아니면 에러 
			if(!genre.equals("0")) {
				String serverData = ctl.entrance("selectGenre?" + "GN_NUMBER=" + genre);
				while(serverData != null) {
					this.display(serverData);
					this.display("    1. 영화 선택     0. 뒤로 가기  ");
					mv_Number = userInput();                 // 영화 코드 ( 번호 ) 입력
					if(mv_Number.equals("0")) {
						break;
					}
					String recordInfo = ctl.entrance("returnMovie?" + "MV_NUMBER=" + mv_Number);
					if(recordInfo != null) {
						this.display(recordInfo);
						this.display("    1. 영화 보기     0. 뒤로 가기  ");
						String a = this.userInput();
						if(a.equals("1")) {
							this.displayMovie(ctl,id, mv_Number);
						}
					}else {
						this.display("  [ 잘못된 값 입력 ] ");
					}

					
				}

			}else if(genre.equals("0")) {
				return;
			}
			
		}
	
	}
	
	private void displayMovie(Controller ctl, String id, String mv_Number) {
		this.display("\n     [  재생중  ]  \n");
		try {
			for(int i = 0; i < 50; i++) {
				Thread.sleep(40);
				System.out.print("-");
			}
		}catch(Exception e) {
			
		}
		this.display("\n   1. 댓글 달기, 평점 등록      0. 뒤로 가기");
		String in = this.userInput();                // 댓글 달기 선택
		if(in.equals("1")) {
			this.display("  [ 댓 글 ] :");
			String comment = this.userInput();
			this.display("  [ 평점 등록 ] ( 0  ~ 5 ) 까지 :");
			String info_Gpa = this.userInput();
			String serverMessage = ctl.entrance("comment?" + "INFO_MB_ID=" + id + "&INFO_MV_NUMBER=" + mv_Number + 
					"&INFO_COMMENT=" + comment + "&INFO_GPA=" + info_Gpa);
			if(serverMessage.equals("1")) {
				this.display("    OK    ");
			}else {
				this.display("    실 패  ");
			}
		}
	}

	
	private String userInput() {
		return this.scanner.next();
	}

	private void display(String contents) {
		System.out.println(contents);
	}
}
