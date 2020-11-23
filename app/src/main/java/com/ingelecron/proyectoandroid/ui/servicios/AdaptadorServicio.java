package com.ingelecron.proyectoandroid.ui.servicios;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ingelecron.proyectoandroid.R;

import java.util.List;

public class AdaptadorServicio extends RecyclerView.Adapter<AdaptadorServicio.ViewHolder> {

    private Context contexto;
    private List<ObjServicio> lista;

    public AdaptadorServicio(Context contexto, List<ObjServicio> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_servicio,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ObjServicio objServicio=lista.get(position);

        holder.tnombre.setText(objServicio.getNombre());
        holder.tmarca.setText(objServicio.getMarca());
        holder.tmodelo.setText(objServicio.getModelo());
        holder.tdescripcion.setText(objServicio.getDescripcion());
        holder.tvalor.setText(objServicio.getValor());


    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tnombre,tmarca, tmodelo,tdescripcion,tvalor;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tnombre = itemView.findViewById(R.id.tnombre);
            tmarca = itemView.findViewById(R.id.tmarca);
            tmodelo = itemView.findViewById(R.id.tmodelo);
            tdescripcion = itemView.findViewById(R.id.tdescripcion);
            tvalor = itemView.findViewById(R.id.tvalor);
        }
    }

}
