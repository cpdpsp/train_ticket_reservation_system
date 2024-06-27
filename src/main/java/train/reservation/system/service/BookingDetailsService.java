package train.reservation.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import train.reservation.system.entity.BookingDetails;
import train.reservation.system.entity.BookingRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.TrainException;
import train.reservation.system.exception.UserException;
import train.reservation.system.repository.BookingDetailsRepository;

@Service
public class BookingDetailsService {

	@Autowired
	BookingDetailsRepository bookingRepository;

	@Autowired
	TrainService trainService;

	public BookingDetails bookTrain(BookingRequest bookingRequest) {
		Optional<Train> train = trainService.getTrain(bookingRequest.getTrainNo());
		if (train.get().getSeats() < bookingRequest.getSeats())
			throw new TrainException(HttpStatus.UNPROCESSABLE_ENTITY,
					"Only " + train.get().getSeats() + " seats are available in this train.");
		else {
			BookingDetails details = new BookingDetails();
			details.setAmount(bookingRequest.getSeats() * train.get().getFare());
			details.setDate(bookingRequest.getDate());
			details.setFromStation(train.get().getFrom_station());
			details.setMailId(bookingRequest.getMail_id());
			details.setSeats(bookingRequest.getSeats());
			details.setTrainNo(bookingRequest.getTrainNo());
			details.setToStation(train.get().getTo_station());
			return bookingRepository.save(details);
		}
	}

	public List<BookingDetails> getBookingDetailsById(String mail) {
		List<BookingDetails> bookingDetails = bookingRepository.getBookingDetailsById(mail);
		if (bookingDetails.isEmpty())
			throw new UserException(HttpStatus.UNPROCESSABLE_ENTITY, "No tickets booked.");
		return bookingDetails;
	}
}
