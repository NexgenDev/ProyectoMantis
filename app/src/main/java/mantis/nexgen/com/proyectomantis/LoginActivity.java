package mantis.nexgen.com.proyectomantis;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

        txt_crearCuenta.setOnClickListener(this);
        btn_ingresar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnIngresar:
                DetectorConexion dc = new DetectorConexion(getApplicationContext());
                boolean conectado = dc.Conectado();
                if(conectado){
                    Base_Datos_Interna entrar = new Base_Datos_Interna(getApplicationContext());
                   boolean exito = entrar.Login(edt_usuario.getText().toString(),edt_contrasena.getText().toString());
                   if(exito){
                        ConsultasExternas ce = new ConsultasExternas(getApplicationContext());
                         ce.LoginExterno(edt_usuario.getText().toString());
                   }else{
                       Toast.makeText(getApplicationContext(),"Usuario y/o Contraseña erroneos", Toast.LENGTH_SHORT).show();
                   }
                }else{
                    Base_Datos_Interna entrar = new Base_Datos_Interna(getApplicationContext());
                    boolean exito = entrar.Login(edt_usuario.getText().toString(),edt_contrasena.getText().toString());
                    if(exito){
                        Intent ir_menuPrincipal = new Intent(getApplicationContext(),MenuInicioActivity.class);
                        startActivity(ir_menuPrincipal);
                        Toast.makeText(this, "Conectado offline", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"Usuario y/o Contraseña erroneos", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.txtCrearUsuario:
                Intent CrearUsuario = new Intent(getApplicationContext(),CrearUsuarioActivity.class);
                startActivity(CrearUsuario);
                break;
        }
    }
}
