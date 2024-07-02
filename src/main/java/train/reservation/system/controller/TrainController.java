package train.reservation.system.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import train.reservation.system.entity.FareEnquiryRequest;
import train.reservation.system.entity.Train;
import train.reservation.system.service.TrainService;

@RestController
public class TrainController {

	@Autowired
	TrainService trainService;

	@GetMapping("/viewTrain/{trainNo}")
	ResponseEntity<Train> getTrain(@PathVariable Long trainNo) {
		Optional<Train> train = trainService.getTrain(trainNo);
		return new ResponseEntity<>(train.get(), HttpStatus.OK);
	}

	@PostMapping("/addTrain")
	ResponseEntity<Train> addTrain(@RequestBody Train train) {
		Train trainAdded = trainService.addTrain(train);
		return new ResponseEntity<>(train, HttpStatus.OK);
	}

	@DeleteMapping("/cancelTrain/{trainNo}")
	ResponseEntity<String> deleteTrain(@PathVariable Long trainNo) {
		trainService.deleteTrain(trainNo);
		return new ResponseEntity<>("Train deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/viewAllTrains")
	ResponseEntity<List<Train>> getAllTrains() {
		List<Train> trainList = trainService.getAllTrains();
		return new ResponseEntity<>(trainList, HttpStatus.OK);
	}

	@GetMapping("/fareEnquiry")
	ResponseEntity<List<Train>> getFare(@RequestBody FareEnquiryRequest request) {
		List<Train> trainList = trainService.getFare(request);
		return new ResponseEntity<>(trainList, HttpStatus.OK);
	}

	@PutMapping("/updateTrain")
	ResponseEntity<Train> updateTrain(@RequestBody Train train) {
		Train updatedTrain = trainService.updateTrain(train);
		return new ResponseEntity<>(updatedTrain, HttpStatus.OK);
	}
}
