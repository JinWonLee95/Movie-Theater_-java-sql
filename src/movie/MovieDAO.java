package movie;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieDAO {

	private static MovieDAO instance = new MovieDAO();
	Connection conn = null;
	PreparedStatement pstmt = null;
	PreparedStatement pstmt2 = null;

	public static MovieDAO getInstance() {
		return instance;
	}

	public MovieDAO() {

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

	public boolean insertMovie(MovieDTO dto) {

		boolean result = false;

		try {
			conn = getConnection();

			String sql = "insert into movie VALUES (?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getMovie_id());
			pstmt.setString(2, dto.getMovie_name());
			pstmt.setString(3, dto.getMovie_director());
			pstmt.setString(4, dto.getMovie_actor());
			pstmt.setString(5, dto.getMovie_rating());
			pstmt.setString(6, dto.getMovie_info());
			pstmt.setString(7, Integer.toString(dto.getMovie_reserve_count()));
			pstmt.setString(8, dto.getTheater_name());

			int r = pstmt.executeUpdate();
			if (r > 0) {
				result = true;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public MovieDTO getMovie(String m_id) {
		MovieDTO dto = null;
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT movie_id, movie_name, movie_director, movie_actor, movie_rating, movie_info, movie_reserve_count, theater_name FROM movie WHERE movie_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			r = pstmt.executeQuery();

			if (r.next()) {
				String movie_id = r.getString("movie_id");
				String movie_name = r.getString("movie_name");
				String movie_director = r.getString("movie_director");
				String movie_actor = r.getString("movie_actor");
				String movie_rating = r.getString("movie_rating");
				String movie_info = r.getString("movie_info");
				int movie_reserve_count = r.getInt("movie_reserve_count");
				String theater_name = r.getString("theater_name");
				dto = new MovieDTO(movie_id, movie_name, movie_director, movie_actor, movie_rating, movie_info,
						movie_reserve_count, theater_name);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	public List<MovieDTO> getMovieList_for_manager() {
		List<MovieDTO> list = new ArrayList<MovieDTO>();

		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "SELECT movie_id, movie_name, movie_director, movie_actor, movie_rating, movie_info, movie_reserve_count, theater_name FROM movie ORDER BY movie_reserve_count DESC";
			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String movie_id = r.getString("movie_id");
				String movie_name = r.getString("movie_name");
				String movie_director = r.getString("movie_director");
				String movie_actor = r.getString("movie_actor");
				String movie_rating = r.getString("movie_rating");
				String movie_info = r.getString("movie_info");
				int movie_reserve_count = r.getInt("movie_reserve_count");
				String theater_name = r.getString("theater_name");
				list.add(new MovieDTO(movie_id, movie_name, movie_director, movie_actor, movie_rating, movie_info,
						movie_reserve_count, theater_name));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<MovieDTO> getMovieList_for_user() {
		List<MovieDTO> list = new ArrayList<MovieDTO>();
		ArrayList<String> list2 = new ArrayList<String>();
		ArrayList<String> movie_id_list = new ArrayList<String>();
		ArrayList<String> time_table_exist_list = new ArrayList<String>();

		ResultSet r = null;
		ResultSet r2 = null;
		ResultSet r3 = null;

		try {
			conn = getConnection();

			String sql3 = "SELECT distinct movie_id FROM time_table";
			pstmt = conn.prepareStatement(sql3);
			r3 = pstmt.executeQuery();

			while (r3.next()) {
				String m_id = r3.getString("movie_id");
				movie_id_list.add(m_id);
			}

			int index = 0;
			while (index < movie_id_list.size()) {
				String sql2 = "SELECT movie_name FROM movie where movie_id = ?";
				pstmt = conn.prepareStatement(sql2);
				pstmt.setString(1, movie_id_list.get(index));
				r2 = pstmt.executeQuery();

				while (r2.next()) {
					String m_name = r2.getString("movie_name");
					time_table_exist_list.add(m_name);
				}
				index++;
			}
			String sql = "SELECT movie_id, movie_name, movie_director, movie_actor, movie_rating, movie_info, movie_reserve_count, theater_name FROM movie ORDER BY movie_reserve_count DESC";
			pstmt = conn.prepareStatement(sql);
			r = pstmt.executeQuery();

			while (r.next()) {
				String movie_id = r.getString("movie_id");
				String movie_name = r.getString("movie_name");
				String movie_director = r.getString("movie_director");
				String movie_actor = r.getString("movie_actor");
				String movie_rating = r.getString("movie_rating");
				String movie_info = r.getString("movie_info");
				int movie_reserve_count = r.getInt("movie_reserve_count");
				String theater_name = r.getString("theater_name");

				MovieDTO m_dto = new MovieDTO(movie_id, movie_name, movie_director, movie_actor, movie_rating,
						movie_info, movie_reserve_count, theater_name);

				if (time_table_exist_list.contains(movie_name)) {
					if (list2.contains(movie_name)) {
						int a = list2.indexOf(movie_name);
						int sum = list.get(a).getMovie_reserve_count() + movie_reserve_count;
						list.get(a).setMovie_reserve_count(sum);
					} else {
						list.add(m_dto);
						list2.add(movie_name);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	// 회원 수정
	public boolean updateMovie(MovieDTO dto, String m_id) {

		boolean result = false;

		try {
			conn = getConnection();

			String sql = "UPDATE movie SET movie_id=?, movie_name=?, movie_director=?, movie_actor=?, movie_rating=?, movie_info=?, movie_reserve_count=?, theater_name=? WHERE movie_id = ?";
			pstmt = conn.prepareStatement(sql);

			pstmt.setString(1, dto.getMovie_id());
			pstmt.setString(2, dto.getMovie_name());
			pstmt.setString(3, dto.getMovie_director());
			pstmt.setString(4, dto.getMovie_actor());
			pstmt.setString(5, dto.getMovie_rating());
			pstmt.setString(6, dto.getMovie_info());
			pstmt.setString(7, Integer.toString(dto.getMovie_reserve_count()));
			pstmt.setString(8, dto.getTheater_name());
			pstmt.setString(9, m_id);
			int r = pstmt.executeUpdate();
			if (r > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public boolean confirmMovie(String m_id) {
		ResultSet r = null;

		try {
			conn = getConnection();

			String sql = "select exists (select * from movie where movie_id = ?) as success";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			r = pstmt.executeQuery();

			r.next();
			if (r.getInt(1) == 1) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteMovie(String m_id) {

		String sql = null;
		boolean result = false;
		try {
			conn = getConnection();

			sql = "delete from time_table where movie_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			int r1 = pstmt.executeUpdate();

			sql = "DELETE FROM movie WHERE movie_id = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m_id);
			int r = pstmt.executeUpdate();

			if (r > 0 && r1 > 0) {
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
