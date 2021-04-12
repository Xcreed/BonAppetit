package com.example.bonappetit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bonappetit.model.Restaurante;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Random;

public class PublicarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_NombreRest;
    private EditText et_TipoComida;
    private EditText et_SitioWeb;
    private EditText et_OrderLink;
    private EditText et_Ubicacion;
    private EditText et_Latitud;
    private EditText et_Longitud;
    private EditText et_ImagenMenu1;
    private EditText et_ImagenMenu2;
    private EditText et_FotoPerfil;
    private EditText et_ImagenComida;
    private Button bt_IngresarRestaurante;

    // Atributos para Storage y Real Time Database
    public StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    // Uri de las Imagenes
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        // Atributos de la vista
        et_NombreRest = findViewById(R.id.et_NombreRest);
        et_TipoComida = findViewById(R.id.et_NombreRest);
        et_SitioWeb = findViewById(R.id.et_NombreRest);
        et_OrderLink = findViewById(R.id.et_NombreRest);
        et_Ubicacion = findViewById(R.id.et_NombreRest);
        et_Latitud = findViewById(R.id.et_NombreRest);
        et_Longitud = findViewById(R.id.et_NombreRest);
        et_ImagenMenu1 = findViewById(R.id.et_NombreRest);
        et_ImagenMenu2 = findViewById(R.id.et_NombreRest);
        et_FotoPerfil = findViewById(R.id.et_NombreRest);
        et_ImagenComida = findViewById(R.id.et_NombreRest);
        bt_IngresarRestaurante = findViewById(R.id.bt_IngresarRestaurante);

        // On click List del Boton
        bt_IngresarRestaurante.setOnClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_publicar){
            if (FirebaseAuth.getInstance().getCurrentUser() != null ){
                startActivity(new Intent(this, PublicarActivity.class));
            }
            else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        }
        else if (item.getItemId() == R.id.menu_logout){
            logout();
        }
        else {
            startActivity(new Intent(this, AcercaDeActivity.class));
        }
        return true;
    }

    public void logout(){
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.bt_IngresarRestaurante:
                IngresarRestaurante();
                break;
        }
    }

    private void IngresarRestaurante() {
        /**
         * Subida de las imagenes a Storage
         */
        // Subida de la Imagen - Foto de Perfil
        String fotoPerfil = et_FotoPerfil.getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference().child(fotoPerfil);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PublicarActivity.this, "Subida de Foto de Perfil Exitosa", Toast.LENGTH_SHORT).show(); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PublicarActivity.this, "Error al Subir la Foto de Perfil", Toast.LENGTH_SHORT).show(); }
        });

        // Subida de la Imagen - ImagenComida
        String ImagenComida = et_ImagenComida.getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference().child(ImagenComida);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PublicarActivity.this, "Subida de Imagen de un Plato Exitosa", Toast.LENGTH_SHORT).show(); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PublicarActivity.this, "Error al Subir Imagen de un Plato", Toast.LENGTH_SHORT).show(); }
        });

        // Subida de la Imagen - Imagen Menu 1
        String ImagenMenu1 = et_ImagenMenu1.getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference().child(ImagenMenu1);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PublicarActivity.this, "Subida de Imagen de Menu #1 Exitosa", Toast.LENGTH_SHORT).show(); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PublicarActivity.this, "Error al Subir Imagen de Menu #1", Toast.LENGTH_SHORT).show(); }
        });

        // Subida de la Imagen - Imagen Menu 2
        String ImagenMenu2 = et_ImagenMenu2.getText().toString();
        storageReference = FirebaseStorage.getInstance().getReference().child(ImagenMenu2);
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(PublicarActivity.this, "Subida de Imagen de Menu #2 Exitosa", Toast.LENGTH_SHORT).show(); }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PublicarActivity.this, "Error al Subir Imagen de Menu #2", Toast.LENGTH_SHORT).show(); }
        });

        /** Link de Subida de Foto de Perfil
         * Esto nos funciona para poder subir los links a RealTime database
         */
        String FotoPerfil = FirebaseStorage.getInstance().getReference().child(fotoPerfil).getPath();
        String FotoComida = FirebaseStorage.getInstance().getReference().child(ImagenComida).getPath();
        String FotoMenu1 = FirebaseStorage.getInstance().getReference().child(ImagenMenu1).getPath();
        String FotoMenu2 = FirebaseStorage.getInstance().getReference().child(ImagenMenu2).getPath();

        /**
         * Subida del Restaurante a Real Time Database
         */

        //Para setear el numero del id, lo generamos random
        Random random = new Random();
        int upperbound = 100000;
        int id = random.nextInt(upperbound);

        // Array De las fotos de los Menus > con el Link de Storage
        ArrayList<String> Menu = new ArrayList<String>();
        Menu.add(FotoMenu1);
        Menu.add(FotoMenu2);

        // Generamos el nuevo objeto restaurante con los valores
        Restaurante rest = new Restaurante();
        rest.setId(Integer.toString(id));
        rest.setNombre(et_NombreRest.getText().toString());
        rest.setTipoComida(et_TipoComida.getText().toString());
        rest.setWebsite(et_SitioWeb.getText().toString());
        rest.setLinkOrdenar(et_OrderLink.getText().toString());
        rest.setUbicacion(et_Ubicacion.getText().toString());
        rest.setLatitud(et_Latitud.getText().toString());
        rest.setLongitud(et_Longitud.getText().toString());
        rest.setImagenesMenu(Menu);
        rest.setImagenPerfil(FotoPerfil);
        rest.setImagenComida(ImagenComida);

        /// Obtenemos la instancia RealTime
       database=FirebaseDatabase.getInstance();
       myRef = database.getReference("restaurantes");
       // Enviamos los valores del restaurante a Real Time Database utilizando el Nombre como identificador
        myRef.child(rest.getNombre()).setValue(rest);

    }
}