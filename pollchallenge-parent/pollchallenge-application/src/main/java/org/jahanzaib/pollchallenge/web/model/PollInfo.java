package org.jahanzaib.pollchallenge.web.model;

import java.util.List;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PollInfo {
	private int id;
	private String name;
	private String questionText;
	private List<PollOptionInfo> options;
	
	public PollInfo(Poll jooqPoll) {
		this.id = jooqPoll.getId();
		this.name = jooqPoll.getName();
		this.questionText = jooqPoll.getQuestionText();
	}
}
