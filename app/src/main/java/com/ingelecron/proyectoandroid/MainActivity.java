package com.ingelecron.proyectoandroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.ingelecron.proyectoandroid.tiposDatos.GetDatos;
import com.ingelecron.proyectoandroid.tiposDatos.NuevoDato;
import com.ingelecron.proyectoandroid.tiposDatos.TipoCiudad;
import com.ingelecron.proyectoandroid.tiposDatos.TipoDepartamento;
import com.ingelecron.proyectoandroid.tiposDatos.TipoPais;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    public static DrawerLayout drawer;
    public static NavController navController;
    NavigationView navigationView;
    public static Toolbar toolbar;

    public static String actualFrame="frame";
    public static MenuItem mperfil, mmensajes, mayuda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        navigationView= findViewById(R.id.nav_view);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio,  R.id.nav_ventas, R.id.nav_ordenes, R.id.nav_clientes,
                R.id.nav_maquinas, R.id.nav_operador, R.id.nav_servicios, R.id.nav_perfil)
                .setDrawerLayout(drawer)
                .build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // getSupportActionBar().setDisplayShowHomeEnabled(false);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);


        //2. obtener las preferencias guardadas
        SharedPreferences preferencias=getSharedPreferences("login",MODE_PRIVATE);
        String usuario=preferencias.getString("usuario","");

        if(usuario.equals("")){

            navController.navigate(R.id.id_frame_login,null, null);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_ayuda) {
            NavOptions navOptions =new NavOptions.Builder()
                    .setEnterAnim(R.anim.nav_default_enter_anim)
                    .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                    .setExitAnim(R.anim.nav_default_exit_anim)
                    .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                    .build();

            navController.navigate(R.id.id_frame_ayuda,null, navOptions);

            return true;
        } else if (id == R.id.action_perfil) {

            NavOptions navOptions =new NavOptions.Builder()
                    .setEnterAnim(R.anim.nav_default_enter_anim)
                    .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                    .setExitAnim(R.anim.nav_default_exit_anim)
                    .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                    .build();

            navController.navigate(R.id.id_frame_perfil,null, navOptions);

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        Fragment fragment=getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);

        }
        if (actualFrame.equals("inicio") || actualFrame.equals("login")) {

            finish();

        } else if (actualFrame.equals("frameOPerador")||
                actualFrame.equals("frameMaquinas")||
                actualFrame.equals("frameClientes")||
                actualFrame.equals("frameOrdenes")||
                actualFrame.equals("frameVentas")||
                actualFrame.equals("frameIngresos")||
                actualFrame.equals("frameFacturas")) {

            NavOptions navOptions =new NavOptions.Builder()
                    .setEnterAnim(R.anim.nav_default_enter_anim)
                    .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                    .setExitAnim(R.anim.nav_default_exit_anim)
                    .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                    .build();

            navController.navigate(R.id.nav_inicio,null, navOptions);

        }else {
            super.onBackPressed();
        }

    }




    static public void sesion(Context context, String usuario, String tipo, String empresa, String nempresa){

        //1. guardar en preferencias: con el archivo "login" la variable "ingreso" con el valor "true"

        SharedPreferences preferencias=context.getSharedPreferences("login",context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("usuario", usuario);
        editor.putString("tipo", tipo);
        editor.putString("empresa", empresa);
        editor.putString("nempresa", nempresa);
        editor.commit();


    }
    static public String cedula(Context context){

        SharedPreferences preferences=context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String cedula=preferences.getString("usuario", "");

        return cedula;
    }

    static public String tipo(Context context){

        SharedPreferences preferences=context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String tipo=preferences.getString("tipo", "");

        return tipo;
    }


    static public String empresa(Context context){

        SharedPreferences preferences=context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String empresa=preferences.getString("empresa", "");

        return empresa;
    }

    static public String nempresa(Context context){

        SharedPreferences preferences=context.getSharedPreferences("login", Context.MODE_PRIVATE);
        String nempresa=preferences.getString("nempresa", "");

        return nempresa;
    }

    static public void bloqueo(){
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        toolbar.setNavigationIcon(null);

    }

    static public String lugarPais(JSONObject objecto){

        GetDatos dato;
        dato = NuevoDato.elemento();
        String lugar="";

        try {

            int pais=Integer.valueOf(objecto.getString("pais"));
            int estado=Integer.valueOf(objecto.getString("estado"));
            int ciudad=Integer.valueOf(objecto.getString("ciudad"));

            dato.setPais(TipoPais.values()[pais]);
            int departamentociudad;
            if (pais == 43) {
                departamentociudad =estado;
            } else if (pais == 0) {
                departamentociudad =0;
            } else {
                departamentociudad= 34;
            }
            if (pais == 43) {
                dato.setDepartamento(TipoDepartamento.values()[departamentociudad + 1]);
            } else {
                dato.setDepartamento(TipoDepartamento.values()[departamentociudad]);
            }
            if (pais == 43) {
                dato.setCiudad(TipoCiudad.values()[TipoCiudad.getValorCiudad(pais, departamentociudad + 1, ciudad)]);
            } else {
                dato.setCiudad(TipoCiudad.values()[TipoCiudad.getValorCiudad(pais, departamentociudad, ciudad)]);
            }

            lugar=dato.getPais().getTextoPais()+" - "+dato.getDepartamento().getTextoDepartamento()+" - "+dato.getCiudad().getTextoCiudad();

            return lugar;

        }catch (Exception e){
            Log.e("Error: ", e.getMessage());
        }

        return lugar;
    }


    static public void mensajeEntendido(Context contexto, String mensaje){

        final AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        builder.setMessage(mensaje)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }

                });
        builder.create();
        builder.show();

    }



}