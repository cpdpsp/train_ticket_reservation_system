package train.reservation.system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import train.reservation.system.entity.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, String> {

}
