package theater;

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

import Auditorium.AuditoriumProc;

public class TheaterProc {

	TheaterDAO dao;

	public TheaterProc() {
		dao = new TheaterDAO();
	}

	public void insertTheater() {

		Scanner scn = new Scanner(System.in);
		AuditoriumProc ap = new AuditoriumProc();
		System.out.println("��ȭ�� ������ �Է����ּ���.");
		System.out.print("���̸� : ");
		String name = reInput();
		System.out.print("���ּ� : ");
		String address = reInput();
		System.out.print("����ȭ��ȣ : ");
		String number = reInput();
		
		TheaterDTO dto = new TheaterDTO(name, address, number);
		
		boolean r = dao.insertTheater(dto);

		if (r) {
			System.out.println("��ȭ�� ����� ���������� �Ϸ�Ǿ����ϴ�.");
			System.out.println(name + " ��ȭ���� �󿵰��� �߰��Ͻʽÿ�.");
			ap.insertAuditorium(name);
			
			while (true) {
				System.out.print("�󿵰��� �� �߰��Ͻðڽ��ϱ�?(Y/N) :");
				String input = scn.nextLine();
				if (input.equalsIgnoreCase("y")) {
					ap.insertAuditorium(name);
				} else {
					System.out.println("�󿵰� �߰� �۾��� ����Ͽ����ϴ�.");
					break;
				}
			}
		} else {
			System.out.println("��ȭ�� ����� ���������� �̷����� �ʾҽ��ϴ�.");
		}

	}

	public void showTheaterList() {

		List<TheaterDTO> list = dao.getTheaterList();

		System.out.println("                             Theater List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �̸� \t\t�ּ�\t\t����ó");
			System.out.println("============================================================================");

			for (TheaterDTO dto : list) {
				System.out.println("\t" + dto.getTheater_name() + "\t\t" + dto.getTheater_address() + "\t\t"
						+ dto.getTheater_number());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}

	public void updateTheater() {

		Scanner scn = new Scanner(System.in);
		System.out.println("������ ��ȭ���� ��ȭ�� �̸��� �Է����ּ��� : ");
		System.out.print("��");
		String t_name = scn.nextLine();
		TheaterDTO dto = dao.getTheater(t_name);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("�����۾��� ����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				System.out.println("##�Է��� �Ͻ��������� ������ ������ �״�� �����˴ϴ�.");
				System.out.print("�������� �̸� : ");
				String name = scn.nextLine();
				if (name.trim().equals(""))
					name = dto.getTheater_name();
				System.out.print("�������� �ּ� : ");
				String address = scn.nextLine();
				if (address.trim().equals(""))
					address = dto.getTheater_address();
				System.out.print("�������� ��ȭ��ȣ : ");
				String number = scn.nextLine();
				if (number.trim().equals(""))
					number = dto.getTheater_number();

				dto = new TheaterDTO(name, address, number);

				boolean r = dao.updateTheater(dto, t_name, name);
				if (r) {
					System.out.println("��ȭ���� ������ ������ ���� �����Ǿ����ϴ�.");
					System.out.println(dto.getInfo());
				} else {
					System.out.println("��ȭ���� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		} else {
			System.out.println("�Է��Ͻ� ��ȭ�� �̸��� �ش��ϴ� ��ȭ���� �������� �ʽ��ϴ�.");
		}
	}

	public void deleteTheater() {

		Scanner scn = new Scanner(System.in);
		System.out.print("������ ��ȭ���� ��ȭ�� �̸��� �Է����ּ��� : ");
		String t_name = scn.nextLine();
		TheaterDTO dto = dao.getTheater(t_name);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("�� ��ȭ���� ������ ������ �����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteTheater(t_name);

				if (r) {
					System.out.println(t_name + "��ȭ���� ������ ���������� �����Ǿ����ϴ�.");
				} else {
					System.out.println("��ȭ���� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		} else {

			System.out.println("�Է��Ͻ� ��ȭ�� �̸��� �ش��ϴ� ��ȭ���� �������� �ʽ��ϴ�.");
		}
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
