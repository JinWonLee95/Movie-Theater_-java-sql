package ticketing;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import Auditorium.AuditoriumDAO;
import Auditorium.AuditoriumDTO;
import Auditorium.AuditoriumProc;
import Payment.PaymentDAO;
import Payment.PaymentDTO;
import Time_table.Time_tableDAO;
import Time_table.Time_tableDTO;
import movie.MovieProc;
import theater.TheaterDTO;

class showTime_seat {
	String show_time;
	int seat;

	public showTime_seat(String show_time, int seat) {
		this.show_time = show_time;
		this.seat = seat;
	}
}

public class TicketingProc {

	TicketingDAO dao;
	static Scanner scn = new Scanner(System.in);
	AuditoriumProc ap = new AuditoriumProc();

	public TicketingProc() {
		dao = new TicketingDAO();
	}

	public void insertTicketing(String client_id) {
		String selectAuditorium = "";
		String selectTheater = "";
		MovieProc mp = new MovieProc();

		System.out.println("���Ÿ� �����մϴ�.");
		System.out.print("������ ������ ��ȭ : ");
		mp.showMovieList_for_user();
		System.out.print("�������� ��ȭ �̸� : ");
		String m_name = reInput();
		if (showTicketingTheater(m_name)) {
			System.out.print("�� ������ ��ȭ�� : ");
			selectTheater = reInput();
			if (showTicketingAuditorium(selectTheater, m_name)) {
				while (true) {
					System.out.print("�� ������ �󿵰� : ");
					selectAuditorium = reInput();
					if (confirmAuditorium(selectTheater, m_name, selectAuditorium) == true) {
						break;
					} else {
						System.out.println("�� �󿵰��� �������� �ʽ��ϴ�.");
					}
				}
				showTicketingDate(selectAuditorium);
				System.out.println("�� ��¥�� �����Ͻÿ�.");
				System.out.print("�� �⵵�� �Է��Ͻÿ�.(YYYY) : ");
				String year = reInput();
				System.out.print("�� ���� �Է��Ͻÿ�.(MM) : ");
				String month = reInput();
				while (true) {
					if (Integer.parseInt(month) > 0 && Integer.parseInt(month) < 13) {
						break;
					}
					System.out.print("�� ���� �˸°� �Է��Ͻÿ�.(MM) : ");
					month = reInput();
				}
				System.out.print("�� ���� �Է��Ͻÿ�.(dd) : ");
				String day = reInput();
				while (true) {
					if (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 32) {
						break;
					}
					System.out.print("�� ���� �˸°� �Է��Ͻÿ�.(MM) : ");
					day = reInput();
				}

				String date = year + "-" + month + "-" + day;

				while (true) {
					if (confirm_Date(selectAuditorium, date)) {
						break;
					} else {
						System.out.println("�� ��¥�� �ùٸ��� �Է��� �ּ���.");
						System.out.print("�� �⵵�� �Է��Ͻÿ�.(YYYY) : ");
						year = reInput();
						System.out.print("�� ���� �Է��Ͻÿ�.(MM) : ");
						month = reInput();
						while (true) {
							if (Integer.parseInt(month) > 0 && Integer.parseInt(month) < 13) {
								break;
							}
							System.out.print("�� ���� �˸°� �Է��Ͻÿ�.(MM) : ");
							month = reInput();
						}
						System.out.print("�� ���� �Է��Ͻÿ�.(dd) : ");
						day = reInput();
						while (true) {
							if (Integer.parseInt(day) > 0 && Integer.parseInt(day) < 32) {
								break;
							}
							System.out.print("�� ���� �˸°� �Է��Ͻÿ�.(MM) : ");
							day = reInput();
						}
						date = year + "-" + month + "-" + day;
					}
				}
				Time_tableDAO tt_dao = new Time_tableDAO();
				Time_tableDTO tt_dto = tt_dao.getTime_table(selectAuditorium);
				String movie_id = tt_dto.getMovie_id();
				showTime_seat ss = showTicketingShow_time(selectAuditorium, month, day, movie_id);

				String twoDigit = findMaxNumber();
				String str_ticketNumber = month + day + ss.show_time + twoDigit;
				int int_ticketNumber = Integer.parseInt(str_ticketNumber);

				TicketingDTO dto = new TicketingDTO(client_id, movie_id, selectTheater, ss.seat, int_ticketNumber);
				boolean r = dao.insertTicketing(dto, client_id);

				if (r) {
					System.out.println("Ƽ�� ����� ���������� �Ϸ�Ǿ����ϴ�.");

				} else {
					System.out.println("Ƽ�� ����� ���������� �̷����� �ʾҽ��ϴ�.");
				}

				PaymentDAO p_dao = new PaymentDAO();

				int payment_no = dao.makePayment_no();
				int price = ss.seat * 10000;
				String pay_option = "Not yet";
				int isPayed = 0;
				PaymentDTO dto2 = new PaymentDTO(payment_no, int_ticketNumber, price, pay_option, isPayed);
				boolean r2 = p_dao.insertPayment(dto2, 0);

				if (!r2) {
					System.out.println("���� ����� ���������� �̷����� �ʾҽ��ϴ�.");
				}

			}

		}
	}

	public boolean showTicketingTheater(String m_name) {

		List<TheaterDTO> list = dao.getTicketingTheater(m_name);
		System.out.println("                             Theater List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �̸� ");
			System.out.println("============================================================================");

			for (TheaterDTO dto : list) {
				System.out.println("\t" + dto.getTheater_name());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
		if (list.size() == 0) {
			System.out.println("�ش��ϴ� ��ȭ�� ���ϴ� ��ȭ���� �������� �ʽ��ϴ�.");
			return false;
		} else {
			return true;
		}
	}

	public boolean showTicketingAuditorium(String selectTheater, String selectMovie) {

		List<AuditoriumDTO> list = dao.getTicketingAuditorium(selectTheater, selectMovie);
		Scanner scn = new Scanner(System.in);
		System.out.println("                             Auditorium List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �̸� ");
			System.out.println("============================================================================");

			for (AuditoriumDTO dto : list) {
				System.out.println("\t" + dto.getAuditorium_name());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");

		if (list.size() == 0) {
			System.out.println("�ش� �̸��� ��ȭ���� ���ų�, �ش� ��ȭ�� ���ϴ� �󿵰��� �������� �ʽ��ϴ�.");
			return false;
		} else {
			return true;
		}
	}

	public boolean confirmAuditorium(String selectTheater, String selectMovie, String a_name) {
		List<AuditoriumDTO> list = dao.getTicketingAuditorium(selectTheater, selectMovie);

		if (list != null && list.size() > 0) {
			for (AuditoriumDTO dto : list) {
				if (dto.getAuditorium_name().equals(a_name)) {
					return true;
				}
			}

		}
		return false;
	}

	public void showTicketingDate(String selectedAuditorium) {

		Time_tableDTO dto = dao.getTicketingDate(selectedAuditorium);
		System.out.println("                             Date");
		System.out.println("============================================================================");
		if (dto != null) {
			System.out.println("reg.No\t  ��  ���� ��¥\t\t�� ���� ��¥ ");
			System.out.println("============================================================================");
			System.out.println("\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
	}

	public boolean confirm_Date(String selectedAuditorium, String date) {

		Time_tableDTO dto = dao.getTicketingDate(selectedAuditorium);

		Date d = transformDate(date);
		int compare1 = transformDate(dto.getStart_date()).compareTo(d);
		int compare2 = transformDate(dto.getEnd_date()).compareTo(d);
		if (compare1 <= 0 && compare2 >= 0) {
			return true;
		} else {
			System.out.println("�߸��� ��¥�� �Է��Ͽ����ϴ�.");
			return false;
		}
	}

	public showTime_seat showTicketingShow_time(String selectedAuditorium, String month, String day, String movie_id) {
		List<Time_tableDTO> list = dao.getTicketingShow_time(selectedAuditorium);
		showTime_seat ss = null;
		System.out.println("                             Show Time");
		System.out.println("============================================================================");
		System.out.println("reg.No\t  �� �ð�\t\t�¼���");
		System.out.println("============================================================================");
		AuditoriumDAO a_dao = new AuditoriumDAO();

		for (Time_tableDTO dto : list) {
			System.out.print("\t" + dto.getShow_time() + "\t\t");
			System.out.println(a_dao.getAuditorium(selectedAuditorium).getSeat()
					- dao.getSeatSum(month, day, dto.getShow_time(), movie_id));
		}

		boolean wrongTime = true;
		boolean notEmpty = true;
		while (notEmpty) {

			System.out.print("�� ������ �� �ð� : ");
			String show_time = reInput();
			for (Time_tableDTO dto : list) {
				if (dto.getShow_time().equals(show_time)) {
					if (a_dao.getAuditorium(selectedAuditorium).getSeat()
							- dao.getSeatSum(month, day, show_time, movie_id) == 0) {
						System.out.println("������ �� �ð��� �� �¼��� �����ϴ�.");
						break;
					} else {
						wrongTime = false;
						break;
					}
				}
			}

			while (wrongTime) {
				for (Time_tableDTO dto : list) {
					System.out.print("\t" + dto.getShow_time() + "\t\t");
					System.out.println(a_dao.getAuditorium(selectedAuditorium).getSeat()
							- dao.getSeatSum(month, day, dto.getShow_time(), movie_id));
				}
				System.out.print("�� �ش��ϴ� �� �ð��� �������ּ���. : ");
				show_time = reInput();
				for (Time_tableDTO dto : list) {
					if (dto.getShow_time().equals(show_time)) {
						if (a_dao.getAuditorium(selectedAuditorium).getSeat()
								- dao.getSeatSum(month, day, show_time, movie_id) == 0) {
							System.out.println("������ �� �ð��� �� �¼��� �����ϴ�.");
							break;
						} else {
							wrongTime = false;
							break;
						}
					}
				}
			}

			System.out.print("�� ������ �ο� �� : ");
			int num = scn.nextInt();

			while (true) {
				if (num <= 0) {
					System.out.println("�¼� ���� �ȹٷ� �Է��� �ּ���.");
				} else {
					if (num <= (a_dao.getAuditorium(selectedAuditorium).getSeat()
							- dao.getSeatSum(month, day, show_time, movie_id))) {
						ss = new showTime_seat(show_time, num);
						return ss;
					} else {
						System.out.println("�Է��� �ο���ŭ �¼� ���� ���� �ʽ��ϴ�.");
					}
				}
				System.out.print("�� ������ �ο� �� : ");
				num = scn.nextInt();
			}

		}
		return ss;
	}

	public Date transformDate(String date) {
		SimpleDateFormat textFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat afterFormat = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date tempDate = null;
		try {
			tempDate = textFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		String transDate = afterFormat.format(tempDate);
		Date d = Date.valueOf(transDate);
		return d;
	}

	public void showTicketingList() {

		List<TicketingDTO> list = dao.getTicketingList();

		System.out.println("                             Ticketing List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  ���Ź�ȣ \t\t��ID\t\t��ȭID\t\t��ȭ�� �̸�\t\t�ο�");
			System.out.println("============================================================================");

			for (TicketingDTO dto : list) {
				System.out.println("\t" + dto.getTicket_number() + "\t\t" + dto.getClient_id() + "\t\t"
						+ dto.getMovie_id() + dto.getTheater_name() + dto.getPeople());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}

	public String findMaxNumber() {

		List<TicketingDTO> list = dao.getTicketingList();
		List<Integer> numbers = new ArrayList<>();

		if (list != null && list.size() > 0) {
			for (TicketingDTO dto : list) {
				int temp = dto.getTicket_number() % 100;
				numbers.add(temp);
			}
			int m = Collections.max(numbers);
			int length = (int) (Math.log10(m) + 1);
			if (length == 2) {
				return Integer.toString(m + 1);
			} else {
				if (m == 9) {
					return "10";
				} else {
					return "0" + Integer.toString(m + 1);
				}
			}
		} else {
			return "01";
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

	public void printTicket(String id) {
		System.out.println("                                         ���� ��Ȳ");
		System.out.println("============================================================================");
		System.out.println("���� ȸ��\t\t���Ź�ȣ\t\t��ȭid\t\t��ȭ��\t\t�����ο�");
		System.out.println("============================================================================");

		dao.getTicketInfo(id);
		System.out.println("============================================================================");

	}

	public void getPrintTicket(String id) {
		printTicket(id);

		if (dao.regiCount == 0) {
			System.out.println("***�߱� ������ ���� ������ �����ϴ�.***");
		} else {
			System.out.print("�߱� ������ ���� ��ȣ�� �Է��ϼ���: ");
			int ticket_no = Integer.parseInt(reInput());

			dao.getPrintTicket(ticket_no);
		}
	}

	public void cancelArrangement(String id) {
		printTicket(id);
		Scanner sc = new Scanner(System.in);
		if (dao.regiCount == 0) {
			System.out.println("����� ���� ������ �����ϴ�.");
		} else {
			System.out.println("���� ����� ���Ź�ȣ�� �Է����ּ���: ");
			String ticket_No = sc.nextLine();
			while (true) {
				int result = dao.confirmTicket_no(ticket_No, id);
				if(result == 1){
					boolean r = dao.deleteArrange(ticket_No, result);
					if (r) {
						System.out.println("Ƽ�� ��Ұ� �Ǿ����ϴ�.");
					} else {
						System.out.println("Ƽ�� ��Ұ� ���������� �̷����� �ʾҽ��ϴ�.");
					}
					break;
				} else if(result == 2){
					boolean r = dao.deleteArrange(ticket_No, result);
					if (r) {
						System.out.println("Ƽ�� ��Ұ� �Ǿ����ϴ�.");
					} else {
						System.out.println("Ƽ�� ��Ұ� ���������� �̷����� �ʾҽ��ϴ�.");
					}
					break;
				}else{
					System.out.println("���� ����� ���Ź�ȣ�� �ȹٷ� �Է����ּ���: ");
					ticket_No = sc.nextLine();
				}
			}
		}
	}

}