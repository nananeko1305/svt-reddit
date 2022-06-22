package com.ftn.redditClone.model.entity;

import javax.persistence.*;

import org.aspectj.weaver.tools.Trace;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
//    @OneToOne(fetch = FetchType.LAZY)
    @Column(name = "role")
    private Role role;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @Column(name = "avatar", nullable = false)
    private String avatar;
    
    @Column(name = "registrationDate", nullable = false)
    private LocalDate registrationDate;
    
    @Column(name = "description", nullable = false)
    private String description;
    
    @Column(name = "displayName", nullable = false)
    private String displayName;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();


	public User(String username, Role role, String password, String email, String avatar, LocalDate registrationDate,
			String description, String displayName) {
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
 
    
    
}
