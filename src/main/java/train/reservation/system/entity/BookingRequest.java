package train.reservation.system.entity;

public class BookingRequest {

	private String mailId;
	private Long trainNo;
	private String date;
	private int seats;

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}

	public Long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(Long trainNo) {
		this.trainNo = trainNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public BookingRequest(String mailId, Long trainNo, String date, int seats) {
		super();
		this.mailId = mailId;
		this.trainNo = trainNo;
		this.date = date;
		this.seats = seats;
	}

	public BookingRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

}
