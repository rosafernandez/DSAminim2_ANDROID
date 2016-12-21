package edu.upc.eetac.dsa.minim2android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView textId = (TextView) findViewById(R.id.id_detall);
        TextView textTipus = (TextView) findViewById(R.id.tipus_detall);
        TextView textDescripcio = (TextView) findViewById(R.id.descripcio_detall);
        TextView textNom = (TextView) findViewById(R.id.nom_detall);
        TextView textPuntuacio = (TextView) findViewById(R.id.puntuacio_detall);

        //rebre les dadea dl intent
        Bundle intentdata = getIntent().getExtras();
        textId.setText(intentdata.getInt("id") + "");
        textTipus.setText(intentdata.getString("tipus"));
        textDescripcio.setText(intentdata.getString("descripcio"));
        textNom.setText(intentdata.getString("nom"));
        textPuntuacio.setText(intentdata.getInt("puntuacio") + "");

    }

    public void onClick (View v){
        finish();
    }
}
