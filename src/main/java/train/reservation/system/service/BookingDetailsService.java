package train.reservation.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import train.reservation.system.entity.BookingDetails;
import train.reservation.system.entity.BookingRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.repository.BookingDetailsRepository;

@Service
public class BookingDetailsService {

	@Autowired
	BookingDetailsRepository bookingRepository;

	@Autowired
	TrainService trainService;

	public BookingDetails bookTrain(BookingRequest bookingRequest) {
		Optional<Train> train = trainService.getTrain(bookingRequest.getTrainNo());
		train.orElseThrow(() -> new UnprocessableEntityException("Invalid train number"));
		if (train.get().getSeats() < bookingRequest.getSeats())
			throw new UnprocessableEntityException(
					"Only " + train.get().getSeats() + " seats are available in this train.");
		else {
			BookingDetails details = new BookingDetails();
			details.setAmount(bookingRequest.getSeats() * train.get().getFare());
			details.setDate(bookingRequest.getDate());
			details.setFromStation(train.get().getFromStation());
			details.setMailId(bookingRequest.getMailId());
			details.setSeats(bookingRequest.getSeats());
			details.setTrainNo(bookingRequest.getTrainNo());
			details.setToStation(train.get().getToStation());
			return bookingRepository.save(details);
		}
	}

	public List<BookingDetails> getBookingDetailsById(String mail) {
		List<BookingDetails> bookingDetails = bookingRepository.getBookingDetailsById(mail);
		if (bookingDetails.isEmpty())
			throw new UnprocessableEntityException("No tickets booked.");
		return bookingDetails;
	}
}
