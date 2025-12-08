package net.javaguides.lms.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookDTO {
    
    @NotBlank(message = "Title is required")
    @Size(max = 255, message = "Title must not exceed 255 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(max = 255, message = "Author must not exceed 255 characters")
    private String author;

    private String genre;
    
    private String language;

    @Size(max = 13, message = "ISBN must not exceed 13 characters")
    private String isbn;

    private String imageUrl;

    private Integer categoryId;
}