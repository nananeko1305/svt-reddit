package com.ftn.swtreddit.model;

import java.time.LocalDate;

public class Reaction {

    private ReactionType reactionType;
    private LocalDate timestamp;

    public Reaction(ReactionType reactionType, LocalDate timestamp) {
        this.reactionType = reactionType;
        this.timestamp = timestamp;
    }

    public Reaction(){}

    public ReactionType getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionType reactionType) {
        this.reactionType = reactionType;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
