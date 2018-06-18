package mantis.nexgen.com.proyectomantis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FormFitosanitarioActivity extends AppCompatActivity {

    TextView txt_nombre,txt_fecha;
    String usuario;
    String Nombre,fecha_actual;
    Spinner spn_producto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_fitosanitario);

        txt_nombre = findViewById(R.id.txtNombre);
        txt_fecha = findViewById(R.id.txtFecha);

        spn_producto = findViewById(R.id.spn_producto);

        Intent IntentUsuario = getIntent();
        Bundle getUsuario = IntentUsuario.getExtras();
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

    }
}
