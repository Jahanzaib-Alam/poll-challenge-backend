package org.jahanzaib.pollchallenge.builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollVote;
import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollVoteRecord;
import org.jahanzaib.pollchallenge.util.PollDataCalculator;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.jahanzaib.pollchallenge.web.model.PollOptionInfo;
import org.jahanzaib.pollchallenge.web.model.PollVoteInfo;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PollDataBuilder {
	private final PollDataCalculator dataCalculator;
	
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
	
	public PollVoteRecord buildVoteRecord(int optionId) {
		PollVoteRecord voteRecord = new PollVoteRecord();
		
		voteRecord.setOptionId(optionId);
		voteRecord.setPlacedDateTime(LocalDateTime.now());
		
		return voteRecord;
	}
	
	public PollInfo buildPollInfoFromFetchedData(Poll fetchedPoll, List<PollOption> fetchedOptions, List<PollVote> fetchedVotes) {
		PollInfo builtInfo = new PollInfo(fetchedPoll);
		
		List<PollOptionInfo> optionsInfo = new ArrayList<>();
		for (PollOption option : fetchedOptions) {
			PollOptionInfo optionInfo = new PollOptionInfo(option);
			optionInfo.setVotes(getVotesForOption(fetchedVotes, option.getId()));
			optionsInfo.add(optionInfo);
		}
		dataCalculator.calculateAndSetTotalsAndPercentages(optionsInfo);
		builtInfo.setOptions(optionsInfo);
		
		return builtInfo;
	}
	
	private List<PollVoteInfo> getVotesForOption(List<PollVote> fetchedVotes, int optionId) {
		List<PollVote> votesForOption = fetchedVotes.stream()
				.filter(vote -> vote.getOptionId().equals(optionId))
				.collect(Collectors.toList());
		
		// Map to response model bean
		return votesForOption.stream().map(vote -> new PollVoteInfo(vote.getPlacedDateTime())).collect(Collectors.toList());
	}
}