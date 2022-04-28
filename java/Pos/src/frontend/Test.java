package frontend;

import java.util.Scanner;

public class Test {
	Scanner sc;
	String[][] menus = {
			{"이전화면", "상품관리", "회원관리", "매장관리"}, 
            {"이전화면", "상품등록", "상품수정"}, 
            {"이전화면", "회원등록", "회원수정"}, 
            {"이전화면", "매장등록", "매장수정"}
           };
	
	
	public Test() {
		sc = new Scanner(System.in);
		this.mainController();
		sc.close();
	}
	
	private void mainController() {
		String ui = new String();
		
		//메뉴입력
		//숫자 유효성검사
		while(true) {
			while(true) {
				this.display(this.makeTitle("Home"));
				this.display("[ Select Menu ] --------------------------------\n");
				this.display("  1. 상품판매      2. POS 관리      0. 종료\n");
				this.display("___________________________________ Select : ");
				
				ui = userInput();
				
				if(ui.equals("1")) {
					salesController();
				}
				else if(ui.equals("2")) {
					mgrController();
				}
				else if(ui.equals("0")) {
				}
				else {
					this.display("\n아무키나 입력 후 다시 선택해주세요 : ");
					sc.next();
				}
			}
		}
	}
	
	
	private void salesController() {
		/* 상품판매 */
	}
	
	private void mgrController() {
		String posMenu = new String();
		/* POS관리 */
		//메뉴출력
			while(true) {
				this.display(this.makeTitle("POS관리"));
				this.display(makeMenu(0));
				
				posMenu = userInput();
	
				if(posMenu.equals("1")) {
					mgrGoodsCtl(Integer.parseInt(posMenu));
				}
				else if(posMenu.equals("2")) {
					mgrMemberCtl(Integer.parseInt(posMenu));
				}
				else if(posMenu.equals("3")) {
					mgrStoreCtl(Integer.parseInt(posMenu));
				}
				else if(posMenu.equals("0")) {
					break;
				}
				else { 
					this.display("\n아무키나 입력 후 다시 선택해주세요 : ");
					sc.next();
				}
			}

		
		//입력
		//유효성 검사
		//메뉴 호출
		
		
	}
	
	

	private void mgrGoodsCtl(int recordIdx) {
		String goodsMenu = new String();
		
		this.display(this.makeTitle(menus[0][recordIdx]));
		this.display(this.makeMenu(recordIdx));
		
		goodsMenu = userInput();
	}
	
	
	private void mgrStoreCtl(int recordIdx) {
		String storeMenu = new String();
		
		this.display(this.makeTitle(menus[0][recordIdx]));
		this.display(this.makeMenu(recordIdx));
		
		storeMenu = userInput();
	}
	
	
	private void mgrMemberCtl(int recordIdx) {
		String memberMenu = new String();
		
		this.display(this.makeTitle(menus[0][recordIdx]));
		this.display(this.makeMenu(recordIdx));
		
		memberMenu = userInput();
	}

	
	private String makeMenu(int recordIdx) {
		StringBuffer sb = new StringBuffer();
		int colIdx;
		
		sb.append("[ Select Menu ] --------------------------------\n");
		
		//배열에 들어있는 메뉴 추출 - 0번을 마지막에 출력 => 반복x
		for(colIdx = 1;colIdx < menus[recordIdx].length;colIdx++) {
			if(colIdx % 2 == 1) { sb.append("  "); }
			sb.append(colIdx+". "+menus[recordIdx][colIdx]+"    "); // 홀수일때 공백 -> "   "|1출력 2출력|\n <- 짝수일때 다음줄
			if(colIdx % 2 == 0 ) { sb.append("\n"); }
		}
		
		if(colIdx % 2 == 1) { sb.append("  "); } //밖으로 뺀 값이라 임의로 공백을 넣어주기 & colIdx를 전역으로 만들기
		sb.append("0. "+menus[recordIdx][0]+"\n");
		sb.append("___________________________________ Select : ");
		
		
		
		return sb.toString();
	}
	
	private String makeTitle(String subject) { //같은 화면만 출력이 된다
		StringBuffer title = new StringBuffer();
		
		title.append("\n\n\n\n\n");
		title.append("****************************************************************\n");
		title.append("\n");
		title.append("⠀⠀⠀⠀⠀⣠⠆⠀⠀⠀⠀⠀⠀⠀⠀⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
		title.append("⠀⠀⠀⠀⣴⣿⠀⠀⠀⠀⢸⠀⠀⠀⠀⢸⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
		title.append("⠀⠀⢠⢴⣿⣿⡄⠀⠀⠀⢸⠀⠀⠀⠀⣼⣿⣷⢠⠀⠀⠀⠀⣤⣤⡄⢠⣤⣤⣤⡄⣤⣤⣤⣀⡀⢀⣤⣤⣤⠀⠀⠀⣤⣤⣤⣀⠀⢀⣠⣤⣄⡀⠀⣀⣤⣤⣄⠀\n");
		title.append("⠀⠀⣾⣾⣿⣿⣿⡆⠀⠀⢸⠀⠀⠀⣴⣿⣿⣿⢸⡆⠀⠀⠀⠉⣿⡇⢸⣿⣭⣭⡁⣿⣿⠉⢻⣷⡄⢹⣿⠉⠀⠀⠀⣿⣏⣉⣿⡇⣾⡟⠉⢹⣿⠀⣿⣯⣬⣙⠀⠀\n");
		title.append("⠀⠀⢻⣷⣿⣿⡟⠀⠀⠀⢸⠀⠀⠀⠘⣿⣿⢧⣿⠇⠀⠀⢀⣀⣿⡇⢸⣿⣤⣤⡄⣿⣿⣠⣼⡿⠁⣸⣿⣀⠀⠀⠀⣿⡟⠛⠋⠀⢿⣧⣀⣼⡿⠀⣤⣉⣻⣿⠆⠀\n");
		title.append("⠀⠀⢨⣿⣿⣿⡇⠀⠀⢰⣾⣷⠀⠀⠀⣿⣿⣿⢿⠂⠀⠀⠈⠉⠉⠀⠈⠉⠉⠉⠁⠉⠉⠉⠉⠀⠀⠉⠉⠉⠀⠀ ⠉⠁⠀⠀⠀⠀⠉⠉⠉⠀⠀⠉⠉⠉⠁⠀⠀\n");
		title.append("⠀⠀⠀⠻⣿⣿⣿⡄⠐⢺⣿⣟⠒⠂⣰⣿⣿⡿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
		title.append("⠀⠀⠀⠀⠉⠻⣿⣿⣦⣈⣿⣏⣠⣾⣿⡿⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
		title.append("⠀⠀⠀⠀⠀⠀⠀⠉⠙⠛⠛⠛⠛⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀  Designed by Team1⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n");
		title.append("\n");
		title.append("[ "+ subject +" ]\n");
		title.append("****************************************************************\n");
		title.append("\n");
		
		return title.toString();
	}
	
	private boolean isNum(String number) {
		try {
			Integer.parseInt(number);
			System.out.print("log");
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private String userInput() {
		return sc.next();
	}
	
	private void display(String text) {
		System.out.print(text);
	}
	
	
	
	
}
