package org.jahanzaib.pollchallenge.web.model.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePollRequest {
	private String name;
	private String question;
	private List<String> options;
}
