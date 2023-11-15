package com.theodore.interactivestory;


import java.util.List;

public class StoryNode {

    private final String narrative;
    private final List<Choice> choices;

    private final List<String> styleTags;



    public StoryNode(String narrative, List<String> styleTags, List<Choice> choices) {
        this.narrative = narrative;
        this.choices = choices;
        this.styleTags = styleTags;


    }

    public List<String> getStyleTags(){
        return styleTags;
    }


    public String getNarrative() {
        return narrative;
    }

    public List<Choice> getChoices() {
        return choices;
    }

}



