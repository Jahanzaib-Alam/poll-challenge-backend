package org.jahanzaib.pollchallenge.service;

import java.util.List;
import java.util.stream.Collectors;

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
	
	/**
	 * Create a poll and save it in the database from a CreatePollRequest object.
	 * @param createRequest model object representing JSON passed in the request to the API
	 * @return success result on successful creation of the poll
	 */
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
	
	/**
	 * Deactivate all polls and activate a poll if specified
	 * @param pollId ID of the poll to activate. If not greater than 0, this method will just deactivate all polls
	 * @return success result on activation of specified poll (or just true if no poll is being activated).
	 */
	public boolean activatePoll(int pollId) {
		dao.deactivateAllPolls();
		// If the poll ID was not > 0, we are just deactivating all polls. Else activate the specified poll.
		return pollId > 0 ? dao.activatePoll(pollId) : true;
	}
	
	public boolean placeVote(int optionId) {
		return dao.insertVote(dataBuilder.buildVoteRecord(optionId));
	}
	
	public List<PollInfo> getAllPolls() {
		return dao.fetchAllPolls().stream().map(poll -> new PollInfo(poll)).collect(Collectors.toList());
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
	
	public PollInfo getActivePollInfo() {
		return getPollInfoByPollId(dao.fetchActivePollId());
	}
}
