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
			System.out.println("상영 시작 시간을 입력해주세요.");
			System.out.print("▶시(HH) : ");
			hour = reInput();
			while (true) {
				if (hour.length() == 2 && Integer.parseInt(hour) >= 0 && Integer.parseInt(hour) < 24) {
					break;
				}
				System.out.println("시간을 형식에 맞게 입력해주세요.");
				System.out.print("▶시(HH) : ");
				hour = reInput();
			}
			System.out.print("▶분(mm) : ");
			min = reInput();
			while (true) {
				if (min.length() == 2 && Integer.parseInt(min) >= 0 && Integer.parseInt(min) < 60) {
					break;
				}
				System.out.println("분을 형식에 맞게 입력해주세요.");
				System.out.print("▶분(mm) : ");
				min = reInput();
			}
			time = hour + min;
			if (confirmTime_tableListByAuditorium(time, a_name) == true) {
				System.out.println("존재하는 상영 시작 시간입니다.");
			} else {
				break;
			}
		}
		String startYear = "";
		String startMon = "";
		String startDay = "";

		System.out.println("▶ 2018년 영화 상영 시작 날짜를 입력해주세요");
		startYear = "2018";
		System.out.print("▶ 월(MM) : ");
		startMon = reInput();
		while (true) {
			if (startMon.length() == 2 && Integer.parseInt(startMon) > 0 && Integer.parseInt(startMon) < 13) {
				break;
			}
			System.out.println("▶ 월을 형식에 맞게 입력해주세요.");
			System.out.print("▶ 월(MM) : ");
			startMon = reInput();
		}
		System.out.print("▶ 일(dd) : ");
		startDay = reInput();
		while (true) {
			if (startDay.length() == 2 && Integer.parseInt(startDay) > 0 && Integer.parseInt(startDay) < 32) {
				break;
			}
			System.out.println("▶ 일을 형식에 맞게 입력해주세요.");
			System.out.print("▶ 일(dd) : ");
			startDay = reInput();
		}
		String start_date = startYear + "-" + startMon + "-" + startDay;

		System.out.print("▶상영 종료 날짜 (YYYY-mm-dd) : ");
		System.out.println("1. 2018년		2. 2019년");
		System.out.print("영화의 종료 년도를 입력하세요 : ");

		int num = 0;
		try {
			num = scn.nextInt();
			if (!(num > 0 && num < 3)) { // 1~3 외의 숫자가 입력되면 예외 강제 발생
				throw new InputMismatchException();
			}
		} catch (InputMismatchException e) {
			System.out.println("입력된 값이 잘못되었습니다. [1-2] 중 선택해주세요!");
		}

		String endYear = "";
		String endMon = "";
		String endDay = "";

		switch (num) {
		case 1:
			System.out.println("▶ 2018년 영화 종료 날짜를 입력해주세요");
			endYear = "2018";
			System.out.print("▶ 월(MM) : ");
			endMon = reInput();
			while (true) {
				if (endMon.length() == 2) {
					if (Integer.parseInt(endMon) >= Integer.parseInt(startMon) && Integer.parseInt(endMon) < 13) {
						if (Integer.parseInt(endMon) == Integer.parseInt(startMon)) {
							System.out.print("▶ 일(dd) : ");
							endDay = reInput();
							while (true) {
								if (Integer.parseInt(endDay) >= Integer.parseInt(startDay)
										&& Integer.parseInt(endDay) < 32) {
									break;
								}
								System.out.println("상영 시작 날짜 전이거나 31일보다 큰 수를 입력하였습니다.");
								System.out.print("▶ 일(dd) : ");
								endDay = reInput();
							}
							break;
						} else {
							System.out.print("▶ 일(dd) : ");
							endDay = reInput();
							while (true) {
								if (Integer.parseInt(endDay) > 0 && Integer.parseInt(endDay) < 32) {
									break;
								}
								System.out.println("상영 시작 날짜를 알맞은 형식으로 입력해주세요.");
								System.out.print("▶ 일(dd) : ");
								endDay = reInput();
							}
							break;
						}
					} else {
						System.out.println("상영 시작 월 전이거나 12월보다 큰 수를 입력하였습니다.");
					}
				} else {
					System.out.println("두 자리 수로 입력해주세요.");
				}
				System.out.print("▶ 월(MM) : ");
				endMon = reInput();
			}
			break;
		case 2:
			System.out.println("▶ 2019년 영화 종료 날짜를 입력해주세요");
			endYear = "2019";
			System.out.print("▶ 월(MM) : ");
			endMon = reInput();
			while (true) {
				if (endMon.length() == 2) {
					if (Integer.parseInt(endMon) > 0 && Integer.parseInt(endMon) < 13) {
						System.out.print("▶ 일(dd) : ");
						endDay = reInput();
						while (true) {
							if (Integer.parseInt(endDay) > 0
									&& Integer.parseInt(endDay) < 32) {
								break;
							}
							System.out.println("01 - 31일을 입력해주세요.");
							System.out.print("▶ 일(dd) : ");
							endDay = reInput();
						}
						break;
					} else {
						System.out.println("01 - 12월를 입력해주세요.");
					}
				} else {
					System.out.println("두 자리 수로 입력해주세요.");
				}
				System.out.print("▶ 월(MM) : ");
				endMon = reInput();
			}
			break;
		}

		String end_date = endYear + "-" + endMon + "-" + endDay;
		Time_tableDTO dto = new Time_tableDTO(a_name, time, m_id, start_date, end_date);
		boolean r = dao.insertTime_table(dto);

		if (r) {
			System.out.println("상영시간표가 정상적으로 완료되었습니다.");
			while (true) {
				System.out.println("상영 시간을 추가하시겠습니까?(Y/N)");
				String input = reInput();
				if (input.equalsIgnoreCase("y")) {
					String time2;
					String hour2;
					String min2;
					while (true) {
						System.out.println("상영 시작 시간을 입력해주세요.");
						System.out.print("▶시(HH) : ");
						hour2 = reInput();
						while (true) {
							if (hour2.length() == 2 && Integer.parseInt(hour2) >= 0 && Integer.parseInt(hour2) < 24) {
								break;
							}
							System.out.println("시간을 형식에 맞게 입력해주세요.");
							System.out.print("▶시(HH) : ");
							hour2 = reInput();
						}
						System.out.print("▶분(mm) : ");
						min2 = reInput();
						while (true) {
							if (min2.length() == 2 && Integer.parseInt(min2) >= 0 
									&& Integer.parseInt(min2) < 60) {
								break;
							}
							System.out.println("분을 형식에 맞게 입력해주세요.");
							System.out.print("▶분(mm) : ");
							min2 = reInput();
						}
						time2 = hour2 + min2;
						if (confirmTime_tableListByAuditorium(time2, a_name) == true) {
							System.out.println("존재하는 상영 시작 시간입니다.");
						} else {
							break;
						}
					}
					Time_tableDTO dto2 = new Time_tableDTO(a_name, time2, m_id, start_date, end_date);
					boolean r2 = dao.insertTime_table(dto2);

					if (r2) {
						System.out.println("상영시간표가 정상적으로 완료되었습니다.");
					} else {
						System.out.println("상영시간표가 정상적으로 이루지지 않았습니다.");
					}
				} else {
					System.out.println("상영 시간표 작업을 취소하였습니다.");
					break;
				}
			}
		} else {
			System.out.println("상영시간표가 정상적으로 이루지지 않았습니다.");
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
		System.out.print("영화 상영을 중지할 상영관 이름을 입력해주세요 : ");
		String a_name = scn.nextLine();
		Time_tableDTO dto = dao.getTime_table(a_name);
		if (dto != null) {
			System.out.println("위 상영관의 영화 상영을 정말로 중지하시겠습니까?(Y/N)");
			String input = scn.nextLine();
			if (input.equalsIgnoreCase("y")) {
				boolean r = dao.deleteTime_table(a_name);

				if (r) {
					System.out.println(a_name + " 상영관의 상영을 정상적으로 중지하였습니다.");
				} else {
					System.out.println("상영관의 상영이 올바르게 중지되지 않았습니다.");
				}
			} else {
				System.out.println("삭제 작업을 취소하였습니다.");
			}
		} else {

			System.out.println("입력하신 상영관 이름에 해당하는 영화가 존재하지 않습니다.");
		}
	}
	
	public void showTime_tableList() {

		List<Time_tableDTO> list = dao.getTime_tableList();

		System.out.println("                           Time table List");
		System.out.println("============================================================================");
		if (list != null && list.size() > 0) {
			System.out.println("reg.No\t  상영관 \t\t상영 시간\t\t영화ID\t\t상영 시작 날짜\t\t상영 종료 날짜");
			System.out.println("============================================================================");

			for (Time_tableDTO dto : list) {
				System.out.println("\t" + dto.getAuditorium_name() + "\t\t" + dto.getShow_time() + "\t\t"
						+ dto.getMovie_id() + "\t\t" + dto.getStart_date() + "\t\t" + dto.getEnd_date());
			}

		} else {
			System.out.println("저장된 데이터가 없습니다. ");
		}
		System.out.println("====================================================================총 "
				+ ((list == null) ? "0" : list.size()) + "개=\n");
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
				System.out.println("공백은 입력하실수없습니다. 올바른값을 입력해주세요!");
				System.out.print("▶");
			}
		}

		return str;
	}

}
