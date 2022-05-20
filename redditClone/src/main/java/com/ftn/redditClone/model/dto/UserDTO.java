package com.ftn.redditClone.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import com.ftn.redditClone.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	
		@NotBlank
		private long id;
		@NotBlank
	    private String username;
		@NotBlank
	    private String password;
		@NotBlank
	    private String email;
		@NotBlank
		private String avatar;
		@NotBlank
		private LocalDate registrationDate;
		@NotBlank
		private String description;
		@NotBlank
		private String displayName;
		
		public UserDTO(User user) {
			this(user.getId(), user.getUsername(), user.getPassword(), user.getEmail(), user.getAvatar(), user.getRegistrationDate(), user.getDescription(), user.getDisplayName());
		}
	
	    
}
