package com.example.bonappetit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bonappetit.R;
import com.example.bonappetit.RestauranteActivity;
import com.example.bonappetit.model.Restaurante;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class FullImage extends AppCompatActivity {
    private ImageView ivFull;
    private ArrayList<String> imagenesMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menurestaurante);

        //Restaurante restaurante = (Restaurante) getIntent().getSerializableExtra("restaurante");
        //Toast.makeText(FullImage.this,
        //        "restaurante" + restaurante.getNombre(), Toast.LENGTH_SHORT).show();

        //int id = getIntent().getIntExtra("id",0);
        //ArrayList<String> imagenesMenu = getIntent().getStringArrayListExtra("array");
        //String url = getIntent().getStringExtra("url");
        //Toast.makeText(getApplicationContext(),imagenesMenu.get(1),Toast.LENGTH_LONG).show();
        //Picasso.get().load(imagenesMenu.get(id)).into(ivFull);

        LinearLayout galleryMenu = findViewById(R.id.galleryMenu);
        LayoutInflater inflater = LayoutInflater.from(this);
        imagenesMenu = getIntent().getStringArrayListExtra("array");
        int arrayLength = imagenesMenu.size();

        for (int i = 0; i < arrayLength; i++) {
            View view = inflater.inflate(R.layout.fullimage, galleryMenu, false);
            ImageView imageView = view.findViewById(R.id.imageView);

            //new DownloadImageTask(imageView)
            //    .execute(imagenesMenu.get(i));
            Picasso.get().load(imagenesMenu.get(i)).into(imageView);

            galleryMenu.addView(view);
        }
    }


}
