package mantis.nexgen.com.proyectomantis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class CrearUsuarioActivity extends AppCompatActivity {

    EditText edt_nombre,edt_apellidos,edt_correo,edt_contrasena,edt_repiteContrasena,edt_telefono;
    Button btn_crearUsuario;
    int id;

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

        btn_crearUsuario.setEnabled(false);

        edt_repiteContrasena.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(s==edt_contrasena.getText()){
                        btn_crearUsuario.setEnabled(true);
                    }else{
                        btn_crearUsuario.setEnabled(false);
                    }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_telefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.toString().equals("")){
                    btn_crearUsuario.setEnabled(false);
                }else{
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

                String Nombre = edt_nombre.getText().toString();
                String Apellidos = edt_apellidos.getText().toString();
                String Correo = edt_correo.getText().toString();
                String Contrasena = edt_contrasena.getText().toString();
                String Telefono = edt_telefono.getText().toString();
                android.text.format.DateFormat df = new android.text.format.DateFormat();
                String FechaCreacion = df.format("yyyy-MM-dd hh:mm:ss a", new java.util.Date()).toString();
                Base_Datos_Interna crearusuario = new Base_Datos_Interna(getApplicationContext());
                boolean creado = crearusuario.CrearUsuario(Nombre,Apellidos,Contrasena,Telefono,Correo,FechaCreacion);
                if(creado){
                    Toast.makeText(getApplicationContext(),"Usuario creado - "+FechaCreacion,Toast.LENGTH_LONG).show();
                    Intent volverMain = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(volverMain);
                }else
                {
                    Toast.makeText(getApplicationContext(),"Usuario no pudo ser creado",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
