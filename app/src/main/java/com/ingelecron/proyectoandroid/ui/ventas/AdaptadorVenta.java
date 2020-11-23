package com.ingelecron.proyectoandroid.ui.ventas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.tiposDatos.GetDatos;
import com.ingelecron.proyectoandroid.tiposDatos.NuevoDato;
import com.ingelecron.proyectoandroid.tiposDatos.TipoEstadoProceso;

import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.tipo;


public class AdaptadorVenta extends RecyclerView.Adapter<AdaptadorVenta.ViewHolder> {

    private Context contexto;
    private List<ObjVenta> lista;
    String tipo;

    public AdaptadorVenta(Context contexto, List<ObjVenta> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_venta,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ObjVenta objVenta=lista.get(position);

        final String estado=objVenta.getProceso();

        int valorEstado= Integer.valueOf(estado);
        GetDatos datosEstado = NuevoDato.elemento();
        datosEstado.setTipoEstadoProceso(TipoEstadoProceso.values()[valorEstado]);
        final String nombreEstado=datosEstado.getTipoEstadoProceso().getTextoEstadoProceso();

        final String id_orden=objVenta.getIdorden();
        final String numero=objVenta.getNumero();
        final String formaPago=objVenta.getFormapago();
        final String fechaCreado=objVenta.getFechaCreado();
        final String servicio=objVenta.getServicio();

        holder.tnumero.setText(numero);
        holder.tfecha.setText(fechaCreado);
        holder.tservicio.setText(servicio);
        holder.tvalor.setText(objVenta.getValorventa());
        holder.tcliente.setText(objVenta.getCliente());
        holder.tproceso.setText(nombreEstado);

        holder.rventa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlertDialog.Builder menu = new AlertDialog.Builder(contexto);
                final CharSequence[] opciones = {"Ver ventas","Ver factura"};
                menu.setItems(opciones, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int opcion) {
                        Bundle bundle=new Bundle();
                        tipo=tipo(contexto);

                        switch (opcion) {

                            case 0:

                                bundle.putString("id_orden", id_orden);
                                bundle.putString("numero", numero);
                                bundle.putString("formaPago", formaPago);
                                bundle.putString("fechaCreado", fechaCreado);
                                bundle.putString("proceso", nombreEstado);
                                bundle.putString("estado", estado);

                                if (tipo.equals("1")) {

                                    Navigation.findNavController(v).navigate(R.id.action_id_frame_ventas_to_id_frame_ver_ventas, bundle);

                                } else {

                                    Navigation.findNavController(v).navigate(R.id.action_nav_ventas_to_id_frame_ver_ventas, bundle);
                                }  break;
                            case 1:

                                bundle.putString("id_orden", id_orden);

                                if (tipo.equals("1")) {

                                    Navigation.findNavController(v).navigate(R.id.action_id_frame_ventas_to_id_frame_ver_facturas, bundle);

                                } else {

                                    Navigation.findNavController(v).navigate(R.id.action_nav_ventas_to_id_frame_ver_facturas, bundle);

                                }
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

        RelativeLayout rventa;
        TextView tnumero, tfecha, tservicio, tvalor, tcliente, tproceso;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rventa=itemView.findViewById(R.id.rventa);
            tnumero = itemView.findViewById(R.id.tnumero);
            tfecha = itemView.findViewById(R.id.tfecha);
            tservicio = itemView.findViewById(R.id.tservicio);
            tvalor = itemView.findViewById(R.id.tvalor);
            tcliente = itemView.findViewById(R.id.tcliente);
            tproceso = itemView.findViewById(R.id.tproceso);
        }
    }

}
