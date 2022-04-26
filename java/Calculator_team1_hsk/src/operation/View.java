package operation;

import java.util.*;

public class View {
	
	public View() {
		control();
	}
	
	//계산기 프로그램 제어 메소드
	public void control() {
		String num1, num2, operator, isRun;
		Operation operation;
		int result = 0;
		
		num1 = new String();
		num2 = new String();
		operator = new String();
		operation = new Operation();
		
		
		while(true) {
			//아무키나 입력받으면 실행
			output();
			output("Press Any Key to Start\n");
			input();
			
			num1 = inputNum();
			
			operator = inputOperator();
			
			num2 = inputNum(operator);
			
			
			while(true) {
				output();
				//****연산 수행 여부
				//출력
				//입력
				//*****************y/n y연산수행 n처음으로 돌아가기 | 다른키 다시입력
				output("연산을 수행하시겠습니까? (y/n): ");
				isRun = input();
				if(isRun.equals("y")) {								//y인 경우
					//탈출
					break;
				}
				else if(isRun.equals("n")) {						//n인 경우
					//처음으로 돌아가기
				}
				else {
					//반복
				}
			}
			
			//****연산 수행
			//입력받은 연산자를 비교 후 연산 메서드 호출
			//사용자 입력값, 결과 출력
			switch(operator) {
			case "+": result = operation.plus(Integer.parseInt(num1), Integer.parseInt(num2)); break;
			case "-": result = operation.minus(Integer.parseInt(num1), Integer.parseInt(num2)); break;
			case "*": result = operation.multi(Integer.parseInt(num1), Integer.parseInt(num2)); break;
			case "/": result = operation.div(Integer.parseInt(num1), Integer.parseInt(num2)); break;
				default:
			}
			
			output();			
			//****연산 재실행 여부
			//출력
			//입력
			//*****************y/n y이어하기 n처음으로 돌아가기 | 다른키 다시입력 |0.종료
			output("사용자 입력값: "+num1+operator+num2+"\n");
			output("결과값: "+result+"\n\n");
			output("y.이어서하기 / n.처음으로 돌아가기 \n*종료하려면 아무키나 누르세요\n");
			isRun = input();
			if(isRun.equals("n")) {
				//처음으로
			}
			else if(isRun.equals("y")) {
				repeat(result);
			}
			else {
				break;
			}
			
		}//while
		
	}
	
	
	//첫번째 숫자 입력 메소드
	public String inputNum() {
		String num;
		output();
		while(true) {
			output("첫번째 숫자를 입력하세요: ");
			num = input();
			if(isNum(num)) {					//숫자가 맞는지 검사
				break;
			}
			else {
				//반복
			}
		}
		return num;
	}
	
	
	
	//두번째 숫자 입력 메소드 *operator변수를 받아 나눗셈을 수행하는지 검사
	public String inputNum(String operator) {
		String num;
		
		//****숫자
		//출력
		//입력
		//유효성검사
		output();
		while(true) {
			output("두번째 숫자를 입력하세요: ");
			num = input();
			if(isNum(num)) {					//숫자가 맞는지 검사
				//나눗셈인 경우 0을 입력받지 않음
				if(operator.equals("/")) {						//연산자가 나눗셈이냐
					if(num.equals("0")) {
						//0임을 알리고 반복
						output("0으로 나눌 수 없습니다. \n아무키나 입력하신 후 다시 입력해주세요.: ");
						input();
						continue;
					}
				}
				//탈출
				break;
			}
			else {
				//반복
			}
		}
		return num;
	}
	
	
	
	//연산자 입력 메소드
	public String inputOperator() {
		String operator;
		//****연산자
		//출력
		//입력
		//유효성검사
		output();
		while(true) {
			output("연산자를 선택하세요\n");
			output("1.더하기 2.빼기 3.곱하기 4.나누기: ");
			operator = input();
			if(operator.equals("1")){					//메뉴에 있는지 검사
				//탈출
				operator = "+";
				break;
			}
			else if(operator.equals("2")) {
				operator = "-";
				break;
			}
			else if(operator.equals("3")) {
				operator = "*";
				break;
			}
			else if(operator.equals("4")) {
				operator = "/";
				break;
			}
			else {
				//반복
			}
		}
		return operator;
	}//inputOperator
	
	
	
	//숫자 유효성 검사 메소드
	public boolean isNum(String num) {
		//숫자가 맞으면 변환 성공, 아니면 에러
		try {
			Integer.parseInt(num);
			return true;
		} catch(Exception e) {
			return false;
		}
	}//isNum
	
	
	
	//입력 메소드
	public String input() {
		Scanner in = new Scanner(System.in);
		return in.next();
	}//input
	
	
	
	//출력 메소드
	public void output(String text) {
		System.out.print(text);
	}//output
	
	
	
	//타이틀 출력 메소드
	public void output() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n"
				+ "****************************************************"
				+ "\n"
				+ "                 Calculator v2.0"
				+ "                                 Designed by Team1"
				+ "\n"
				+ "****************************************************\n");
	}//output
	
	
	
	//연산 이어서 하기 메소드
	public void repeat(int result_1) {
		String operator, isRun, num2;
		int result_2 = 0;
		Operation operation = new Operation();
		
		operator = inputOperator();
		
		num2 = inputNum(operator);
		
		
		while(true) {
			output();
			//****연산 수행 여부
			//출력
			//입력
			//*****************y/n y연산수행 n처음으로 돌아가기 | 다른키 다시입력
			output("연산을 수행하시겠습니까? (y/n): ");
			isRun = input();
			if(isRun.equals("y")) {								//y인 경우
				//탈출
				break;
			}
			else if(isRun.equals("n")) {						//n인 경우
				//처음으로 돌아가기
			}
			else {
				//반복
			}
		}
		
		//****연산 수행
		//입력받은 연산자를 비교 후 연산 메서드 호출
		//사용자 입력값, 결과 출력
		switch(operator) {
		case "+": result_2 = operation.plus(result_1, Integer.parseInt(num2)); break;
		case "-": result_2 = operation.minus(result_1, Integer.parseInt(num2)); break;
		case "*": result_2 = operation.multi(result_1, Integer.parseInt(num2)); break;
		case "/": result_2 = operation.div(result_1, Integer.parseInt(num2)); break;
			default:
		}
		
		output();			
		//****연산 재실행 여부
		//출력
		//입력
		//*****************y/n y이어하기 n처음으로 돌아가기 | 다른키 다시입력 |0.종료
		output("사용자 입력값: "+result_1+operator+num2+"\n");
		output("결과값: "+result_2+"\n\n");
		output("y.이어서하기 / n.처음으로 돌아가기 \n*종료하려면 아무키나 누르세요\n");
		isRun = input();
		if(isRun.equals("n")) {
			//처음으로
		}
		else if(isRun.equals("y")) {
			repeat(result_2);
		}
		else System.exit(0);
		
	}//repeat
	
	
}
