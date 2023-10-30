package com.theodore.interactivestory;

public class Choice {
    private String description;
    private StoryNode nextNode;

    public Choice(String description, StoryNode nextNode) {
        this.description = description;
        this.nextNode = nextNode;
    }

    public String getDescription() {
        return description;
    }

    public StoryNode getNextNode() {
        return nextNode;
    }

}
