package com.example.bonappetit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;

public class PublicarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publicar);
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