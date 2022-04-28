package frontend;

import java.util.Scanner;

public class FrontEnd {
	
	Scanner sc;
	
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
		
		String ui = new String();
		
		//메뉴입력
		
		while(true) {
			while(true) {
				this.display(this.makeTitle("Home"));
				this.display("[ Select Menu ] --------------------------------\n");
				this.display("  1. 상품판매      2. POS 관리      0. 종료\n");
				this.display("___________________________________ Select : ");
				
				ui = userInput();
				//숫자 유효성검사
				if(ui.equals("1")) {
					salesController();
				}
				else if(ui.equals("2")) {
					mgrController();
				}
				else if(ui.equals("0")) {
					break;
				}
				else {
					this.display("\n아무키나 입력 후 다시 선택해주세요 : ");
					sc.next();
				}
			} break;
		}
	}
	
	
	private void salesController() {
		/* 상품판매 */
	}
	
	private void mgrController() {
		/* POS관리 */
		//메뉴출력
		//makeMenu가 필드에서 인덱스로 읽어오는방법, controller에서 배열전체를 보내는 방법
		String[][] menus = {
				{"이전화면", "상품관리", "회원관리", "매장관리"}, 
	            {"이전화면", "상품등록", "상품수정"}, 
	            {"이전화면", "회원등록", "회원수정"}, 
	            {"이전화면", "매장등록", "매장수정"}
	           };
		//String posMenu = new String();
		int posMenu;
		
			while(true) {
				this.display(this.makeTitle("POS관리"));
				this.display(makeMenu(menus[0], false));
				
				posMenu = Integer.parseInt(userInput());
				/* 유효성 검사, 출력할 메뉴 배열 보내기
				 * mgr___Ctl(타이틀, 메뉴배열)
				 *  */
				if(posMenu == 1) {
					mgrGoodsCtl(menus[0][posMenu], menus[posMenu]);
				}
				else if(posMenu == 2) {
					mgrMemberCtl(menus[0][posMenu], menus[posMenu]);
				}
				else if(posMenu == 3) {
					mgrStoreCtl(menus[0][posMenu], menus[posMenu]);
				}
				else if(posMenu == 0) {
					break;
				}
				else { 
					this.display("\n아무키나 입력 후 다시 선택해주세요 : ");
					sc.next();
				}
			}		
	}

	private void mgrGoodsCtl(String subject, String[] menu) {
		/* 상품관리 제어 */
		String goodsMenu = new String();
		String[] goodsInfo;
		
		while(true) {
			this.display(this.makeTitle(subject));
			this.display(this.makeMenu(menu, false));
			
			goodsMenu = userInput();
			
			if(goodsMenu.equals("1"))
				goodsInfo = this.getGoodsInfo();
			else if(goodsMenu.equals("2"))
				goodsInfo = this.modGoodsInfo();
			else if(goodsMenu.equals("0"))
				break;
			else ;
		}
	}
	
	private String[] getGoodsInfo() {
		String[] infoList = {"매장코드","상품코드","상품이름","상품재고","매입가격","판매가격","면세여부","판매대상","상품상태"};
		String[] goodsInfo = new String[10];
			
		this.display(this.makeTitle("상품등록"));
		this.display(this.makeItem("입력항목"));
		
		for(int colIdx = 0;colIdx<infoList.length;colIdx++) {
			this.display("  "+infoList[colIdx]+" : ");
			goodsInfo[colIdx] = this.userInput();
		}
		
		return goodsInfo;
	}
	
	private String[] modGoodsInfo() {
		String[] modList = {"상품코드","상품재고","매입가격","판매가격","상품상태"};
		String[] goodsInfo = new String[5];
		/*goodsInfo = 1~4 db에서 불러와서 넣어두기 (값이 들어가 있어야 함)*/
		int modNumber;
		
		this.display(this.makeTitle("상품수정"));
		this.display(this.makeItem("입력항목"));
		this.display("  "+modList[0] + " : "); /*0번을 출력하지 않으므로&필수입력 먼저 입력*/
		goodsInfo[0] = this.userInput();
		
		this.display(this.makeTitle("상품수정"));
		this.display(this.makeMenu(modList, true)); 
		modNumber = Integer.parseInt(this.userInput());
		this.display("  "+modList[modNumber]+" : ");
		goodsInfo[modNumber] = this.userInput();
		
		return goodsInfo;
	}
	
		private void mgrMemberCtl(String subject, String[] menu) {
		/* 회원관리 제어 */
		String memberMenu = new String();
		
		this.display(this.makeTitle(subject));
		this.display(this.makeMenu(menu, false));
		
		memberMenu = userInput();
	}
	
	private void mgrStoreCtl(String subject, String[] menu) {
		/* 매장관리 제어 */
		String storeMenu = new String();
		
		this.display(this.makeTitle(subject));
		this.display(this.makeMenu(menu, false));
		
		storeMenu = userInput();
	}
	
	private String makeItem(String item) {
		StringBuffer sb = new StringBuffer();
		sb.append("[ "+ item +" ] _____________________________________\n\n");
		return sb.toString();
	}
	


	
	private String makeMenu(String[] menus, boolean type) {
		StringBuffer sb = new StringBuffer();
		int colIdx;
		
		sb.append("[ Select Menu ] --------------------------------\n");
		
		//배열에 들어있는 메뉴 추출 - 0번이 이전화면인 경우 0번을 마지막에 출력 => 반복x 밖으로 빼기
		for(colIdx = 1;colIdx < menus.length;colIdx++) {
			if(colIdx % 2 == 1) sb.append("  ");
			sb.append(colIdx+". "+menus[colIdx]);
			if(colIdx % 2 == 0 ) sb.append("\n");
			else sb.append("        ");
		}
		
		if(colIdx % 2 == 1 && !type) sb.append("  "); //밖으로 뺀 값이라 맨앞에 임의로 공백을 넣어주기
		
		//!0번이 이전화면인 경우 0번을 마지막에 출력! type으로 구분
		if(!type) sb.append("0. "+menus[0]+"\n");
		
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
