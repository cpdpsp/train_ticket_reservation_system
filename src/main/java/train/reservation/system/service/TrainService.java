package train.reservation.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import train.reservation.system.entity.FareEnquiryRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.TrainException;
import train.reservation.system.repository.TrainRepository;

@Service
public class TrainService {

	@Autowired
	TrainRepository trainRepository;

	public Optional<Train> getTrain(Long trainNo) {
		Optional<Train> train = trainRepository.findById(trainNo);
		if (train.isPresent())
			return train;
		else
			throw new TrainException(HttpStatus.UNPROCESSABLE_ENTITY, "Invalid train number");
	}

	public Train addTrain(Train train) {
		return trainRepository.save(train);
	}

	public void deleteTrain(Long trainNo) {
		trainRepository.deleteById(trainNo);
	}

	public List<Train> getAllTrains() {
		return trainRepository.findAll();
	}

	public List<Train> getFare(FareEnquiryRequest request) {
		List<Train> trainList = trainRepository.getFareForStations(request);
		if (trainList.isEmpty())
			throw new TrainException(HttpStatus.UNPROCESSABLE_ENTITY,
					"There are no trains between " + request.getToStation() + " to " + request.getFromStation());
		else
			return trainList;
	}
}
