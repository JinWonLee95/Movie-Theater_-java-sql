package User;

import java.util.Formatter;

public class UserDTO {

	private String user_id;
	private String user_password;
	private String user_name;
	private String user_birth;
	private String user_address;
	private String user_number;
	private int user_point;
	private int user_purchase_count;

	public UserDTO() {

	}

	public UserDTO(String client_id, String client_password, String client_name, String client_birth,
			String client_address, String client_number, int client_point, int client_purchase_count) {
		this.user_id = client_id;
		this.user_password = client_password;
		this.user_name = client_name;
		this.user_birth = client_birth;
		this.user_address = client_address;
		this.user_number = client_number;
		this.user_point = client_point;
		this.user_purchase_count = client_purchase_count;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getUser_password() {
		return user_password;
	}

	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_birth() {
		return user_birth;
	}

	public void setUser_birth(String user_birth) {
		this.user_birth = user_birth;
	}

	public String getUser_address() {
		return user_address;
	}

	public void setUser_address(String user_address) {
		this.user_address = user_address;
	}

	public String getUser_number() {
		return user_number;
	}

	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}

	public int getUser_point() {
		return user_point;
	}

	public void setUser_point(int user_point) {
		this.user_point = user_point;
	}

	public int getUser_purchase_count() {
		return user_purchase_count;
	}

	public void setUser_purchase_count(int user_purchase_count) {
		this.user_purchase_count = user_purchase_count;
	}

	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ " + user_id + " ] ȸ���� ����====\n");
		sb.append("��й�ȣ : " + user_password + "\n");
		sb.append("ȸ�� �̸� : " + user_name + "\n");
		sb.append("���� : " + user_birth + "\n");
		sb.append("�ּ� : " + user_address + "\n");
		sb.append("��ȭ��ȣ : " + user_number + "\n");
		sb.append("����Ʈ : " + user_point + "\n");
		sb.append("���� Ƚ�� : " + user_purchase_count + "\n");

		return sb.toString();
	}

}
