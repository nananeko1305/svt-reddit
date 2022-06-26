package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Reaction;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReactionService {

    void saveReaction(Reaction reaction);

    List<Reaction> alreadyVoted(int userID, int postID);

    void delete(Reaction reaction);
}
