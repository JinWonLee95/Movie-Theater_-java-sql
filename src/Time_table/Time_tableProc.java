package Time_table;

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

import Auditorium.AuditoriumDTO;

public class Time_tableProc {

	Time_tableDAO dao;

	public Time_tableProc() {
		dao = new Time_tableDAO();
	}

	public void insertTime_table(String a_name, String m_id) {
		String time = "";
		String hour = "";
		String min = "";
		Scanner scn = new Scanner(System.in);
		while (true) {
			System.out.println("�� ���� �ð��� �Է����ּ���.");
			System.out.print("����(HH) : ");
			hour = reInput();
			while (true) {
				if (hour.length() == 2 && Integer.parseInt(hour) >= 0 && Integer.parseInt(hour) < 24) {
					break;
				}
				System.out.println("�ð��� ���Ŀ� �°� �Է����ּ���.");
				System.out.print("����(HH) : ");
				hour = reInput();
			}
			System.out.print("����(mm) : ");
			min = reInput();
			while (true) {
				if (min.length() == 2 && Integer.parseInt(min) >= 0 && Integer.parseInt(min) < 60) {
					break;
				}
				System.out.println("���� ���Ŀ� �°� �Է����ּ���.");
				System.out.print("����(mm) : ");
				min = reInput();
			}
			time = hour + min;
			if (confirmTime_tableListByAuditorium(time, a_name) == true) {
				System.out.println("�����ϴ� �� ���� �ð��Դϴ�.");
			} else {
				break;
			}
		}
		String startYear = "";
		String startMon = "";
		String startDay = "";

		System.out.println("�� 2018�� ��ȭ �� ���� ��¥�� �Է����ּ���");
		startYear = "2018";
		System.out.print("�� ��(MM) : ");
		startMon = reInput();
		while (true) {
			if (startMon.length() == 2 && Integer.parseInt(startMon) > 0 && Integer.parseInt(startMon) < 13) {
				break;
			}
			System.out.println("�� ���� ���Ŀ� �°� �Է����ּ���.");
			System.out.print("�� ��(MM) : ");
			startMon = reInput();
		}
		System.out.print("�� ��(dd) : ");
		startDay = reInput();
		while (true) {
			if (startDay.length() == 2 && Integer.parseInt(startDay) > 0 && Integer.parseInt(startDay) < 32) {
				break;
			}
			System.out.println("�� ���� ���Ŀ� �°� �Է����ּ���.");
			System.out.print("�� ��(dd) : ");
			startDay = reInput();
		}
		String start_date = startYear + "-" + startMon + "-" + startDay;

		System.out.print("���� ���� ��¥ (YYYY-mm-dd) : ");
		System.out.println("1. 2018��		2. 2019��");
		System.out.print("��ȭ�� ���� �⵵�� �Է��ϼ��� : ");

		int num = 0;
		try {
			num = scn.nextInt();
			if (!(num > 0 && num < 3)) { // 1~3 ���� ���ڰ� �ԷµǸ� ���� ���� �߻�
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("�Էµ� ���� �߸��Ǿ����ϴ�. [1-2] �� �������ּ���!");
		}

		String endYear = "";
		String endMon = "";
		String endDay = "";

		switch (num) {
		case 1:
			System.out.println("�� 2018�� ��ȭ ���� ��¥�� �Է����ּ���");
			endYear = "2018";
			System.out.print("�� ��(MM) : ");
			endMon = reInput();
			while (true) {
				if (endMon.length() == 2) {
					if (Integer.parseInt(endMon) >= Integer.parseInt(startMon) && Integer.parseInt(endMon) < 13) {
						if (Integer.parseInt(endMon) == Integer.parseInt(startMon)) {
							System.out.print("�� ��(dd) : ");
							endDay = reInput();
							while (true) {
								if (Integer.parseInt(endDay) >= Integer.parseInt(startDay)
										&& Integer.parseInt(endDay) < 32) {
									break;
								}
								System.out.println("�� ���� ��¥ ���̰ų� 31�Ϻ��� ū ���� �Է��Ͽ����ϴ�.");
								System.out.print("�� ��(dd) : ");
								endDay = reInput();
							}
							break;
						} else {
							System.out.print("�� ��(dd) : ");
							endDay = reInput();
							while (true) {
								if (Integer.parseInt(endDay) > 0 && Integer.parseInt(endDay) < 32) {
									break;
								}
								System.out.println("�� ���� ��¥�� �˸��� �������� �Է����ּ���.");
								System.out.print("�� ��(dd) : ");
								endDay = reInput();
							}
							break;
						}
					} else {
						System.out.println("�� ���� �� ���̰ų� 12������ ū ���� �Է��Ͽ����ϴ�.");
					}
				} else {
					System.out.println("�� �ڸ� ���� �Է����ּ���.");
				}
				System.out.print("�� ��(MM) : ");
				endMon = reInput();
			}
			break;
		case 2:
			System.out.println("�� 2019�� ��ȭ ���� ��¥�� �Է����ּ���");
			endYear = "2019";
			System.out.print("�� ��(MM) : ");
			endMon = reInput();
			while (true) {
				if (endMon.length() == 2) {
					if (Integer.parseInt(endMon) > 0 && Integer.parseInt(endMon) < 13) {
						System.out.print("�� ��(dd) : ");
						endDay = reInput();
						while (true) {
							if (Integer.parseInt(endDay) > 0
									&& Integer.parseInt(endDay) < 32) {
								break;
							}
							System.out.println("01 - 31���� �Է����ּ���.");
							System.out.print("�� ��(dd) : ");
							endDay = reInput();
						}
						break;
					} else {
						System.out.println("01 - 12���� �Է����ּ���.");
					}
				} else {
					System.out.println("�� �ڸ� ���� �Է����ּ���.");
				}
				System.out.print("�� ��(MM) : ");
				endMon = reInput();
			}
			break;
		}

		String end_date = endYear + "-" + endMon + "-" + endDay;
		Time_tableDTO dto = new Time_tableDTO(a_name, time, m_id, start_date, end_date);
		boolean r = dao.insertTime_table(dto);

		if (r) {
			System.out.println("�󿵽ð�ǥ�� ���������� �Ϸ�Ǿ����ϴ�.");
			while (true) {
				System.out.println("�� �ð��� �߰��Ͻðڽ��ϱ�?(Y/N)");
				String input = reInput();
				if (input.equalsIgnoreCase("y")) {
					String time2;
					String hour2;
					String min2;
					while (true) {
						System.out.println("�� ���� �ð��� �Է����ּ���.");
						System.out.print("����(HH) : ");
						hour2 = reInput();
						while (true) {
							if (hour2.length() == 2 && Integer.parseInt(hour2) >= 0 && Integer.parseInt(hour2) < 24) {
								break;
							}
							System.out.println("�ð��� ���Ŀ� �°� �Է����ּ���.");
							System.out.print("����(HH) : ");
							hour2 = reInput();
						}
						System.out.print("����(mm) : ");
						min2 = reInput();
						while (true) {
							if (min2.length() == 2 && Integer.parseInt(min2) >= 0 
									&& Integer.parseInt(min2) < 60) {
								break;
							}
							System.out.println("���� ���Ŀ� �°� �Է����ּ���.");
							System.out.print("����(mm) : ");
							min2 = reInput();
						}
						time2 = hour2 + min2;
						if (confirmTime_tableListByAuditorium(time2, a_name) == true) {
							System.out.println("�����ϴ� �� ���� �ð��Դϴ�.");
						} else {
							break;
						}
					}
					Time_tableDTO dto2 = new Time_tableDTO(a_name, time2, m_id, start_date, end_date);
					boolean r2 = dao.insertTime_table(dto2);

					if (r2) {
						System.out.println("�󿵽ð�ǥ�� ���������� �Ϸ�Ǿ����ϴ�.");
					} else {
						System.out.println("�󿵽ð�ǥ�� ���������� �̷����� �ʾҽ��ϴ�.");
					}
				} else {
					System.out.println("�� �ð�ǥ �۾��� ����Ͽ����ϴ�.");
					break;
				}
			}
		} else {
			System.out.println("�󿵽ð�ǥ�� ���������� �̷����� �ʾҽ��ϴ�.");
		}
	}

	public boolean showTime_tableExist(String a_name) {
		if (dao.showTime_tableExist(a_name)) {
			return true;
		}
		return false;

	}

	public void deleteTime_table() {
		Scanner scn = new Scanner(System.in);
		System.out.print("��ȭ ���� ������ �󿵰� �̸��� �Է����ּ��� : ");
		String a_name = scn.nextLine();
		Time_tableDTO dto = dao.getTime_table(a_name);
		if (dto != null) {
			System.out.println("�� �󿵰��� ��ȭ ���� ������ �����Ͻðڽ��ϱ�?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteTime_table(a_name);

				if (r) {
					System.out.println(a_name + " �󿵰��� ���� ���������� �����Ͽ����ϴ�.");
				} else {
					System.out.println("�󿵰��� ���� �ùٸ��� �������� �ʾҽ��ϴ�.");
				}
			} else {
				System.out.println("���� �۾��� ����Ͽ����ϴ�.");
			}
		} else {

			System.out.println("�Է��Ͻ� �󿵰� �̸��� �ش��ϴ� ��ȭ�� �������� �ʽ��ϴ�.");
		}
	}
	
	public void showTime_tableList() {

		List<Time_tableDTO> list = dao.getTime_tableList();

		System.out.println("                           Time table List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  �󿵰� \t\t�� �ð�\t\t��ȭID\t\t�� ���� ��¥\t\t�� ���� ��¥");
			System.out.println("============================================================================");

			for (Time_tableDTO dto : list) {
				System.out.println("\t" + dto.getAuditorium_name() + "\t\t" + dto.getShow_time() + "\t\t"
						+ dto.getMovie_id() + "\t\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
			}

		} else {
			System.out.println("����� �����Ͱ� �����ϴ�. ");
		}
		System.out.println("====================================================================�� "
				+ ((list == null) ? "0" : list.size()) + "��=\n");
	}

	public boolean confirmTime_tableListByAuditorium(String t_name, String a_name) {
		List<Time_tableDTO> list = dao.getTime_tableListByAuditorium(a_name);
		if (list != null && list.size() > 0) {
			for (Time_tableDTO dto : list) {
				if (dto.getShow_time().equals(t_name)) {
					return true;
				}
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
