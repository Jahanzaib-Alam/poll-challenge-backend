package org.jahanzaib.pollchallenge.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PollOptionInfo {
	private String optionText;
	private int numVotes;
	private List<PollVoteInfo> votes;
}
