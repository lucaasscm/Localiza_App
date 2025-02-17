package br.com.lucas.localapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.os.Build;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG = "MainActivity";
    List<Localidade> localidadeList = new ArrayList<>();
     RecyclerView myRecyclerView;

    //Gerenciador de layout da recyclerview
    RecyclerView.LayoutManager layoutManager;

    //Instanciando o Firestore db
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    LocalidadeAdapter adapter;
    Context context;


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
        //adapter
        adapter = new LocalidadeAdapter(MainActivity.this, localidadeList, db);

        //Set adapter na recyclerview
        myRecyclerView.setAdapter(adapter);

        //Mostrar dados na recyclerview
        showData();
    }

    public void showData() {

        db.collection("Locais")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        Log.d(TAG, "Local Salvo com Sucesso");
                        for(DocumentSnapshot documentSnapshot: task.getResult()){
                            Map<String, Object> dados = documentSnapshot.getData();
                            String id =(String) dados.get("id");
                            String data = (String) dados.get("data");
                            if (data == null || data.isEmpty()){
                                data = String.valueOf(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                            }
                            String descricao = (String) dados.get("descricao");
                            Localidade localidade = new Localidade(
                                    data, (String) dados.get("descricao"),(double) dados.get("lat"),(double)dados.get("lon"),  id);
                            localidadeList.add(localidade);
                        }
                        adapter.notifyDataSetChanged();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //Quando fracasso
                        Exception exception = e;
                    }
                });
    }

    public void onItemLongClick(View view, int position){

    }


    public void novaLocalidade (View view){
        Intent mapIntent = new Intent(this, CadastroLocalidade.class);

        startActivity(mapIntent);
    }
}