package com.theodore.interactivestory;

public class Choice {
    private final String description;
    private final StoryNode nextNode;

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
