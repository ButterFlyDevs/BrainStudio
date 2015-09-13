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

import android.content.Context;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class ConexionServidor {

    private List<String> ranking;

    private List<Jugador> rankingJugadores;

    public ConexionServidor(){
        System.out.println("START CLIENT");
        this.ranking=new ArrayList();
        this.rankingJugadores=new ArrayList();
    }

    public String recorteNombre(String nombre){
        //Esto siempre dependerá de nuestra base de datos.
        if(nombre.length()>10) {
            return nombre.substring(0, 9);

        }else{
            return nombre;
        }

    }


    String alias, pais;
    int puntos;
    String idUser;

    /*
    1. Crear usuario en la BD.
     */
    public void crearUsuarioBD(String alias, int puntos, String pais, String idUser){

        this.alias=alias;
        this.puntos=puntos;
        this.idUser=idUser;
        this.pais=pais;

        //Lanzamos la conexión a la base de datos y la petición de los datos en una hebra (debe de ser así para que funcione):
        sqlThreadaddUser.start();

        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadaddUser.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    /*
    4. actualizar.
     */
    public void actualizarNombre(String nuevoNombre, String idUser){

        alias=nuevoNombre;
        this.idUser=idUser;

        //Lanzamos la conexión a la base de datos y la petición de los datos en una hebra (debe de ser así para que funcione):
        sqlThreadactualizarNombre.start();

        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadactualizarNombre.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private boolean existe=true;
    /*
    2. Comprueba usuario.
     */
    public boolean existeUsuario(String idUser){

        this.idUser=idUser;

        //Lanzamos la conexión a la base de datos y la petición de los datos en una hebra (debe de ser así para que funcione):
        sqlThreadexisteUsuario.start();

        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadexisteUsuario.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return existe;
    }

    /*
    5. Perdir ranking.
     */
    public List<Jugador> pedirRankingNueva(){
        //Lanzamos la conexión a la base de datos y la petición de los datos en una hebra (debe de ser así para que funcione):
        sqlThreadpedirRanking.start();

        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadpedirRanking.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Devolvemos el ranking que es una variable privada de clase
        if(rankingJugadores.isEmpty())
            return null;
        else
            return rankingJugadores;
    }

    /**
     * 3.
     * Actualiza los puntos de un jugador en la BD usando nueva puntuacion y el id de su
     * dispositivo.
     * @param nuevaPuntuacion Nueva puntuacion con la que actualizar la actual.
     * @param idUser Identificado con el que distinguir unívocamente al user en la BD.
     */
    public void actualizarPuntuacionEnBD(int nuevaPuntuacion, String idUser){

        this.puntos=nuevaPuntuacion;
        this.idUser=idUser;

        //Lanzamos la conexión a la base de datos y la petición de los datos en una hebra (debe de ser así para que funcione):
        sqlThreadactualizarPuntuacionENBD.start();

        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadactualizarPuntuacionENBD.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



    }



    Thread sqlThreadactualizarPuntuacionENBD = new Thread() {
        public void run() {


            try{
                Context mContext;

                //Registramos el driver:
                System.out.println(Class.forName("org.postgresql.Driver"));

                System.out.println("Cargado driver de la base de datos!!");
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.println("Imposible encontrar driver del motor: ");
                e.printStackTrace();
            }
            String url=Claves.url;
            String username=Claves.username;
            String password=Claves.password;

            try{

                System.out.println("Ejecutando la conexión");
                //Establecemos la conexión:
                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();


                //Para insertar una columna que sea id_user
                String modificacion1="UPDATE puntuaciones SET puntuacion = '"+puntos+"' WHERE id_user= '"+idUser+"';";

            /*Si usamos executeQuery aunque la operación se realice bien nos dará un error por que la
            consulta no reotorna nigún resultado.
            */
                st.executeUpdate(modificacion1);

                st.close();
                db.close();

                System.out.println("Conexión terminada");


            }catch (java.sql.SQLException e) {
                System.out.println(e.getMessage()+e.getErrorCode()+e);
            }

        }
    };


    //Hebra que se ejecuta realizando una acción en pararlelo a la ejecución normal del programa.
    Thread sqlThreadpedirRanking = new Thread() {
        public void run() {


            try{
                Context mContext;

                //Registramos el driver:
                System.out.println(Class.forName("org.postgresql.Driver"));

                System.out.println("Cargado driver de la base de datos!!");
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.println("Imposible encontrar driver del motor: ");
                e.printStackTrace();
            }

            String url=Claves.url;
            String username=Claves.username;
            String password=Claves.password;

            try{

                System.out.println("Ejecutando la conexión");
                //Establecemos la conexión:
                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();

                //Creamos la tabla
                //String orden="CREATE TABLE puntuaciones (nombre varchar(10), puntuacion integer)";
                //Introducimos datos
                //ResultSet rs = st.executeQuery("INSERT INTO puntuaciones VALUES ( 'pepe', 47857)");


                ResultSet rs = st.executeQuery("SELECT * FROM puntuaciones ORDER BY puntuacion DESC");

                while (rs.next()) {
                    //Mostramos por terminal
                    System.out.print("Nombre: ");
                    System.out.println(rs.getString(1));
                    System.out.print("Puntuacion: ");
                    System.out.println(rs.getString(2));
                    //Cargamos los datos que obtenemos en la variable ranking propia del objeto que luego devolveremos:
                   // ranking.add(rs.getString(1)+" "+rs.getString(2));
                    //Probamos una nueva forma
                    rankingJugadores.add(new Jugador(rs.getString(1), rs.getInt(2), rs.getString(3)));

                }
                System.out.println("añadidos "+ranking.size()+" elementos a ranking");


                rs.close();
                st.close();
                db.close();

                System.out.println("Conexión terminada");


            }catch (java.sql.SQLException e) {
                System.out.println(e.getMessage()+e.getErrorCode()+e);
            }
        }
    };


    //Hebra que se ejecuta realizando una acción en pararlelo a la ejecución normal del programa.
    Thread sqlThreadaddUser = new Thread() {
        public void run() {
            System.out.println("Usuario nuevo en la base de datos");

            try{
                Context mContext;

                //Registramos el driver:
                System.out.println(Class.forName("org.postgresql.Driver"));

                System.out.println("Cargado driver de la base de datos!!");
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.println("Imposible encontrar driver del motor: ");
                e.printStackTrace();
            }
            String url=Claves.url;
            String username=Claves.username;
            String password=Claves.password;


            try{

                System.out.println("Ejecutando la conexión");
                //Establecemos la conexión:
                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();


                //Para insertar un usuario en la BD.
                //String query="INSERT INTO puntuaciones VALUES ('"+alias+"','"+puntos+"','"+pais+"','"+idUser+"')";
                //String query="INSERT INTO puntuaciones VALUES ('prueba','200','UGR','8848873626')";
                String query="INSERT INTO puntuaciones VALUES ('"+alias+"','"+puntos+"','"+pais+"','"+idUser+"')";

            /*Si usamos executeQuery aunque la operación se realice bien nos dará un error por que la
            consulta no reotorna nigún resultado.
            */
                st.executeUpdate(query);

                st.close();
                db.close();

                System.out.println("Conexión terminada");


            }catch (java.sql.SQLException e) {
                System.out.println(e.getMessage()+e.getErrorCode()+e);
            }

        }
    };



    //Hebra que se ejecuta realizando una acción en pararlelo a la ejecución normal del programa.
    Thread sqlThreadactualizarNombre = new Thread() {
        public void run() {


            try{
                Context mContext;

                //Registramos el driver:
                System.out.println(Class.forName("org.postgresql.Driver"));

                System.out.println("Cargado driver de la base de datos!!");
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.println("Imposible encontrar driver del motor: ");
                e.printStackTrace();
            }

            String url=Claves.url;
            String username=Claves.username;
            String password=Claves.password;

            try{

                System.out.println("Ejecutando la conexión");
                //Establecemos la conexión:
                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();


                //Para insertar una columna que sea id_user
                String query="UPDATE puntuaciones SET nombre = '"+alias+"' WHERE id_user= '"+idUser+"';";

                System.out.println(query);

            /*Si usamos executeQuery aunque la operación se realice bien nos dará un error por que la
            consulta no reotorna nigún resultado.
            */
                st.executeUpdate(query);

                st.close();
                db.close();

                System.out.println("Conexión terminada");


            }catch (java.sql.SQLException e) {
                System.out.println(e.getMessage()+e.getErrorCode()+e);
            }
        }
    };









    Thread sqlThreadexisteUsuario = new Thread() {
        public void run() {


            try{


                //Registramos el driver:
                System.out.println(Class.forName("org.postgresql.Driver"));

                System.out.println("Cargado driver de la base de datos!!");
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.println("Imposible encontrar driver del motor: ");
                e.printStackTrace();
            }

            String url=Claves.url;
            String username=Claves.username;
            String password=Claves.password;


            try{

                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();

                //Establecemos la query que vamos a usar.
                String query2="SELECT * FROM puntuaciones WHERE id_user="+idUser;

                System.out.println(query2);

                //String query="INSERT INTO puntuaciones VALUES ( '"+nombre+"', '"+puntuacion+"', '"+pais+"', '"+idUser+"')";
                //Ejecutamos la consulta y guardamos los datos en rs

                ResultSet rs = st.executeQuery(query2);

                if(!rs.isBeforeFirst())
                    existe=false;
                //Cerramos todos los objetos que lo necesitan.

                st.close();
                db.close();



            }catch (java.sql.SQLException e) {
                System.out.println(e.getMessage()+e.getErrorCode()+e);
            }
        }
    };


}
