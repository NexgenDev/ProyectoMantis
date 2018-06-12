package mantis.nexgen.com.proyectomantis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edt_usuario,edt_contrasena;
    Button btn_ingresar;
    TextView txt_crearCuenta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_usuario = findViewById(R.id.edtUsuario);
        edt_contrasena = findViewById(R.id.edtContrasena);
        btn_ingresar = findViewById(R.id.btnIngresar);
        txt_crearCuenta = findViewById(R.id.txtCrearUsuario);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnIngresar:
                break;
            case R.id.txtCrearUsuario:
                break;
        }
    }
}
