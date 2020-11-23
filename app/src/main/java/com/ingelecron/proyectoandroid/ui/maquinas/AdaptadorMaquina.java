package com.ingelecron.proyectoandroid.ui.maquinas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.tiposDatos.GetDatos;
import com.ingelecron.proyectoandroid.tiposDatos.NuevoDato;
import com.ingelecron.proyectoandroid.tiposDatos.TipoEstadoMaquina;

import java.util.List;

public class AdaptadorMaquina extends RecyclerView.Adapter<AdaptadorMaquina.ViewHolder> {

    private Context contexto;
    private List<ObjMaquina> lista;


    public AdaptadorMaquina(Context contexto, List<ObjMaquina> lista) {
        this.contexto = contexto;
        this.lista = lista;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_maquina,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ObjMaquina objMaquina=lista.get(position);

        String estado=objMaquina.getEstado();

        int valorEstado= Integer.valueOf(estado);
        GetDatos datosEstado = NuevoDato.elemento();
        datosEstado.setTipoEstadoMaquina(TipoEstadoMaquina.values()[valorEstado]);
        String nombreEstado=datosEstado.getTipoEstadoMaquina().getTextoEstadoMaquina();

        final String id_maquina=objMaquina.getId();
        holder.tnombre.setText(objMaquina.getNombre());
        holder.tmarca.setText(objMaquina.getMarca());
        holder.tmodelo.setText(objMaquina.getModelo());
        holder.tdescripcion.setText(objMaquina.getDescripcion());
        holder.testado.setText(nombreEstado);

        holder.rmaquina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder menu = new AlertDialog.Builder(contexto);
                final CharSequence[] opciones = {"Ver máquina","Editar máquina","Eliminar máquina"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {

                        switch (opcion) {

                            case 0:
                                Bundle bundle=new Bundle();
                                bundle.putString("id_maquina", id_maquina);
                                Navigation.findNavController(v).navigate(R.id.action_nav_maquinas_to_id_frame_ver_maquina,bundle);
                                break;
                            case 1:
                                Toast.makeText(contexto, "editar", Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                Toast.makeText(contexto, "eliminar", Toast.LENGTH_LONG).show();
                                break;
                        }
                    }
                });

                menu.create().show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rmaquina;
        TextView tnombre,tmarca, tmodelo,tdescripcion,testado;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rmaquina=itemView.findViewById(R.id.rmaquina);
            tnombre = itemView.findViewById(R.id.tnombre);
            tmarca = itemView.findViewById(R.id.tmarca);
            tmodelo = itemView.findViewById(R.id.tmodelo);
            tdescripcion = itemView.findViewById(R.id.tdescripcion);
            testado= itemView.findViewById(R.id.testado);
        }
    }

}
