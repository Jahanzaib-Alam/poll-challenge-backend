package org.jahanzaib.pollchallenge.web.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PollVoteInfo {
	private LocalDateTime votePlacedDateTime;
}
