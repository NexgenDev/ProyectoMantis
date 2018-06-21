package mantis.nexgen.com.proyectomantis;


import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorPlagas extends ArrayAdapter<ModeloPlagas>{

    private Context mContext;
    private List<ModeloPlagas> listaPlagas = new ArrayList<>();

    public AdaptadorPlagas(@NonNull Context context, ArrayList<ModeloPlagas> list ){
        super(context,0,list);
        mContext=context;
        listaPlagas = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.lista_plagas,parent,false);

        ModeloPlagas plagaActual = listaPlagas.get(position);

        TextView plaga = listItem.findViewById(R.id.plaga_nombre);
        plaga.setText(plagaActual.getPlaga());

        TextView estado = listItem.findViewById(R.id.plaga_estado);
        estado.setText(plagaActual.getEstado());

        TextView intensidad = listItem.findViewById(R.id.plaga_intensidad);
        intensidad.setText(plagaActual.getIntensidad());

        return listItem;
    }

}
