package com.example.bonappetit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.bonappetit.model.Restaurante;

public class RestauranteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        Restaurante restaurante = (Restaurante) getIntent().getSerializableExtra("restaurante");
        Toast.makeText(RestauranteActivity.this,
        "restaurante" + restaurante.getNombre() , Toast.LENGTH_SHORT).show();
    }
}