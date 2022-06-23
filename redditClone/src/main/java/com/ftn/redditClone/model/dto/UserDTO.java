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

	private List<PostDTO> posts = new ArrayList<>();

	private List<CommentDTO> comments = new ArrayList<>();

	private List<ReportDTO> reports = new ArrayList<>();

	private List<BannedDTO> banneds = new ArrayList<>();

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

		for (Post post: user.getPosts()){
			this.posts.add(new PostDTO(post));
		}

		for (Comment comment: user.getComments()){
			this.comments.add(new CommentDTO(comment));
		}

		for (Report report: user.getReports()){
			this.reports.add(new ReportDTO(report));
		}

		for (Banned banned: user.getBanneds()){
			this.banneds.add(new BannedDTO(banned));
		}
	}
}
