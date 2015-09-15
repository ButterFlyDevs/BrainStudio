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
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterflydevs.brainstudio.R;

/**
 * Created by juan on 6/06/15.
 */
public class MyAdapter  extends ArrayAdapter<Nivel> {

    private final Context context;
    private final ArrayList<Nivel> itemsArrayList;

    public MyAdapter(Context context, ArrayList<Nivel> itemsArrayList){
        super(context, R.layout.nivel, itemsArrayList);

        this.context = context;
        this.itemsArrayList=itemsArrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 1. Create inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // 2. Get rowView from inflater
        View rowView = inflater.inflate(R.layout.nivel, parent, false);

        // 3. Get the two text view from the rowView
        ImageView imagen = (ImageView) rowView.findViewById(R.id.imageView_imagen);
        TextView labelView = (TextView) rowView.findViewById(R.id.textView_superior);
        TextView valueView = (TextView) rowView.findViewById(R.id.textView_inferior);

        // 4. Set the text for textView
        switch (position){
            case 0: imagen.setImageResource(R.drawable.ojos); break;
            case 1: imagen.setImageResource(R.drawable.colores);break;
            case 2: imagen.setImageResource(R.drawable.secuencial); break;
            case 3: imagen.setImageResource(R.drawable.asociacion);break;
            default: imagen.setImageResource(R.drawable.mono);break;

        }
        labelView.setText(Integer.toString(itemsArrayList.get(position).getPuntuacion())+"%");
        valueView.setText(Integer.toString(itemsArrayList.get(position).getPorcentaje())+" PUNTOS");

        // 5. retrn rowView
        return rowView;
    }
}
