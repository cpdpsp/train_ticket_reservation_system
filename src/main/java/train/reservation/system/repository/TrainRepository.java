package train.reservation.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import train.reservation.system.entity.FareEnquiryRequest;
import train.reservation.system.entity.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

	@Query(value = "select * from train where from_station=:request.getFromStation and to_station=:request.getToStation()", nativeQuery = true)
	List<Train> getFareForStations(FareEnquiryRequest request);
}
