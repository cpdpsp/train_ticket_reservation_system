package train.reservation.system.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<ErrorResponse> handleUserException(UserException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(ex.getCode()).body(errorResponse);
	}

	@ExceptionHandler(TrainException.class)
	public ResponseEntity<ErrorResponse> handleTrainException(UserException ex) {
		ErrorResponse errorResponse = new ErrorResponse(ex.getCode(), ex.getMessage());
		return ResponseEntity.status(ex.getCode()).body(errorResponse);
	}
}
