package User;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;


public class UserProc {

	UserDAO dao;
	static String loggedId;

	public String getLoggedId() {
		return loggedId;
	}

	public void setLoggedId(String loggedId) {
		this.loggedId = loggedId;
	}

	public UserProc() {
		dao = new UserDAO();
	}

	public void SignUpUser() {

		Scanner scn = new Scanner(System.in);

		System.out.println("���� ������ �Է����ּ���.");
		System.out.print("�����̵� : ");
		String client_id = reInput();
		System.out.print("����й�ȣ : ");
		String client_password = reInput();
		System.out.print("���̸� : ");
		String client_name = reInput();
		System.out.print("������ : ");
		String client_birth = reInput();
		System.out.print("���ּ� : ");
		String client_address = reInput();
		System.out.print("����ȭ��ȣ : ");
		String client_number = reInput();

		UserDTO dto = new UserDTO(client_id, client_password, client_name, client_birth, client_address, client_number,
				0, 0);
		boolean r = dao.signUpUser(dto);

		if (r) {
			System.out.println("ȸ�� ������ ���������� �Ϸ�Ǿ����ϴ�.");
		} else {
			System.out.println("ȸ�� ������ ���������� �̷����� �ʾҽ��ϴ�.");
		}

	}

	public int loginUser() {
		Scanner scn = new Scanner(System.in);

		System.out.print("�����̵� : ");
		String client_id = reInput();
		System.out.print("����й�ȣ : ");
		String client_password = reInput();
		boolean r = dao.loginUser(client_id, client_password);
		
		System.out.println(client_id +" "+ client_password);
		
		if (client_id.equals("root") && client_password.equals("root")) {
			if (r) {
				System.out.println("�����ڴ� �ȳ��ϼ���!");
				return 1;
				//������ �α��� 1 ����
			} else {
				System.out.println("�α����� �̷������ �ʾҽ��ϴ�.");
				return -1;
			}
		} else {
			if (r) {
				setLoggedId(client_id);
				System.out.println("�α����� ���������� �Ϸ�Ǿ����ϴ�.");
				return 0;
				//�� �α��� 0����
			} else {
				System.out.println("�α����� �̷������ �ʾҽ��ϴ�.");
				return -1;
			}
		}
	}

	public void updateUser() {

		Scanner scn = new Scanner(System.in);

		UserDTO dto = dao.getUser(getLoggedId());
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("�����۾��� ����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				System.out.println("##�Է��� �Ͻ��������� ������ ������ �״�� �����˴ϴ�.");

				System.out.print("�������� ��й�ȣ : ");
				String password = scn.nextLine();
				if (password.trim().equals(""))
					password = dto.getUser_password();
				System.out.print("�������� �̸� : ");
				String name = scn.nextLine();
				if (name.trim().equals(""))
					name = dto.getUser_name();
				System.out.print("�������� ���� : ");
				String birth = scn.nextLine();
				if (birth.trim().equals(""))
					birth = dto.getUser_birth();
				System.out.print("�������� �ּ� : ");
				String address = scn.nextLine();
				if (address.trim().equals(""))
					address = dto.getUser_address();
				System.out.print("�������� ��ȭ��ȣ : ");
				String number = scn.nextLine();
				if (number.trim().equals(""))
					number = dto.getUser_number();

				String id = dto.getUser_id();
				int point = dto.getUser_point();
				int count = dto.getUser_purchase_count();

				dto = new UserDTO(id, password, name, birth, address, number, point, count);

				boolean r = dao.updateUser(dto, loggedId);
				if (r) {
					System.out.println("ȸ���� ������ ������ ���� �����Ǿ����ϴ�.");
					System.out.println(dto.getInfo());
				} else {
					System.out.println("ȸ���� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		}
	}

	public boolean deleteUser() {

		Scanner scn = new Scanner(System.in);

		UserDTO dto = dao.getUser(getLoggedId());
		if (dto != null) {
			System.out.println("������ ȸ�� Ż�� �Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteUser(loggedId);

				if (r) {
					System.out.println(loggedId + "ȸ��Ż�� ���������� �̷�������ϴ�.");
					return true;
				} else {
					System.out.println("ȸ��Ż�� �̷������ �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("ȸ��Ż�� �۾��� ����Ͽ����ϴ�.");
			}
		}
		return false;
	}

	public String reInput() {
		Scanner scn = new Scanner(System.in);
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
