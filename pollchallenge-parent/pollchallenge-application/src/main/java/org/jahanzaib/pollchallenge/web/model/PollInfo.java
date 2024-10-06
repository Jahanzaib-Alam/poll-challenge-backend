package org.jahanzaib.pollchallenge.web.model;

import java.util.List;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
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
