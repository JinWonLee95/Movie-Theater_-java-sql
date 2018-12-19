package Payment;
public class PaymentDTO { 

	private int payment_no;
	private int ticket_no;
	private int price;
	private String pay_option;
	private int isPayed;

	public PaymentDTO() {
	
	}
	
	public PaymentDTO(int payment_no, int ticket_no, int price, String pay_option,
			int isPayed) {
		this.payment_no = payment_no;
		this.ticket_no = ticket_no;
		this.price = price;
		this.pay_option = pay_option;
		this.isPayed = isPayed;
	}

	public int getPayment_no() {
		return payment_no;
	}

	public void setPayment_no(int payment_no) {
		this.payment_no = payment_no;
	}

	public int getTicket_no() {
		return ticket_no;
	}

	public void setTicket_no(int ticket_no) {
		this.ticket_no = ticket_no;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getPay_option() {
		return pay_option;
	}

	public void setPay_option(String pay_option) {
		this.pay_option = pay_option;
	}

	public int getIsPayed() {
		return isPayed;
	}

	public void setIsPayed(int isPayed) {
		this.isPayed = isPayed;
	}


	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+payment_no+ " ] ���� ����====\n");
		sb.append("������ȣ : "+ payment_no +"\n");
		sb.append("���Ź�ȣ : "+ ticket_no +"\n");
		sb.append("���� ���� : "+ price +"\n");
		sb.append("���� ��� : "+ pay_option +"\n");
		sb.append("���� ���� : "+ isPayed +"\n");
		return sb.toString();
	}

}
