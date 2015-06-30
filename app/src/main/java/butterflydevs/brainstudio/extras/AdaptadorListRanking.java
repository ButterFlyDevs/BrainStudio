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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import butterflydevs.brainstudio.R;

/**
 * Created by juan on 6/06/15.
 */
public class AdaptadorListRanking extends ArrayAdapter<Jugador> {

    private final Context context;
    private final ArrayList<Jugador> itemsArrayList;

    public AdaptadorListRanking(Context context, ArrayList<Jugador> itemsArrayList){
        super(context, R.layout.jugador, itemsArrayList);

        this.context = context;
        this.itemsArrayList=itemsArrayList;
    }

    /**
     * Configurador de cada uno de los elementos de la lista.
     * Se coge cada un de los objetos de la lista y se configura al layout como se quiera.
     * @param position Posicon en la lista que se pasa por argumento al constructor del adaptador.
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.jugador, parent, false);

        // 3. Get the two text view from the rowView
        TextView textoSuperior = (TextView) rowView.findViewById(R.id.textView_superior);
        TextView textoInferior = (TextView) rowView.findViewById(R.id.textView_inferior);
        TextView textoPosicion = (TextView) rowView.findViewById(R.id.textPos);
        TextView textoPais     = (TextView) rowView.findViewById(R.id.textPais);

        // 4. Set the text for textView
        textoInferior.setText(Integer.toString(itemsArrayList.get(position).getPuntuacion())+" puntos");
        textoSuperior.setText(itemsArrayList.get(position).getNombre()+"");
        textoPosicion.setText(Integer.toString(position+1));
        textoPais.setText(itemsArrayList.get(position).getPais());

        // 5. retrn rowView
        return rowView;
    }
}
