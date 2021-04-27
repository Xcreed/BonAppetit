package com.example.bonappetit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PublicarActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_NombreRest;
    private EditText et_TipoComida;
    private EditText et_SitioWeb;
    private EditText et_OrderLink;
    private EditText et_Ubicacion;
    private EditText et_Latitud;
    private EditText et_Longitud;


    private Button bt_IngresarRestaurante;
    private Button bt_buscarM;
    private Button bt_buscarC;
    private Button bt_buscarP;

    // Atributos para Storage y Real Time Database
    public StorageReference storageReference;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    // Links de la foto de perfil y la foto de comida
    final String[] FotoPerfil = new String[1];
    final String[] FotoComida = new String[1];


    // Variables de Elegir Imagenes para el menu
    int PICK_IMAGE = 100;
    List<Uri> listaImagenes = new ArrayList<>();
    int i = 0;
    List<String> linksMenus = new ArrayList<>();
    String links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);

        // Atributos de la vista
        et_NombreRest = findViewById(R.id.et_NombreRest);
        et_TipoComida = findViewById(R.id.et_TipoComida);
        et_SitioWeb = findViewById(R.id.et_SitioWeb);
        et_OrderLink = findViewById(R.id.et_OrderLink);
        et_Ubicacion = findViewById(R.id.et_Ubicacion);
        et_Latitud = findViewById(R.id.et_Latitud);
        et_Longitud = findViewById(R.id.et_Longitud);

        bt_IngresarRestaurante = findViewById(R.id.bt_IngresarRestaurante);
        bt_buscarM = findViewById(R.id.bt_buscarM);
        bt_buscarC = findViewById(R.id.bt_buscarC);
        bt_buscarP = findViewById(R.id.bt_buscarP);

        // On click List del Boton
        bt_IngresarRestaurante.setOnClickListener(this);
        bt_buscarM.setOnClickListener(this);
        bt_buscarC.setOnClickListener(this);
        bt_buscarP.setOnClickListener(this);
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
                IngresarRestauranteRealTime();
                break;
            case R.id.bt_buscarM:
                busca();
                SubirFotosMenu();
                break;
            case R.id.bt_buscarC:
                busca();
                SubirFotoComida();
                break;
            case R.id.bt_buscarP:
                busca();
                SubirFotoPerfil();
                break;
        }
    }

    private void SubirFotosMenu() {

        for (i =0; i<listaImagenes.size() ; i++) {

            storageReference = FirebaseStorage.getInstance().getReference().child(listaImagenes.get(i).toString());
            storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(PublicarActivity.this, "Subida de Imagen de Menu Exitosa", Toast.LENGTH_SHORT).show(); }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PublicarActivity.this, "Error al Subir Imagen", Toast.LENGTH_SHORT).show(); }
            });

            // LINK ImagenMenu
            storageReference.child(listaImagenes.get(i).toString()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri)  {
                    //Download URL para ImagenComida
                    Task<Uri> downloadUri = FirebaseStorage.getInstance().getReference().child(listaImagenes.get(i).toString()).getDownloadUrl();
                    linksMenus.add(i, downloadUri.toString());  //Link de la Imagen
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Toast.makeText(PublicarActivity.this, "Error al obtener el link de la imagen", Toast.LENGTH_SHORT).show();
                }
            });
        }


         links = android.text.TextUtils.join(",", linksMenus);

    }


    private void SubirFotoComida() {
        // Subida de la Imagen - ImagenComida
        String ImagenComida = listaImagenes.toString();
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

        // LINK Imagen Comida
        storageReference.child(ImagenComida).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)  {
                //Download URL para ImagenComida
                Task<Uri> downloadUri = FirebaseStorage.getInstance().getReference().child(ImagenComida).getDownloadUrl();
                FotoComida[0] = downloadUri.toString();  //Link de la Imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(PublicarActivity.this, "Error al obtener el link de la imagen", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void SubirFotoPerfil() {
        /**
         * Subida de las imagenes a Storage
         */
        // Subida de la Imagen - Foto de Perfil
        String fotoPerfil = listaImagenes.toString();
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

        // LINK Foto Perfil
        storageReference.child(fotoPerfil).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri)  {
                //Download URL para fotoPerfil
                Task<Uri> downloadUri = FirebaseStorage.getInstance().getReference().child(fotoPerfil).getDownloadUrl();
                FotoPerfil[0] = downloadUri.toString();  //Link de la Imagen
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(PublicarActivity.this, "Error al obtener el link de la imagen", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void busca() {
        Intent intent = new Intent(); intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagenes:"), PICK_IMAGE);
    }

    private Uri uri;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClipData clipData = data.getClipData();

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            if(clipData == null) {
                uri = data.getData();
                listaImagenes.add(uri);
            } else {
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    listaImagenes.add(clipData.getItemAt(i).getUri());
                }
            }
        }
    }




    private void IngresarRestauranteRealTime() {
        /**
         * Subida del Restaurante a Real Time Database
         */
        //Para setear el numero del id, lo generamos random
        Random random = new Random();
        int upperbound = 100000;
        int id = random.nextInt(upperbound);


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
        rest.setImagenesMenu(links);
        rest.setImagenPerfil(FotoPerfil[0]);
        rest.setImagenComida(FotoComida[0]);

        /// Obtenemos la instancia RealTime
       database=FirebaseDatabase.getInstance();
       myRef = database.getReference("restaurantes");
       // Enviamos los valores del restaurante a Real Time Database utilizando el Nombre como identificador
        myRef.child(rest.getNombre()).setValue(rest);
    }
}