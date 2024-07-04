package train.reservation.system.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import train.reservation.system.entity.FareEnquiryRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.UnprocessableEntityException;
import train.reservation.system.repository.TrainRepository;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrainServiceTest {

	@Mock
	private TrainRepository trainRepository;

	@InjectMocks
	private TrainService trainService;

	private Train train;

	@BeforeEach
	private void setup() {
		MockitoAnnotations.initMocks(this);
		train = new Train(1001L, "JAN SATABDI EXP", "RANCHI", "PATNA", 100, 50.0);
	}

	@Test
	public void whenGetTrainAndValidTrainNo_thenCorrectResponse() {
		Mockito.when(trainRepository.findById(Mockito.any())).thenReturn(Optional.of(train));
		Optional<Train> trainDetails = trainService.getTrain(1001L);

		Assertions.assertEquals(train, trainDetails.get());

	}

	@Test
	public void whenGetTrainAndInvalidTrainNo_thenThrowException() {
		Mockito.when(trainRepository.findById(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Invalid train number"));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> trainService.getTrain(1L));

	}

	@Test
	public void whenAddTrainAndValidTrain_thenCorrectResponse() {
		Mockito.when(trainRepository.save(Mockito.any())).thenReturn(train);

		Train trainAdded = trainService.addTrain(train);

		Assertions.assertEquals(train, trainAdded);
	}

	@Test
	public void whenDeleteTrainAndValidTrain_thenCorrectResponse() {
		Mockito.doNothing().when(trainRepository).deleteById(Mockito.any());

		trainService.deleteTrain(1001L);

		Mockito.verify(trainRepository, Mockito.times(1)).deleteById(1001L);
	}

	@Test
	public void whenGetAllTrains_theCorrectResponse() {
		List<Train> trainList = new ArrayList<>();
		trainList.add(train);

		Mockito.when(trainRepository.findAll()).thenReturn(trainList);

		List<Train> trainListReceived = trainService.getAllTrains();

		Assertions.assertEquals(trainList, trainListReceived);
	}

	@Test
	public void whenGetFareAndValidDetails_thenCorrectResponse() {
		List<Train> trainList = new ArrayList<>();
		trainList.add(train);

		Mockito.when(trainRepository.getFareForStations(Mockito.any(), Mockito.any())).thenReturn(trainList);

		FareEnquiryRequest request = new FareEnquiryRequest("RANCHI", "PATNA");

		List<Train> trainListReceived = trainService.getFare(request);

		Assertions.assertEquals(trainList, trainListReceived);
	}

	@Test
	public void whenGetFareAndInvalidDetails_thenThrowException() {
		FareEnquiryRequest request = new FareEnquiryRequest("RANCHI", "PATNA");

		Mockito.when(trainRepository.getFareForStations(Mockito.any(), Mockito.any()))
				.thenThrow(new UnprocessableEntityException(
						"There are no trains between " + request.getFromStation() + " to " + request.getToStation()));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> trainService.getFare(request));
	}

	@Test
	public void whenUpdateTrainAndValidTrain_thenCorrectResponse() {
		Train updatedTrain = new Train(1001L, "JAN SATABDI", "GAYA", "PATNA", 10, 25.0);

		Mockito.when(trainRepository.findById(Mockito.any())).thenReturn(Optional.of(train));
		Mockito.when(trainRepository.save(Mockito.any())).thenReturn(updatedTrain);

		Train trainReceived = trainService.updateTrain(updatedTrain);

		Assertions.assertEquals(updatedTrain, trainReceived);
	}

	@Test
	public void whenUpdateTrainAndInvalidTrainNo_thenThrowException() {
		Mockito.when(trainRepository.findById(Mockito.any()))
				.thenThrow(new UnprocessableEntityException("Invalid train number"));

		Assertions.assertThrows(UnprocessableEntityException.class, () -> trainService.updateTrain(train));
	}
}
