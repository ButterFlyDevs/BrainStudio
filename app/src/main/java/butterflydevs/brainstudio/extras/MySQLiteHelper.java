/*
        This program is free software: you can redistribute it and/or modify
        it under the terms of the GNU General Public License as published by
        the Free Software Foundation, either version 3 of the License, or
        (at your option) any later version.

        This program is distributed in the hope that it will be useful,
        but WITHOUT ANY WARRANTY; without even the implied warranty of
        MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
        GNU General Public License for more details.

        You should have received a copy of the GNU General Public License
        along with this program. If not, see <http://www.gnu.org/licenses/>.

        Copyright 2015 Jose A. Gonzalez Cervera
        Copyright 2015 Juan A. Fernández Sánchez
*/

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
 * Clase de apoyo para la gestión de la base de datos de la aplicación.
 *
 * Con esta clase abstraemos el funcionamiento interno de la base de datos de la aplicación. Ofreciendo
 * sencillos métodos para trabajar con ella e introducir objetos usados en la aplicación como Jugada
 * o Medalla para agilizar su uso y simplificar el código.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "BrainStudioDB";


    // ### Constantes de clase para el acceso seguro a la base de datos ### //

    //Nombre de la tabla
    private static final String J1N1="juego1nivel1";
    private static final String J1N2="juego1nivel2";
    private static final String J1N3="juego1nivel3";

    private static final String MEDALLAS="medallas";

    //Nombre de las columnas
    private static final String KEY_ID="id";
    private static final String KEY_PUNTUACION="puntuacion";
    private static final String KEY_PORCENTAJE="porcentaje";

    private static final String KEY_JUEGO="juego";
    private static final String KEY_NIVEL="nivel";


    private static final String[] COLUMNS ={KEY_ID, KEY_PUNTUACION, KEY_PORCENTAJE};


    public MySQLiteHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Creacion de las tablas.
     * Función con la que se crean todas las tablas necesarias que la aplicación necesita.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db){


        //Tabla de Juego 1 nivel 1

            //Especificación
            String CREATE_TABLE_JUEGO1_NIVEL1 = "CREATE TABLE juego1nivel1 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO1_NIVEL1);



        //Tabla de Juego 1 nivel 2

            //Especificación
            String CREATE_LEVEL_TABLE_JUEGO1_NIVEL2 = "CREATE TABLE juego1nivel2 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_LEVEL_TABLE_JUEGO1_NIVEL2);

        //Tabla de Juego 1 nivel 3

        //Especificación
        String CREATE_LEVEL_TABLE_JUEGO1_NIVEL3 = "CREATE TABLE juego1nivel3 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_LEVEL_TABLE_JUEGO1_NIVEL3);



        //Tabla de información de medallas

            //Especificación
            String CREATE_LEVEL_TABLE_MEDALLAS = "CREATE TABLE medallas ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "juego INTEGER, "+
                    "nivel INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_LEVEL_TABLE_MEDALLAS);



    }

    /**
     * FUnción de actualización de la base de datos.
     * Como se puede ver elimina las tablas de la base de datos para luego llamar a onCreate
     * para que las vuelva a crear, usando variables internas para esto del motor de la bd.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Elimina si exisitera alguna versión anterior
        db.execSQL("DROP TABLE IF EXISTS juego1nivel1");

        db.execSQL("DROP TABLE IF EXISTS juego1nivel2");

        db.execSQL("DROP TALBE IF EXISTS juego1nivel3");

        db.execSQL("DROP TABLE IF EXISTS medallas");

        //Crear la nueva tabla en la base de datos
        this.onCreate(db);
    }


    // ### METODOS DE USO DIRECTO ###


    /**
     * Añade una juaga a la tabla del nivel J1N1
     * @param jugada
     * @param nivel
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
            else if(nivel==3)
                db.insert(J1N3, null, values);



        //4. Cerrar la bd
        db.close();
    }


    /**
     * Añade una medalla a la tabla de medallas:
     * @param juego El juego al que corresponde
     * @param nivel El nivel al que corresponde
     */
    public void addMedalla(int juego, int nivel){

        //1. Referencia a la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        //2. Crear contenido
        ContentValues values = new ContentValues();
        values.put(KEY_JUEGO, juego);
        values.put(KEY_NIVEL, nivel);

        //3. Inserccion en la tabla
        db.insert(MEDALLAS, null, values);

        //4. Cerrar la bd
        db.close();
    }

    /**
     * Método necesario para la comprobación de medallas en la lista. La identificación
     * unívoca se realiza a partir del parámetro juego y nivel.
     * @param juego El juego de la medalla
     * @param nivel El nivel del juego
     * @return Si la medalla ha sido obtenida.
     */
    public boolean compruebaMedala(int juego, int nivel){

        boolean encontrada=false;


        //1º Obtenemos una referencia a la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        //2º Creamos una consulta que nos devuelva todos las entradas de la tabla
        String query = "SELECT * FROM "+ MEDALLAS;

        //3º Creamos un cursor al que le pasamos la consulta para manejar los resultados de esta.
        Cursor cursor = db.rawQuery(query,null);

        //4º Recorremos las filas
            if(cursor.moveToFirst()){

                do{
                    //Si la columna 1 (juego) coincide con el juego buscado y también el nivel se trata de esa medalla.
                    if(   Integer.parseInt(cursor.getString(1))==juego   &&   Integer.parseInt(cursor.getString(2))==nivel  )
                        encontrada=true;

                }while(cursor.moveToNext());
            }

        //Si se ha encontrado coincidencia se devolverá true y si nó se devolverá false.
        return encontrada;
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
        if(nivel==3)
            tabla="J1N3";



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
        if(nivel==3)
            tabla=J1N3;

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

    /**
     * Función que nos servirá para ver el estado de la base de datos,
     * Con ella podremos conocer el tamaño que tiene y como va creciendo para depuraciones.
     */
    public void detallesBD(){

        String query = "SELECT COUNT (*) FROM "+ J1N1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery(query, null);
        cursor.moveToFirst();
        int count= cursor.getInt(0);
        cursor.close();

        System.out.println("Número de elmentos en juego1nivel1: "+count);




        query = "SELECT COUNT (*) FROM "+ J1N2;


        cursor= db.rawQuery(query, null);
        cursor.moveToFirst();
        count= cursor.getInt(0);
        cursor.close();

        System.out.println("Número de elmentos en juego1nivel2: "+count);



        query = "SELECT COUNT (*) FROM "+ J1N3;


        cursor= db.rawQuery(query, null);
        cursor.moveToFirst();
        count= cursor.getInt(0);
        cursor.close();

        System.out.println("Número de elmentos en juego1nivel3: "+count);



        query = "SELECT COUNT (*) FROM medallas";


        cursor= db.rawQuery(query, null);
        cursor.moveToFirst();
        count= cursor.getInt(0);
        cursor.close();

        System.out.println("Número de elmentos en medallas: "+count);

    }


}

