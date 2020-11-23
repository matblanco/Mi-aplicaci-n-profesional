package com.ingelecron.proyectoandroid.ui.operador;

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


public class AdaptadorOperador extends RecyclerView.Adapter<AdaptadorOperador.ViewHolder> {

    private Context contexto;
    private List<ObjOperador> lista;


    public AdaptadorOperador(Context contexto, List<ObjOperador> lista) {
        this.contexto = contexto;
        this.lista = lista;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(contexto).inflate(R.layout.item_lista_operador,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ObjOperador objOperador=lista.get(position);

        String condicion=objOperador.getCondicion();
        if(condicion.equals("1")){
            condicion="Activo";
        }else {
            condicion="Inactivo";
        }
        String lugar="";
        try {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("pais",objOperador.getPais());
            jsonObject.put("estado",objOperador.getEstado());
            jsonObject.put("ciudad",objOperador.getCiudad());
            lugar=lugarPais(jsonObject);
        }catch (JSONException e){
            Log.e("JSONERROR", e.toString());
        }


        holder.tnombre.setText(objOperador.getNombre());
        holder.tcondicion.setText(condicion);
        holder.tcelular.setText(objOperador.getCelular());
        holder.tcorreo.setText(objOperador.getCorreo());
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
