package com.ftn.redditClone.model.dto;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.security.core.GrantedAuthority;

import com.ftn.redditClone.model.entity.Role;
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

	private int id;
	@NotBlank
	private String username;
	@NotBlank
	private Role role;
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

	public UserDTO(@NotBlank String username, @NotBlank Role role, @NotBlank String password, @NotBlank String email,
			@NotBlank String avatar, @NotBlank LocalDate registrationDate, @NotBlank String description,
			@NotBlank String displayName) {
		super();
		this.username = username;
		this.role = role;
		this.password = password;
		this.email = email;
		this.avatar = avatar;
		this.registrationDate = registrationDate;
		this.description = description;
		this.displayName = displayName;
	}

	public UserDTO(User user) {
		this(user.getId(), user.getUsername(), user.getRole(), user.getPassword(), user.getEmail(), user.getAvatar(),
				user.getRegistrationDate(), user.getDescription(), user.getDisplayName());
	}
}
