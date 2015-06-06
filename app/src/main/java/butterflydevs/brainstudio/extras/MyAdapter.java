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
        TextView labelView = (TextView) rowView.findViewById(R.id.textView_superior);
        TextView valueView = (TextView) rowView.findViewById(R.id.textView_inferior);

        // 4. Set the text for textView
        labelView.setText(Integer.toString(itemsArrayList.get(position).getPuntuacion())+"%");
        valueView.setText(Integer.toString(itemsArrayList.get(position).getPorcentaje())+" PUNTOS");

        // 5. retrn rowView
        return rowView;
    }
}
