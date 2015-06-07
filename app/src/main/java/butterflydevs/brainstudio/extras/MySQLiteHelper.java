package butterflydevs.brainstudio.extras;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * Ahora mismo solo va a trabajar con una sola tabla de prueba.
 *
 * Created by juan on 7/06/15.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "BrainStudioDB";

    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creacion de las tablas
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){


        //Tabla de Juego 1 nivel 1
            String CREATE_TABLE_JUEGO1_NIVEL1 = "CREATE TABLE juego1nivel1 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //creación de la tabla
            db.execSQL(CREATE_TABLE_JUEGO1_NIVEL1);

        //Tabla de Juego 2 nivel 2
            String CREATE_LEVEL_TABLE_JUEGO1_NIVEL2 = "CREATE TABLE juego1nivel2 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //creación de la tabla
            db.execSQL(CREATE_LEVEL_TABLE_JUEGO1_NIVEL2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Elimina si exisitera alguna versión anterior
        db.execSQL("DROP TABLE IF EXISTS juego1nivel1");
        db.execSQL("DROP TABLE IF EXISTS juego1nivel2");
        //Crear la nueva tabla en la base de datos
        this.onCreate(db);
    }

    // ### OPERACIONES ### //

    //Constantes de clase para el acceso seguro a la base de datos:

    //Nombre de la tabla
    private static final String J1N1="juego1nivel1";
    private static final String J1N2="juego1nivel2";

    //Nombre de las columnas
    private static final String KEY_ID="id";
    private static final String KEY_PUNTUACION="puntuacion";
    private static final String KEY_PORCENTAJE="porcentaje";

    private static final String[] COLUMNS ={KEY_ID, KEY_PUNTUACION, KEY_PORCENTAJE};

    /**
     * Añade una juaga a la tabla del nivel J1N1
     * @param jugada
     */
    public void addJugada(Jugada jugada, int nivel){

        //Para depuracion
        System.out.println("AñadiendoJugada " + jugada.toString());

        //1. Referencia a la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        //2. Crear contenido
        ContentValues values = new ContentValues();
        values.put(KEY_PUNTUACION, jugada.getPuntuacion());
        values.put(KEY_PORCENTAJE, jugada.getPorcentaje());


        //3. Inserccion en la tabla que corresponda

            if(nivel==1)
                db.insert(J1N1, null, values);
            else if(nivel==2)
                db.insert(J1N2, null, values);



        //4. Cerrar la bd
        db.close();
    }

    /**
     * Para obtener un objeto jugada dando su id (esto podría cambiarse por la fecha o por un rango de fechas o por cualquier otra cosa)
     * @param id Identificador de la jugada
     * @return Un objeto de tipo Juagada
     */
    public Jugada getJugada(int id, int nivel){
        //Referencia a la base de datos;
        SQLiteDatabase db = this.getReadableDatabase();

        String tabla="";
        if(nivel==1)
            tabla="J1N1";
        if(nivel==2)
            tabla="J1N2";



            //Construimos petición
            Cursor cursor =
                    db.query(tabla, // a table
                            COLUMNS, // b columns names
                            " id = ?", //c selections
                            new String[]{String.valueOf(id)}, //d. selections
                            null, //e. group by
                            null, //f. having
                            null, //g. order by
                            null); //h. limit


            if(cursor!=null)
                cursor.moveToFirst();

            //Construimos el objeto a devolver
            Jugada jugada = new Jugada(
                    Integer.parseInt(cursor.getString(1)), //Puntuacion
                    Integer.parseInt(cursor.getString(2)) //Porcentaje
            );

            Log.d("getJugada("+id+")",jugada.toString());





        //Devolvemos la jugada de la base de datos.
        return jugada;
    }

    /**
     * Rescata todas las jugadas de la tabla J1N1 por ahora, después podría
     * pasarsele algún parámetro para cambiar de tabla.
     *
     * @return Una lista con las jugadas rescatadas de la base de datos
     */
    public List<Jugada> getAllJugadas(int nivel){

        //Creamos la lista de jugadas.
        List<Jugada>jugadas = new LinkedList<Jugada>();

        //Construimos la consulta (esto cambiará cuando tenamos más tablas)

        String tabla="";

        if(nivel==1)
            tabla=J1N1;
        if(nivel==2)
            tabla=J1N2;

        String query = "SELECT * FROM "+ tabla;


        //Obtenemos una referencia a la base de datos
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);



        //Recorremos el resultados y rellenamos la lista que la función devuelve:
        Jugada jugada = null;
        if(cursor.moveToFirst()){
            do{
                jugada=new Jugada(
                   Integer.parseInt(cursor.getString(1)),
                   Integer.parseInt(cursor.getString(2))
                );
                System.out.println("datooo"+cursor.getString(1));

                //Sacada la jugada la añadimos a la lista
                jugadas.add(jugada);

            }while(cursor.moveToNext());
        }

        Log.d("getAllJugadas", jugadas.toString());

        //Devolvemos la lista que hemos construido
        return jugadas;
    }



}

