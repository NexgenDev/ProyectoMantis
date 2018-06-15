package mantis.nexgen.com.proyectomantis;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

public class MenuInicioActivity extends AppCompatActivity implements View.OnClickListener{

    CardView card_fitosanitario,card_fumigacion,card_nutricion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicio);

        card_fitosanitario = findViewById(R.id.cardFitosanitario);
        card_fumigacion = findViewById(R.id.cardFumigacion);
        card_nutricion = findViewById(R.id.cardNutricion);

        card_fitosanitario.setOnClickListener(this);
        card_fumigacion.setOnClickListener(this);
        card_nutricion.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cardFitosanitario:
                Intent act_fito = new Intent(getApplicationContext(),FormFitosanitarioActivity.class);
                startActivity(act_fito);
                break;
            case R.id.cardFumigacion:
                Intent act_fumi = new Intent(getApplicationContext(),FormFumigacionActivity.class);
                startActivity(act_fumi);
                break;
            case R.id.cardNutricion:
                Intent act_nutri = new Intent(getApplicationContext(),FormNutricionActivity.class);
                startActivity(act_nutri);
                break;
        }
    }
}
