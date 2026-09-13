package review.service;

import review.modal.Review;
import review.payload.dto.ReviewRequest;
import review.payload.dto.SalonDTO;
import review.payload.dto.UserDTO;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest req, UserDTO user, SalonDTO salon);

    List<Review> getReviewBySalonId(Long salonId);

    Review updateReview(ReviewRequest req, Long reviewId, Long userId) throws Exception;

    void deleteReview(Long reviewId, Long userId) throws Exception;
}
