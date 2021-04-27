package com.example.bonappetit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.bonappetit.adapter.RestauranteAdapter;
import com.example.bonappetit.model.Restaurante;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    RestauranteAdapter adapter;
    DatabaseReference mbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpRecyclerView();
    }

    private void setUpRecyclerView(){
        mbase = FirebaseDatabase.getInstance().getReference("restaurantes");

        recyclerView = findViewById(R.id.rvRestaurantes);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Restaurante> options
                = new FirebaseRecyclerOptions.Builder<Restaurante>()
                .setQuery(mbase, Restaurante.class)
                .build();
        adapter = new RestauranteAdapter(options);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RestauranteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DataSnapshot dataSnapshot, int position) {
                Restaurante restaurante = dataSnapshot.getValue(Restaurante.class);
//                Toast.makeText(MainActivity.this,
//                        "Position: " + position + "restaurante" + restaurante.getNombre() , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), RestauranteActivity.class);
                intent.putExtra("restaurante", restaurante);
                startActivity(intent);
            }
        });

    }

    @Override protected void onStart()
    {
        super.onStart();
        adapter.startListening();
    }

    @Override protected void onStop()
    {
        super.onStop();
        adapter.stopListening();
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


}