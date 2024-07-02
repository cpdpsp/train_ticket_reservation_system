package train.reservation.system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import train.reservation.system.entity.FareEnquiryRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.exception.UnprocessableEntityException;
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
			throw new UnprocessableEntityException("Invalid train number");
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
		List<Train> trainList = trainRepository.getFareForStations(request.getFromStation(), request.getToStation());
		if (trainList.isEmpty())
			throw new UnprocessableEntityException(
					"There are no trains between " + request.getFromStation() + " to " + request.getToStation());
		else
			return trainList;
	}

	public Train updateTrain(Train train) {
		Optional<Train> originalTrain = trainRepository.findById(train.getTrainNo());
		if (originalTrain.isPresent()) {
			return trainRepository.save(train);
		} else
			throw new UnprocessableEntityException("Invalid train number");
	}
}
