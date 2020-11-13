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
public class CadastroLocalidade extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int GPS_REQUEST_PERMISION_CODE = 1001;
    public TextView coordenadas;
    private double latitudeAtual;
    private double longitudeAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_localidade);
        coordenadas = findViewById(R.id.coordenadas);
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                latitudeAtual = lat;
                longitudeAtual = lon;
                coordenadas.setText(String.format("Lat: %f, Long: %f", lat, lon));

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
    Intent intent = new Intent(this, MainActivity.class);
    startActivity(intent);

    }
}