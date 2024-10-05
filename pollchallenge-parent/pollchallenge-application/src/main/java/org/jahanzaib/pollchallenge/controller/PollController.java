package org.jahanzaib.pollchallenge.controller;

import org.jahanzaib.pollchallenge.service.PollService;
import org.jahanzaib.pollchallenge.web.model.CreatePollRequest;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/polls")
@Slf4j
public class PollController {
	private final PollService service;
	
	@GetMapping("/active")
	public ResponseEntity<PollInfo> getActivePoll() {
		log.info("Processing request to get currently active poll...");
		PollInfo info = service.getActivePollInfo();
		
		if (info == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(info);
	}
	
	@GetMapping("/{pollId}")
	public ResponseEntity<PollInfo> getPoll(@PathVariable("pollId") int pollId) {
		log.info("Processing request to get poll with ID {}", pollId);
		PollInfo info = service.getPollInfoByPollId(pollId);
		
		if (info == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		return ResponseEntity.ok(info);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Void> createPoll(@RequestBody(required=true) CreatePollRequest createRequest) {
		log.info("Processing request to create poll for request {}", createRequest.toString());
		boolean pollCreated = service.createPoll(createRequest);
		
		return pollCreated ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}
	
	@PostMapping("/vote/{optionId}")
	public ResponseEntity<Void> placeVote(@PathVariable("optionId") int optionId) {
		log.info("Processing request to place vote for option with ID {}", optionId);
		boolean votePlaced = service.placeVote(optionId);
		
		return votePlaced ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}
}
