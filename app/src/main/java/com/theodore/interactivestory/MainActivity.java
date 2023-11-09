package com.theodore.interactivestory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;




import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView narrativeTextView;
    private ImageView imageView; // Class member variable
    private final List<Button> choiceButtons = new ArrayList<>();
    private StoryNode currentStoryNode;
    private StoryNode startingStoryNode;
    private final Handler typewriterHandler = new Handler(Looper.getMainLooper());
    private int typewriterIndex;
    private static final long TYPEWRITER_DELAY_MS = 50; // Delay in milliseconds




    private void initializeStory() {
        List<Choice> choices = new ArrayList<>();
        List<String> humorousStyleTags = Arrays.asList("humorous", "light-hearted");
        List<String> mysteriousStyleTags = Arrays.asList("mysterious", "dark");

        StoryNode nextNode1 = new StoryNode("TIn a tidy forest, a neat-freak squirrel named Simon had his acorns scrambled by a playful wind spirit. " +
                "\nThe mess revealed a map to the fabled Golden Acorn. Simon and his friends embarked on a humorous quest, filled with silly mishaps. \nThey discovered the Golden Acorn was just a painted regular acorn. The real treasure? The laughter and adventures shared along the way. " +
                "\nSimon learned that a little disorder could lead to unexpected joy.",humorousStyleTags, new ArrayList<>());
        StoryNode nextNode2 = new StoryNode("Beneath the shadowy canopy of an ancient forest, a whispering pond held the secret to the village's lost memories. " +
                "\nEvery full moon, an ethereal glow would emanate from the water, and one brave villager, Luna, dared to touch the surface. As her fingers met the liquid, " +
                "\nvisions of forgotten times swirled before her eyes, revealing the village's ancient protectors, spirits bound to the water. Luna, now keeper of secrets, " +
                "\nhad to choose between sharing these truths or preserving the tranquility of ignorance. Her decision remained as enigmatic as the pond itself.",mysteriousStyleTags, new ArrayList<>());

        choices.add(new Choice("Humorous", nextNode1));
        choices.add(new Choice("Mysterious", nextNode2));


        startingStoryNode = new StoryNode("You find a funny joke. Choose the following to start...", humorousStyleTags,  choices);

        // Set the starting story node to the current story node
        currentStoryNode = startingStoryNode;
    }


    private void typewriterEffect(final TextView textView, final String text, final boolean isEndOfStory) {
        textView.setText("");  // Clear the text view at the beginning of the typewriter effect
        typewriterIndex = 0;  // Reset the typewriter index

        // Remove any existing callbacks to the handler that might be from previous typewriter effect runs
        typewriterHandler.removeCallbacksAndMessages(null);

        Runnable typewriterRunnable = new Runnable() {
            @Override
            public void run() {
                if (typewriterIndex < text.length()) {
                    // Add the next character to the TextView's text
                    textView.append(String.valueOf(text.charAt(typewriterIndex)));
                    typewriterIndex++;
                    typewriterHandler.postDelayed(this, TYPEWRITER_DELAY_MS);
                } else if (isEndOfStory) {
                    // If this is the end of the story, append the restart prompt
                    textView.append("\n\nTap here to restart.");
                    // Also, set an OnClickListener to restart the game when the text is tapped
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            restartGame();
                        }
                    });
                    // Since the story is finished, we don't need to post any more runnables
                    typewriterHandler.removeCallbacksAndMessages(null);
                }
            }
        };

        typewriterHandler.post(typewriterRunnable);
    }
    private void updateStoryAndImage(StoryNode storyNode) {
        // Update the text with typewriter effect
        typewriterEffect(narrativeTextView, currentStoryNode.getNarrative(), true);
        Log.d("MainActivity", "Updating story and image.");

        // Update the image based on style tags
        if (storyNode.getStyleTags().contains("humorous")) {
            Log.d("MainActivity", "Setting image to humorous.");

            imageView.setImageResource(R.drawable.heart_of_this_forest);
        } else if (storyNode.getStyleTags().contains("mysterious")) {
            Log.d("MainActivity", "Setting image to humorous.");
            imageView.setImageResource(R.drawable.image_of_a_mysterious);
        }

        updateChoiceButtons();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Remove any callbacks to stop the typewriter effect when the activity is not visible
        typewriterHandler.removeCallbacksAndMessages(null);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any callbacks to stop the typewriter effect when the activity is being destroyed
        typewriterHandler.removeCallbacksAndMessages(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        narrativeTextView = findViewById(R.id.narrativeTextView);
        imageView = findViewById(R.id.imageView); // Initialize class member variable, not a local variable
        Button choiceButton1 = findViewById(R.id.choiceButton1);
        Button choiceButton2 = findViewById(R.id.choiceButton2);




        choiceButtons.add(choiceButton1);
        choiceButtons.add(choiceButton2);

//        imageView.setImageResource(R.drawable.heart_of_this_forest);
//        imageView.setBackgroundColor(Color.RED);


        // Set up click listeners for buttons
        setUpChoiceButtonListeners();

        initializeStory();



        loadStoryNode();
        Log.d("MainActivity", "onCreate completed");

        // Load the initial story node
        updateStoryAndImage(currentStoryNode);

    }



    private void setUpChoiceButtonListeners() {
        View.OnClickListener choiceButtonListener = v -> {
            int buttonIndex = choiceButtons.indexOf(v);
            if (buttonIndex != -1) {
                Choice chosenChoice = currentStoryNode.getChoices().get(buttonIndex);
                updateStoryNode(chosenChoice.getNextNode());
                updateStoryAndImage(chosenChoice.getNextNode());
            }
        };

        for (Button button : choiceButtons) {
            button.setOnClickListener(choiceButtonListener);
        }
    }

    private void updateStoryNode(StoryNode nextStoryNode) {
        currentStoryNode = nextStoryNode;
        loadStoryNode();
    }

    private void loadStoryNode(){
        // Check if this is the end of the story
        boolean isEndOfStory = currentStoryNode.getChoices() == null || currentStoryNode.getChoices().isEmpty();

        // Apply the typewriter effect to the narrative of the current node
        typewriterEffect(narrativeTextView, currentStoryNode.getNarrative(), isEndOfStory);

        if (isEndOfStory) {
            // No choices, end of story - we handle the restart prompt in the typewriterEffect method
            for (Button button : choiceButtons) {
                button.setVisibility(View.GONE);
            }
        } else {
            // Display choices if there are any
            displayChoices(currentStoryNode.getChoices());
        }
    }

    private void displayChoices(List<Choice> choices) {
        for (int i = 0; i < choiceButtons.size(); i++) {
            final Button button = choiceButtons.get(i);
            if (i < choices.size()) {
                final Choice choice = choices.get(i);
                button.setText(choice.getDescription());
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Move to the next story node based on the chosen option
                        updateStoryNode(choice.getNextNode());
                    }
                });
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }





    private void updateChoiceButtons() {
        List<Choice> choices = currentStoryNode.getChoices();
        for (int i = 0; i < choiceButtons.size(); i++) {
            Button button = choiceButtons.get(i);
            if (i < choices.size()) {
                Choice choice = choices.get(i);
                button.setText(choice.getDescription());
                button.setVisibility(View.VISIBLE);
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }

    private void restartGame() {
        // Reset the game to the starting node
        currentStoryNode = startingStoryNode;

        // Make sure to clear any text that was appended as part of the end of story
        narrativeTextView.setText("");

        // Load the starting node again
        loadStoryNode();
    }

//    private void applyVisualStyle(List<String> styleTags) {
//        if (styleTags.contains("humorous")) {
//            narrativeTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.humorousBackground));
//            narrativeTextView.setTextColor(ContextCompat.getColor(this, R.color.humorousText));
//        } else if (styleTags.contains("mysterious")) {
//            narrativeTextView.setBackgroundColor(ContextCompat.getColor(this, R.color.mysteriousBackground));
//            narrativeTextView.setTextColor(ContextCompat.getColor(this, R.color.mysteriousText));
//        }
//        // Add more styles as needed
//    }
}
