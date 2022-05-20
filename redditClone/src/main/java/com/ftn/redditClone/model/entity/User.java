package com.ftn.redditClone.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.aspectj.weaver.tools.Trace;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
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

    
    
    	
}
