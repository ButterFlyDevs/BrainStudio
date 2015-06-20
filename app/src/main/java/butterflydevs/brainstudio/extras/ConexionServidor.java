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

    //Lo usamos como paliativo a no poder enviar datos a las hebras
    private Jugador jugadorTMP = new Jugador();

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

    public void enviaPuntuacion(String alias, int puntos, String pais){



        /*
        El campo nombre de nuestra base de datos tiene una limitación (10char) por lo que podremos permitir que los nombres tenan mayor longitud,
        para asegurarnos de esto llamaremos siempre a la función recorteNombre que en el caso de que excedan eliminará los carácteres necesarios.
         */
        alias=recorteNombre(alias);

        jugadorTMP.setNombre(alias);
        jugadorTMP.setPuntuacion(puntos);
        jugadorTMP.setPais(pais);



        sqlThreadintroducirPuntuacion.start();
        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadintroducirPuntuacion.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public List<String> pedirRanking(){

        //Lanzamos la conexión a la base de datos y la petición de los datos en una hebra (debe de ser así para que funcione):
        sqlThreadpedirRanking.start();

        //Forzamos a que la ejecución de la hebra termine antes de continuar:
        try {
            sqlThreadpedirRanking.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Devolvemos el ranking que es una variable pribada de clase
        return ranking;

    }

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



    Thread sqlThreadpedirRanking = new Thread() {
        public void run() {


            try{
                Context mContext;
                //  mContext.getResources().getClass()
                //Registramos el driver:

                System.out.println(Class.forName("org.postgresql.Driver"));

                System.out.println("Cargado driver de la base de datos!!");
            }
            catch(java.lang.ClassNotFoundException e){
                System.err.println("Imposible encontrar driver del motor: ");
                e.printStackTrace();
            }

            String url="jdbc:postgresql://horton.elephantsql.com:5432/idviomlw";
            String username = "idviomlw";
            String password = "CiOKqiaqBk6FXQzDAVbEUbP-Kj5Oeopb";
            // jdbc:postgresql://host:port/database";
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
                    System.out.print("Puntuación: ");
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

    Thread sqlThreadintroducirPuntuacion = new Thread() {
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

            String url="jdbc:postgresql://horton.elephantsql.com:5432/idviomlw";
            String username = "idviomlw";
            String password = "CiOKqiaqBk6FXQzDAVbEUbP-Kj5Oeopb";
            // jdbc:postgresql://host:port/database";
            try{

                System.out.println("Ejecutando la conexión");
                //Establecemos la conexión:
                Connection db = DriverManager.getConnection(url, username, password);
                Statement st = db.createStatement();

                System.out.println(ranking.size()+" elementos en ranking. ANTES");

                String query="INSERT INTO puntuaciones VALUES ('"+jugadorTMP.getNombre()+"',"+jugadorTMP.getPuntuacion()+",'"+jugadorTMP.getPais()+"')";
                System.out.println(query);
                //Introducimos datos
                ResultSet rs = st.executeQuery(query);

                System.out.println(ranking.size()+" elementos en ranking. DESPUÉS");


                rs.close();
                st.close();
                db.close();

                System.out.println("Conexión terminada");


            }catch (java.sql.SQLException e) {
                System.out.println(e.getMessage()+e.getErrorCode()+e);
            }
        }
    };

}
