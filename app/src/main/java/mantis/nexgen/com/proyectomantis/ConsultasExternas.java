package mantis.nexgen.com.proyectomantis;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConsultasExternas{
    Context context;
    public ConsultasExternas(Context context) {
        this.context = context;
    }
    public void LoginExterno (final String usuario){
        String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/Login.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("1")){
                    Base_Datos_Interna actualizarEstado = new Base_Datos_Interna(context);
                    actualizarEstado.ActualizarEstadoLocal(usuario,1);
                    Intent ir_menu_principal = new Intent(context,MenuInicioActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ir_menu_principal.putExtra("com.nexgen.mantis.usuario",usuario);
                    context.startActivity(ir_menu_principal);
                }else
                {
                    Base_Datos_Interna actualizarEstado = new Base_Datos_Interna(context);
                    actualizarEstado.ActualizarEstadoLocal(usuario,0);
                    Toast.makeText(context,"Usuario no configurado por Administrador ",Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,"Error: "+error,Toast.LENGTH_SHORT).show();
            }
        }
        ){
            protected HashMap<String,String> getParams(){
                HashMap<String,String> params = new HashMap<>();
                params.put("usuario",usuario);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
    public void DatosFitosanitario (final String usuario, final String fecha, final String producto, final String variedad, final String U5, final List<String> plagas, final List<String> enfermedades){
       String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/cliente_fitosanitario.php";
       final String plagas_final= plagas.toString().replace("[","").replace("]","");
       final String enfermedades_final= enfermedades.toString().replace("[","").replace("]","");

       StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {
                Toast.makeText(context,response+"",Toast.LENGTH_SHORT).show();
           }
       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               Toast.makeText(context,"Error: "+error,Toast.LENGTH_SHORT).show();
           }
       }
       ){
           protected HashMap<String,String> getParams (){
               HashMap<String,String> params = new HashMap<>();
               params.put("id_usuario",usuario);
               params.put("fecha_monitoreo",fecha);
               params.put("id_formulario","F1");
               params.put("tipo_flor",producto);
               params.put("variedad_flor",variedad);
               params.put("hijo",U5);
               params.put("plagas",plagas_final);
               params.put("enfermedad",enfermedades_final);
               return params;
           }
       };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
