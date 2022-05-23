package com.ftn.redditClone.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PasswordChangeDTO {

	@NotBlank
	private String username;
	@NotBlank
	private String oldPassword;
	@NotBlank
	private String newPassword;
}
