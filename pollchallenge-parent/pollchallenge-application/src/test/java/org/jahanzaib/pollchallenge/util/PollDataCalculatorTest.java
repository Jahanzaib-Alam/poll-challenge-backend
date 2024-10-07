package org.jahanzaib.pollchallenge.util;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.jahanzaib.pollchallenge.web.model.PollOptionInfo;
import org.jahanzaib.pollchallenge.web.model.PollVoteInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;

class PollDataCalculatorTest {
	private PollDataCalculator calculator;
	
	@BeforeEach
	public void setup() {
		calculator = new PollDataCalculator();
	}
	
	@Test
	public void testCalculateAndSetTotalsAndPercentages() {
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
		List<PollOptionInfo> optionsInfo = enhancedRandom.objects(
				PollOptionInfo.class, 2, "numVotes", "percentageOfTotal", "votes")
				.collect(Collectors.toList());
		
		// 3 Votes on option 1
		List<PollVoteInfo> option1Votes = enhancedRandom.objects(PollVoteInfo.class, 3).collect(Collectors.toList());
		// 2 Votes on option 2
		List<PollVoteInfo> option2Votes = enhancedRandom.objects(PollVoteInfo.class, 2).collect(Collectors.toList());
		optionsInfo.get(0).setVotes(option1Votes);
		optionsInfo.get(1).setVotes(option2Votes);
		
		calculator.calculateAndSetTotalsAndPercentages(optionsInfo);
		
		// Assert the number of votes has been set on each option
		assertEquals(3, optionsInfo.get(0).getNumVotes());
		assertEquals(2, optionsInfo.get(1).getNumVotes());
		// Assert the percentages have been set on each option 60% for option 1 and 40% for option 2
		assertEquals(new BigDecimal(60f).setScale(2), optionsInfo.get(0).getPercentageOfTotal());
		assertEquals(new BigDecimal(40f).setScale(2), optionsInfo.get(1).getPercentageOfTotal());
	}
	
	@Test
	public void testCalculateAndSetTotalsAndPercentages_ZeroVotes() {
		EnhancedRandom enhancedRandom = EnhancedRandomBuilder.aNewEnhancedRandom();
		List<PollOptionInfo> optionsInfo = enhancedRandom.objects(
				PollOptionInfo.class, 2, "numVotes", "percentageOfTotal", "votes")
				.collect(Collectors.toList());
		optionsInfo.get(0).setVotes(Collections.emptyList());
		optionsInfo.get(1).setVotes(Collections.emptyList());
		
		calculator.calculateAndSetTotalsAndPercentages(optionsInfo);
		
		// Assert the number of votes has been set on each option
		assertEquals(0, optionsInfo.get(0).getNumVotes());
		assertEquals(0, optionsInfo.get(1).getNumVotes());
		// Assert the percentages have not been set as there are no votes.
		assertNull(optionsInfo.get(0).getPercentageOfTotal());
		assertNull(optionsInfo.get(1).getPercentageOfTotal());
	}
}
