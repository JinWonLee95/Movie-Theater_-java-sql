package Payment;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import User.UserDAO;
import User.UserDTO;
import ticketing.TicketingDAO;
import ticketing.TicketingDTO;
import ticketing.TicketingProc;

public class PaymentProc {

	PaymentDAO dao;
	int using_point;
	Scanner scn = new Scanner(System.in);

	public PaymentProc() {
		dao = new PaymentDAO();
	}

	public void insertPayment(String c_id) {
		int p_no = showTicketingList_for_pay(c_id);

		if (p_no != 0) {
			int ticket_no = dao.getPayment(p_no).getTicket_no();
			int price = payment_process(ticket_no);
			String pay_option = pay_option(price);
			int isPayed = 1;
			PaymentDTO dto = new PaymentDTO(p_no, ticket_no, price, pay_option, isPayed);
			TicketingDAO t_dao = new TicketingDAO();
			TicketingDTO t_dto = t_dao.getTicketing(ticket_no);
			boolean r = dao.updatePayment(dto, p_no, using_point, t_dto.getPeople());

			if (r) {
				System.out.println("Ƽ�� ���Ű� ���������� �Ϸ�Ǿ����ϴ�.");
				dao.addPurchased(c_id, ticket_no);
			} else {
				System.out.println("Ƽ�� ���Ű� ���������� �̷����� �ʾҽ��ϴ�.");
			}
		} else {
			System.out.println("������ ������ �������� �ʽ��ϴ�.");
		}
	}

	public int payment_process(int ticket_no) {
		TicketingDAO t_dao = new TicketingDAO();
		TicketingDTO t_dto = t_dao.getTicketing(ticket_no);
		UserDAO u_dao = new UserDAO();
		UserDTO u_dto = u_dao.getUser_byTicketNo(ticket_no);

		int people = t_dto.getPeople();
		int price = 10000 * people;
		int point = u_dto.getUser_point();
		int final_price;

		System.out.println("���� ������ " + people + "�� * 10000������  ��  = " + price + "���Դϴ�.");

		if (point < 1000) {
			System.out.println("ȸ������ ����Ʈ : " + point + " (����Ʈ ����� 1000 ����Ʈ �̻��� ��� ����� �����մϴ�.)");
			final_price = dao.getFinal_price(ticket_no, 0);
		} else {
			System.out.println("ȸ������ ����Ʈ�� �� " + point + " ����Ʈ�Դϴ�.");
			System.out.print("����Ʈ�� ����Ͻðڽ��ϱ�? (��� �� ����Ʈ�� ��ȯ���� �ʽ��ϴ�) (Y/N) : ");
			String input = scn.next();

			if (input.equalsIgnoreCase("y")) {

				while (true) {
					System.out.print("�󸶸� ����Ͻðڽ��ϱ�? : ");
					using_point = scn.nextInt();
					if (using_point > point || using_point > price) {
						System.out.println("�Է��Ͻ� ����Ʈ�� �����ϰ� �ִ� ����Ʈ���� ũ�ų� ���� ���ݺ��� Ů�ϴ�. �ٽ� �Է��� �ּ���.");
					} else {
						final_price = dao.getFinal_price(ticket_no, using_point);
						System.out.println("�� ������ 10000 * " + people + "�� - " + using_point + " ����Ʈ�̹Ƿ� �����ؾ� �� ������ "
								+ final_price + "�� �Դϴ�.");
						break;
					}
				}
			} else {
				final_price = dao.getFinal_price(ticket_no, 0);
				System.out.println("�� ������ 10000 * " + people + "���̹Ƿ� �����ؾ� �� ������ " + final_price + " �Դϴ�.");
			}
		}
		return final_price;
	}

	public String pay_option(int final_price) {
		int num = 0;
		System.out.println("1. ���ͳ� ����          2. ���� ����");
		System.out.println("============== aaaaaaaaaaaaaaaaaa ==============");

		try {
			System.out.print("���� ����� �������ּ���. : ");
			num = scn.nextInt();
			while (!(num == 1 || num == 2)) { // 1~2���� ���ڰ� �ԷµǸ� ���� ���� �߻�
				System.out.print("�߸��� �Է��Դϴ�. �ٽ� �Է����ּ��� : ");
				num = scn.nextInt();
			}
		} catch (InputMismatchException e) {
		}

		if (num == 1) {
			System.out.println("���ͳ� ������ �����մϴ�.");
			System.out.println("������ ī���ȣ�� �Է����ּ���.(- ����) : ");
			scn.nextLong();
			System.out.println("ī���� ��й�ȣ�� �Է����ּ���. : ");
			scn.nextInt();
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			return "Internet";

		} else {
			int c = 0;
			int c2 = 0;
			System.out.println("1. ī�� ����          2. ���� ����");
			System.out.print("���� ����� �������ּ���. : ");
			try {
				c = scn.nextInt();
				while (c != 1 && c != 2) { // 1~2���� ���ڰ� �ԷµǸ� ���� ���� �߻�
					System.out.print("�߸� �� �Է��Դϴ�. �ٽ� �Է��� �ּ���: ");
					c = scn.nextInt();

				}
			} catch (InputMismatchException e) {
				System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-2] �޴��� �������ּ���!");
			}

			switch (c) {
			case 1:
				System.out.println("ī�带 �޾ҽ��ϴ�.");
				System.out.println("���� �Ǿ����ϴ�. �̿����ּż� �����մϴ�.");
				break;
			case 2:
				System.out.println("������ �������ּ���. : ");
				while (true) {
					c2 = scn.nextInt();
					if (c2 < final_price) {
						System.out.println("�մ�, �����Ͻ� �ݾ��� �����մϴ�. �����ϼž��� �ݾ��� " + final_price + "�� �Դϴ�.");
						System.out.print("�ٽ� �������ּ��� : ");
					} else {
						System.out.println(c2 + "�� �޾ҽ��ϴ�.");
						if (c2 > final_price) {
							System.out.println("�Ž��� �� " + (c2 - final_price) + "���� �����ʽÿ�.");
						}
						System.out.println("������ �Ϸ�Ǿ����ϴ�. �����մϴ�.");
						break;
					}
				}
				break;
			}
			return "on-site";
		}
	}

	public void showPaymentList() {

		List<PaymentDTO> list = dao.getPaymentList();

		System.out.println("                             Payment List");
		System.out.println("========================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("���� \t  ���� ��ȣ \t\t���� ��ȣ\t\t���� ����\t\t���� ���\t\t���� ����");
			System.out.println(
					"============================================================================================");

			for (PaymentDTO dto : list) {
				System.out.println("\t" + dto.getPayment_no() + "\t\t" + dto.getTicket_no() + "\t\t" + dto.getPrice()
						+ "\t\t" + dto.getPay_option() + "\t\t" + dto.getIsPayed());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}

	public int showTicketingList_for_pay(String c_id) {
		List<PaymentDTO> list = dao.getPaymentList_for_pay(c_id);

		System.out.println("                             Payment List");
		System.out.println("============================================================================");

		if (list != null && list.size() > 0 && new PaymentDTO().getIsPayed() != 1) {
			System.out.println("reg.No\t  ���� ��ȣ \t\t���� ��ȣ\t\t���� ����\t\t���� ���\t\t���� ����");
			System.out.println("============================================================================");

			for (PaymentDTO dto : list) {
				if (dto.getIsPayed() != 1) {
					System.out.println("\t" + dto.getPayment_no() + "\t\t" + dto.getTicket_no() + "\t\t"
							+ dto.getPrice() + "\t\t" + dto.getPay_option() + "\t\t" + dto.getIsPayed());
				}
			}

			System.out.println(
					"====================================================================�� " + list.size() + "��=\n");

			while (true) {
				System.out.print("������ ���� ��ȣ�� �Է��� �ּ���. : ");
				int p_no = scn.nextInt();

				for (PaymentDTO dto : list) {
					if (p_no == dto.getPayment_no()) {
						return p_no;
					}
				}
				System.out.println("�Է��Ͻ� ���� ��ȣ�� �������� �ʽ��ϴ�.");
			}
		} else {
			System.out.println("������ �����Ͱ� �����ϴ�. ");
			System.out.println("====================================================================�� " + "0��==\n");
			return 0;
		}
	}

	public String reInput() {
		String str = "";
		while (true) {
			str = scn.nextLine();
			if (!(str == null || str.trim().equals(""))) {
				break;
			} else {
				System.out.println("������ �Է��ϽǼ������ϴ�. �ùٸ����� �Է����ּ���!");
				System.out.print("��");
			}
		}

		return str;
	}

}
