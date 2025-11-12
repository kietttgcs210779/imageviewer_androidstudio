package com.example.imageviewerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton previousButton, nextButton;
    private Button homeButton;

    private int[] imageResources = {
            R.drawable.cat,
            R.drawable.chicken,
            R.drawable.dog,
            R.drawable.elephant,
            R.drawable.frog,
            R.drawable.lion,
            R.drawable.monkey,
            R.drawable.pig,
            R.drawable.tiger
    };
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        homeButton = findViewById(R.id.homeButton);

        updateImage();

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= imageResources.length) {
                    currentIndex = 0;
                }
                updateImage();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = imageResources.length - 1;
                }
                updateImage();
            }
        });
    }

    private void updateImage() {
        if (imageResources.length > 0) {
            imageView.setImageResource(imageResources[currentIndex]);
        }
    }
}
