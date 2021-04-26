package com.example.bonappetit;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bonappetit.model.Restaurante;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class RestauranteActivity extends AppCompatActivity {

    private TextView tvNombre;
    private TextView tvtipoComida;
    private TextView tvUbic;
    private TextView tvPrecio;
    private String website;
    private String linkOrdenar;
    private ImageView imagenPerfil;
    private ImageView imagenComida;
    private String latitud_longitud;
    private ArrayList<String> imagenesMenu;
    private Button visitar;
    private Button ordenar;
    private ImageButton mapa;


    private Restaurante restaurante;

    public int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        tvNombre = findViewById(R.id.tvNombre);
        tvtipoComida = findViewById(R.id.tvtipoComida);
        tvUbic = findViewById(R.id.tvUbic);
        tvPrecio = findViewById(R.id.tvPrecio);
        imagenPerfil = findViewById(R.id.imgPerfil);
        imagenComida = findViewById(R.id.imgComida);
        visitar = findViewById(R.id.btVisitar);
        ordenar = findViewById(R.id.btOrdenar);
        mapa = findViewById(R.id.mapa);

        Restaurante restaurante = (Restaurante) getIntent().getSerializableExtra("restaurante");
        Toast.makeText(RestauranteActivity.this,
        "restaurante" + restaurante.getNombre() , Toast.LENGTH_SHORT).show();

        tvNombre.setText(""+restaurante.getNombre());
        tvtipoComida.setText(""+restaurante.getTipoComida());
        tvUbic.setText(""+restaurante.getUbicacion());
        tvPrecio.setText(""+restaurante.getRangoPrecio());

        new DownloadImageTask(imagenPerfil)
                .execute(restaurante.getImagenPerfil());

        new DownloadImageTask(imagenComida)
                .execute(restaurante.getImagenComida());


        website = restaurante.getWebsite();
        linkOrdenar = restaurante.getLinkOrdenar();
        visitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(website != null) {
                    Uri webpage = Uri.parse(website);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                } else {
                    Toast.makeText(RestauranteActivity.this,
                            "Esta opción no está disponible. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        ordenar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(linkOrdenar != null) {
                    Uri webpage = Uri.parse(linkOrdenar);
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(intent);
                } else {
                    Toast.makeText(RestauranteActivity.this,
                            "Esta opción no está disponible. ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        new DownloadImageTask(mapa)
                .execute("https://firebasestorage.googleapis.com/v0/b/bon-appetit-8942a.appspot.com/o/mapa.jpg?alt=media&token=f8a9b4c7-fc1c-487c-bc5d-de9f99412783");
        latitud_longitud = restaurante.getLatitud_longitud();
        mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri location = Uri.parse("geo:"+latitud_longitud+"?q="+restaurante.getNombre());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, location);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });


        LinearLayout galleryMenu = findViewById(R.id.galleryMenu);
        LayoutInflater inflater = LayoutInflater.from(this);
        String listaImagenes = restaurante.getImagenesMenu();
        imagenesMenu = new ArrayList<String>(Arrays.asList(listaImagenes.split(",")));
        int arrayLength = imagenesMenu.size();

        for(i=0; i<arrayLength;i++) {
            View view = inflater.inflate(R.layout.items, galleryMenu, false);
            ImageView imageView = view.findViewById(R.id.imageView);
            new DownloadImageTask(imageView)
                    .execute(imagenesMenu.get(i));

            galleryMenu.addView(view);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }


}