package com.ftn.redditClone.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

//@Entity
//@Table(name = "rule")
public class Rule {

//    @Id
    private int idPravila;
    private String description;
    private Community community;

}
