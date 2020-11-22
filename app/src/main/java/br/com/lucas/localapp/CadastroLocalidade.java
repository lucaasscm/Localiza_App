package br.com.lucas.localapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CadastroLocalidade extends AppCompatActivity {

    private static final String TAG = "Cadastro Localidade";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int GPS_REQUEST_PERMISION_CODE = 1001;
    private TextView coordenadas;
    private EditText EditTextDescricao;
    private EditText EditTextData;
    public double latitudeAtual;
    public double longitudeAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_localidade);
        EditTextDescricao = findViewById(R.id.descricao);
        coordenadas = findViewById(R.id.coordenadas);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitudeAtual = location.getLatitude();
                longitudeAtual = location.getLongitude();
                coordenadas.setText(String.format("Lat: %f, Long: %f", latitudeAtual, longitudeAtual));
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }
            @Override
            public void onProviderEnabled(String provider) {
            }
            @Override
            public void onProviderDisabled(String provider) {
            }
        };

        }

    @Override
    public void onStart(){
        super.onStart();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER
                    , 0, 0, locationListener);
        }
        else{
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    GPS_REQUEST_PERMISION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == GPS_REQUEST_PERMISION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
            }
            else{
                Toast.makeText(this,"Sem GPS não rola", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    public void inserirLocalidade (View view){
        //Declarando variáveis
        String descricao = EditTextDescricao.getText().toString().trim();
       // Location location = new Location("");
//        double latitudeAtual = location.getLatitude();
//        double longitudeAtual = location.getLongitude();

        uploadData( descricao, latitudeAtual, longitudeAtual);

    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);

    }

    public void uploadData(String descricao, double latitudeAtual, double longitudeAtual){
        //Criando id aleatória
        String id = UUID.randomUUID().toString();

        //Criando documento no db
        Map<String, Object> localidade = new HashMap<>();
        localidade.put("descricao", descricao);
        localidade.put("lat", latitudeAtual);
        localidade.put("lon", longitudeAtual);
        localidade.put("id", id);


        db.collection("Locais").document(id).set(localidade)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Local Salvo com Sucesso");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erro!", e);
                    }
                });

        }
    }
