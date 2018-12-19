package Auditorium;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AuditoriumDTO { 
	
	private String auditorium_name;
	private int seat;
	private String theater_name;
	
	public AuditoriumDTO() {
	
	}
	
	public AuditoriumDTO(String auditorium_name, int seat, 
			String theater_name) {
		this.auditorium_name = auditorium_name;
		this.seat = seat;
		this.theater_name = theater_name;
	}

	public String getAuditorium_name() {
		return auditorium_name;
	}

	public void setAuditorium_name(String auditorium_name) {
		this.auditorium_name = auditorium_name;
	}

	public int getSeat() {
		return seat;
	}

	public void setSeat(int seat) {
		this.seat = seat;
	}

	public String getTheater_name() {
		return theater_name;
	}

	public void setTheater_name(String theater_name) {
		this.theater_name = theater_name;
	}

	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+auditorium_name+ " ] 상영관의 정보====\n");
		sb.append("상영관 좌석 수 : "+seat+"\n");
		sb.append("영화관 이름 : "+theater_name+"\n");

		return sb.toString();
	}

}
