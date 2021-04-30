package com.example.bonappetit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.example.bonappetit.model.Restaurante;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.common.base.Strings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PublicarActivity extends AppCompatActivity implements View.OnClickListener {

    private final int PICK_IMAGE_MENU = 1;
    private final int PICK_IMAGE_PLATE = 1;
    private EditText et_NombreRest;
    private EditText et_TipoComida;
    private EditText et_SitioWeb;
    private EditText et_OrderLink;
    private EditText et_Ubicacion;
    private EditText et_Latitud;
    private EditText et_Longitud;
    private SeekBar sb_rangoPrecio;
    private Restaurante rest = new Restaurante();
    private Uri uri;

    private BootstrapButton bt_IngresarRestaurante;
    private BootstrapButton bt_buscarM;
    private BootstrapButton bt_buscarC;
    private BootstrapButton bt_buscarP;

    private Uri perfilURI;
    private Uri platoURI;

    // Atributos para Storage y Real Time Database
    public StorageReference storageReference = null;
    private FirebaseDatabase database = null;
    private DatabaseReference myRef = null;


    // Links de la foto de perfil y la foto de comida
    private String StringFotoComida;
    private String StringFotoPerfil;


    // Variables de Elegir Imagenes para el menu
    private int PICK_IMAGE_PROF = 100;
    private List<Uri> listaImagenes = new ArrayList<>();
    private int i = 0;
    private List<String> linksMenus = new ArrayList<>();
    private String links;
    private ArrayList<Task<Uri>> uploadTasks = new ArrayList<Task<Uri>>();
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
        sb_rangoPrecio = findViewById(R.id.sb_rangoPrecio);

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
                buscarMenu();
                break;
            case R.id.bt_buscarC:
                buscarPlato();
                break;
            case R.id.bt_buscarP:
                buscarProf();
                break;
        }
    }

    private void SubirFotosMenu(String nombreRest) {


        for (i =0; i<listaImagenes.size() ; i++) {

            storageReference = FirebaseStorage.getInstance().getReference().child(nombreRest);
            StorageReference imageRef = storageReference.child("menu" + i + ".png");
            Task uploadTask = imageRef.putFile(listaImagenes.get(i));

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String menuUrl = downloadUri.toString();
                        linksMenus.add(menuUrl);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

            uploadTasks.add(urlTask);
        }


    }


    private void SubirFotoComida(String nombreRest) {
            storageReference = FirebaseStorage.getInstance().getReference().child(nombreRest);//.child(listaImagenes.get(i).toString())
            StorageReference imageRef = storageReference.child("fotoPlato.png");
            Task uploadTask  = imageRef.putFile(platoURI);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return imageRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        StringFotoComida = downloadUri.toString();
                        rest.setImagenComida(StringFotoComida);
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });

        uploadTasks.add(urlTask);
    }

    private void SubirFotoPerfil(String nombreRest) {
        /**
         * Subida de las imagenes a Storage
         */

        storageReference = FirebaseStorage.getInstance().getReference().child(nombreRest);//.child(listaImagenes.get(i).toString())
        StorageReference imageRef = storageReference.child("fotoPerfil.png");
        Task uploadTask  = imageRef.putFile(perfilURI);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }

                // Continue with the task to get the download URL
                return imageRef.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    StringFotoPerfil = downloadUri.toString();
                    rest.setImagenPerfil(StringFotoPerfil);
                } else {
                    // Handle failures
                    // ...
                }
            }
        });

        uploadTasks.add(urlTask);

    }

    private void buscarProf() {
        Intent intent = new Intent(); intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagenes:"), PICK_IMAGE_PROF);
    }
    private void buscarPlato() {
        Intent intent = new Intent(); intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagenes:"), PICK_IMAGE_PLATE);
    }private void buscarMenu() {
        Intent intent = new Intent(); intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Seleccionar Imagenes:"), PICK_IMAGE_MENU);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ClipData clipData = data.getClipData();

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_PROF) {
            if(clipData == null) {
                perfilURI = data.getData();
            }
        }
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_PLATE) {
            if(clipData == null) {
                platoURI = data.getData();
            }
        }
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE_MENU) {
            if(clipData == null) {
                uri = data.getData();
                listaImagenes.add(uri);
            } else {
                for (int i = 0; i < clipData.getItemCount()-1; i++) {
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

        rest.setId(Integer.toString(id));
        rest.setNombre(et_NombreRest.getText().toString());
        rest.setTipoComida(et_TipoComida.getText().toString());
        rest.setWebsite(et_SitioWeb.getText().toString());
        rest.setLinkOrdenar(et_OrderLink.getText().toString());
        rest.setUbicacion(et_Ubicacion.getText().toString());
        rest.setLatitud(et_Latitud.getText().toString());
        rest.setLongitud(et_Longitud.getText().toString());
        String valor = Strings.repeat("$",sb_rangoPrecio.getProgress()+1 );
        rest.setRangoPrecio(valor);
        SubirFotoComida(rest.getNombre()+rest.getId());
        SubirFotoPerfil(rest.getNombre()+rest.getId());
        SubirFotosMenu(rest.getNombre()+rest.getId());
        //

        //rest.setImagenComida(StringFotoComida);
        Tasks.whenAllComplete(uploadTasks).addOnCompleteListener(new OnCompleteListener<List<Task<?>>>() {
            @Override
            public void onComplete(@NonNull Task<List<Task<?>>> task) {
                links = android.text.TextUtils.join(", ", linksMenus);
                rest.setImagenesMenu(links);
                database=FirebaseDatabase.getInstance();
                myRef = database.getReference("restaurantes");
                // Enviamos los valores del restaurante a Real Time Database utilizando el Nombre como identificador
                myRef.child(rest.getNombre()+rest.getId()).setValue(rest);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


    }
}