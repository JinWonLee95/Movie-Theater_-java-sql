package theater;

import java.util.Formatter;

public class TheaterDTO { 
	
	private String theater_name;
	private String theater_address;
	private String theater_number;

	public TheaterDTO() {
	
	}
	
	public TheaterDTO(String theater_name, String theater_address, 
			String theater_number) {
		this.theater_name = theater_name;
		this.theater_address = theater_address;
		this.theater_number = theater_number;
	}

	public String getTheater_name() {
		return theater_name;
	}

	public void setTheater_name(String theater_name) {
		this.theater_name = theater_name;
	}

	public String getTheater_address() {
		return theater_address;
	}

	public void setTheater_address(String theater_address) {
		this.theater_address = theater_address;
	}

	public String getTheater_number() {
		return theater_number;
	}

	public void setTheater_number(String theater_number) {
		this.theater_number = theater_number;
	}

	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+theater_name+ " ] 영화관의 정보====\n");
		sb.append("영화관 주소 : "+theater_address+"\n");
		sb.append("영화관 전화번호 : "+theater_number+"\n");

		return sb.toString();
	}

}
