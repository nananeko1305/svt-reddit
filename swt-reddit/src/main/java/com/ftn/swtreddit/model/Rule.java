package com.ftn.swtreddit.model;

public class Rule {

    private String description;

    public Rule(String description) {
        this.description = description;
    }
    public Rule(){}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
