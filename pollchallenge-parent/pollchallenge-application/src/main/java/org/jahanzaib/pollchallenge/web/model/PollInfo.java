package org.jahanzaib.pollchallenge.web.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PollInfo {
	private int id;
	private String name;
	private List<PollOptionInfo> options;
}
