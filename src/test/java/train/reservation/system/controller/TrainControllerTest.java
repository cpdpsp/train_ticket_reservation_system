package train.reservation.system.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.aMapWithSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import train.reservation.system.entity.FareEnquiryRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.GlobalExceptionHandler;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.repository.TrainRepository;
import train.reservation.system.service.TrainService;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrainControllerTest {

	@Mock
	private TrainService trainService;

	@Mock
	private TrainRepository trainRepository;

	@InjectMocks
	private TrainController trainController;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;

	private Train train;

	@BeforeEach
	private void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(trainController).setControllerAdvice(new GlobalExceptionHandler())
				.build();
		train = new Train(1001L, "JAN SATABDI EXP", "RANCHI", "PATNA", 100, 50.0);
	}

	@Test
	public void whenGetTrainAndValidTrain_thenCorrectResponse() throws Exception {
		Mockito.when(trainService.getTrain(Mockito.any())).thenReturn(Optional.ofNullable(train));

		mockMvc.perform(MockMvcRequestBuilders.get("/viewTrain/{trainNo}", 1001))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(jsonPath("$", aMapWithSize(6)))
				.andExpect(jsonPath("$.trainNo").value(train.getTrainNo()))
				.andExpect(jsonPath("$.trainName", is(train.getTrainName())))
				.andExpect(jsonPath("$.fromStation", is(train.getFromStation())))
				.andExpect(jsonPath("$.toStation", is(train.getToStation())))
				.andExpect(jsonPath("$.seats", is(train.getSeats())))
				.andExpect(jsonPath("$.fare", is(train.getFare())));
	}

	@Test
	public void whenGetTrainAndInvalidTrainNo_thenThrowException() throws Exception {
		Mockito.when(trainService.getTrain(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Invalid train number"));
		mockMvc.perform(MockMvcRequestBuilders.get("/viewTrain/{trainNo}", 1001))
				.andExpect(MockMvcResultMatchers.status().is4xxClientError());
	}

	@Test
	public void whenAddTrainAndValidTrain_thenCorrectResponse() throws Exception {
		Mockito.when(trainService.addTrain(Mockito.any())).thenReturn(train);

		mockMvc.perform(MockMvcRequestBuilders.post("/addTrain").content(objectMapper.writeValueAsString(train))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(6))).andExpect(jsonPath("$.trainNo").value(train.getTrainNo()))
				.andExpect(jsonPath("$.trainName", is(train.getTrainName())))
				.andExpect(jsonPath("$.fromStation", is(train.getFromStation())))
				.andExpect(jsonPath("$.toStation", is(train.getToStation())))
				.andExpect(jsonPath("$.seats", is(train.getSeats())))
				.andExpect(jsonPath("$.fare", is(train.getFare())));

	}

	@Test
	public void whenCancelTrainAndValidTrain_thenCorrectResponse() throws Exception {
		Mockito.doNothing().when(trainService).deleteTrain(Mockito.any());

		mockMvc.perform(MockMvcRequestBuilders.delete("/cancelTrain/{trainNo}", 1001))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	public void whenGetAllTrains_theCorrectResponse() throws Exception {
		List<Train> trainList = new ArrayList<>();
		trainList.add(train);

		Mockito.when(trainService.getAllTrains()).thenReturn(trainList);

		mockMvc.perform(MockMvcRequestBuilders.get("/viewAllTrains"))
				.andExpect(jsonPath("$[0].trainNo").value(trainList.get(0).getTrainNo()))
				.andExpect(jsonPath("$[0].trainName", is(trainList.get(0).getTrainName())))
				.andExpect(jsonPath("$[0].fromStation", is(trainList.get(0).getFromStation())))
				.andExpect(jsonPath("$[0].toStation", is(trainList.get(0).getToStation())))
				.andExpect(jsonPath("$[0].seats", is(trainList.get(0).getSeats())))
				.andExpect(jsonPath("$[0].fare", is(trainList.get(0).getFare())));
	}

	@Test
	public void whenGetFareAndValidDetails_thenCorrectResponse() throws Exception {
		List<Train> trainList = new ArrayList<>();
		trainList.add(train);

		FareEnquiryRequest request = new FareEnquiryRequest("RANCHI", "PATNA");

		Mockito.when(trainService.getFare(Mockito.any())).thenReturn(trainList);

		mockMvc.perform(MockMvcRequestBuilders.get("/fareEnquiry").content(objectMapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].trainNo").value(trainList.get(0).getTrainNo()))
				.andExpect(jsonPath("$[0].trainName", is(trainList.get(0).getTrainName())))
				.andExpect(jsonPath("$[0].fromStation", is(trainList.get(0).getFromStation())))
				.andExpect(jsonPath("$[0].toStation", is(trainList.get(0).getToStation())))
				.andExpect(jsonPath("$[0].seats", is(trainList.get(0).getSeats())))
				.andExpect(jsonPath("$[0].fare", is(trainList.get(0).getFare())));
	}

	@Test
	public void whenUpdateAndValidTrain_thenCorrectResponse() throws Exception {
		Train updatedTrain = new Train(1001L, "JAN SATABDI", "RANCHI", "GAYA", 200, 30.0);

		Mockito.when(trainService.updateTrain(Mockito.any())).thenReturn(updatedTrain);

		mockMvc.perform(MockMvcRequestBuilders.put("/updateTrain").content(objectMapper.writeValueAsString(train))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(jsonPath("$", aMapWithSize(6)))
				.andExpect(jsonPath("$.trainNo").value(updatedTrain.getTrainNo()))
				.andExpect(jsonPath("$.trainName", is(updatedTrain.getTrainName())))
				.andExpect(jsonPath("$.fromStation", is(updatedTrain.getFromStation())))
				.andExpect(jsonPath("$.toStation", is(updatedTrain.getToStation())))
				.andExpect(jsonPath("$.seats", is(updatedTrain.getSeats())))
				.andExpect(jsonPath("$.fare", is(updatedTrain.getFare())));
	}
}
