package com.ftn.redditClone.service;

import com.ftn.redditClone.model.entity.Reaction;

import java.util.List;

public interface ReactionService {

    void saveReaction(Reaction reaction);

    List<Reaction> alreadyVoted(int userID, int postID);
}
