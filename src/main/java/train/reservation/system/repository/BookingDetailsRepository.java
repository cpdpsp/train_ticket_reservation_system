package train.reservation.system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import train.reservation.system.entity.BookingDetails;

public interface BookingDetailsRepository extends JpaRepository<BookingDetails, String> {

	@Query(value = "Select * from booking_details where mail_id=:mail", nativeQuery = true)
	List<BookingDetails> getBookingDetailsById(String mail);
}
