package mantis.nexgen.com.proyectomantis;

import android.content.Context;
import android.content.Intent;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class ConsultasExternas {
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
    public void Get_Producto (final Context context, final String usuario){
        String URL = "http://stage.inteli-bpm.com/Portal/ws/soap/cliente_mantis.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String[] respuesta =  response.split("&");
                String[] producto = respuesta[0].split(",");
                ArrayAdapter adpProducto = new ArrayAdapter(context,android.R.layout.simple_spinner_dropdown_item,producto);
                adpProducto.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
                params.put("id_usuario",usuario);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
