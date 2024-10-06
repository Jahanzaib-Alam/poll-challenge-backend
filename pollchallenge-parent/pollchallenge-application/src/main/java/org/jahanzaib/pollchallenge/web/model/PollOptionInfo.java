package org.jahanzaib.pollchallenge.web.model;

import java.util.List;

import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PollOptionInfo {
	private int id;
	private String optionText;
	private int numVotes;
	private int percentageOfTotal;
	private List<PollVoteInfo> votes;
	
	public PollOptionInfo(PollOption jooqOption) {
		this.setId(jooqOption.getId());
		this.setOptionText(jooqOption.getOptionText());
	}
}
