package train.reservation.system.exception;

import org.springframework.http.HttpStatus;

public class ErrorResponse {

	private HttpStatus statusCode;
	private String message;

	public ErrorResponse(HttpStatus httpStatus, String message) {
		statusCode = httpStatus;
		this.message = message;
	}

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

}
