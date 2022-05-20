package com.ftn.redditClone.model.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class Administrator extends User {
}
