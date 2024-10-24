package org.jahanzaib.pollchallenge.controller;

import java.util.List;

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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/polls")
@Slf4j
public class PollController {
	private final PollService service;
	
	// -- GET MAPPINGS --
	
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
	
	@GetMapping("/list")
	public ResponseEntity<List<PollInfo>> getAllPolls() {
		log.info("Processing request to get list of all polls");
		List<PollInfo> allPolls = service.getAllPolls();
		
		return ResponseEntity.ok(allPolls);
	}
	
	// -- POST MAPPINGS --
	
	@PostMapping("/create")
	public ResponseEntity<Void> createPoll(@Valid @RequestBody(required=true) CreatePollRequest createRequest) {
		log.info("Processing request to create poll for request {}", createRequest.toString());
		boolean pollCreated = service.createPoll(createRequest);
		
		return returnOkIfSuccess(pollCreated);
	}
	
	@PostMapping("/activate/{pollId}")
	public ResponseEntity<Void> activatePoll(@PathVariable("pollId") int pollId) {
		log.info("Processing request to activate poll with ID {}", pollId);
		boolean pollActivated = service.activatePoll(pollId);
		
		return returnOkIfSuccess(pollActivated);
	}
	
	@PostMapping("/vote/{optionId}")
	public ResponseEntity<Void> placeVote(@PathVariable("optionId") int optionId) {
		log.info("Processing request to place vote for option with ID {}", optionId);
		boolean votePlaced = service.placeVote(optionId);
		
		return returnOkIfSuccess(votePlaced);
	}
	
	private ResponseEntity<Void> returnOkIfSuccess(boolean success) {
		return success ? ResponseEntity.ok().build() : ResponseEntity.internalServerError().build();
	}
}
