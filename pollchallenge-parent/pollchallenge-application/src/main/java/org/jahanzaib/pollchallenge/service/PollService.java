package org.jahanzaib.pollchallenge.service;

import java.util.List;
import java.util.stream.Collectors;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollVote;
import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.jahanzaib.pollchallenge.builder.PollRecordBuilder;
import org.jahanzaib.pollchallenge.dao.PollDao;
import org.jahanzaib.pollchallenge.util.PollDataCalculator;
import org.jahanzaib.pollchallenge.web.model.CreatePollRequest;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.jahanzaib.pollchallenge.web.model.PollOptionInfo;
import org.jahanzaib.pollchallenge.web.model.PollVoteInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PollService {
	private final PollRecordBuilder recordBuilder;
	private final PollDao dao;
	private final PollDataCalculator dataCalculator;
	
	public boolean createPoll(CreatePollRequest createRequest) {
		boolean success = false;
		
		PollRecord poll = recordBuilder.buildPollRecord(createRequest.getName(), createRequest.getQuestion());
		int insertedPollId = dao.insertPoll(poll);
		
		if (insertedPollId > 0) {
			log.info("Inserted new poll successfully with ID {}, now inserting options", insertedPollId);
			List<PollOptionRecord> optionRecords = recordBuilder.buildOptionRecords(createRequest.getOptions(), insertedPollId);
			success = dao.insertPollOptions(optionRecords);
		} else {
			log.info("Failed to insert new poll for request {}", createRequest.toString());
			success = false;
		}
		
		return success;
	}
	
	public boolean activatePoll(int pollId) {
		dao.deactivateAllPolls();
		return dao.activatePoll(pollId);
	}
	
	public boolean placeVote(int optionId) {
		return dao.insertVote(recordBuilder.buildVoteRecord(optionId));
	}
	
	public PollInfo getActivePollInfo() {
		return getPollInfoByPollId(dao.fetchActivePollId());
	}
	
	public PollInfo getPollInfoByPollId(int pollId) {
		Poll fetchedPoll = dao.fetchPollById(pollId);
		
		if (fetchedPoll == null) {
			log.info("Failed to fetch poll with ID {}", pollId);
			return null;
		}
		
		log.info("Successfully fetched poll with ID {}, now fetching options and votes...", pollId);
		PollInfo pollInfoToReturn = new PollInfo(fetchedPoll);
		pollInfoToReturn.setOptions(getOptionsForPoll(pollId));
		return pollInfoToReturn;
	}

	private List<PollOptionInfo> getOptionsForPoll(int pollId) {
		List<PollOption> fetchedOptions = dao.fetchPollOptionsByPollId(pollId);
		
		List<PollOptionInfo> optionsInfo = fetchedOptions.stream().map(option -> {
			PollOptionInfo optionInfo = new PollOptionInfo(option);
			optionInfo.setVotes(getVotesForOption(option.getId()));
			return optionInfo;
		}).collect(Collectors.toList());
		
		dataCalculator.calculateAndSetTotalsAndPercentages(optionsInfo);
		
		return optionsInfo;
	}

	private List<PollVoteInfo> getVotesForOption(int optionId) {
		List<PollVote> fetchedVotes = dao.fetchPollVotesByOptionId(optionId);
		return fetchedVotes.stream().map(vote -> new PollVoteInfo(vote.getPlacedDateTime())).collect(Collectors.toList());
	}
}
