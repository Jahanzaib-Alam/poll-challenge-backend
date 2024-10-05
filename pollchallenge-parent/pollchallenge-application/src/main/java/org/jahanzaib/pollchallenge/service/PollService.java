package org.jahanzaib.pollchallenge.service;

import java.util.ArrayList;
import java.util.List;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollVote;
import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.jahanzaib.pollchallenge.builder.PollRecordBuilder;
import org.jahanzaib.pollchallenge.db.PollDao;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.jahanzaib.pollchallenge.web.model.PollOptionInfo;
import org.jahanzaib.pollchallenge.web.model.PollVoteInfo;
import org.jahanzaib.pollchallenge.web.model.request.CreatePollRequest;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PollService {
	private final PollRecordBuilder recordBuilder;
	private final PollDao dao;
	
	public boolean createPoll(CreatePollRequest createRequest) {
		boolean success = false;
		
		PollRecord poll = recordBuilder.buildPollRecord(createRequest.getName(), createRequest.getQuestion());
		int insertedPollId = dao.insertPoll(poll);
		
		if (insertedPollId > 0) {
			List<PollOptionRecord> optionRecords = recordBuilder.buildOptionRecords(createRequest.getOptions(), insertedPollId);
			success = dao.insertPollOptions(optionRecords);
		} else {
			success = false;
		}
		
		return success;
	}
	
	public PollInfo getActivePollInfo() {
		int activePollId = dao.fetchActivePollId();
		
		return getPollInfoByPollId(activePollId);
	}
	
	public PollInfo getPollInfoByPollId(int pollId) {
		
		Poll fetchedPoll = dao.fetchPollById(pollId);
		
		List<PollOptionInfo> optionsInfo = getOptionsForPoll(pollId);
		PollInfo pollInfo = new PollInfo(pollId, fetchedPoll.getName(), optionsInfo);
		
		return pollInfo;
	}

	private List<PollOptionInfo> getOptionsForPoll(int pollId) {
		List<PollOptionInfo> optionsInfo = new ArrayList<>();
		List<PollOption> fetchedOptions = dao.fetchPollOptionsByPollId(pollId);
		for (PollOption option : fetchedOptions) {
			PollOptionInfo optionInfo = new PollOptionInfo(option.getOptionText(), getVotesForOption(option.getId()));
			optionsInfo.add(optionInfo);
		}
		return optionsInfo;
	}

	private List<PollVoteInfo> getVotesForOption(int optionId) {
		List<PollVoteInfo> votesInfo = new ArrayList<>();
		List<PollVote> fetchedVotes = dao.fetchPollVotesByOptionId(optionId);
		for (PollVote vote : fetchedVotes) {
			votesInfo.add(new PollVoteInfo(vote.getPlacedDateTime()));
		}
		return votesInfo;
	}
}
