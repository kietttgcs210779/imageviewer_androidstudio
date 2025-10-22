package com.example.imageviewerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.documentfile.provider.DocumentFile;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton previousButton, nextButton;
    private Button homeButton;

    private ArrayList<Uri> imageUris = new ArrayList<>();
    private int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        previousButton = findViewById(R.id.previousButton);
        nextButton = findViewById(R.id.nextButton);
        homeButton = findViewById(R.id.homeButton);

        String uriString = getIntent().getStringExtra("image_folder_uri");
        if (uriString != null) {
            Uri directoryUri = Uri.parse(uriString);
            loadImagesFromDirectory(directoryUri);
        } else {
            Toast.makeText(this, "No image folder selected", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

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
                if (imageUris.isEmpty()) return;
                currentIndex++;
                if (currentIndex >= imageUris.size()) {
                    currentIndex = 0;
                }
                updateImage();
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUris.isEmpty()) return;
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = imageUris.size() - 1;
                }
                updateImage();
            }
        });
    }

    private void loadImagesFromDirectory(Uri directoryUri) {
        DocumentFile directory = DocumentFile.fromTreeUri(this, directoryUri);
        if (directory != null && directory.isDirectory()) {
            for (DocumentFile file : directory.listFiles()) {
                String mimeType = file.getType();
                if (mimeType != null && mimeType.startsWith("image/")) {
                    imageUris.add(file.getUri());
                }
            }
            if (imageUris.isEmpty()) {
                Toast.makeText(this, "No images found in the selected folder", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateImage() {
        if (!imageUris.isEmpty()) {
            imageView.setImageURI(imageUris.get(currentIndex));
        } else {
            imageView.setImageResource(android.R.drawable.ic_menu_gallery);
        }
    }
}
