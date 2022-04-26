package loader;

import java.util.*;
import operation.View;

public class Execute {

	public static void main(String[] args) {
		//new View();
		// 계산기 실행 여부
		Scanner scanner = new Scanner(System.in);
		String answer = new String();
		System.out.print("프로그램을 실행하시겠습니까? (y/n): ");
		answer = scanner.next();
		if(answer.equals("y")) {
				new View();
		}
		else {
			scanner.close();
			System.exit(0);
		}

	}

}
