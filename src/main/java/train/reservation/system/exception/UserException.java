package train.reservation.system.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {

	private HttpStatus code;

	public UserException(HttpStatus status, String message) {
		super("message");
		this.code = status;
	}

	public HttpStatus getCode() {
		return code;
	}

}
