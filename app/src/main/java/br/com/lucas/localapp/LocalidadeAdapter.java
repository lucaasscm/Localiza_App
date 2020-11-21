package br.com.lucas.localapp;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class LocalidadeAdapter extends RecyclerView.Adapter<ViewHolder> {

   private MainActivity mainActivity;
   private List<Localidade> localidadesList;
  // private  Context context;
    private FirebaseFirestore firebaseFirestore;

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
        return new ViewHolder(view);

        /*ViewHolder viewHolder = new ViewHolder(itemView);
        //lidar com os cliques
        viewHolder.serOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                //Chamado quando o usuário clicar no item

            }

            @Override
            public void onItemLongClick(View view, int position) {
                //Chamado quando o usuário fizer um clique longo
            }
        });

        return viewHolder;*/
}

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        //blind view/ set dados
        Localidade localidade = localidadesList.get(position);

        viewHolder.dataModel.setText(localidade.getData());
        viewHolder.descricaoModel.setText(localidade.getDescricao());
        viewHolder.latitudeModel.setText(String.format("Lat: %f",localidade.getLat()));
        viewHolder.longitudeModel.setText(String.format("Lon: %f",localidade.getLon()));
        Log.i("Log OnBindViewHolder",localidade.toString());



    }

    @Override
    public int getItemCount() {
        return localidadesList.size();
    }
}