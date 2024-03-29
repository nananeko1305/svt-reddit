package com.ftn.redditClone.model.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.ftn.redditClone.model.entity.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.security.core.GrantedAuthority;

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

	private Role role;
	@NotBlank
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String avatar;
	@NotBlank
	private String registrationDate;
	@NotBlank
	private String description;
	@NotBlank
	private String displayName;

	public UserDTO(User user){

		this.id = user.getId();
		this.username = user.getUsername();
		this.role = user.getRole();
		this.password = user.getPassword();
		this.email = user.getEmail();
		this.avatar = user.getAvatar();
		this.registrationDate = user.getRegistrationDate();
		this.description = user.getDescription();
		this.displayName = user.getDisplayName();
	}
}
