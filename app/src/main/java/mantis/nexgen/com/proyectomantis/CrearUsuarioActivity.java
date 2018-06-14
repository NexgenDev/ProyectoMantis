package mantis.nexgen.com.proyectomantis;

import android.content.Intent;
import android.net.LocalSocketAddress;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class CrearUsuarioActivity extends AppCompatActivity {

    EditText edt_nombre,edt_apellidos,edt_correo,edt_contrasena,edt_repiteContrasena,edt_telefono;
    Button btn_crearUsuario;
    String FechaCreacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_usuario);

        edt_nombre = findViewById(R.id.edtNombre);
        edt_apellidos = findViewById(R.id.edtApellidos);
        edt_correo = findViewById(R.id.edtCorreo);
        edt_telefono = findViewById(R.id.edtTelefono);
        edt_contrasena = findViewById(R.id.edtContrasena);
        edt_repiteContrasena = findViewById(R.id.edtcheckContrasena);

        btn_crearUsuario = findViewById(R.id.btnCrearusuario);


        btn_crearUsuario.setEnabled(false);

        edt_telefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString()==""){
                    btn_crearUsuario.setEnabled(false);
                }else
                {
                    btn_crearUsuario.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btn_crearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edt_contrasena.getText().toString().equals(edt_repiteContrasena.getText().toString()) ) {
                    boolean exito;
                    Date fecha_actual = new Date();
                    SimpleDateFormat formato_fecha = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
                    FechaCreacion = formato_fecha.format(fecha_actual);
                    Base_Datos_Interna crearusuario = new Base_Datos_Interna(getApplicationContext());
                    exito = crearusuario.CrearUsuario(edt_nombre.getText().toString(),edt_apellidos.getText().toString(),edt_contrasena.getText().toString(),edt_telefono.getText().toString(),edt_correo.getText().toString(), FechaCreacion);
                    if (exito) {
                        Toast.makeText(getApplicationContext(), "Usuario Creado Existosamente", Toast.LENGTH_SHORT).show();
                        Intent volver = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(volver);
                    } else {
                        Toast.makeText(getApplicationContext(), "Se presento un error al crear el usuario", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Las contraseñas no coinciden",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
