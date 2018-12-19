package User;

import java.util.InputMismatchException;
import java.util.Scanner;

import Auditorium.AuditoriumManagement;
import Client.ClientManagement;
import Payment.PaymentProc;
import movie.MovieManagement;
import movie.MovieProc;
import theater.TheaterManagement;
import ticketing.TicketingProc;

public class UserManagement {

	private static String logIn_id;

	public static void main(String[] args) {

		UserProc mm = new UserProc();
		boolean run = true;
		
		while (run) {
			System.out.println();
			System.out.println("============== ��ȭ ���� �ֿܼ� ���� ���� ȯ���մϴ�! ==============");
			System.out.println("1. �α���		2. ȸ������");
			System.out.println("3. ����");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 4)) { // 1~3 ���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-3] �޴��� �������ּ���!");
			}

			switch (num) {
			case 1:
				int r = mm.loginUser();
				if (r == 1) { // ������
					manager_view();
				} else if (r == 0) {
					logIn_id = mm.getLoggedId();
					user_view();
				}
				break;
			case 2:
				mm.SignUpUser();
				break;
			case 3:
				System.out.println("���α׷��� �����մϴ�.");
				run=false;
				break;
			}
		}

	}

	public static void manager_view() {

		AuditoriumManagement am = new AuditoriumManagement();
		TheaterManagement tm = new TheaterManagement();
		MovieManagement mm = new MovieManagement();
		ClientManagement cm = new ClientManagement();
		boolean run_manager = true;

		while (run_manager) {
			System.out.println();
			System.out.println("=================================");
			System.out.println("1.��ȭ��");
			System.out.println("2.�󿵰�");
			System.out.println("3.��ȭ");
			System.out.println("4.ȸ��");
			System.out.println("5.�α׾ƿ�");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num <= 5)) {
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-5] �޴��� �������ּ���!");
			}

			switch (num) {
			case 1:
				tm.theater_management();
				break;
			case 2:
				am.auditorium_management();
				break;
			case 3:
				mm.movie_management();
				break;
			case 4:
				cm.client_management();
				break;
			case 5:
				System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
				run_manager = false;
			}
		}
	}

	public static void user_view() {

		UserProc mm = new UserProc();
		MovieProc mp = new MovieProc();
		TicketingProc tp = new TicketingProc();
		PaymentProc pp = new PaymentProc();
		boolean run_User = true;

		while (run_User) {
			System.out.println();
			System.out.println("=================================");
			System.out.println("1.ȸ����������");
			System.out.println("2.ȸ�� Ż��");
			System.out.println("3.��ȭ �˻�");
			System.out.println("4.��ȭ ����");
			System.out.println("5.��ȭ ����");
			System.out.println("6.���� ��Ȳ Ȯ��");
			System.out.println("7.���� ���");
			System.out.println("8.Ƽ�� �߱�");
			System.out.println("9.�α׾ƿ�");
			System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");
			System.out.print("�޴��� �Է��ϼ��� : ");

			Scanner scn = new Scanner(System.in);
			int num = 0;
			try {
				num = scn.nextInt();
				if (!(num > 0 && num < 10)) { // 1~7 ���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					throw new InputMismatchException();
				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-8] �޴��� �������ּ���!");
			}

			switch (num) {
			case 1:
				mm.updateUser();
				break;
			case 2:
				boolean r = mm.deleteUser();
				if (r == true) {
					run_User = false;
					break;
				} else {
					break;
				}
			case 3:
				mp.showMovieList_for_user();
				break;
			case 4:
				tp.insertTicketing(logIn_id);
				break;
			case 5:
				pp.insertPayment(logIn_id);
				break;
			case 6:
				tp.printTicket(logIn_id);
				break;
			case 7:
				tp.cancelArrangement(logIn_id);
				break;
			case 8:
				tp.getPrintTicket(logIn_id);
				break;
			case 9:
				System.out.println("�α׾ƿ� �Ǿ����ϴ�.");
				run_User = false;
			}
		}
	}
}
