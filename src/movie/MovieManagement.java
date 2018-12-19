package movie;
import java.util.InputMismatchException;
import java.util.Scanner;


public class MovieManagement {	
	
	public void movie_management() {
		
		MovieProc mm = new MovieProc();
		boolean run = true;
		
		while (run) {
			System.out.println();
			System.out.println("============== ��ȭ ���� ���α׷� ==============");
			System.out.println("1. ��ȭ ���");			
			System.out.println("2. ��ȭ ���   3. ��ȭ ����   4. ��ȭ ���� ����");
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
				mm.showMovieList_for_manager();
				break;
			case 2:
				mm.insertMovie();
				break;
			case 3:
				mm.showMovieList_for_manager();
				mm.deleteMovie();			
				break;
			case 4:
				mm.showMovieList_for_manager();
				mm.updateMovie();
				break;
			case 5:
				System.out.println("�ڷΰ���");
				run = false;		
			}
		}	
		
	}
	
}
