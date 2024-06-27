package train.reservation.system.exception;

import org.springframework.http.HttpStatus;

public class TrainException extends RuntimeException {

	private HttpStatus code;

	public TrainException(HttpStatus status, String message) {
		super("message");
		this.code = status;
	}

	public HttpStatus getCode() {
		return code;
	}

}
