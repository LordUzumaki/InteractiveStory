package com.theodore.interactivestory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Arrays;
import java.util.List;




public class MainActivity extends AppCompatActivity {

    private StoryNode currentStoryNode;
    private TextView narrativeTextView;
    private Button choiceButton1, choiceButton2, choiceButton3; // Assuming up to 3 choices for simplicity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        narrativeTextView = findViewById(R.id.narrativeTextView);
        choiceButton1 = findViewById(R.id.choiceButton1);
        choiceButton2 = findViewById(R.id.choiceButton2);
        choiceButton3 = findViewById(R.id.choiceButton3);

        startGame();

    }

    private void startGame(){
        // Example: Initialize your story nodes and choices
        Choice choice1 = new Choice("Go left", null);
        Choice choice2 = new Choice("Go right", null);
        Choice choice3 = new Choice("Go up", null);
        currentStoryNode = new StoryNode("You are at a crossroad", Arrays.asList(choice1, choice2, choice3));

        choiceButton1.setOnClickListener(v -> {
            currentStoryNode = choice1.getNextNode();

            loadStoryNode();
        });

        choiceButton2.setOnClickListener(v ->{
            currentStoryNode = choice2.getNextNode();
            loadStoryNode();
        });

        choiceButton3.setOnClickListener(v ->{
            currentStoryNode = choice3.getNextNode();
            loadStoryNode();
        });
        loadStoryNode();




    }


    private void loadStoryNode(){
            // Display the narrative of the current node
            narrativeTextView.setText(currentStoryNode.getNarrative());

            // Assuming each StoryNode has a method `getChoices()` that returns a list of choices
            List<Choice> currentChoices = currentStoryNode.getChoices();

            if (currentChoices.size() > 0) {
                choiceButton1.setText(currentChoices.get(0).getDescription());
                choiceButton1.setVisibility(View.VISIBLE);
            } else {
                choiceButton1.setVisibility(View.GONE);
            }

            if (currentChoices.size() > 1) {
                choiceButton2.setText(currentChoices.get(1).getDescription());
                choiceButton2.setVisibility(View.VISIBLE);
            } else {
                choiceButton2.setVisibility(View.GONE);
            }

            if (currentChoices.size() > 2) {
                choiceButton3.setText(currentChoices.get(2).getDescription());
                choiceButton3.setVisibility(View.VISIBLE);
            } else {
                choiceButton3.setVisibility(View.GONE);
            }

            // ... and so on for other buttons



    }


}