package org.jahanzaib.pollchallenge.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollVote;
import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollVoteRecord;
import org.jahanzaib.pollchallenge.builder.PollDataBuilder;
import org.jahanzaib.pollchallenge.dao.PollDao;
import org.jahanzaib.pollchallenge.web.model.CreatePollRequest;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

@ExtendWith(MockitoExtension.class)
class PollServiceTest {
	@Mock
	private PollDao dao;
	@Mock
	private PollDataBuilder dataBuilder;
	@InjectMocks
	private PollService pollService;
	
	// Repeatedly mocked or randomised objects:
	private EnhancedRandom enhancedRandom;
	private CreatePollRequest createRequest;
	@Mock
	private PollRecord pollRecord;
	
	@BeforeEach
	public void setup() {
		enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
		createRequest = enhancedRandom.nextObject(CreatePollRequest.class); 
	}

	@Test
	public void testCreatePoll_Success() {
		int expectedInsertedPollId = 1;
		List<PollOptionRecord> builtOptionRecords = Collections.singletonList(mock(PollOptionRecord.class));
		
		when(dataBuilder.buildPollRecord(createRequest.getName(), createRequest.getQuestion())).thenReturn(pollRecord);
		when(dao.insertPoll(pollRecord)).thenReturn(expectedInsertedPollId);
		when(dataBuilder.buildOptionRecords(createRequest.getOptions(), expectedInsertedPollId)).thenReturn(builtOptionRecords);
		when(dao.insertPollOptions(builtOptionRecords)).thenReturn(true);
		
		assertTrue(pollService.createPoll(createRequest));
		
		verify(dataBuilder).buildPollRecord(anyString(), anyString());
		verify(dao).insertPoll(pollRecord);
		verify(dataBuilder).buildOptionRecords(anyList(), anyInt());
		verify(dao).insertPollOptions(builtOptionRecords);
	}

	@Test
	public void testCreatePoll_OptionsInsertFailed() {
		int expectedInsertedPollId = 1;
		List<PollOptionRecord> builtOptionRecords = Collections.singletonList(mock(PollOptionRecord.class));
		
		when(dataBuilder.buildPollRecord(createRequest.getName(), createRequest.getQuestion())).thenReturn(pollRecord);
		when(dao.insertPoll(pollRecord)).thenReturn(expectedInsertedPollId);
		when(dataBuilder.buildOptionRecords(createRequest.getOptions(), expectedInsertedPollId)).thenReturn(builtOptionRecords);
		when(dao.insertPollOptions(builtOptionRecords)).thenReturn(false);
		
		assertFalse(pollService.createPoll(createRequest));
		
		verify(dataBuilder).buildPollRecord(anyString(), anyString());
		verify(dao).insertPoll(pollRecord);
		verify(dataBuilder).buildOptionRecords(anyList(), anyInt());
		verify(dao).insertPollOptions(builtOptionRecords);
	}
	
	@Test
	public void testCreatePoll_PollInsertFailed() {
		when(dataBuilder.buildPollRecord(createRequest.getName(), createRequest.getQuestion())).thenReturn(pollRecord);
		when(dao.insertPoll(pollRecord)).thenReturn(0);
		
		assertFalse(pollService.createPoll(createRequest));
		
		verify(dataBuilder).buildPollRecord(anyString(), anyString());
		verify(dao).insertPoll(pollRecord);
		verifyNoMoreInteractions(dataBuilder, dao);
	}
	
	@Test
	public void testActivatePoll() {
		int pollIdToActivate = 1;
		
		when(dao.activatePoll(pollIdToActivate)).thenReturn(true);
		
		assertTrue(pollService.activatePoll(pollIdToActivate));
		
		verify(dao).deactivateAllPolls();
		verify(dao).activatePoll(pollIdToActivate);
	}
	
	@Test
	public void testActivatePoll_FailedToActivate() {
		int pollIdToActivate = 1;
		
		when(dao.activatePoll(pollIdToActivate)).thenReturn(false);
		
		assertFalse(pollService.activatePoll(pollIdToActivate));
		
		verify(dao).deactivateAllPolls();
		verify(dao).activatePoll(pollIdToActivate);
	}
	
	@Test
	public void testActivatePoll_NoPollToActivate() {
		assertTrue(pollService.activatePoll(0));
		
		verify(dao).deactivateAllPolls();
		verifyNoMoreInteractions(dao);
	}
	
	@Test
	public void testPlaceVote() {
		int optionToVoteFor = 1;
		PollVoteRecord builtVote = mock(PollVoteRecord.class);
		
		when(dataBuilder.buildVoteRecord(optionToVoteFor)).thenReturn(builtVote);
		when(dao.insertVote(builtVote)).thenReturn(true);
		
		assertTrue(pollService.placeVote(optionToVoteFor));
		
		verify(dao).insertVote(builtVote);
	}
	
	@Test
	public void testPlaceVote_InsertFailed() {
		int optionToVoteFor = 1;
		PollVoteRecord builtVote = mock(PollVoteRecord.class);
		
		when(dataBuilder.buildVoteRecord(optionToVoteFor)).thenReturn(builtVote);
		when(dao.insertVote(builtVote)).thenReturn(false);
		
		assertFalse(pollService.placeVote(optionToVoteFor));
		
		verify(dao).insertVote(builtVote);
	}
	
	@Test
	public void testGetAllPolls() {
		when(dao.fetchAllPolls()).thenReturn(enhancedRandom.objects(Poll.class, 2).collect(Collectors.toList()));
		
		assertEquals(2, pollService.getAllPolls().size());
	}
	
	@Test
	public void testGetPollInfoByPollId() {
		int pollId = 1;
		Poll fetchedPoll = mock(Poll.class);
		List<PollOption> fetchedOptions = Collections.singletonList(mock(PollOption.class));
		List<PollVote> fetchedVotes = Collections.singletonList(mock(PollVote.class));
		PollInfo expectedBuiltInfo = mock(PollInfo.class);
		
		when(dao.fetchPollById(pollId)).thenReturn(fetchedPoll);
		when(dao.fetchPollOptionsByPollId(pollId)).thenReturn(fetchedOptions);
		when(dao.fetchPollVotesByPollId(pollId)).thenReturn(fetchedVotes);
		when(dataBuilder.buildPollInfoFromFetchedData(fetchedPoll, fetchedOptions, fetchedVotes))
				.thenReturn(expectedBuiltInfo);
		
		assertEquals(expectedBuiltInfo, pollService.getPollInfoByPollId(pollId));
		
		verify(dao).fetchPollById(pollId);
		verify(dao).fetchPollOptionsByPollId(pollId);
		verify(dao).fetchPollVotesByPollId(pollId);
		verify(dataBuilder).buildPollInfoFromFetchedData(fetchedPoll, fetchedOptions, fetchedVotes);
	}
	
	@Test
	public void testGetPollInfoByPollId_FailedToFetchPoll() {
		int pollId = 1;
		
		when(dao.fetchPollById(pollId)).thenReturn(null);
		
		assertNull(pollService.getPollInfoByPollId(pollId));
		
		verify(dao).fetchPollById(pollId);
		verifyNoMoreInteractions(dao);
		verifyNoInteractions(dataBuilder);
	}
	
	@Test
	public void testGetActivePollInfo() {
		int activePollId = 1;
		Poll fetchedPoll = mock(Poll.class);
		List<PollOption> fetchedOptions = Collections.singletonList(mock(PollOption.class));
		List<PollVote> fetchedVotes = Collections.singletonList(mock(PollVote.class));
		PollInfo expectedBuiltInfo = mock(PollInfo.class);
		
		when(dao.fetchActivePollId()).thenReturn(activePollId);
		when(dao.fetchPollById(activePollId)).thenReturn(fetchedPoll);
		when(dao.fetchPollOptionsByPollId(activePollId)).thenReturn(fetchedOptions);
		when(dao.fetchPollVotesByPollId(activePollId)).thenReturn(fetchedVotes);
		when(dataBuilder.buildPollInfoFromFetchedData(fetchedPoll, fetchedOptions, fetchedVotes))
				.thenReturn(expectedBuiltInfo);
		
		assertEquals(expectedBuiltInfo, pollService.getActivePollInfo());
		
		verify(dao).fetchPollById(activePollId);
		verify(dao).fetchPollOptionsByPollId(activePollId);
		verify(dao).fetchPollVotesByPollId(activePollId);
		verify(dataBuilder).buildPollInfoFromFetchedData(fetchedPoll, fetchedOptions, fetchedVotes);
	}
}
