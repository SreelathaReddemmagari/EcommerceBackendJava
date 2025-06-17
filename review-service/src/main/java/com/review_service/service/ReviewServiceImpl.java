package com.review_service.service;

import com.review_service.client.OrderServiceClient;
import com.review_service.client.UserServiceClient;
import com.review_service.dto.ReviewDTO;
import com.review_service.mapper.ReviewMapper;
import com.review_service.model.Review;
import com.review_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final UserServiceClient userServiceClient;
    private final OrderServiceClient orderServiceClient;

    @Override
    public ReviewDTO createReview(ReviewDTO reviewDTO) {
        // Get authenticated user ID from JWT
        String userIdStr = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = Long.parseLong(userIdStr);

        // Validate user is registered
        if (!userServiceClient.userExists(userId)) {
            throw new IllegalArgumentException("User is not registered");
        }

        // Validate user has purchased the product
        if (!orderServiceClient.hasPurchased(userId, reviewDTO.getProductId())) {
            throw new IllegalArgumentException("User has not purchased this product");
        }

        // Ensure the review is associated with the authenticated user
        reviewDTO.setUserId(userId);

        Review review = reviewMapper.toEntity(reviewDTO);
        review.setCreatedAt(LocalDateTime.now());
        return reviewMapper.toDTO(reviewRepository.save(review));
    }

    @Override
    public List<ReviewDTO> getReviewsByProductId(Long productId) {
        return reviewRepository.findByProductId(productId)
                .stream().map(reviewMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public List<ReviewDTO> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId)
                .stream().map(reviewMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}