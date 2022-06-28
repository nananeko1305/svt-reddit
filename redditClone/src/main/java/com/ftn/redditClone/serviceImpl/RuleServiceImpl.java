package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Rule;
import com.ftn.redditClone.repository.RuleRepository;
import com.ftn.redditClone.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    private RuleRepository ruleRepository;

    @Override
    public void save(Rule rule) {
        ruleRepository.save(rule);
    }

    @Override
    public Optional<Rule> findOne(int id) {
        return ruleRepository.findById(id);
    }
}
