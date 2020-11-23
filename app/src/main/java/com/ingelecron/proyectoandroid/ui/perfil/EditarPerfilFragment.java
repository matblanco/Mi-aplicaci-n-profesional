package com.ingelecron.proyectoandroid.ui.perfil;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcel;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.ingelecron.proyectoandroid.AlmacenDatos;
import com.ingelecron.proyectoandroid.BDPHP;
import com.ingelecron.proyectoandroid.MainActivity;
import com.ingelecron.proyectoandroid.R;
import com.ingelecron.proyectoandroid.RespuestasPHP;
import com.ingelecron.proyectoandroid.tiposDatos.TipoCiudad;
import com.ingelecron.proyectoandroid.tiposDatos.TipoDepartamento;
import com.ingelecron.proyectoandroid.tiposDatos.TipoPais;

import java.util.Calendar;
import java.util.Date;

import static com.ingelecron.proyectoandroid.MainActivity.mensajeEntendido;


public class EditarPerfilFragment extends Fragment {

    private EditarPerfilViewModel mViewModel;

   View root;

    LinearLayout lfechacumple;

    RadioGroup rgrupo;
    RadioButton rb1,rb2;
    TextInputEditText tiepnombre, tiesnombre, tiepapellido, tiesapellido, tiecelular, tiedireccion, tieemail, tiefechan;
    TextInputLayout tilpnombre, tilsnombre, tilpapellido, tilsapellido, tilfechan;
    Button bguardar;
    LinearLayout lgenero;
    private Spinner spais, sestado, sciudad;

    private int dian2=0, mesn2=0, añon2=0, genero2=0, añoa2=0, edad2=0, pais2 = 0, estado2 = 0, ciudad2 = 0, parg2 = 0;

    ArrayAdapter<String> adaptadorp, adaptadord, adaptadorc;

    String tipo="", cedula="", direccion="",pnombre="",snombre="",papellido="",sapellido="",pais="",estado="",ciudad="",fechan="", genero="",correo="",celular="";


    Bundle bundle;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        root=inflater.inflate(R.layout.frame_editar_perfil, container, false);

        tipo= MainActivity.tipo(getActivity());
        cedula=MainActivity.cedula(getActivity());

        tiepnombre=root.findViewById(R.id.tiepnombre);
        tiesnombre=root.findViewById(R.id.tiesnombre);
        tiepapellido=root.findViewById(R.id.tiepapellido);
        tiesapellido=root.findViewById(R.id.tiesapellido);
        tiecelular=root.findViewById(R.id.tiecelular);
        tiedireccion=root.findViewById(R.id.tiedireccion);
        tieemail=root.findViewById(R.id.tieemail);
        tiefechan=root.findViewById(R.id.tiefechan);

        tilpnombre=root.findViewById(R.id.tilpnombre);
        tilsnombre=root.findViewById(R.id.tilsnombre);
        tilpapellido=root.findViewById(R.id.tilpapellido);
        tilsapellido=root.findViewById(R.id.tilsapellido);
        tilfechan=root.findViewById(R.id.tilfechan);

        lgenero=root.findViewById(R.id.lgenero);

        rgrupo =  root.findViewById(R.id.rgrupo);
        rb1 =  root.findViewById(R.id.rb1);
        rb2 =  root.findViewById(R.id.rb2);


        bundle=getArguments();
        if(bundle!=null) {
            if(tipo.equals("1")){
                pnombre= getArguments().getString("pnombre");
                snombre= getArguments().getString("snombre");
                papellido= getArguments().getString("papellido");
                sapellido= getArguments().getString("sapellido");
                correo= getArguments().getString("correo");
                celular= getArguments().getString("celular");
                direccion= getArguments().getString("direccion");
                fechan= getArguments().getString("fechan");
                genero2= getArguments().getInt("genero");
                pais2= getArguments().getInt("pais");
                estado2= getArguments().getInt("estado");
                ciudad2= getArguments().getInt("ciudad");

            }else {
                pnombre= getArguments().getString("nombre");
                correo= getArguments().getString("correo");
                celular= getArguments().getString("celular");
                direccion= getArguments().getString("direccion");
                pais2= getArguments().getInt("pais");
                estado2= getArguments().getInt("estado");
                ciudad2= getArguments().getInt("ciudad");
            }

        }
        if (tipo.equals("2")){
            tilpnombre.setHint("Nombre");
            tilsnombre.setVisibility(TextView.GONE);
            tilpapellido.setVisibility(TextView.GONE);
            tilsapellido.setVisibility(TextView.GONE);
            lgenero.setVisibility(RelativeLayout.GONE);
            tilfechan.setVisibility(RelativeLayout.GONE);
        }

        tiepnombre.setText(pnombre);
        tiesnombre.setText(snombre);
        tiepapellido.setText(papellido);
        tiesapellido.setText(sapellido);
        tiecelular.setText(celular);
        tiedireccion.setText(direccion);
        tieemail.setText(correo);
        tiefechan.setText(fechan);

        spais =  root.findViewById(R.id.spais);
        adaptadorp = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoPais.getDatosPais());
        adaptadorp.setDropDownViewResource(R.layout.vista_texto_spinner);
        spais.setAdapter(adaptadorp);
        spais.setSelection(pais2);
        spais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                if (arg2!=pais2){
                    estado2=0;
                    ciudad2=0;
                }

                parg2 = arg2;
                sestado = root.findViewById(R.id.sestado);
                sciudad =  root.findViewById(R.id.sciudad);

                adaptadord = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoDepartamento.getDatosDepartamento(arg2));
                adaptadord.setDropDownViewResource(R.layout.vista_texto_spinner);
                sestado.setAdapter(adaptadord);
                sestado.setSelection(estado2);
                sestado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                        if (arg2!=estado2){
                            ciudad2=0;
                        }

                        adaptadorc = new ArrayAdapter<String>(getActivity(), R.layout.vista_texto_spinner, TipoCiudad.getDatosCiudad(arg2, parg2));
                        adaptadorc.setDropDownViewResource(R.layout.vista_texto_spinner);
                        sciudad.setAdapter(adaptadorc);
                        sciudad.setSelection(ciudad2);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });


                adaptadorp.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ((RadioButton)rgrupo.getChildAt(genero2)).setChecked(true);
        rb1 =root.findViewById(R.id.rb1);
        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genero2 = 0;
            }
        });

        rb2 = root.findViewById(R.id.rb2);
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                genero2 = 1;
            }
        });


        tiefechan.setInputType(InputType.TYPE_NULL);
        tiefechan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

                ocultarTeclado(getActivity(), view);

                if (b) {
                    pickerFecha(tiefechan);
                }
            }
        });
        /*
        lfechacumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendario = Calendar.getInstance();
                calendario.add(Calendar.YEAR, -18);
                long maxDate = calendario.getTime().getTime();
                int año = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener listenerDeDatePicker=new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        String dian= String.valueOf(dayOfMonth);
                        String mesn= String.valueOf(month+1);
                        String añon= String.valueOf(year);

                        fechan=dian+"/"+mesn+"/"+añon;
                        tiefechan.setText(fechan);
                    }
                };

                DatePickerDialog datePickerDialog= new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT,listenerDeDatePicker, año, mes, dia);
                datePickerDialog.getDatePicker().setMaxDate(maxDate);
                datePickerDialog.show();

            }
        });
        */

        bguardar=root.findViewById(R.id.bguardar);
        bguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                AlmacenDatos almacenDatos=new BDPHP(getActivity());

                pnombre=tiepnombre.getText().toString();
                snombre=tiesnombre.getText().toString();
                papellido=tiepapellido.getText().toString();
                sapellido=tiesapellido.getText().toString();
                correo=tieemail.getText().toString();
                celular=tiecelular.getText().toString();
                direccion=tiedireccion.getText().toString();
                fechan=tiefechan.getText().toString();

                pais2 = spais.getSelectedItemPosition();
                if (pais2 == 43) {
                    estado2 = sestado.getSelectedItemPosition();
                } else if (pais2 == 0) {
                    estado2 = 0;
                } else {
                    estado2 = 0;
                }
                ciudad2 = sciudad.getSelectedItemPosition();
                pais= String.valueOf(pais2);
                estado= String.valueOf(estado2);
                ciudad= String.valueOf(ciudad2);

                genero= String.valueOf(genero2);

                if (tipo.equals("1")){

                    almacenDatos.editarOperador(cedula, pnombre, snombre, papellido, sapellido, correo, celular, direccion, fechan, genero, pais, estado, ciudad, new RespuestasPHP() {
                        @Override
                        public void leerRespuesta(String respuesta) {

                            if (respuesta.equals("1")){
                                mensajeEntendido(getActivity(), "¡Información actualizada!");
                                getActivity().onBackPressed();
                            }else {
                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar registrarse.\n" +
                                        "Por favor intentelo nuevamente");
                            }
                        }
                    });

                }else {
                    almacenDatos.editarEmpresa(cedula, pnombre, correo, celular, direccion, pais, estado, ciudad, new RespuestasPHP() {
                        @Override
                        public void leerRespuesta(String respuesta) {

                            if (respuesta.equals("1")){
                                mensajeEntendido(getActivity(), "¡Información actualzada!");
                                getActivity().onBackPressed();
                            }else {
                                mensajeEntendido(getActivity(), "Se ha producido un error al intentar registrarse.\n" +
                                        "Por favor intentelo nuevamente");
                            }
                        }
                    });
                }



            }
        });

        return root;

    }

    public void pickerFecha(final TextInputEditText textInputEditText){

        Date hoy = new Date();
        Calendar calendario= Calendar.getInstance();
        calendario.setTime(hoy);
        calendario.add(Calendar.YEAR, -18);
        long fechaActual=calendario.getTimeInMillis();

        MaterialDatePicker.Builder<Long> builder= MaterialDatePicker.Builder.datePicker();
        builder.setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR);
        builder.setTitleText("Fecha de naicimiento");
        builder.setCalendarConstraints(limiteRango().build());
        builder.setSelection(fechaActual);

        final MaterialDatePicker<Long> picker = builder.build();
        picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override
            public void onPositiveButtonClick(Long selection) {
                textInputEditText.setText(picker.getHeaderText());
                tiefechan.clearFocus();
            }
        });
        picker.addOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                tiefechan.clearFocus();
            }
        });
        picker.show(getActivity().getSupportFragmentManager(), picker.toString());

    }

    private CalendarConstraints.Builder limiteRango(){

        Date hoy = new Date();
        Calendar inicioCalendario= Calendar.getInstance();
        inicioCalendario.setTime(hoy);
        inicioCalendario.add(Calendar.YEAR, -100);
        long inicioFecha=inicioCalendario.getTimeInMillis();

        Calendar finalCalendario= Calendar.getInstance();
        finalCalendario.setTime(hoy);
        finalCalendario.add(Calendar.YEAR, -18);
        long finalFecha=finalCalendario.getTimeInMillis();

        CalendarConstraints.Builder builder= new CalendarConstraints.Builder();
        builder.setStart(inicioFecha);
        builder.setEnd(finalFecha);
        builder.setOpenAt(finalFecha);
        builder.setValidator(new RangeValidator(inicioFecha, finalFecha));


        return builder;
    }

    static class RangeValidator implements CalendarConstraints.DateValidator {

        long minDate, maxDate;

        RangeValidator(long minDate, long maxDate) {
            this.minDate = minDate;
            this.maxDate = maxDate;
        }

        RangeValidator(Parcel parcel) {
            minDate = parcel.readLong();
            maxDate = parcel.readLong();
        }

        @Override
        public boolean isValid(long date) {
            return !(minDate > date || maxDate < date);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(minDate);
            dest.writeLong(maxDate);
        }

        public static final Creator<RangeValidator> CREATOR = new Creator<RangeValidator>() {

            @Override
            public RangeValidator createFromParcel(Parcel parcel) {
                return new RangeValidator(parcel);
            }

            @Override
            public RangeValidator[] newArray(int size) {
                return new RangeValidator[size];
            }
        };

    }


    public static void ocultarTeclado(Context context, View view){

        InputMethodManager imm=(InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

}


   /*
        lfechacumple =root.findViewById(R.id.lfechacumple);
        lfechacumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date hoy = new Date();
                Calendar calendario = Calendar.getInstance();
                calendario.setTime(hoy);
                calendario.add(Calendar.YEAR, -18);
                long maxDate = calendario.getTime().getTime();
                int año = calendario.get(Calendar.YEAR);
                int mes = calendario.get(Calendar.MONTH);
                int dia = calendario.get(Calendar.DAY_OF_MONTH);

                final Dialog dialogf = new Dialog(getActivity());
                dialogf.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialogf.setContentView(R.layout.vista_calendario);
                dialogf.setCanceledOnTouchOutside(false);

                final DatePicker datepicker= dialogf.findViewById(R.id.datepicker);
                datepicker.setMaxDate(maxDate);
                datepicker.init(año, mes, dia, null);
                Button blisto = dialogf.findViewById(R.id.blisto);
                blisto.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        dian2= datepicker.getDayOfMonth();
                        mesn2= datepicker.getMonth()+1;
                        añon2= datepicker.getYear();

                        fechan=dian2+"/"+mesn2+"/"+añon2;
                        tfechan.setText(dian2+"/"+mesn2+"/"+añon2);

                        dialogf.dismiss();

                    }
                });
                dialogf.show();


            }
        });
*/
