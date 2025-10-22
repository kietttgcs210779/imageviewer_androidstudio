package com.example.imageviewerapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private Button importButton;

    private final ActivityResultLauncher<Intent> openDocumentTreeLauncher = 
        registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                Uri directoryUri = result.getData().getData();
                if (directoryUri != null) {
                    getContentResolver().takePersistableUriPermission(directoryUri, 
                        Intent.FLAG_GRANT_READ_URI_PERMISSION);

                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.putExtra("image_folder_uri", directoryUri.toString());
                    startActivity(intent);
                }
            }
        });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        importButton = findViewById(R.id.importButton);

        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                openDocumentTreeLauncher.launch(intent);
            }
        });
    }
}
