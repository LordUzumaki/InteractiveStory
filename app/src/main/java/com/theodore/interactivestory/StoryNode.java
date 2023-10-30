package com.theodore.interactivestory;

import java.util.List;

public class StoryNode {

    private String narrative;
    private List<Choice> choices;

    public StoryNode(String narrative, List<Choice> choices) {
        this.narrative = narrative;
        this.choices = choices;
    }

    public String getNarrative() {
        return narrative;
    }

    public List<Choice> getChoices() {
        return choices;
    }

}



