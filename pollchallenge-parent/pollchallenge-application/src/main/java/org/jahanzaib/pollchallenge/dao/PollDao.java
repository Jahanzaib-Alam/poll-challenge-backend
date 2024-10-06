package org.jahanzaib.pollchallenge.dao;

import java.util.List;

import org.jahanzaib.pollchalenge.generated.Tables;
import org.jahanzaib.pollchalenge.generated.tables.pojos.Poll;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollOption;
import org.jahanzaib.pollchalenge.generated.tables.pojos.PollVote;
import org.jahanzaib.pollchalenge.generated.tables.records.PollOptionRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollRecord;
import org.jahanzaib.pollchalenge.generated.tables.records.PollVoteRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class PollDao {
	private final DSLContext dsl;
	
	public int insertPoll(PollRecord poll) {
		return dsl.insertInto(Tables.POLL)
			.set(poll)
			.returning(Tables.POLL.ID)
			.fetchOne()
			.getId();
	}
	
	public boolean insertPollOptions(List<PollOptionRecord> pollOptions) {
		return dsl.batchInsert(pollOptions)
			.execute().length == pollOptions.size();
	}
	
	public int fetchActivePollId() {
		return dsl.select(Tables.POLL.ID)
			.from(Tables.POLL)
			.where(Tables.POLL.IS_ACTIVE.eq(true))
			.fetchOptionalInto(Integer.class).orElse(0);
	}
	
	public Poll fetchPollById(int pollId) {
		return dsl.selectFrom(Tables.POLL)
			.where(Tables.POLL.ID.eq(pollId))
			.fetchOneInto(Poll.class);
	}
	
	public List<PollOption> fetchPollOptionsByPollId(int pollId) {
		return dsl.selectFrom(Tables.POLL_OPTION)
			.where(Tables.POLL_OPTION.POLL_ID.eq(pollId))
			.fetchInto(PollOption.class);
	}
	
	public List<PollVote> fetchPollVotesByPollId(int pollId) {
		return dsl.select(Tables.POLL_VOTE.fields())
			.from(Tables.POLL_VOTE)
			.join(Tables.POLL_OPTION)
			.on(Tables.POLL_OPTION.ID.eq(Tables.POLL_VOTE.OPTION_ID))
			.where(Tables.POLL_OPTION.POLL_ID.eq(pollId))
			.fetchInto(PollVote.class);
	}
	
	public void deactivateAllPolls() {
		dsl.update(Tables.POLL)
			.set(Tables.POLL.IS_ACTIVE, false)
			.execute();
	}
	
	public boolean activatePoll(int pollId) {
		return dsl.update(Tables.POLL)
			.set(Tables.POLL.IS_ACTIVE, true)
			.where(Tables.POLL.ID.eq(pollId))
			.execute() > 0;
	}
	
	public boolean insertVote(PollVoteRecord vote) {
		return dsl.insertInto(Tables.POLL_VOTE)
			.set(vote)
			.execute() > 0;
	}
}
