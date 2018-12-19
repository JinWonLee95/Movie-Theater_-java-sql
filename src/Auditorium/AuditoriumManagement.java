package Auditorium;
import java.util.InputMismatchException;
import java.util.Scanner;

import Time_table.Time_tableProc;
import theater.TheaterDAO;
import theater.TheaterManagement;
import theater.TheaterProc;


public class AuditoriumManagement {	
	
	public void auditorium_management() {
		
		AuditoriumProc mm = new AuditoriumProc();
		Time_tableProc tp = new Time_tableProc();
		TheaterDAO tdao = new TheaterDAO();
		
		boolean run = true;
		String t_name;
		
		while (run) {
			System.out.println();
			System.out.println("============== �󿵰� ���� ���α׷� ==============");
			System.out.println("1. �󿵰� ���   2. �󿵰� ���");			
			System.out.println("3. �󿵰� ����   4. ��ȭ �� ����");
			System.out.println("5. �ڷ�");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");
			
			Scanner scn = new Scanner(System.in);
			Scanner scn2 = new Scanner(System.in);
			int num=0;
			try {
				num = scn.nextInt();
				if(!(num>0 && num<6)){ 
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-4] �޴��� �������ּ���!");
			}
			
			switch (num) {
			case 1:
				mm.showAuditoriumList();	
				break;
			case 2:
				while (true) {
					new TheaterProc().showTheaterList();
					System.out.print("�� �󿵰��� �߰��� ��ȭ�� �̸��� �Է����ּ���. : ");
					t_name = scn2.nextLine();
					if (tdao.confirmTheater(t_name) == true){
						break;
					}else{
						System.out.println("�� ��ȭ���� �������� �ʽ��ϴ�.");
					}
				}
				mm.insertAuditorium(t_name); 
				break;
			case 3:
				mm.showAuditoriumList();
				mm.deleteAuditorium(); 			
				break;
			case 4:
				tp.showTime_tableList();
				tp.deleteTime_table();
				break;
			case 5:
				System.out.println("�ڷΰ���");
				run = false;	
			}
		}	
		
	}
	
}
