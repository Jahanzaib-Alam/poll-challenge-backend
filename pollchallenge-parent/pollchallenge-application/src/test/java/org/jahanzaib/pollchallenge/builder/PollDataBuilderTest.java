package org.jahanzaib.pollchallenge.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

@ExtendWith(MockitoExtension.class)
class PollDataBuilderTest {
	@Mock
	private PollDataCalculator dataCalculator;
	@InjectMocks
	private PollDataBuilder dataBuilder;
	
	private EnhancedRandom enhancedRandom;
	
	@BeforeEach
	public void setup() {
		enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
	}
	
	@Test
	public void testBuildPollRecord() {
		String testName = "pollName";
		String testQuestion = "pollQuestion";
		
		PollRecord builtRecord = dataBuilder.buildPollRecord(testName, testQuestion);
		
		assertEquals(testName, builtRecord.getName());
		assertEquals(testQuestion, builtRecord.getQuestionText());
	}

	@Test
	public void testBuildOptionRecords() {
		int pollId = 1;
		List<String> options = enhancedRandom.objects(String.class, 2).collect(Collectors.toList());
		
		List<PollOptionRecord> builtRecords = dataBuilder.buildOptionRecords(options, pollId);
		
		assertEquals(options.get(0), builtRecords.get(0).getOptionText());
		assertEquals(options.get(1), builtRecords.get(1).getOptionText());
	}

	@Test
	public void testBuildVoteRecord() {
		int optionId = 1;
		
		PollVoteRecord builtRecord = dataBuilder.buildVoteRecord(optionId);
		
		assertEquals(optionId, builtRecord.getOptionId());
	}
	
	@Test
	public void testBuildPollInfoFromFetchedData() {
		Poll fetchedPoll = enhancedRandom.nextObject(Poll.class);
		List<PollOption> fetchedOptions = enhancedRandom.objects(PollOption.class, 2).collect(Collectors.toList());
		List<PollVote> fetchedVotes = enhancedRandom.objects(PollVote.class, 2).collect(Collectors.toList());
		// Set one vote for each option
		fetchedOptions.get(0).setId(1);
		fetchedVotes.get(0).setOptionId(1);
		fetchedOptions.get(1).setId(2);
		fetchedVotes.get(1).setOptionId(2);
		
		PollInfo builtInfo = dataBuilder.buildPollInfoFromFetchedData(fetchedPoll, fetchedOptions, fetchedVotes);

		assertEquals(fetchedPoll.getName(), builtInfo.getName());
		assertEquals(fetchedPoll.getQuestionText(), builtInfo.getQuestionText());
		// Ensure the votes were mapped correctly so we have one vote on each option
		assertEquals(1, builtInfo.getOptions().get(0).getVotes().size());
		assertEquals(1, builtInfo.getOptions().get(1).getVotes().size());
		
		verify(dataCalculator).calculateAndSetTotalsAndPercentages(anyList());
	}
}
