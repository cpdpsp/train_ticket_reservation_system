package train.reservation.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import train.reservation.system.entity.BookingDetails;
import train.reservation.system.entity.BookingRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.repository.BookingDetailsRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingDetailsServiceTest {

	@Mock
	private BookingDetailsRepository bookingRepository;

	@Mock
	private TrainService trainService;

	@InjectMocks
	private BookingDetailsService bookingService;

	private BookingDetails bookingDetails;

	private BookingRequest request;

	@BeforeEach
	private void setup() {
		MockitoAnnotations.initMocks(this);
		bookingDetails = new BookingDetails(String.valueOf(UUID.randomUUID()), "suny@gmail.com", 1001L, "02-FEB-2024",
				"RANCHI", "JODHPUR", 2, 100.0);

		request = new BookingRequest("suny@gmail.com", 1001L, "02-FEB-2024", 2);

	}

	@Test
	public void whenBookTrainAndValidDetails_thenCorrectResponse() throws Exception {
		Optional<Train> train = Optional.ofNullable(new Train(1001L, "JAN SATABDI EXP", "RANCHI", "PATNA", 100, 50.0));

		Mockito.when(trainService.getTrain(Mockito.any())).thenReturn(train);
		Mockito.when(bookingRepository.save(Mockito.any())).thenReturn(bookingDetails);

		BookingDetails bookedTicket = bookingService.bookTrain(request);

		Assertions.assertEquals(bookingDetails, bookedTicket);
	}

	@Test
	public void whenBookTrainAndInvalidTrainNo_thenThrowException() throws Exception {
		Optional<Train> train = Optional.ofNullable(new Train(1001L, "JAN SATABDI EXP", "RANCHI", "PATNA", 100, 50.0));

		Mockito.when(trainService.getTrain(Mockito.any())).thenReturn(train);
		Mockito.when(bookingRepository.save(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Invalid train number"));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> bookingService.bookTrain(request));

		Mockito.verify(bookingRepository, Mockito.never()).save(bookingDetails);
	}

	@Test
	public void whenBookTrainAndinvalidSeats_thenThrowException() throws Exception {
		Optional<Train> train = Optional.ofNullable(new Train(1001L, "JAN SATABDI EXP", "RANCHI", "PATNA", 0, 50.0));

		Mockito.when(trainService.getTrain(Mockito.any())).thenReturn(train);
		Mockito.when(bookingRepository.save(Mockito.any())).thenThrow(new UnprocessableEntityException(
				"Only " + train.get().getSeats() + " seats are available in this train."));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> bookingService.bookTrain(request));

		Mockito.verify(bookingRepository, Mockito.never()).save(bookingDetails);
	}

	@Test
	public void whenGetBookingDetailsAndValidDetails_thenCorrectResponse() throws Exception {
		List<BookingDetails> bookingHistory = new ArrayList<>();
		bookingHistory.add(bookingDetails);

		Mockito.when(bookingRepository.getBookingDetailsById(Mockito.any())).thenReturn(bookingHistory);

		List<BookingDetails> bookingHistoryReceived = bookingService.getBookingDetailsById("suny@gmail.com");
		Assertions.assertEquals(bookingHistory, bookingHistoryReceived);

	}

	@Test
	public void whenGetBookingDetailsAndInvalidEmail_thenThrowException() throws Exception {
		Mockito.when(bookingRepository.getBookingDetailsById(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("No tickets booked."));

		Assertions.assertThrows(UnprocessableEntityException.class,
				() -> bookingService.getBookingDetailsById("suny@gmail.com"));

	}
}
