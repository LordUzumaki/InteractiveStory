package com.theodore.interactivestory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;




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


}