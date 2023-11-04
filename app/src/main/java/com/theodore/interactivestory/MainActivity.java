package com.theodore.interactivestory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class MainActivity extends AppCompatActivity {

//    private DataBaseHelper dbHelper;
//    private StoryDataBaseHelper dbHelper;

    private TextView narrativeTextView;
    private List<Button> choiceButtons = new ArrayList<>();
    private StoryNode currentStoryNode;
    private StoryNode startingStoryNode;
   private Button choiceButton1, choiceButton2, choiceButton3;

    private void initializeStory() {

        StoryNode forestNode = new StoryNode("You've entered a dense forest. Birds are chirping.", null); // '...' will be choices for this node
        StoryNode caveNode = new StoryNode("You've entered a dark cave. Bats fly past.", null); // '...' will be choices for this node

        Choice exploreForestChoice = new Choice("Explore the forest", forestNode);
        Choice enterCaveChoice = new Choice("Enter the cave", caveNode);

        // When initializing your game for the first time
        List<Choice> someListOfChoices = Arrays.asList(exploreForestChoice, enterCaveChoice);// Define or retrieve the list of choices for the starting node.
        startingStoryNode = new StoryNode("Beginning of your story...", someListOfChoices);
        currentStoryNode = startingStoryNode;

//        if(enterCaveChoice != false){
//            system.out.printlin("GameOver");
//        }

        //add more stories
        StoryNode ending1 = new StoryNode("You lived happily ever after.", null);
        StoryNode ending2 = new StoryNode("Alas, the dragon got you.", null);

        Choice choice1 = new Choice("Fight the dragon", ending2);
        Choice choice2 = new Choice("Run away from the dragon", ending1);

        StoryNode encounterDragon = new StoryNode("You encounter a dragon. What do you do?", Arrays.asList(choice1, choice2));


        currentStoryNode = encounterDragon;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // This should come first

        narrativeTextView = findViewById(R.id.narrativeTextView);

        choiceButton1 = findViewById(R.id.choiceButton1);
        choiceButton2 = findViewById(R.id.choiceButton2);
        choiceButton3 = findViewById(R.id.choiceButton3);

        choiceButtons.add(choiceButton1);
        choiceButtons.add(choiceButton2);
        choiceButtons.add(choiceButton3);






        choiceButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the action for choiceButton1 here
                // For example, display a message or perform some other action
                // You can also update the story node or game state as needed
                Log.d("ButtonTest", "Button 2 clicked");

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

        initializeStory();
        loadStoryNode();
//        startGame();


        // ... other initialization code ...
    }


    // You can define the updateStoryNode method to update the current story node and UI
    private void updateStoryNode(StoryNode nextStoryNode) {
        currentStoryNode = nextStoryNode;
        loadStoryNode();
    }


    private void restartGame() {
        currentStoryNode = startingStoryNode;
        narrativeTextView.setOnClickListener(null);  // Remove the restart click listener
        loadStoryNode();
    }




    private void loadStoryNode(){
            // Display the narrative of the current node
        narrativeTextView.setText(currentStoryNode.getNarrative());
        if (currentStoryNode.getChoices() == null || currentStoryNode.getChoices().isEmpty()) {
            // No choices, end of story
            for (Button button : choiceButtons) {
                button.setVisibility(View.GONE);
            }
            // Set the narrativeTextView to display a message prompting the user to restart
            narrativeTextView.append("\n\nTap here to restart.");
            narrativeTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    restartGame();
                }
            });
            return;
            // Optionally, show a "Restart" or "Exit" button here
        }

        for (int i = 0; i < currentStoryNode.getChoices().size(); i++) {
            Choice choice = currentStoryNode.getChoices().get(i);
            Button button = choiceButtons.get(i);
            button.setText(choice.getDescription());
            button.setTextColor(getColor(R.color.Red));
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