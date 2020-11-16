package br.com.lucas.localapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    List<Localidade> localidadeList = new ArrayList<>();
     RecyclerView myRecyclerView;

    //Gerenciador de layout da recyclerview
    RecyclerView.LayoutManager layoutManager;

    //Instanciando o Firestore db
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LocalidadeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializando Views
        myRecyclerView = findViewById(R.id.localidadesRecyclerView);

        //Setando as propriedades da recyclerview
        myRecyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(layoutManager);

        //Mostrar dados na recyclerview
        showData();
    }

    private void showData() {
        db.collection("Locais")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        //Quando sucesso
                        Log.d(TAG, "Local Salvo com Sucesso");
                        //Mostrando os dados
                        for(DocumentSnapshot documentSnapshot: task.getResult()){
                            Localidade localidade = new Localidade(documentSnapshot.getDouble("lat"),
                                    documentSnapshot.getDouble("lon"),
                                    documentSnapshot.getString("data"),
                                    documentSnapshot.getString("descricao"));
                            localidadeList.add(localidade);
                        }
                        //adapter
                        adapter = new LocalidadeAdapter(MainActivity.this, localidadeList);
                        //Set adapter na recyclerview
                        myRecyclerView.setAdapter(adapter);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Quando fracasso
                        Log.w(TAG, "Erro!", e);

                    }
                });
    }


    public void novaLocalidade (View view){
        Intent mapIntent = new Intent(this, CadastroLocalidade.class);

        startActivity(mapIntent);
    }
}