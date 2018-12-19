package theater;

import java.util.InputMismatchException;
import java.util.Scanner;


public class TheaterManagement {	
	
	public void theater_management() {
		
		TheaterProc mm = new TheaterProc();
		boolean run = true;
		
		while (run) {
			System.out.println();
			System.out.println("============== ��ȭ�� ���� ���α׷� ==============");
			System.out.println("1. ��ȭ�� ���");			
			System.out.println("2. ��ȭ�� ���   3. ��ȭ�� ����   4. ��ȭ�� ���� ����");
			System.out.println("5. �ڷ�");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");
			
			Scanner scn = new Scanner(System.in);
			int num=0;
			try {
				num = scn.nextInt();
				if(!(num>0 && num<6)){ //1~5���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-5] �޴��� �������ּ���!");
			}
			
			switch (num) {
			case 1:
				mm.showTheaterList();	
				break;
			case 2:
				mm.insertTheater(); 
				break;
			case 3:
				mm.showTheaterList();
				mm.deleteTheater(); 				
				break;
			case 4:
				mm.showTheaterList();
				mm.updateTheater(); 
				break;
			case 5:
				System.out.println("�ڷΰ���");
				run = false;
			}
		}	
		
	}
	
}
