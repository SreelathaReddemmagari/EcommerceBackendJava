package com.review_service.mapper;

import com.review_service.dto.ReviewDTO;
import com.review_service.model.Review;
import org.springframework.stereotype.Component;

@Component
public class ReviewMapper {

    public ReviewDTO toDTO(Review review) {
        return ReviewDTO.builder()
                .id(review.getId())
                .productId(review.getProductId())
                .userId(review.getUserId())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }

    public Review toEntity(ReviewDTO dto) {
        return Review.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .userId(dto.getUserId())
                .rating(dto.getRating())
                .comment(dto.getComment())
                .build();
    }

}
