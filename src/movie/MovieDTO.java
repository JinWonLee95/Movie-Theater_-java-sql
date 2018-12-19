package movie;
public class MovieDTO { 

	private String movie_id;
	private String movie_name;
	private String movie_director;
	private String movie_actor;
	private String movie_rating;
	private String movie_info;
	private int movie_reserve_count;
	private String theater_name;

	public MovieDTO() {
	
	}
	
	public MovieDTO(String movie_id, String movie_name,
	String movie_director, String movie_actor, String movie_rating,
	String movie_info, int movie_reserve_count, String theater_name) {
		this.movie_id = movie_id;
		this.movie_name = movie_name;
		this.movie_director = movie_director;
		this.movie_actor = movie_actor;
		this.movie_rating = movie_rating;
		this.movie_info = movie_info;
		this.movie_reserve_count = movie_reserve_count;
		this.theater_name = theater_name;
	}

	public String getMovie_id() {
		return movie_id;
	}

	public void setMovie_id(String movie_id) {
		this.movie_id = movie_id;
	}

	public String getMovie_name() {
		return movie_name;
	}

	public void setMovie_name(String movie_name) {
		this.movie_name = movie_name;
	}

	public String getMovie_director() {
		return movie_director;
	}

	public void setMovie_director(String movie_director) {
		this.movie_director = movie_director;
	}

	public String getMovie_actor() {
		return movie_actor;
	}

	public void setMovie_actor(String movie_actor) {
		this.movie_actor = movie_actor;
	}

	public String getMovie_rating() {
		return movie_rating;
	}

	public void setMovie_rating(String movie_rating) {
		this.movie_rating = movie_rating;
	}

	public String getMovie_info() {
		return movie_info;
	}

	public void setMovie_info(String movie_info) {
		this.movie_info = movie_info;
	}

	public int getMovie_reserve_count() {
		return movie_reserve_count;
	}

	public void setMovie_reserve_count(int movie_reserve_count) {
		this.movie_reserve_count = movie_reserve_count;
	}

	public String getTheater_name() {
		return theater_name;
	}

	public void setTheater_name(String t_name) {
		this.theater_name = t_name;
	}

	public String getInfo() {
		StringBuffer sb = new StringBuffer();
		sb.append("\r\n");
		sb.append("[ "+movie_name+ " ] ��ȭ ����====\n");
		sb.append("��ȭ id : "+ movie_id +"\n");
		sb.append("��ȭ �̸� : "+ movie_name +"\n");
		sb.append("��ȭ ���� : "+ movie_director +"\n");
		sb.append("��ȭ �⿬ : "+ movie_actor +"\n");
		sb.append("��ȭ ��� : "+ movie_rating +"\n");
		sb.append("��ȭ ���� : "+ movie_info +"\n");
		sb.append("��ȭ ���� Ƚ�� : "+ movie_reserve_count +"\n");
		sb.append("��ȭ�� �̸� : "+ theater_name +"\n");

		return sb.toString();
	}

}
