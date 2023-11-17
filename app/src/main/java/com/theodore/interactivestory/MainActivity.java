package com.theodore.interactivestory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.text.method.ScrollingMovementMethod;





import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Declaration of member variables for the activity
    private TextView narrativeTextView; // TextView for displaying the story narrative
    private ImageView imageView; // ImageView for showing images associated with the story
    private final List<Button> choiceButtons = new ArrayList<>(); // List to hold choice buttons
    private StoryNode currentStoryNode; // Current node in the story
    private StoryNode startingStoryNode; // Starting node of the story
    private final Handler typewriterHandler = new Handler(Looper.getMainLooper()); // Handler for typewriter effect
    private int typewriterIndex; // Index used in typewriter effect
    private static final long TYPEWRITER_DELAY_MS = 50; // Delay for typewriter effect in milliseconds

    public boolean image1Displaying = false; // Flag to track if a specific image is currently displayed



    // Initialize the story nodes and choices
    private void initializeStory() {

        List<Choice> choices = new ArrayList<>();
        List<String> humorousStyleTags = Arrays.asList("humorous", "light-hearted");
        List<String> mysteriousStyleTags = Arrays.asList("mysterious", "dark");



        StoryNode nextNode1 = new StoryNode("Tin a tidy forest, a neat-freak squirrel named Simon had his acorns scrambled by a playful wind spirit. " +
                "\nThe mess revealed a map to the fabled Golden Acorn. Simon and his friends embarked on a humorous quest, filled with silly mishaps. \nThey discovered the Golden Acorn was just a painted regular acorn. The real treasure? The laughter and adventures shared along the way. " +
                "\nSimon learned that a little disorder could lead to unexpected joy.",humorousStyleTags, new ArrayList<>());
        StoryNode nextNode2 = new StoryNode("Beneath the shadowy canopy of an ancient forest, a whispering pond held the secret to the village's lost memories. " +
                "\nEvery full moon, an ethereal glow would emanate from the water, and one brave villager, Luna, dared to touch the surface. As her fingers met the liquid, " +
                "\nvisions of forgotten times swirled before her eyes, revealing the village's ancient protectors, spirits bound to the water. Luna, now keeper of secrets, " +
                "\nhad to choose between sharing these truths or preserving the tranquility of ignorance. Her decision remained as enigmatic as the pond itself.",mysteriousStyleTags, new ArrayList<>());


        choices.add(new Choice("Humorous", nextNode1));
        choices.add(new Choice("Mysterious", nextNode2));


        startingStoryNode = new StoryNode("You find a funny joke. Choose the following to start...", humorousStyleTags, choices);

        // Set the starting story node to the current story node
        currentStoryNode = startingStoryNode;
    }

    // Creates a typewriter effect for displaying text
    private void typewriterEffect(final TextView textView, final String text, final boolean isEndOfStory) {
        // ... Typewriter effect logic ...
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
                    textView.setOnClickListener(v -> restartGame());
                    // Since the story is finished, we don't need to post any more runnable
                    typewriterHandler.removeCallbacksAndMessages(null);
                }
            }
        };

        typewriterHandler.post(typewriterRunnable);
    }

    // Updates the story and the image based on the current story node
    private void updateStoryAndImage(StoryNode storyNode) {

        narrativeTextView.setText(storyNode.getNarrative());
        // Update the text with typewriter effect
        typewriterEffect(narrativeTextView, currentStoryNode.getNarrative(), true);
        for (Button button : choiceButtons) {
            button.setOnClickListener(v -> {
                int buttonIndex = choiceButtons.indexOf(v);
                if (buttonIndex != -1) {
                    Choice choices = currentStoryNode.getChoices().get(buttonIndex);
                    updateStoryNode(choices.getNextNode());

                    // Check if the 'Humorous' button is clicked
                    if (choices.getDescription().equalsIgnoreCase("Humorous")) {
                        imageView.setImageResource(R.drawable.ancient_forest); // Replace with your image resource
                        image1Displaying = false;
                    } else if (choices.getDescription().equalsIgnoreCase("Mysterious")) {
                        imageView.setImageResource(R.drawable.image_of_a_mysterious); // Replace with your image resource
                        image1Displaying = true;
                    }
                    handleChoiceButtonClicked(choices.getDescription());
                    updateStoryAndImage(choices.getNextNode());
                    initializeStory();

                }
            });
        }
        updateChoiceButtons();
    }
    // Lifecycle callback method, called when the activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        // Remove any callbacks to stop the typewriter effect when the activity is not visible
        typewriterHandler.removeCallbacksAndMessages(null);
    }
    // Lifecycle callback method, called when the activity is destroyed
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove any callbacks to stop the typewriter effect when the activity is being destroyed
        typewriterHandler.removeCallbacksAndMessages(null);
    }
    // Lifecycle callback method, called when the activity resumes
    @Override
    protected void onResume() {
        super.onResume();
        // Reset the image to the original one when the activity resumes
        imageView.setImageResource(R.drawable.heart_of_this_forest); // Replace 'original_image' with the actual name of your original image

    }

    // Lifecycle callback method, called when the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        narrativeTextView = findViewById(R.id.narrativeTextView);
        narrativeTextView.setMovementMethod(new ScrollingMovementMethod());
        imageView = findViewById(R.id.imageView); // Initialize class member variable, not a local variable
        Button Humorous = findViewById(R.id.Humorous);
        Button Mysterious = findViewById(R.id.Mysterious);
        Humorous.setOnClickListener(v -> handleChoiceButtonClicked("Humorous"));
        Mysterious.setOnClickListener(v -> handleChoiceButtonClicked("Mysterious"));

        initializeStory();
        // Load the initial story node
        updateStoryAndImage(currentStoryNode);

        loadStoryNode();

    }
    // Handles button clicks based on the choice description
    private void handleChoiceButtonClicked(String choiceDescription) {
        for (int i = 0; i < currentStoryNode.getChoices().size(); i++) {
            Choice choice = currentStoryNode.getChoices().get(i);
            if (choice.getDescription().equalsIgnoreCase(choiceDescription)) {
                updateStoryNode(choice.getNextNode());

                if (choiceDescription.equalsIgnoreCase("Humorous")) {
                    imageView.setImageResource(R.drawable.ancient_forest);
                } else if (choiceDescription.equalsIgnoreCase("Mysterious")) {
                    imageView.setImageResource(R.drawable.image_of_a_mysterious);
                }

                break;
            }
        }
    }
    // Updates the current story node
    private void updateStoryNode(StoryNode nextStoryNode) {
        currentStoryNode = nextStoryNode;
        loadStoryNode();
    }
    // Loads the current story node and updates UI accordingly
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
    // Displays the available choices as buttons
    private void displayChoices(List<Choice> choices) {
        for (int i = 0; i < choiceButtons.size(); i++) {
            final Button button = choiceButtons.get(i);
            if (i < choices.size()) {
                final Choice choice = choices.get(i);
                button.setText(choice.getDescription());
                button.setVisibility(View.VISIBLE);
                button.setOnClickListener(v -> {
                    // Move to the next story node based on the chosen option
                    updateStoryNode(choice.getNextNode());
                });
            } else {
                button.setVisibility(View.GONE);
            }
        }
    }
    // Updates the visibility and text of choice buttons
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
    // Resets the game to the starting story node
    private void restartGame() {
        // Reset the game to the starting node
        currentStoryNode = startingStoryNode;
        onResume();
        // Make sure to clear any text that was appended as part of the end of story
        narrativeTextView.setText("");
        // Load the starting node again
        loadStoryNode();




    }
}
