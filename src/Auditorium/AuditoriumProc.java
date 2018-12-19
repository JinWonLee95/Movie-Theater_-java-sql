package Auditorium;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AuditoriumProc {

	AuditoriumDAO dao;
	String a = "";

	public AuditoriumProc() {
		dao = new AuditoriumDAO();
	}

	public void insertAuditorium(String t_name) {
		Scanner scn = new Scanner(System.in);
		System.out.println("�󿵰� ������ �Է����ּ���.");
		System.out.print("���̸� : ");
		String a_name = reInput();
		System.out.print("���¼� �� : ");
		int seat = scn.nextInt();
		while(true){
			if(seat > 0){
				break;
			}
			System.out.println("���¼� ���� 0������ ���ƾ� �մϴ�.");
			System.out.print("���¼� �� : ");
			seat = scn.nextInt();
		}
		a = a_name;
		AuditoriumDTO dto = new AuditoriumDTO(a_name, seat, t_name);
		boolean r = dao.insertAuditorium(dto);

		if (r) {
			System.out.println("�󿵰� ����� ���������� �Ϸ�Ǿ����ϴ�.");
		} else {
			System.out.println("�󿵰� ����� ���������� �̷����� �ʾҽ��ϴ�.");
		}

	}

	public void showAuditoriumList() {

		List<AuditoriumDTO> list = dao.getAuditoriumList();

		System.out.println("                             Auditorium List");
		System.out.println("============================================================================");

		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �󿵰� �̸� \t\t�¼� ��\t\t��ȭ�� �̸�");
			System.out.println("============================================================================");

			for (AuditoriumDTO dto : list) {
				System.out.println(
						"\t" + dto.getAuditorium_name() + "\t\t" + dto.getSeat() + "\t\t" + dto.getTheater_name());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}

		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");

	}

	public void showAuditoriumListByTheater(String t_name) {

		List<AuditoriumDTO> list = dao.getAuditoriumListByTheater(t_name);

		System.out.println("                             Auditorium List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �󿵰� �̸� \t\t�¼� ��\t\t��ȭ�� �̸�");
			System.out.println("============================================================================");

			for (AuditoriumDTO dto : list) {
				System.out.println(
						"\t" + dto.getAuditorium_name() + "\t\t" + dto.getSeat() + "\t\t" + dto.getTheater_name());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}
	
	public boolean confirmAuditoriumListByTheater(String t_name, String a_name){
		List<AuditoriumDTO> list = dao.getAuditoriumListByTheater(t_name);
		if (list != null && list.size() > 0) {
			for (AuditoriumDTO dto : list) {
				if(dto.getAuditorium_name().equals(a_name)){
					return true;
				}
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		return false;
	}

	public void deleteAuditorium() {
		Scanner scn = new Scanner(System.in);
		System.out.print("������ �󿵰��� �󿵰� �̸��� �Է����ּ��� : ");
		String a_name = scn.nextLine();
		AuditoriumDTO dto = dao.getAuditorium(a_name);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("�� �󿵰��� ������ ������ �����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteAuditorium(a_name);

				if (r) {
					System.out.println(a_name + "�󿵰��� ������ ���������� �����Ǿ����ϴ�.");
				} else {
					System.out.println("�󿵰��� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		} else {

			System.out.println("�Է��Ͻ� �󿵰� �̸��� �ش��ϴ� �󿵰��� �������� �ʽ��ϴ�.");
		}
	}

	public String reInput() {

		String str;
		Scanner scn = new Scanner(System.in);
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
