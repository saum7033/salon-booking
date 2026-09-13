package review.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import review.modal.Review;
import review.payload.dto.ReviewRequest;
import review.payload.dto.SalonDTO;
import review.payload.dto.UserDTO;
import review.repository.ReviewRepository;
import review.service.ReviewService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    @Override
    public Review createReview(ReviewRequest req, UserDTO user, SalonDTO salon) {
        Review review = new Review();
        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        review.setUserId(user.getId());
        review.setSalonId(salon.getId());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getReviewBySalonId(Long salonId) {
        return reviewRepository.findBySalonId(salonId);
    }

    public Review getReviewById(Long id) throws Exception {
        return reviewRepository.findById(id).orElseThrow(()-> new Exception("review not exist"));
    }
    @Override
    public Review updateReview(ReviewRequest req, Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(!review.getUserId().equals(userId)){
            throw new Exception("You don't have permission to update this review");
        }
        review.setReviewText(req.getReviewText());
        review.setRating(req.getRating());
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        Review review = getReviewById(reviewId);
        if(!review.getUserId().equals(userId)){
            throw new Exception("You don't have permission to delete this review");
        }
        reviewRepository.delete(review);
    }
}
