package com.ingelecron.proyectoandroid.ui.ordenes;

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
import com.ingelecron.proyectoandroid.tiposDatos.TipoEstadoProceso;

import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class AdaptadorOrden extends RecyclerView.Adapter<AdaptadorOrden.ViewHolder> {

    private Context contexto;
    private List<ObjOrden> lista;
    String tipo;

    public AdaptadorOrden(Context contexto, List<ObjOrden> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_orden,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ObjOrden objOrden=lista.get(position);

        String estado=objOrden.getProceso();

        int valorEstado= Integer.valueOf(estado);
        GetDatos datosEstado = NuevoDato.elemento();
        datosEstado.setTipoEstadoProceso(TipoEstadoProceso.values()[valorEstado]);
        String nombreEstado=datosEstado.getTipoEstadoProceso().getTextoEstadoProceso();

        final String id_orden=objOrden.getId();
        final String numero=objOrden.getNumero();
        final String servicio=objOrden.getServicio();
        final String proceso=objOrden.getProceso();

        holder.tnumero.setText(numero);
        holder.tfecha.setText(objOrden.getFechaCreado());
        holder.tservicio.setText(servicio);
        holder.tmaquina.setText(objOrden.getMaquina());
        holder.tcliente.setText(objOrden.getCliente());
        holder.tproceso.setText(nombreEstado);

        holder.rorden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                tipo=tipo(contexto);

                if (tipo.equals("1")){

                    AlertDialog.Builder menu = new AlertDialog.Builder(contexto);
                    final CharSequence[] opciones = {"Ver trabajos","Ver orden"};
                    menu.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int opcion) {

                            Bundle bundle=new Bundle();

                            switch (opcion) {

                                case 0:
                                    bundle.putString("id_orden", id_orden);
                                    bundle.putString("numero", numero);
                                    bundle.putString("servicio", servicio);
                                    bundle.putString("proceso", proceso);
                                    Navigation.findNavController(v).navigate(R.id.action_id_frame_ordenes_to_id_frame_ver_trabajos,bundle);
                                    break;
                                case 1:
                                    bundle.putString("id_orden", id_orden);
                                    Navigation.findNavController(v).navigate(R.id.action_id_frame_ordenes_to_id_frame_ver_orden,bundle);
                                    break;
                            }
                        }
                    });

                    menu.create().show();

                }else {

                    AlertDialog.Builder menu = new AlertDialog.Builder(contexto);
                    final CharSequence[] opciones = {"Ver trabajos","Ver orden","Editar orden","Eliminar orden"};
                    menu.setItems(opciones, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int opcion) {

                            Bundle bundle=new Bundle();

                            switch (opcion) {

                                case 0:
                                    bundle.putString("id_orden", id_orden);
                                    bundle.putString("numero", numero);
                                    bundle.putString("servicio", servicio);
                                    bundle.putString("proceso", proceso);
                                    Navigation.findNavController(v).navigate(R.id.action_nav_ordenes_to_id_frame_ver_trabajos,bundle);
                                    break;
                                case 1:
                                    bundle.putString("id_orden", id_orden);
                                    Navigation.findNavController(v).navigate(R.id.action_nav_ordenes_to_id_frame_ver_orden,bundle);
                                    break;
                                case 2:
                                    Toast.makeText(contexto, "editar", Toast.LENGTH_LONG).show();
                                    break;
                                case 3:
                                    Toast.makeText(contexto, "eliminar", Toast.LENGTH_LONG).show();
                                    break;
                            }
                        }
                    });

                    menu.create().show();
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout rorden;
        TextView tnumero, tfecha, tservicio, tmaquina, tcliente, tproceso;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rorden=itemView.findViewById(R.id.rorden);
            tnumero = itemView.findViewById(R.id.tnumero);
            tfecha = itemView.findViewById(R.id.tfecha);
            tservicio = itemView.findViewById(R.id.tservicio);
            tmaquina = itemView.findViewById(R.id.tmaquina);
            tcliente = itemView.findViewById(R.id.tcliente);
            tproceso = itemView.findViewById(R.id.tproceso);
        }
    }

}
