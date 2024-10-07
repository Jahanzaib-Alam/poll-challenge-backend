package org.jahanzaib.pollchallenge.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.jahanzaib.pollchallenge.service.PollService;
import org.jahanzaib.pollchallenge.web.model.CreatePollRequest;
import org.jahanzaib.pollchallenge.web.model.PollInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PollControllerTest {
	@Mock
	private PollService service;
	@InjectMocks
	private PollController controller;
	
	// Repeatedly used mocks
	@Mock
	private PollInfo pollInfo;
	
	@Test
	public void testGetActivePoll() {
		when(service.getActivePollInfo()).thenReturn(pollInfo);
		
		ResponseEntity<PollInfo> response = controller.getActivePoll();
		
		assertEquals(pollInfo, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		verify(service).getActivePollInfo();
	}
	
	@Test
	public void testGetActivePoll_NoActivePoll() {
		when(service.getActivePollInfo()).thenReturn(null);
		
		ResponseEntity<PollInfo> response = controller.getActivePoll();
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		verify(service).getActivePollInfo();
	}
	
	@Test
	public void testGetPoll() {
		when(service.getPollInfoByPollId(anyInt())).thenReturn(pollInfo);
		
		ResponseEntity<PollInfo> response = controller.getPoll(1);
		
		assertEquals(pollInfo, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		verify(service).getPollInfoByPollId(anyInt());
	}
	
	@Test
	public void testGetPoll_NotFound() {
		when(service.getPollInfoByPollId(anyInt())).thenReturn(null);
		
		ResponseEntity<PollInfo> response = controller.getPoll(1);
		
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		
		verify(service).getPollInfoByPollId(anyInt());
	}
	
	@Test
	public void testGetAllPolls() {
		when(service.getAllPolls()).thenReturn(Collections.singletonList(pollInfo));
		
		ResponseEntity<List<PollInfo>> response = controller.getAllPolls();
		
		assertEquals(pollInfo, response.getBody().get(0));
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		verify(service).getAllPolls();
	}
	
	@Test
	public void testCreatePoll() {
		when(service.createPoll(any(CreatePollRequest.class))).thenReturn(true);
		
		ResponseEntity<Void> response = controller.createPoll(mock(CreatePollRequest.class));
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		verify(service).createPoll(any(CreatePollRequest.class));
	}
	
	@Test
	public void testCreatePoll_CreationFailed() {
		when(service.createPoll(any(CreatePollRequest.class))).thenReturn(true);
		
		ResponseEntity<Void> response = controller.createPoll(mock(CreatePollRequest.class));
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		verify(service).createPoll(any(CreatePollRequest.class));
	}
	
	@Test
	public void testActivatePoll() {
		when(service.activatePoll(anyInt())).thenReturn(true);
		
		ResponseEntity<Void> response = controller.activatePoll(1);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		verify(service).activatePoll(anyInt());
	}
	
	@Test
	public void testActivatePoll_ActivationFailed() {
		when(service.activatePoll(anyInt())).thenReturn(false);
		
		ResponseEntity<Void> response = controller.activatePoll(1);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		verify(service).activatePoll(anyInt());
	}
	
	@Test
	public void testPlaceVote() {
		when(service.placeVote(anyInt())).thenReturn(true);
		
		ResponseEntity<Void> response = controller.placeVote(1);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		
		verify(service).placeVote(anyInt());
	}
	
	@Test
	public void testPlaceVote_PlacingVoteFailed() {
		when(service.activatePoll(anyInt())).thenReturn(false);
		
		ResponseEntity<Void> response = controller.placeVote(1);
		
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		
		verify(service).placeVote(anyInt());
	}
}
