package train.reservation.system.entity;

public class FareEnquiryRequest {

	private String fromStation;
	private String toStation;

	public FareEnquiryRequest(String fromStation, String toStation) {
		super();
		this.fromStation = fromStation;
		this.toStation = toStation;
	}

	public FareEnquiryRequest() {
		super();
		// TODO Auto-generated constructor stub
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

}
