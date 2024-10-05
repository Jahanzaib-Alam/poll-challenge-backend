package org.jahanzaib.pollchallenge.controller;

import org.jahanzaib.pollchallenge.service.PollService;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.jahanzaib.pollchallenge.web.model.request.CreatePollRequest;
import org.jahanzaib.pollchallenge.web.model.request.VoteRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/polls")
public class PollController {
	private final PollService service;
	
	@GetMapping("/active")
	public ResponseEntity<PollInfo> getActivePoll() {
		PollInfo info = service.getActivePollInfo();
		
		return ResponseEntity.ok(info);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PollInfo> getPoll(@PathVariable(required=true) int pollId) {
		PollInfo info = service.getPollInfoByPollId(pollId);
		
		return ResponseEntity.ok(info);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Void> createPoll(@RequestBody(required=true) CreatePollRequest createRequest) {
		boolean pollCreated = service.createPoll(createRequest);
		
		return pollCreated ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}
	
	@PostMapping("/vote")
	public ResponseEntity<Void> placeVote(@RequestBody VoteRequest voteRequest) {
		
		
		return ResponseEntity.ok().build();
	}
}
