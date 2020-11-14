package br.com.lucas.localapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    public void novaLocalidade (View view){
       // Uri gmmIntentUri = Uri.parse(String.format("geo:%f,%f?q=", latitudeAtual, longitudeAtual));
        Intent mapIntent = new Intent(this, CadastroLocalidade.class);
       // mapIntent.setPackage("com.google.android.apps.maps");
        //Intent intent = new Intent(this , CadastroLocalidade.class);
        startActivity(mapIntent);
    }
}