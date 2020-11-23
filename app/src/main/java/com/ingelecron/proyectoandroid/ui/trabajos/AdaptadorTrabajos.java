package com.ingelecron.proyectoandroid.ui.trabajos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ingelecron.proyectoandroid.R;

import java.util.List;

public class AdaptadorTrabajos extends RecyclerView.Adapter<AdaptadorTrabajos.ViewHolder> {

    private Context contexto;
    private List<ObjTrabajo> lista;
    String tipo;

    public AdaptadorTrabajos(Context contexto, List<ObjTrabajo> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_trabajo,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ObjTrabajo objTrabajo=lista.get(position);

        holder.ttiempo.setText(objTrabajo.getHorasTrabajo());
        holder.tfechai.setText(objTrabajo.getFechaInicio());
        holder.tfechaf.setText(objTrabajo.getFechaFin());

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ttiempo, tfechai, tfechaf;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ttiempo = itemView.findViewById(R.id.ttiempo);
            tfechai = itemView.findViewById(R.id.tfechai);
            tfechaf = itemView.findViewById(R.id.tfechaf);
        }
    }

}
