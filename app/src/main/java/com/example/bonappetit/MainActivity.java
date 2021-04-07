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

        mbase = FirebaseDatabase.getInstance().getReference();

        recyclerView = findViewById(R.id.rvRestaurantes);

        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Restaurante> options
                = new FirebaseRecyclerOptions.Builder<Restaurante>()
                .setQuery(mbase, Restaurante.class)
                .build();



        mbase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Restaurante> list = new ArrayList<Restaurante>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    list.add(child.getValue(Restaurante.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error...", Toast.LENGTH_SHORT).show();
            }
        });

        adapter = new RestauranteAdapter(options);
        recyclerView.setAdapter(adapter);
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