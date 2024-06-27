package train.reservation.system.entity;

public class BookingRequest {

	private String mailId;
	private Long trainNo;
	private String date;
	private int seats;

	public String getMail_id() {
		return mailId;
	}

	public void setMail_id(String mail_id) {
		this.mailId = mail_id;
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

}
