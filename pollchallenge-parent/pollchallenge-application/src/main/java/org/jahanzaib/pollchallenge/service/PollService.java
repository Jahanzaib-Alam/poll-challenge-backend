package org.jahanzaib.pollchallenge.service;

import java.util.List;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollVote;
import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.jahanzaib.pollchallenge.builder.PollDataBuilder;
import org.jahanzaib.pollchallenge.dao.PollDao;
import org.jahanzaib.pollchallenge.web.model.CreatePollRequest;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class PollService {
	private final PollDataBuilder dataBuilder;
	private final PollDao dao;
	
	public boolean createPoll(CreatePollRequest createRequest) {
		boolean success = false;
		
		PollRecord poll = dataBuilder.buildPollRecord(createRequest.getName(), createRequest.getQuestion());
		int insertedPollId = dao.insertPoll(poll);
		
		if (insertedPollId > 0) {
			log.info("Inserted new poll successfully with ID {}, now inserting options", insertedPollId);
			List<PollOptionRecord> optionRecords = dataBuilder.buildOptionRecords(createRequest.getOptions(), insertedPollId);
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
		return dao.insertVote(dataBuilder.buildVoteRecord(optionId));
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
		log.info("Successfully fetched poll with ID {}, now fetching options and votes and building response...", pollId);
		List<PollOption> fetchedOptions = dao.fetchPollOptionsByPollId(pollId);
		List<PollVote> fetchedVotes = dao.fetchPollVotesByPollId(pollId);
		
		return dataBuilder.buildPollInfoFromFetchedData(fetchedPoll, fetchedOptions, fetchedVotes);
	}
}
