package mantis.nexgen.com.proyectomantis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

        btn_crearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
