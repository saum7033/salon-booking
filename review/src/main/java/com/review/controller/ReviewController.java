package review.controller;

import review.service.client.SalonFeignClient;
import review.service.client.UserFeignClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import review.modal.Review;
import review.payload.dto.ApiResponse;
import review.payload.dto.ReviewRequest;
import review.payload.dto.SalonDTO;
import review.payload.dto.UserDTO;
import review.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserFeignClient userFeignClient;
    private final SalonFeignClient salonFeignClient;

    @PostMapping("/salon/{salonId}")
    public ResponseEntity<Review> createReview(@PathVariable Long salonId,@RequestBody ReviewRequest req, @RequestHeader("Authorization") String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        Review review = reviewService.createReview(req,user,salon);
        return ResponseEntity.ok(review);
    }

    @GetMapping("/salon/{salonId}")
    public ResponseEntity<List<Review>> getReviewsBySalonId(@PathVariable Long salonId, @RequestHeader("Authorization")String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        SalonDTO salon = salonFeignClient.getSalonById(salonId).getBody();

        List<Review> reviews = reviewService.getReviewBySalonId(salon.getId());
        return ResponseEntity.ok(reviews);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<Review> updateReview(@PathVariable Long reviewId,@RequestBody ReviewRequest req,@RequestHeader("Authorization")String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        Review reviews = reviewService.updateReview(req,reviewId,user.getId());
        return ResponseEntity.ok(reviews);
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse> deleteReview(@PathVariable Long reviewId,@RequestHeader("Authorization")String jwt) throws Exception {
        UserDTO user = userFeignClient.getUserProfile(jwt).getBody();
        reviewService.deleteReview(reviewId,user.getId());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Review deleted");
        return ResponseEntity.ok(apiResponse);
    }

}
