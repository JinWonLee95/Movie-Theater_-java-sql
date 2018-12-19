package Auditorium;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AuditoriumDAO {

	private static AuditoriumDAO instance = new AuditoriumDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;

	public static AuditoriumDAO getInstance() {
		return instance;
	}

	public AuditoriumDAO() {

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

	public boolean insertAuditorium(AuditoriumDTO dto) {
		
		PreparedStatement pstmt2 = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into auditorium VALUES (?,?,?)";
			String sql2 = "select exists (select * from auditorium where auditorium_name = ?) as success";
			pstmt = conn.prepareStatement(sql);
			pstmt2 = conn.prepareStatement(sql2);
			pstmt.setString(1, dto.getAuditorium_name());
			pstmt.setString(2, Integer.toString(dto.getSeat()));
			pstmt.setString(3, dto.getTheater_name());
			
			pstmt2.setString(1, dto.getAuditorium_name());
			ResultSet rs = pstmt2.executeQuery();

			rs.next();
			if (rs.getInt(1) == 1) {
				System.out.println(dto.getAuditorium_name() + "은 존재하는 상영관입니다.");
			} else {
				int r = pstmt.executeUpdate();
				if (r > 0) {
					result = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public AuditoriumDTO getAuditorium(String a_name) {
		AuditoriumDTO dto = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, seat, theater_name FROM auditorium WHERE auditorium_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_name);
			r = pstmt.executeQuery();

			if (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				int seat = r.getInt("seat");
				String theater_name = r.getString("theater_name");
				dto = new AuditoriumDTO(auditorium_name, seat, theater_name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return dto;
	}

	public List<AuditoriumDTO> getAuditoriumList() {
		List<AuditoriumDTO> list = new ArrayList<AuditoriumDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, seat, theater_name FROM auditorium ORDER BY auditorium_name DESC";

			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				int seat = r.getInt("seat");
				String theater_name = r.getString("theater_name");
				list.add(new AuditoriumDTO(auditorium_name, seat, theater_name));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<AuditoriumDTO> getAuditoriumListByTheater(String t_name) {
		List<AuditoriumDTO> list = new ArrayList<AuditoriumDTO>();
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, seat, theater_name FROM auditorium where theater_name = ?";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t_name);
			r = pstmt.executeQuery();

			while (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				int seat = r.getInt("seat");
				String theater_name = r.getString("theater_name");
				list.add(new AuditoriumDTO(auditorium_name, seat, theater_name));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean deleteAuditorium(String a_name) {
		boolean result = false;
		try {
			conn = getConnection();

			String sql = "DELETE FROM auditorium WHERE auditorium_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_name);
			int r = pstmt.executeUpdate();
			if (r > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
