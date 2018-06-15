package mantis.nexgen.com.proyectomantis;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  Base_Datos_Interna extends SQLiteOpenHelper {
    private static final String DB_NOMBRE = "dbinterna";
    private static final int DB_VERSION = 2;
    private static final String TABLA_USUARIO = "CREATE TABLE USUARIOS (NOMBRE TEXT,APELLIDOS TEXT,CORREO TEXT,TELEFONO TEXT UNIQUE,CONTRASENA TEXT,FECHACREACION TEXT,ESTADO INTEGER DEFAULT 0)";
    public Base_Datos_Interna(Context context) {
        super(context, DB_NOMBRE, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(TABLA_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE USUARIOS");
        db.execSQL(TABLA_USUARIO);
    }
    public boolean CrearUsuario (String Nombre,String Apellidos,String Contrasena,String Telefono,String Correo,String FechaCreacion){
        boolean usuario_creado=false;
        SQLiteDatabase db = getWritableDatabase();
        if (db!=null){
            try{
                db.execSQL("INSERT INTO USUARIOS VALUES ('"+Nombre+"','"+Apellidos+"','"+Correo+"','"+Telefono+"','"+Contrasena+"','"+FechaCreacion+"',0);");
                usuario_creado=true;
            }catch(SQLException ex){
                usuario_creado=false;
            }
        }
        return usuario_creado;
    }
    public boolean Login (String usuario, String contrasena){
        boolean exito = false;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null){
            try{
            Cursor registro = db.rawQuery("SELECT 1 FROM USUARIOS WHERE TELEFONO = '"+usuario+"' AND CONTRASENA = '"+contrasena+"';",null);
            if(registro.moveToFirst()){
                exito = true;
            }else{
                exito = false;
            }
            }catch(SQLException ex){
                exito = false;
            }
        }
        return exito;
    }

    public void ActualizarEstadoLocal (String usuario,int estado){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            try {
                db.execSQL("UPDATE USUARIOS SET ESTADO = "+estado+"  WHERE TELEFONO = '" + usuario + "';");
            }catch(SQLException ex){ }
        }
    }
    public boolean LoginInterno(String usuario, String contrasena){
        boolean exito = false;
        SQLiteDatabase db = getReadableDatabase();
        if (db != null){
            try{
                Cursor registro = db.rawQuery("SELECT 1 FROM USUARIOS WHERE TELEFONO = '"+usuario+"' AND CONTRASENA = '"+contrasena+"' AND ESTADO ="+1+";",null);
                if(registro.moveToFirst()){
                    exito = true;
                }else{
                    exito = false;
                }
            }catch(SQLException ex){
                exito = false;
            }
        }
        return exito;
    }
}
