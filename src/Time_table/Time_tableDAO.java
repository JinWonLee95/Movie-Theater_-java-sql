package Time_table;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Auditorium.AuditoriumDTO;

public class Time_tableDAO {

	private static Time_tableDAO instance = new Time_tableDAO();

	public static Time_tableDAO getInstance() {
		return instance;
	}

	public Time_tableDAO() {

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

	public boolean insertTime_table(Time_tableDTO dto) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into time_table VALUES (?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getAuditorium_name());
			pstmt.setString(2, dto.getShow_time());
			pstmt.setString(3, dto.getMovie_id());
			pstmt.setString(4, dto.getStart_date());
			pstmt.setString(5, dto.getEnd_date());

			int r = pstmt.executeUpdate();
			if (r > 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public Time_tableDTO getTime_table(String a_name) {
		Time_tableDTO dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, " + "show_time, movie_id, start_date, end_date "
					+ "FROM time_table WHERE auditorium_name = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_name);
			r = pstmt.executeQuery();

			if (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				String show_time = r.getString("show_time");
				String movie_id = r.getString("movie_id");
				String start_date = r.getDate("start_date").toString();
				String end_date = r.getDate("end_date").toString();
				dto = new Time_tableDTO(auditorium_name, show_time, movie_id, start_date, end_date);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}
	
	public boolean showTime_tableExist(String a_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;
		boolean result = false;
		int success = -1;
		try {
			conn = getConnection();

			String sql = "select exists (select * from time_table where auditorium_name = ?) as success";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_name);
			r = pstmt.executeQuery();
			if (r.next()) {
				success = r.getInt("success");
			}
			if (success > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public List<Time_tableDTO> getTime_tableList() {
		List<Time_tableDTO> list = new ArrayList<Time_tableDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, show_time, movie_id,"
					+ "start_date, end_date FROM time_table ORDER BY auditorium_name ASC";
			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				String show_time = r.getString("show_time");
				String movie_id = r.getString("movie_id");
				String start_date = r.getDate("start_date").toString();
				String end_date = r.getDate("end_date").toString();
				list.add(new Time_tableDTO(auditorium_name, show_time, movie_id, start_date, end_date));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} 

		return list;
	}

	public List<Time_tableDTO> getTime_tableListByAuditorium(String a_name) {
		List<Time_tableDTO> list = new ArrayList<Time_tableDTO>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT auditorium_name, show_time, movie_id,"
					+ "start_date, end_date FROM time_table where auditorium_name = ? ORDER BY show_time ASC";

			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, a_name);
			r = pstmt.executeQuery();

			while (r.next()) {
				String auditorium_name = r.getString("auditorium_name");
				String show_time = r.getString("show_time");
				String movie_id = r.getString("movie_id");
				String start_date = r.getDate("start_date").toString();
				String end_date = r.getDate("end_date").toString();
				list.add(new Time_tableDTO(auditorium_name, show_time, movie_id, start_date, end_date));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public boolean deleteTime_table(String a_name) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();

			String sql = "DELETE FROM time_table WHERE auditorium_name = ?";
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
