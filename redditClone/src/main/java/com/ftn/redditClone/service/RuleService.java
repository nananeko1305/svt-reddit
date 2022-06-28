package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Rule;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RuleService {

    void save(Rule rule);

    Optional<Rule> findOne(int id);
}
