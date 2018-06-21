package mantis.nexgen.com.proyectomantis;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PlagasDialog extends DialogFragment {

    private Spinner spn_plaga,spn_intensidad,spn_estado;
    Button btn_agregar,btn_cancelar;
    String usuario;
    //ArrayAdapter<String> adapter;
    AdaptadorPlagas adaptador;
    ArrayList<ModeloPlagas> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_plagas,container,false);
        spn_plaga = view.findViewById(R.id.spnPlagas);
        spn_intensidad = view.findViewById(R.id.spnIntensidad);
        spn_estado = view.findViewById(R.id.spnEstado);
        btn_agregar = view.findViewById(R.id.btnAgregarPlaga);
        btn_cancelar = view.findViewById(R.id.btnCancelarPlaga);


        Bundle tomar_datos = getArguments();
        usuario = tomar_datos.getString("com.nexgen.mantis.usuario");
        GetPlagas(usuario);

        spn_plaga.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                GetEstado(usuario,spn_plaga.getSelectedItem().toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        arrayList = new ArrayList<>();
        adaptador = new AdaptadorPlagas(getActivity().getApplicationContext(),arrayList);
        ((FormFitosanitarioActivity)getActivity()).tabla_plagas.setAdapter(adaptador);
        arrayList.add(new ModeloPlagas("Plaga","Estado","Intensidad"));
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String plaga = spn_plaga.getSelectedItem().toString();
            String intensidad = spn_intensidad.getSelectedItem().toString();
            String estado = spn_estado.getSelectedItem().toString();
            arrayList.add(new ModeloPlagas(plaga,estado,intensidad));
            adaptador.notifyDataSetChanged();
            }
        });
        return view;
    }
    public void GetPlagas (final String usuario){
        String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/cliente_mantis.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] respuesta =  response.split("&");
                String[] plagas = respuesta[4].split(",");
                String[] intensidad = respuesta[6].split(",");
                ArrayAdapter adpProducto = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,plagas);
                adpProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_plaga.setAdapter(adpProducto);
                ArrayAdapter adpIntensidad = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,intensidad);
                adpIntensidad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_intensidad.setAdapter(adpIntensidad);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"Error: "+error,Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
    public void GetEstado (final String usuario,final String plaga){
        String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/cliente_mantis_estadoplaga.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] estados = response.split(",");
                ArrayAdapter adpEstados = new ArrayAdapter(getActivity().getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,estados);
                adpEstados.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spn_estado.setAdapter(adpEstados);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(),"Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected HashMap<String,String> getParams(){
                HashMap<String,String> params = new HashMap<>();
                params.put("id_usuario",usuario);
                params.put("contrasena","12345");
                params.put("plaga",plaga);
                params.put("fecha_peticion","now");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        requestQueue.add(stringRequest);
    }
}
