package net.javaguides.lms.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationRequest {
    
    @NotNull(message = "Book ID is required")
    private Integer bookId;

    @NotNull(message = "Days is required")
    @Min(value = 1, message = "Days must be at least 1")
    private Integer days;
}