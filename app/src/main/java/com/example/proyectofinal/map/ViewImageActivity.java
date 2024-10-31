package com.example.proyectofinal.map;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.proyectofinal.R;

public class ViewImageActivity extends AppCompatActivity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        imageView = findViewById(R.id.imageView);

        String imageUri = getIntent().getStringExtra("imageUri");
        if (imageUri != null) {
            imageView.setImageURI(Uri.parse(imageUri));
        }
    }
}
