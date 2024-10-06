package org.jahanzaib.pollchallenge.web.model;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreatePollRequest {
	@NotBlank(message = "Name is required")
	@Size(max = 255, message = "Name must not exceed 255 characters")
	private String name;
	
	@NotBlank(message = "Question is required")
	@Size(max = 255, message = "Question must not exceed 255 characters")
	private String question;
	
	@Size(min = 2, max = 7, message = "Only 2-7 options can be provided for a poll")
	private List<@NotBlank(message = "Option cannot be blank") @Size(max = 50, message = "Option must not exceed 50 characters") String> options;
}
