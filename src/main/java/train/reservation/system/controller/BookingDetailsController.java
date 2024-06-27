package train.reservation.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import train.reservation.system.entity.BookingDetails;
import train.reservation.system.entity.BookingRequest;
import train.reservation.system.service.BookingDetailsService;

@RestController
public class BookingDetailsController {

	@Autowired
	BookingDetailsService bookingService;

	@PostMapping("/booktrain")
	public ResponseEntity<BookingDetails> bookTrain(@RequestBody BookingRequest bookingRequest) {
		BookingDetails bookedTicket = bookingService.bookTrain(bookingRequest);
		return new ResponseEntity<>(bookedTicket, HttpStatus.OK);
	}

	@GetMapping("/getBookingDetails/{id}")
	public ResponseEntity<List<BookingDetails>> getBookingDetails(@PathVariable("id") String mail) {
		List<BookingDetails> bookingDetails = bookingService.getBookingDetailsById(mail);
		return new ResponseEntity<>(bookingDetails, HttpStatus.OK);
	}
}
