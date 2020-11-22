package br.com.lucas.localapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    TextView descricaoModel, dataModel, latitudeModel, longitudeModel, idModel;
    View myView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        myView = itemView;

        //item click
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myClickListener.onItemClick(v, getAdapterPosition());
            }
        });
        //item long click listener
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                myClickListener.onItemLongClick(v, getAdapterPosition());
                return true;
            }
        });

        //inicializar view com localidade_item.xml
        dataModel = itemView.findViewById(R.id.dataModel);
        descricaoModel = itemView.findViewById(R.id.descricaoModel);
        latitudeModel = itemView.findViewById(R.id.latitudeModel);
        longitudeModel = itemView.findViewById(R.id.longitudeModel);
    }
    private ViewHolder.ClickListener myClickListener;
    //interface para o click listener
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        myClickListener = clickListener;
    }
}
