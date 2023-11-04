package com.theodore.interactivestory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class MainActivity extends AppCompatActivity {

//    private DataBaseHelper dbHelper;
//    private StoryDataBaseHelper dbHelper;

    private TextView narrativeTextView;
    private List<Button> choiceButtons = new ArrayList<>();
    private StoryNode currentStoryNode;
   private Button choiceButton1, choiceButton2, choiceButton3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        narrativeTextView = findViewById(R.id.narrativeTextView);
        choiceButtons.add(findViewById(R.id.choiceButton1));
        choiceButtons.add(findViewById(R.id.choiceButton2));
        choiceButtons.add(findViewById(R.id.choiceButton3));



        choiceButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action for choiceButton1 here
                // For example, display a message or perform some other action
                // You can also update the story node or game state as needed
                narrativeTextView.setText("You chose option 1");
            }
        });

        choiceButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action for choiceButton2 here
                // For example, display a message or perform other actions
                narrativeTextView.setText("You chose option 2");
            }
        });


        choiceButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action for choiceButton3 here
                // For example, display a message or perform other actions
                narrativeTextView.setText("You chose option 3");
            }
        });
//        startGame();


        // ... other initialization code ...
    }

    // You can define the updateStoryNode method to update the current story node and UI
    private void updateStoryNode(StoryNode nextStoryNode) {
        currentStoryNode = nextStoryNode;
        loadStoryNode();
    }



//    private void startGame(){
//        // Example: Initialize your story nodes and choices
//
//        List<Choice> initialChoices = new ArrayList<>();
//        initialChoices.add(new Choice("Go left", null));
//        initialChoices.add(new Choice("Go right", null));
//        initialChoices.add(new Choice("Go up", null));
//
//        currentStoryNode = new StoryNode("You are at a crossroad", initialChoices);
//        loadStoryNode();
//
//
//    }


    private void loadStoryNode(){
            // Display the narrative of the current node
        narrativeTextView.setText(currentStoryNode.getNarrative());

        for (int i = 0; i < currentStoryNode.getChoices().size(); i++) {
            Choice choice = currentStoryNode.getChoices().get(i);
            Button button = choiceButtons.get(i);
            button.setText(choice.getDescription());
            button.setVisibility(View.VISIBLE);
            button.setOnClickListener(v -> {
                // Move to the next story node based on the chosen option
                currentStoryNode = choice.getNextNode();
                loadStoryNode();
            });
        }

        // Hide any unused buttons
        for (int i = currentStoryNode.getChoices().size(); i < choiceButtons.size(); i++) {
            choiceButtons.get(i).setVisibility(View.GONE);
        }



    }


}