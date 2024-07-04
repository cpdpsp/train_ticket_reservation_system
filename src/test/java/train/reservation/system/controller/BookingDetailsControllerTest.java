package train.reservation.system.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import train.reservation.system.entity.BookingDetails;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.GlobalExceptionHandler;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.service.BookingDetailsService;
import train.reservation.system.service.TrainService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class BookingDetailsControllerTest {

	@Mock
	private BookingDetailsService bookingService;

	@Mock
	private TrainService trainService;

	@InjectMocks
	private BookingDetailsController bookingController;

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private BookingDetails bookingDetail;

	@BeforeEach
	private void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(bookingController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
		bookingDetail = new BookingDetails(String.valueOf(UUID.randomUUID()), "suny@gmail.com", 1001L, "02-FEB-2024",
				"RANCHI", "JODHPUR", 2, 100.0);
	}

	@Test
	public void whenBookTrainAndValidTrain_thenCorrectResponse() throws Exception {

		String request = objectMapper.writeValueAsString(bookingDetail);
		Optional<Train> train = Optional.ofNullable(new Train(1001L, "JAN SATABDI EXP", "RANCHI", "PATNA", 100, 50.0));

		Mockito.when(trainService.getTrain(Mockito.any())).thenReturn(train);
		Mockito.when(bookingService.bookTrain(Mockito.any())).thenReturn(bookingDetail);

		mockMvc.perform(
				MockMvcRequestBuilders.post("/booktrain").content(request).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$", aMapWithSize(8)))
				.andExpect(jsonPath("$.transId", is(bookingDetail.getTransId())))
				.andExpect(jsonPath("$.mailId", is(bookingDetail.getMailId())))
				.andExpect(jsonPath("$.date", is(bookingDetail.getDate())))
				.andExpect(jsonPath("$.trainNo").value(bookingDetail.getTrainNo()))
				.andExpect(jsonPath("$.fromStation", is(bookingDetail.getFromStation())))
				.andExpect(jsonPath("$.toStation", is(bookingDetail.getToStation())))
				.andExpect(jsonPath("$.seats", is(bookingDetail.getSeats())))
				.andExpect(jsonPath("$.amount", is(bookingDetail.getAmount())));
	}

	@Test
	public void whenBookTrainAndInvalidDetails_thenThrowException() throws Exception {
		BookingDetails invalidBookingDetail = new BookingDetails("1", "suny@gmail.com", 1001L, "02-FEB-2024", "RANCHI",
				"JODHPUR", 100, 50.0);

		String request = objectMapper.writeValueAsString(invalidBookingDetail);

		Mockito.when(bookingService.bookTrain(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Invalid train number"));

		mockMvc.perform(
				MockMvcRequestBuilders.post("/booktrain").content(request).contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void whenGetBookingDetailsAndValidEmail_thenCorrectResponse() throws Exception {
		List<BookingDetails> bookingHistory = new ArrayList<>();
		bookingHistory.add(bookingDetail);

		Mockito.when(bookingService.getBookingDetailsById(Mockito.any())).thenReturn(bookingHistory);

		mockMvc.perform(MockMvcRequestBuilders.get("/getBookingDetails/{id}", bookingDetail.getMailId()))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$[0].transId", is(bookingHistory.get(0).getTransId())))
				.andExpect(jsonPath("$[0].mailId", is(bookingHistory.get(0).getMailId())))
				.andExpect(jsonPath("$[0].date", is(bookingHistory.get(0).getDate())))
				.andExpect(jsonPath("$[0].trainNo").value(bookingHistory.get(0).getTrainNo()))
				.andExpect(jsonPath("$[0].fromStation", is(bookingHistory.get(0).getFromStation())))
				.andExpect(jsonPath("$[0].toStation", is(bookingHistory.get(0).getToStation())))
				.andExpect(jsonPath("$[0].seats", is(bookingHistory.get(0).getSeats())))
				.andExpect(jsonPath("$[0].amount", is(bookingHistory.get(0).getAmount())));

	}
}
