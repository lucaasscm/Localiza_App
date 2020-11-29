package br.com.lucas.localapp;

import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class LocalidadeAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "Adapter";
   private MainActivity mainActivity;
   private List<Localidade> localidadesList;
  // private  Context context;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();



    public LocalidadeAdapter(MainActivity mainActivity, List<Localidade> localidadesList,  FirebaseFirestore firebaseFirestore) {
        this.mainActivity = mainActivity;
        this.localidadesList  = localidadesList;
       // this.context = context;
        this.firebaseFirestore = firebaseFirestore;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflando o layout
       //LayoutInflater inflater   = LayoutInflater.from(context);
        //View v = inflater.inflate(R.layout.localidade_item, parent, false);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.localidade_item, parent, false);
       // return new ViewHolder(view);

        ViewHolder viewHolder = new ViewHolder(view);
        //lidar com os cliques
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Chamado quando o usuário clicar no item

            }


            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemLongClick(View view, int position) {
                //Chamado quando o usuário fizer um clique longo
                ;
                if (deleteData(position)){
                    Toast.makeText(mainActivity, "Não removido", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mainActivity, "Local removido com sucesso", Toast.LENGTH_LONG).show();
                }
            }
        });

        return viewHolder;
}


    CharSequence text = "Local salvo com Sucesso";
    int duration = Toast.LENGTH_SHORT;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean deleteData(int index){
        firebaseFirestore.collection("Locais").document(localidadesList.get(index).getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Local Deletado com Sucesso");
                        localidadesList.clear();
                        mainActivity.showData();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Erro!", e);
                    }

                });
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //blind view/ set dados
        Localidade localidade = localidadesList.get(position);

        viewHolder.dataModel.setText(localidade.getData());
        viewHolder.descricaoModel.setText(localidade.getDescricao());
        viewHolder.latitudeModel.setText(String.format("Latitude: %f",localidade.getLat()));
        viewHolder.longitudeModel.setText(String.format("Longitude: %f",localidade.getLon()));
        Log.i("Log OnBindViewHolder",localidade.toString());



    }

    @Override
    public int getItemCount() {
        return localidadesList.size();
    }
}
