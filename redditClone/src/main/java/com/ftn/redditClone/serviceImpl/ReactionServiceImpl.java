package com.ftn.redditClone.serviceImpl;

import com.ftn.redditClone.model.entity.Reaction;
import com.ftn.redditClone.repository.ReactionRepository;
import com.ftn.redditClone.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    @Override
    public void saveReaction(Reaction reaction) {
        reactionRepository.save(reaction);
    }

    @Override
    public List<Reaction> alreadyVoted(int userID, int postID) {
        return reactionRepository.alreadyVoted(userID, postID);
    }
}
