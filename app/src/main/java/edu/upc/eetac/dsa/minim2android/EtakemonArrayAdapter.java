package edu.upc.eetac.dsa.minim2android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rosa on 17/12/2016.
 */
public class EtakemonArrayAdapter extends ArrayAdapter<Etakemon> {
    private final Context context;
    private final EtakemonCollection etakemonCollection;

    public EtakemonArrayAdapter(Context context, EtakemonCollection etakemonCollection) {
        super(context, R.layout.etakemon_custom, etakemonCollection.getEtakemons());
        this.context = context;
        this.etakemonCollection = etakemonCollection;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.etakemon_custom, parent, false);
        TextView textId = (TextView) rowView.findViewById(R.id.etakemonID);
        TextView textNom = (TextView) rowView.findViewById(R.id.nom);

        String id = etakemonCollection.getEtakemons().get(position).getId() + "";
        textId.setText(id);
        textNom.setText(etakemonCollection.getEtakemons().get(position).getNom());

        return rowView;
    }
}

//    Per mostrar un missage per consola


