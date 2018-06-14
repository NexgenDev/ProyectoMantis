package mantis.nexgen.com.proyectomantis;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class DetectorConexion {

    Context context;

    public DetectorConexion(Context context) {
        this.context = context;
    }
    public boolean Conectado(){
        ConnectivityManager conectividad = (ConnectivityManager) context.getSystemService(Service.CONNECTIVITY_SERVICE);
        if(conectividad != null){
            NetworkInfo info = conectividad.getActiveNetworkInfo();
            if(info != null){
                return true;
            }
        }
        return false;
    }
}
