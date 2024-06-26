package train.reservation.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import train.reservation.system.entity.Train;

public interface TrainRepository extends JpaRepository<Train, Long> {

}
