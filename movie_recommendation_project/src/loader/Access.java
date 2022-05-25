package loader;

import java.util.Scanner;

import frontend.manager.ManagerFrontEnd;
import frontend.member.User;


public class Access {
	Scanner scanner;
	public Access() {
		scanner = new Scanner(System.in);
		systemAccessCtl();
		scanner.close();
	}
	

	private void systemAccessCtl() {          // 프로그램 시작
		String ui = new String();
		while(true) {
			
			this.display("[ Select Menu ] ----------------------------\n");
			this.display("  1. 영화 보기        2.  관리        0. 종료\n");
			this.display("___________________________________ Select : ");
			ui = this.userInput();
			/* 유효성 검사 */

			if(ui.equals("1")) {			
				new User(scanner);  // 
			}else if(ui.equals("2")) {
				new ManagerFrontEnd(scanner);
			}else if(ui.equals("0")) {
				this.display("  [ 종 료 ] ");
				break;
			}else {
				this.display(" [ 올바른 값을 입력 하세요. ] \nu");
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
