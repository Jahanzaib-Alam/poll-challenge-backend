package org.jahanzaib.pollchallenge.builder;

import java.util.ArrayList;
import java.util.List;

import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.springframework.stereotype.Component;

@Component
public class PollRecordBuilder {
	public PollRecord buildPollRecord(String pollName, String question) {
		PollRecord pollRecord = new PollRecord();
		
		pollRecord.setName(pollName);
		pollRecord.setQuestionText(question);
		
		return pollRecord;
	}
	
	public List<PollOptionRecord> buildOptionRecords(List<String> options, int pollId) {
		List<PollOptionRecord> optionRecords = new ArrayList<>();
		
		for (String option : options) {
			PollOptionRecord pollOption = new PollOptionRecord();
			pollOption.setOptionText(option);
			pollOption.setPollId(pollId);
			optionRecords.add(pollOption);
		}
		
		return optionRecords;
	}
}
