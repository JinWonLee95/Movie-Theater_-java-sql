package movie;

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

import Auditorium.AuditoriumDTO;
import Auditorium.AuditoriumProc;
import Time_table.Time_tableProc;
import theater.TheaterDAO;
import theater.TheaterProc;

public class MovieProc {

	MovieDAO dao;
	Scanner scn = new Scanner(System.in);


	public MovieProc() {
		dao = new MovieDAO();
	}

	public void insertMovie() {

		AuditoriumProc ap = new AuditoriumProc();
		TheaterProc thp = new TheaterProc();
		Time_tableProc tp = new Time_tableProc();
		TheaterDAO td = new TheaterDAO();
		String m_id = "";
		String t_name = "";
		String a_name = "";
		
		System.out.println("��ȭ ������ �Է����ּ���.");
		while (true) {
			System.out.print("�� ��ȭ id : ");
			m_id = reInput();
			if (dao.confirmMovie(m_id) == true){
				System.out.println("�����ϴ� ��ȭ id�Դϴ�.");
			}else{
				break;
			}
		}
		System.out.print("�� �̸� : ");
		String m_name = reInput();
		System.out.print("�� ���� : ");
		String director = reInput();
		System.out.print("�� �⿬ : ");
		String actor = reInput();
		System.out.print("�� ��� : ");
		String rating = reInput();
		System.out.print("�� ���� : ");
		String info = reInput();
		while (true) {
			thp.showTheaterList();
			System.out.print("�� ��ȭ�� �̸�: ");
			t_name = reInput();
			if (td.confirmTheater(t_name) == true){
				break;
			}else{
				System.out.println("�� ��ȭ���� �������� �ʽ��ϴ�.");
			}
		}
		
		while (true) {
			ap.showAuditoriumListByTheater(t_name);
			System.out.print("�� �󿵰� �̸�: ");
			a_name = reInput();
			if (ap.confirmAuditoriumListByTheater(t_name, a_name) == true){
				break;
			}else{
				System.out.println("�� �󿵰��� �������� �ʽ��ϴ�.");
			}
		}
		if (tp.showTime_tableExist(a_name)) {
			System.out.println("�� �󿵰����� �̹� ���ϴ� ��ȭ�� �ֽ��ϴ�.");
		} else {
			tp.insertTime_table(a_name, m_id);
			MovieDTO dto = new MovieDTO(m_id, m_name, director, actor, rating, info, 0, t_name);
			boolean r = dao.insertMovie(dto);

			if (r) {
				System.out.println("��ȭ ����� ���������� �Ϸ�Ǿ����ϴ�.");
			} else {
				System.out.println("��ȭ ����� ���������� �̷����� �ʾҽ��ϴ�.");
			}
		}
	}

	public void showMovieList_for_manager() {

		List<MovieDTO> list = dao.getMovieList_for_manager();

		System.out.println("                             Movie List");
		System.out.println("========================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("���� \t  id \t\t����\t\t����\t\t�⿬\t\t���\t\t����\t\t���� Ƚ��\t\t��ȭ��");
			System.out.println(
					"============================================================================================");

			int order = 1;
			for (MovieDTO dto : list) {
				System.out.println(order + "��\t" + dto.getMovie_id() + "\t\t" + dto.getMovie_name() + "\t\t"
						+ dto.getMovie_director() + "\t\t" + dto.getMovie_actor() + "\t\t" + dto.getMovie_rating()
						+ "\t\t" + dto.getMovie_info() + "\t\t" + dto.getMovie_reserve_count() + "\t\t"
						+ dto.getTheater_name());
				order++;
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}

	public void showMovieList_for_user() {

		List<MovieDTO> list = dao.getMovieList_for_user();

		System.out.println("                             Movie List");
		System.out.println(
				"===========================================================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("��Ʈ\t  ����\t\t����\t\t�⿬\t\t���\t\t����\t\t����Ƚ��");
			System.out.println(
					"==========================================================================================================");

			int order = 1;
			for (MovieDTO dto : list) {
				System.out.println(order + "��\t" + dto.getMovie_name() + "\t\t" + dto.getMovie_director() + "\t\t"
						+ dto.getMovie_actor() + "\t\t" + dto.getMovie_rating() + "\t\t" + dto.getMovie_info() + "\t\t"
						+ dto.getMovie_reserve_count());
				order++;
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��========================================\n");
	}

	public void updateMovie() {

		System.out.println("������ ��ȭ�� ��ȭ id�� �Է����ּ��� : ");
		System.out.print("��");
		String m_id = scn.nextLine();
		MovieDTO dto = dao.getMovie(m_id);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("�����۾��� ����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				System.out.println("##�Է��� �Ͻ��������� ������ ������ �״�� �����˴ϴ�.");
				String id = "";
				if (id.trim().equals(""))
					id = dto.getMovie_id();
				System.out.print("�������� �̸� : ");
				String name = scn.nextLine();
				if (name.trim().equals(""))
					name = dto.getMovie_name();
				System.out.print("�������� ���� : ");
				String dir = scn.nextLine();
				if (dir.trim().equals(""))
					dir = dto.getMovie_director();
				System.out.print("�������� �⿬ : ");
				String actor = scn.nextLine();
				if (actor.trim().equals(""))
					actor = dto.getMovie_actor();
				System.out.print("�������� ��� : ");
				String rating = scn.nextLine();
				if (rating.trim().equals(""))
					rating = dto.getMovie_rating();
				System.out.print("�������� ���� : ");
				String info = scn.nextLine();
				if (info.trim().equals(""))
					info = dto.getMovie_info();
				int count = dto.getMovie_reserve_count();
				String t_name = dto.getTheater_name();

				dto = new MovieDTO(id, name, dir, actor, rating, info, count, t_name);

				boolean r = dao.updateMovie(dto, m_id);
				if (r) {
					System.out.println("��ȭ�� ������ ������ ���� �����Ǿ����ϴ�.");
					System.out.println(dto.getInfo());
				} else {
					System.out.println("��ȭ�� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		} else {
			System.out.println("�Է��Ͻ� ��ȭ id�� �ش��ϴ� ��ȭ�� �������� �ʽ��ϴ�.");
		}
	}

	public void deleteMovie() {

		System.out.print("������ ��ȭ�� ��ȭ id�� �Է����ּ��� : ");
		String m_id = reInput();
		MovieDTO dto = dao.getMovie(m_id);
		if (dto != null) {
			System.out.println(dto.getInfo());

			System.out.println("�� ��ȭ�� ������ ������ �����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteMovie(m_id);

				if (r) {
					System.out.println(m_id + "��ȭ�� ������ ���������� �����Ǿ����ϴ�.");
				} else {
					System.out.println("��ȭ�� ������ ���������� ���� ���� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		} else {

			System.out.println("�Է��Ͻ� ��ȭ id�� �ش��ϴ� ��ȭ�� �������� �ʽ��ϴ�.");
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
