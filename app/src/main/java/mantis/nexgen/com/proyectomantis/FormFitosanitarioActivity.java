package mantis.nexgen.com.proyectomantis;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class FormFitosanitarioActivity extends AppCompatActivity {

    TextView txt_nombre,txt_fecha;
    String usuario;
    String Nombre,fecha_actual;
    Spinner spn_producto,spn_variedad,spn_u1,spn_u2,spn_u3,spn_u4,spn_u5;
    Button btn_plagas,btn_enviar;
    ListView tabla_plagas;
    CheckBox checkBox;
    List<String> lista_enfermedades = new ArrayList<>();
    List<String> info_plagas = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fitosanitario);

        txt_nombre = findViewById(R.id.txtNombre);
        txt_fecha = findViewById(R.id.txtFecha);

        spn_producto =  findViewById(R.id.spnProducto);
        spn_variedad = findViewById(R.id.spnVariedad);
        spn_u1 = findViewById(R.id.spnU1);
        spn_u2 = findViewById(R.id.spnU2);
        spn_u3 = findViewById(R.id.spnU3);
        spn_u4 = findViewById(R.id.spnU4);
        spn_u5 = findViewById(R.id.spnU5);

        tabla_plagas = findViewById(R.id.tablaPlagas);

        btn_plagas = findViewById(R.id.btnPlagas);
        btn_enviar = findViewById(R.id.btnEnviarFito);

        Intent IntentUsuario = getIntent();
        final Bundle getUsuario = IntentUsuario.getExtras();
        if(getUsuario != null){
            usuario = getUsuario.getString("com.nexgen.mantis.usuario");
        }
        Base_Datos_Interna obtener_nombre = new Base_Datos_Interna(this);
        Nombre = obtener_nombre.ObtenerNombre(usuario);
        txt_nombre.setText(Nombre);

        SimpleDateFormat formato_fecha = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date fecha = new Date();
        fecha_actual = formato_fecha.format(fecha);
        txt_fecha.setText(fecha_actual);
        Inicializar(usuario);

        spn_producto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetVariedad(usuario,spn_producto.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_u1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetUbicacionHijo(usuario,spn_u1.getSelectedItem().toString(),"U1");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_u2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetUbicacionHijo(usuario,spn_u2.getSelectedItem().toString(),"U2");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_u3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetUbicacionHijo(usuario,spn_u3.getSelectedItem().toString(),"U3");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spn_u4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetUbicacionHijo(usuario,spn_u4.getSelectedItem().toString(),"U4");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_plagas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlagasDialog dialogPlagas = new PlagasDialog();
                Bundle datos_plaga = new Bundle();
                datos_plaga.putString("com.nexgen.mantis.usuario",usuario);
                dialogPlagas.setArguments(datos_plaga);
                dialogPlagas.show(getFragmentManager(),"Menu Plagas");
                info_plagas.clear();
            }
        });
        tabla_plagas.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        btn_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = txt_nombre.getText().toString();
                String fecha = txt_fecha.getText().toString();
                String producto = spn_producto.getSelectedItem().toString();
                String variedad = spn_variedad.getSelectedItem().toString();
                String U1 = spn_u1.getSelectedItem().toString();
                String U2 = spn_u2.getSelectedItem().toString();
                String U3 = spn_u3.getSelectedItem().toString();
                String U4 = spn_u4.getSelectedItem().toString();
                String U5 = spn_u5.getSelectedItem().toString();


            }
        });

    }
    public void Inicializar (final String usuario){
        String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/cliente_mantis.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] respuesta =  response.split("&");
                String[] producto = respuesta[0].split(",");
                String[] ubicacion_inicial = respuesta[3].split(",");
                final String[] enfermedades = respuesta[5].split(",");
                ArrayAdapter adpProducto = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,producto);
                adpProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_producto.setAdapter(adpProducto);
                ArrayAdapter adpUbicacionInicial = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,ubicacion_inicial);
                adpProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_u1.setAdapter(adpUbicacionInicial);
                ViewGroup checkboxContainer =findViewById(R.id.chksEnfermedad);
                for (int i = 0; i < enfermedades.length; i++) {
                    checkBox = new CheckBox(getApplicationContext());
                    checkBox.setText(enfermedades[i]);
                    checkboxContainer.addView(checkBox);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if(isChecked){
                                lista_enfermedades.add(buttonView.getText().toString());

                            }else{
                                lista_enfermedades.remove(lista_enfermedades.indexOf(buttonView.getText().toString()));
                            }
                        }
                    });
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected HashMap<String,String> getParams(){
                HashMap<String,String> params = new HashMap<>();
                params.put("id_usuario",usuario);
                params.put("contrasena","12345");
                params.put("id_formulario","F1");
                params.put("fecha_peticion","now");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void GetVariedad (final String usuario,final String producto){
        String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/cliente_mantis_variedad.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] variedad = response.split(",");
                ArrayAdapter adpVariedad = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,variedad);
                adpVariedad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_variedad.setAdapter(adpVariedad);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected HashMap<String,String> getParams(){
                HashMap<String,String> params = new HashMap<>();
                params.put("id_usuario",usuario);
                params.put("contrasena","12345");
                params.put("tipo_flor",producto);
                params.put("fecha_peticion","now");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void GetUbicacionHijo (final String usuario,final String valorPadre,final String padre){
        String URL ="http://stage.inteli-bpm.com/Portal/ws/soap/cliente_mantis_ubicacionhijos.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] valoresHijo = response.split(",");
                ArrayAdapter adpValoresHijo = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,valoresHijo);
                adpValoresHijo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                switch(padre){
                    case "U1":
                        spn_u2.setAdapter(adpValoresHijo);
                        break;
                    case "U2":
                        spn_u3.setAdapter(adpValoresHijo);
                        break;
                    case "U3":
                        spn_u4.setAdapter(adpValoresHijo);
                        break;
                    case "U4":
                        spn_u5.setAdapter(adpValoresHijo);
                        break;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected HashMap<String,String> getParams(){
                HashMap<String,String> params = new HashMap<>();
                params.put("id_usuario",usuario);
                params.put("contrasena","12345");
                params.put("valor_padre",valorPadre);
                params.put("fecha_peticion","now");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
