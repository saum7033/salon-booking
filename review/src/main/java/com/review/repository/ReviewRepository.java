package review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import review.modal.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findBySalonId(Long salonId);
}
