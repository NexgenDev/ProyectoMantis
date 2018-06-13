package mantis.nexgen.com.proyectomantis;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Base_Datos_Interna extends SQLiteOpenHelper {
    static final String DB_NOMBRE = "dbinterna";
    static final int DB_VERSION = 1;
    static final String TABLA_USUARIO = "CREATE TABLE USUARIOS (NOMBRE TEXT,APELLIDOS TEXT,CORREO TEXT,TELEFONO TEXT UNIQUE,CONTRASENA TEXT,FECHACREACION TEXT)";
    public Base_Datos_Interna(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE USUARIOS");
        db.execSQL(TABLA_USUARIO);
    }
    public boolean CrearUsuario (String Nombre,String Apellidos,String Contrasena,String Telefono,String Correo,String FechaCreacion){
        boolean usuario_creado=false;
        SQLiteDatabase db = getWritableDatabase();
        if (db!=null){
            try{
                db.execSQL("INSERT INTO USUARIOS VALUES ('"+Nombre+"','"+Apellidos+"','"+Correo+"','"+Telefono+"','"+Contrasena+"','"+FechaCreacion+"');");
                usuario_creado=true;
            }catch(SQLException ex){
                usuario_creado=false;
            }
        }
        return usuario_creado;
    }
}
