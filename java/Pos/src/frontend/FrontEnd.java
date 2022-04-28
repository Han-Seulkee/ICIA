package frontend;

import java.util.Scanner;

public class FrontEnd {
	
	Scanner sc;
	String[][] menus = {
			{"이전화면", "상품관리", "회원관리", "매장관리"}, 
            {"이전화면", "상품등록", "상품수정"}, 
            {"이전화면", "회원등록", "회원수정"}, 
            {"이전화면", "매장등록", "매장수정"}
           };
	
	
	public FrontEnd() {
		this.sc = new Scanner(System.in);
		this.mainController();
		this.sc.close();
		
	}	
	
	
	private void mainController() {
		/* 메인 메뉴 제어 
		 *  1 --> salesContoller() 판매
		 *  2 --> mgrContoller() POS관리
		 * */
		this.display(makeTitle("Home"));
		this.display("[ Select Menu ] --------------------------------\n");
		this.display(" 1. 상품판매      2. POS 관리      0. 종료\n");
		this.display("___________________________________ Select : ");
		if(this.userInput().equals("2")) {
			this.mgrController();
		}
		
	}
	
	

	private void mgrController(){
		/* POS관리 메뉴 제어 
		 * 0 --> 메서드 종료 - 이전화면
		 * 1 --> mgrGoodsCtl(1) - 상품관리
		 * 2 --> mgrMemberCtl(2) - 회원관리
		 * 3 --> mgrStoreCtl(3) - 매장관리
		 * */
		String ui = new String();
		
		this.display(makeTitle("POS관리"));
		this.display(makeMenu(0));
		
		ui = this.userInput();
		
		switch(ui) {
			case "0": break;		
			case "1": mgrGoodsCtl(Integer.parseInt(ui)); break;
			case "2": mgrMemberCtl(Integer.parseInt(ui)); break;
			case "3": mgrStoreCtl(Integer.parseInt(ui)); break;
		}
	}
	

	private void salesController(){
		/* 판매 메뉴 제어 */
		
	}
	
	
	private void mgrGoodsCtl(int recordIdx) {
		/* 상품관리 제어
		 * menu 출력 --> this.menus[recordIdx][?]
		 * */
		this.display(makeTitle("상품관리"));
		this.display(makeMenu(recordIdx));
		
		this.userInput();
		
	}
	
	

	private void mgrMemberCtl(int recordIdx) {
		/* 회원관리 제어
		 * menu 출력 --> this.menus[recordIdx][?]
		 * */
		this.display(makeTitle("회원관리"));
		this.display(makeMenu(recordIdx));
		
	}
	
	private void mgrStoreCtl(int recordIdx) {
		/* 매장관리 제어
		 * menu 출력 --> this.menus[recordIdx][?]
		 * */
		this.display(makeTitle("매장관리"));
		this.display(makeMenu(recordIdx));
	}
	
	/* 상품등록 
	 *  매장코드(d), 상품코드(d), 상품명(d), 재고(d), 매입가격(d), 
	 *  판매가격(d), 유통기한(d), 면세여부(s), 판매대상(s), 상품상태(s)	
	 * */
	/* 회원등록
	 *  전화번호(d)
	 * */
	/* 매장등록 
	 *  매장코드(사업자등록번호, d), 매장명(d), 대표자명(d), 주소(d)
	 * */
	
	
	private String makeMenu(int recordIdx) {
		StringBuffer menu = new StringBuffer(); //String을 저장하는 임시저장소
		
		menu.append("[ Select Menu ] --------------------------------\n");
		for(int colIdx=0; colIdx < menus[recordIdx].length; colIdx++) {
			menu.append(colIdx + ". " +this.menus[recordIdx][colIdx] + "     ");
			if(colIdx % 2 == 1 || colIdx == this.menus[recordIdx].length-1) menu.append("\n");
		}
		menu.append("___________________________________ Select : ");
			
		return menu.toString(); //임시저장소에 있는 String 데이터를 변환
	}
	
	private String makeTitle(String subject) {
		/*
		 * 타이틀 동적 생성(현재 화면의 위치) 
		 * 같은 화면만 나온다면 메소드로 정의하지 않아도 됨
		 */
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
		title.append("\n\n");
		
		return title.toString();
		
	}
	
	
	public String userInput() {
		//Scanner sc = new Scanner(System.in);
		return sc.next();
		/*
		 * 스캐너를 여러번 사용 = 메모리에 여러번 띄우게 됨 
		 * --> 멤버로 만들어서 한번만 메모리에 띄우고 사용 후 닫기
		 */
		
	}
	
	
	public void display(String text) {
		System.out.print(text);
		
	}
	
}
