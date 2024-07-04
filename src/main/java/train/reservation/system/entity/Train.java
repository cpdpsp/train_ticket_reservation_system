package train.reservation.system.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "train")
public class Train {

	@Id
	@Column(name = "train_no")
	private Long trainNo;

	@Column(name = "train_name")
	private String trainName;

	@Column(name = "from_station")
	private String fromStation;

	@Column(name = "to_station")
	private String toStation;

	private Integer seats;
	private Double fare;

	public Long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(Long trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getFromStation() {
		return fromStation;
	}

	public void setFromStation(String fromStation) {
		this.fromStation = fromStation;
	}

	public String getToStation() {
		return toStation;
	}

	public void setToStation(String toStation) {
		this.toStation = toStation;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public Double getFare() {
		return fare;
	}

	public void setFare(Double fare) {
		this.fare = fare;
	}

	public Train(Long trainNo, String trainName, String fromStation, String toStation, Integer seats, Double fare) {
		super();
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.fromStation = fromStation;
		this.toStation = toStation;
		this.seats = seats;
		this.fare = fare;
	}

	public Train() {
		super();
		// TODO Auto-generated constructor stub
	}

}
