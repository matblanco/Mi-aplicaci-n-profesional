package com.ingelecron.proyectoandroid.ui.clientes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ingelecron.proyectoandroid.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.ingelecron.proyectoandroid.MainActivity.lugarPais;

public class AdaptadorCliente extends RecyclerView.Adapter<AdaptadorCliente.ViewHolder> {

    private Context contexto;
    private List<ObjCliente> lista;

    public AdaptadorCliente(Context contexto, List<ObjCliente> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_cliente,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ObjCliente objCliente =lista.get(position);

        String condicion= objCliente.getCondicion();
        if(condicion.equals("1")){
            condicion="Activo";
        }else {
            condicion="Inactivo";
        }
        String lugar="";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("pais", objCliente.getPais());
            jsonObject.put("estado", objCliente.getEstado());
            jsonObject.put("ciudad", objCliente.getCiudad());
            lugar=lugarPais(jsonObject);
        }catch (JSONException e){
            Log.e("JSONERROR", e.toString());
        }


        holder.tnombre.setText(objCliente.getNombre());
        holder.tcondicion.setText(condicion);
        holder.tcelular.setText(objCliente.getCelular());
        holder.tcorreo.setText(objCliente.getCorreo());
        holder.tlugar.setText(lugar);

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tnombre,tcondicion, tcelular,tcorreo,tlugar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tnombre = itemView.findViewById(R.id.tnombre);
            tcondicion = itemView.findViewById(R.id.tcondicion);
            tcelular = itemView.findViewById(R.id.tcelular);
            tcorreo = itemView.findViewById(R.id.tcorreo);
            tlugar = itemView.findViewById(R.id.tlugar);
        }
    }

}
