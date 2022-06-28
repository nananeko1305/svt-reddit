package com.ftn.redditClone.repository;

import com.ftn.redditClone.model.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule, Integer> {
}
