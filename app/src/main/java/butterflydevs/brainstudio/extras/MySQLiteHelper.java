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
import android.widget.Toast;

import java.util.ArrayList;
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

    private static final String J2N1="juego2nivel1";
    private static final String J2N2="juego2nivel2";
    private static final String J2N3="juego2nivel3";

    private static final String J3N1="juego3nivel1";
    private static final String J3N2="juego3nivel2";
    private static final String J3N3="juego3nivel3";

    private static final String J4N1="juego4nivel1";
    private static final String J4N2="juego4nivel2";
    private static final String J4N3="juego4nivel3";

    private static final String J5N1="juego5nivel1";
    private static final String J5N2="juego5nivel2";
    private static final String J5N3="juego5nivel3";

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


        // ### JUEGO 1 ### //

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


        // ### JUEGO 2 ### //

        //Tabla de Juego 2 nivel 1

            //Especificación
            String CREATE_TABLE_JUEGO2_NIVEL1 = "CREATE TABLE juego2nivel1 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO2_NIVEL1);


        //Tabla de Juego 2 nivel 2

            //Especificación
            String CREATE_TABLE_JUEGO2_NIVEL2 = "CREATE TABLE juego2nivel2 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO2_NIVEL2);


        //Tabla de Juego 2 nivel 3

            //Especificación
            String CREATE_TABLE_JUEGO2_NIVEL3 = "CREATE TABLE juego2nivel3 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO2_NIVEL3);


        // ### JUEGO 3 ### //

        //Tabla de Juego 3 nivel 1

        //Especificación
        String CREATE_TABLE_JUEGO3_NIVEL1 = "CREATE TABLE juego3nivel1 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_TABLE_JUEGO3_NIVEL1);

        //Tabla de Juego 3 nivel 2

        //Especificación
        String CREATE_TABLE_JUEGO3_NIVEL2 = "CREATE TABLE juego3nivel2 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_TABLE_JUEGO3_NIVEL2);


        //Tabla de Juego 3 nivel 3

        //Especificación
        String CREATE_TABLE_JUEGO3_NIVEL3 = "CREATE TABLE juego3nivel3 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_TABLE_JUEGO3_NIVEL3);



        // ### JUEGO 4 ### //

        //Tabla de Juego 4 nivel 1

        //Especificación
        String CREATE_TABLE_JUEGO4_NIVEL1 = "CREATE TABLE juego4nivel1 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_TABLE_JUEGO4_NIVEL1);

        //Tabla de Juego 4 nivel 2

        //Especificación
        String CREATE_TABLE_JUEGO4_NIVEL2 = "CREATE TABLE juego4nivel2 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_TABLE_JUEGO4_NIVEL2);


        //Tabla de Juego 4 nivel 3

        //Especificación
        String CREATE_TABLE_JUEGO4_NIVEL3 = "CREATE TABLE juego4nivel3 ( "+
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "puntuacion INTEGER, "+
                "porcentaje INTEGER )";

        //Ejecución de la creación
        db.execSQL(CREATE_TABLE_JUEGO4_NIVEL3);


        // ### JUEGO 5 ### //

        //Tabla de Juego 5 nivel 1

            //Especificación
            String CREATE_TABLE_JUEGO5_NIVEL1 = "CREATE TABLE juego5nivel1 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO5_NIVEL1);

        //Tabla de Juego 5 nivel 2

            //Especificación
            String CREATE_TABLE_JUEGO5_NIVEL2 = "CREATE TABLE juego5nivel2 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO5_NIVEL2);


        //Tabla de Juego 5 nivel 3

            //Especificación
            String CREATE_TABLE_JUEGO5_NIVEL3 = "CREATE TABLE juego5nivel3 ( "+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    "puntuacion INTEGER, "+
                    "porcentaje INTEGER )";

            //Ejecución de la creación
            db.execSQL(CREATE_TABLE_JUEGO5_NIVEL3);





        //Tabla de información de medallas

        //En esta tabla solo aparecen las medallas que se tienen, nada más.

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


        // JUEGO1
            db.execSQL("DROP TABLE IF EXISTS juego1nivel1");
            db.execSQL("DROP TABLE IF EXISTS juego1nivel2");
            db.execSQL("DROP TALBE IF EXISTS juego1nivel3");

        // JUEGO2

            db.execSQL("DROP TABLE IF EXISTS juego2nivel1");
            db.execSQL("DROP TABLE IF EXISTS juego2nivel2");
            db.execSQL("DROP TALBE IF EXISTS juego2nivel3");


        // JUEGO3

            db.execSQL("DROP TABLE IF EXISTS juego3nivel1");
            db.execSQL("DROP TABLE IF EXISTS juego3nivel2");
            db.execSQL("DROP TALBE IF EXISTS juego3nivel3");

        // JUEGO4

            db.execSQL("DROP TABLE IF EXISTS juego4nivel1");
            db.execSQL("DROP TABLE IF EXISTS juego4nivel2");
            db.execSQL("DROP TALBE IF EXISTS juego4nivel3");


        // JUEGO5

            db.execSQL("DROP TABLE IF EXISTS juego5nivel1");
            db.execSQL("DROP TABLE IF EXISTS juego5nivel2");
            db.execSQL("DROP TALBE IF EXISTS juego5nivel3");

        // MEDALLAS

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
    public void addJugada(Jugada jugada, int nivel, int juego){

        //Para depuracion
        System.out.println("AñadiendoJugada " + jugada.toString());

        //1. Referencia a la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        //2. Crear contenido
        ContentValues values = new ContentValues();
        values.put(KEY_PUNTUACION, jugada.getPuntuacion());
        values.put(KEY_PORCENTAJE, jugada.getPorcentaje());


        //3. Inserccion en la tabla que corresponda
        switch (juego) {
            case 1:     //Se inserta en las tablas del Juego 1
                    if (nivel == 1)
                        db.insert(J1N1, null, values);
                    else if (nivel == 2)
                        db.insert(J1N2, null, values);
                    else if (nivel == 3)
                        db.insert(J1N3, null, values);
                    break;
            case 2:     //Se inserta en las tablas del Juego 2
                    if (nivel == 1)
                        db.insert(J2N1, null, values);
                    else if (nivel == 2)
                        db.insert(J2N2, null, values);
                    else if (nivel == 3)
                        db.insert(J2N3, null, values);
                    break;

            case 3:     //Se inserta en las tablas del Juego 2
                if (nivel == 1)
                    db.insert(J3N1, null, values);
                else if (nivel == 2)
                    db.insert(J3N2, null, values);
                else if (nivel == 3)
                    db.insert(J3N3, null, values);
                break;

            case 4:     //Se inserta en las tablas del Juego 2
                if (nivel == 1)
                    db.insert(J4N1, null, values);
                else if (nivel == 2)
                    db.insert(J4N2, null, values);
                else if (nivel == 3)
                    db.insert(J4N3, null, values);
                break;


            case 5:
                    if (nivel == 1)
                        db.insert(J5N1, null, values);
                    else if (nivel == 2)
                        db.insert(J5N2, null, values);
                    else if (nivel == 3)
                        db.insert(J5N3, null, values);
                    break;


            default: break;
        }



        //4. Cerrar la bd
        db.close();
    }


    /**
     * Añade una medalla a la tabla de medallas:
     * @param juego El juego al que corresponde
     * @param nivel El nivel al que corresponde
     */
    public void addMedalla(int juego, int nivel){

        boolean existe=false;

        //Comprobamos si existe ya esa medalla
            List<Medalla> medallas = getMedallas();
            for(Medalla medallita: medallas)
                if(medallita.juego==juego && medallita.nivel==nivel)
                    existe=true;

        //Si no existe la añadimos:
        if(!existe) {

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
    }

    /**
     * Método necesario para la comprobación de medallas en la lista. La identificación
     * unívoca se realiza a partir del parámetro juego y nivel.
     * @param juego El juego de la medalla
     * @param nivel El nivel del juego
     * @return Si la medalla ha sido obtenida.
     */
    public boolean compruebaMedallas(int juego, int nivel){

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


    public List<Medalla> getMedallas(){

        List<Medalla> medallas = new ArrayList();

        //1º Obtenemos una referencia a la base de datos
        SQLiteDatabase db = this.getWritableDatabase();

        //2º Creamos una consulta que nos devuelva todos las entradas de la tabla
        String query = "SELECT * FROM "+ MEDALLAS;

        //3º Creamos un cursor al que le pasamos la consulta para manejar los resultados de esta.
        Cursor cursor = db.rawQuery(query,null);

        System.out.println("PUTA MIERDA");

        if(cursor.moveToFirst()){
            do{
                medallas.add(new Medalla(Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2))));
               System.out.print("juego"+Integer.parseInt(cursor.getString(1)));
               System.out.print("nivel" + Integer.parseInt(cursor.getString(2)));
               System.out.println("");

               System.out.println(cursor.toString());
            }while(cursor.moveToNext());
        }

        return medallas;
    }


    /**
     * Para obtener un objeto jugada dando su id (esto podría cambiarse por la fecha o por un rango de fechas o por cualquier otra cosa)
     * @param id Identificador de la jugada
     * @return Un objeto de tipo Juagada
     */
    public Jugada getJugada(int id, int nivel, int juego){
        //Referencia a la base de datos;
        SQLiteDatabase db = this.getReadableDatabase();

        String tabla="";
        switch (juego) {
            case 1:
                    if (nivel == 1)
                        tabla = "J1N1";
                    if (nivel == 2)
                        tabla = "J1N2";
                    if (nivel == 3)
                        tabla = "J1N3";
                    break;
            case 2:
                    if (nivel == 1)
                        tabla = "J2N1";
                    if (nivel == 2)
                        tabla = "J2N2";
                    if (nivel == 3)
                        tabla = "J2N3";
                    break;
            default: break;
        }



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
    public List<Jugada> getAllJugadas(int nivel, int juego){

        //Creamos la lista de jugadas.
        List<Jugada>jugadas = new LinkedList<Jugada>();

        //Construimos la consulta (esto cambiará cuando tenamos más tablas)

        String tabla="";
        switch (juego) {
            case 1:
                if (nivel == 1)
                    tabla = J1N1;
                if (nivel == 2)
                    tabla = J1N2;
                if (nivel == 3)
                    tabla = J1N3;
                break;
            case 2:
                if (nivel == 1)
                    tabla = J2N1;
                if (nivel == 2)
                    tabla = J2N2;
                if (nivel == 3)
                    tabla = J2N3;
                break;

            case 3:
                if (nivel == 1)
                    tabla = J3N1;
                if (nivel == 2)
                    tabla = J3N2;
                if (nivel == 3)
                    tabla = J3N3;
                break;
            case 4:
                if (nivel == 1)
                    tabla = J4N1;
                if (nivel == 2)
                    tabla = J4N2;
                if (nivel == 3)
                    tabla = J4N3;
                break;

            case 5:
                if (nivel == 1)
                    tabla = J5N1;
                if (nivel == 2)
                    tabla = J5N2;
                if (nivel == 3)
                    tabla = J5N3;
                break;



            default:break;
        }

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
                //System.out.println("datooo"+cursor.getString(1));

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

    /**
     * Para obtener la máxima jugada de un juego y un nivel concreto.
     * @param juego
     * @param nivel
     * @return
     */
    public Jugada obtenerMaximaJugada(int juego, int nivel){


        //1º Llamamos a getAllJugada (que requiere nivel y juego) para sacar de la base de datos todas las jugadas.
        List<Jugada>todasJugadas=getAllJugadas(nivel, juego);

        //System.out.println("Obtenidas "+todasJugadas.size()+" jugadas del juego "+juego+" nivel "+nivel);

        //2º Llamamos a la funcion obtenMaximaJugada para que nos saque la maxima jugada de ese nivel:
        if(juego==1 || juego==2 || juego==3 || juego==4 || juego==5)
            return Jugada.obtenMaximaJugada(todasJugadas);
        else //si se trata del juego 2 se usa otra métrica.
            return Jugada.obtenMaximaJugada2(todasJugadas);


        //return maxima;
    }

    public int calcularPuntuacionGeneral(){

        int maxNiveles=3;
        int maxJuegos=5;

        int puntuacionAcumulada=0;

        for(int juego=1; juego<=maxJuegos; juego++){
            for(int nivel=1; nivel<=maxNiveles; nivel++)
                puntuacionAcumulada+=(obtenerMaximaJugada(juego,nivel)).getPuntuacion();
        }

        return puntuacionAcumulada;

    }

    public int calcularPorcentajeGeneral(){


        int maxJuegos=5;

        int porcentajeAcumulado=0;

        for(int juego=1; juego<=maxJuegos; juego++){
                porcentajeAcumulado += calcularPorcentajeGeneral(juego);

        }

       return porcentajeAcumulado/maxJuegos;

    }


    // ### funciones de llamada en la activity  juegos.java ### //

    /**
     * Sobrecarga del metodo anterior para ofrecer la puntuación general de un nivel.
     * Esta es la suma de todas las puntuaciones conseguidas en los distintos niveles que esta tenga.
     * @param juego Por el que preguntamos.
     * @return La suma de las puntuaciones de todos los niveles que ese juego tenga.
     */
    public int calcularPuntuacionGeneral(int juego){
        int maxNiveles=3;
        int puntuacionAcumulada=0;
        for(int nivel=1; nivel<maxNiveles; nivel++)
            puntuacionAcumulada+=(obtenerMaximaJugada(juego, nivel)).getPuntuacion();
         return puntuacionAcumulada;
    }

    public int calcularPorcentajeGeneral(int juego){
        int maxNiveles=3;
        int porcentajeAcumulado=0;
        for(int nivel=1; nivel<maxNiveles; nivel++)
            porcentajeAcumulado+=(obtenerMaximaJugada(juego,nivel)).getPorcentaje();
        return porcentajeAcumulado/maxNiveles;
    }


    /**
     *  ## Función ERASE ALL ##
     *
     *  Con esta función borramos todo el contenido de la base de datos.
     */
    public void eraseAll(){

    }

}

