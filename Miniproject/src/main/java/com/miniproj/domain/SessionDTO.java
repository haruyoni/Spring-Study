package com.miniproj.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SessionDTO {
	@NonNull
	private String userId;
	
	private Timestamp sessionLimit;
	private String sessionKey;
}