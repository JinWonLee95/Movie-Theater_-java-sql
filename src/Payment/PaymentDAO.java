package Payment;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Time_table.Time_tableDTO;
import User.UserManagement;

public class PaymentDAO {

	private static PaymentDAO instance = new PaymentDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;

	public static PaymentDAO getInstance() {
		return instance;
	}

	public PaymentDAO() {

	}

	private Connection getConnection() throws Exception {
		Connection conn = null;
		String jdbcUrl = "jdbc:mysql://localhost:3306/dbterm?serverTimezone=UTC&useSSL=false";
		String dbId = "root";
		String dbPass = "tkrhkak7170";

		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(jdbcUrl, dbId, dbPass);
		return conn;

	}

	public boolean insertPayment(PaymentDTO dto, int using_point) {
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into payment VALUES (?,?,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, Integer.toString(dto.getPayment_no()));
			pstmt.setString(2, Integer.toString(dto.getTicket_no()));
			pstmt.setString(3, Integer.toString(dto.getPrice()));
			pstmt.setString(4, dto.getPay_option());
			pstmt.setString(5, Integer.toString(dto.getIsPayed()));
			
			int r = pstmt.executeUpdate();

			sql = "UPDATE client SET client_point = client_point - ?, "
					+ "client_purchase_count = client_purchase_count + 1 WHERE client_id "
					+ "in (select client_id from ticketing where ticket_number = ?);";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(using_point));
			pstmt.setString(2, Integer.toString(dto.getTicket_no()));

			
			int r2 = pstmt.executeUpdate();
			
			if (r > 0 && r2 > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public PaymentDTO getPayment(int p_no) {
		PaymentDTO dto = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT payment_no, ticket_no, price, pay_option,"
					+ " isPayed FROM payment WHERE payment_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(p_no));
			r = pstmt.executeQuery();

			if (r.next()) {
				int payment_no = r.getInt("payment_no");
				int ticket_no = r.getInt("ticket_no");
				int price = r.getInt("price");
				String pay_option = r.getString("pay_option");
				int isPayed = r.getInt("isPayed");

				dto = new PaymentDTO(payment_no, ticket_no, price, pay_option, isPayed);
			}

		} catch (Exception e) {
			new UserManagement().user_view();
		}
		return dto;
	}

	public List<PaymentDTO> getPaymentList() {
		List<PaymentDTO> list = new ArrayList<PaymentDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT payment_no, ticket_no, price, pay_option,"
					+ " isPayed FROM payment ORDER BY payment_no ASC";
			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				int payment_no = r.getInt("payment_no");
				int ticket_no = r.getInt("ticket_no");
				int price = r.getInt("price");
				String pay_option = r.getString("pay_option");
				int isPayed = r.getInt("isPayed");

				list.add(new PaymentDTO(payment_no, ticket_no, price, pay_option, isPayed));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<PaymentDTO> getPaymentList_for_pay(String c_id) {
		List<PaymentDTO> list = new ArrayList<PaymentDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT payment_no, ticket_no, price, pay_option, isPayed " + "FROM payment "
					+ "where ticket_no in (select ticket_number from ticketing where "
					+ "client_id = ? ) ORDER BY ticket_no ASC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, c_id);
			r = pstmt.executeQuery();

			while (r.next()) {
				if(r.getInt("isPayed") == 0) {
					int payment_no = r.getInt("payment_no");
					int ticket_no = r.getInt("ticket_no");
					int price = r.getInt("price");
					String pay_option = r.getString("pay_option");
					int isPayed = r.getInt("isPayed");
					list.add(new PaymentDTO(payment_no, ticket_no, price, pay_option, isPayed));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public boolean updatePayment(PaymentDTO dto, int p_no, int using_point, int people) {
		Connection conn = null;
		Connection conn2 = null;
		Connection conn3 = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		boolean result = false;

		try {
			conn = getConnection();
			conn2 = getConnection();
			conn3 = getConnection();

			String sql = "UPDATE payment SET price = ?, pay_option = ?, isPayed = ? " + "where payment_no = ?;";
			String sql2 = "UPDATE client SET client_point = client_point - ?" + "WHERE client_id in "
					+ "(select client_id from ticketing where ticket_number in "
					+ "(select ticket_no from payment where payment_no = ? ));";
			String sql3 = "UPDATE client SET client_point = client_point + 100 * ? "
					+ "where client_id in (select client_id from ticketing where ticket_number in "
					+ "(select ticket_no from payment where payment_no = ? ));";

			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn2.prepareStatement(sql2);
			pstmt3 = conn3.prepareStatement(sql3);

			pstmt.setString(1, Integer.toString(dto.getPrice()));
			pstmt.setString(2, dto.getPay_option());
			pstmt.setString(3, Integer.toString(dto.getIsPayed()));
			pstmt.setString(4, Integer.toString(p_no));

			pstmt2.setString(1, Integer.toString(using_point));
			pstmt2.setString(2, Integer.toString(p_no));

			pstmt3.setString(1, Integer.toString(people));
			pstmt3.setString(2, Integer.toString(p_no));

			int r = pstmt.executeUpdate();
			int r2 = pstmt2.executeUpdate();
			int r3 = pstmt3.executeUpdate();
			if (r > 0 && r2 > 0 && r3 > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean deletePayment(String p_no) {
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "DELETE FROM payment WHERE payment_no = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p_no);
			int r = pstmt.executeUpdate();
			if (r > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public void addPurchased(String id, int t_no) {
		ResultSet rs = null;
		String m_na=null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		String date = sdf.format(cal.getTime());

		try {
			conn = getConnection();

			String sql = "select movie_name from movie where movie_id in (select movie_id from ticketing where ticket_number = ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, t_no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				m_na = rs.getString("movie_name");
			}
			
			sql = "insert into purchased (client_id,purchase_date,movie_name) values('" + id + "','" + date + "','" + m_na + "')";
			pstmt = conn.prepareStatement(sql);
			int r = pstmt.executeUpdate();

			if (r > 0) {
				System.out.println("구매 내역에 저장 되었습니다.");
			} else {
				System.out.println("구매 내역 저장에 오류가 생겼습니다.");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getFinal_price(int ticket_no, int using_point) {
		ResultSet r = null;
		int final_price = 1;

		try {
			conn = getConnection();

			String sql = "select (10000 * (select people " + "from ticketing where ticket_number = ? ) "
					+ "- ?) as final_price;";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, Integer.toString(ticket_no));
			pstmt.setString(2, Integer.toString(using_point));
			r = pstmt.executeQuery();
			if (r.next()) {
				final_price = r.getInt("final_price");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return final_price;
	}
}
