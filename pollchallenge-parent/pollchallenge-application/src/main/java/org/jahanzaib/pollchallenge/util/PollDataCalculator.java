package org.jahanzaib.pollchallenge.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.jahanzaib.pollchallenge.web.model.PollOptionInfo;
import org.springframework.stereotype.Component;

@Component
public class PollDataCalculator {
	public void calculateAndSetTotalsAndPercentages(List<PollOptionInfo> optionsInfo) {
		// Set number of votes on each option
		optionsInfo.stream().forEach(option -> option.setNumVotes(option.getVotes().size()));
		
		// Calculate total votes across all options
		int totalVotes = optionsInfo.stream().mapToInt(option -> option.getVotes().size()).sum();
		
		// Set percentages of each total
		if (totalVotes > 0) {
			optionsInfo.stream().forEach(option -> {
				BigDecimal percentage = new BigDecimal(((float) option.getNumVotes() / totalVotes) * 100)
						.setScale(2, RoundingMode.HALF_UP);
				
				option.setPercentageOfTotal(percentage);
			});
		}
	}
}
