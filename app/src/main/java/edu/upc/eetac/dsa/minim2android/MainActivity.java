package edu.upc.eetac.dsa.minim2android;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class MainActivity extends ListActivity {

    //URL MOVIL
    //public static final String BASE_URI = "http://192.168.1.101:8080/etakemon";

    //URL EMULADOR
    public static final String BASE_URI = "http://10.0.2.2:8080/etakemon";

    private static final int CREATE_CODE = 100;
    private ClientConfig clientConfig = null;
    private Client client = null;
    EtakemonCollection etakemonCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clientConfig = new ClientConfig();
        clientConfig.register(GsonMessageBodyHandler.class);
        client = ClientBuilder.newClient(clientConfig);

        //Per atacar a la API
        GetEtakemonTask mEtakemonTask = new GetEtakemonTask();
        mEtakemonTask.execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.newRegister);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, ThirdActivity.class);
                startActivityForResult(i, CREATE_CODE);
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_CODE && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            String etakemonJson = b.getString("etakemonCollection");
            EtakemonCollection etakemonCollection = new Gson().fromJson(etakemonJson, EtakemonCollection.class);
            setListAdapter(new EtakemonArrayAdapter(MainActivity.this, etakemonCollection));
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //agafa els items seleccionats
        Etakemon e = (Etakemon) getListAdapter().getItem(position);

        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        //passem els parametres d'una pantalla a l'altra
        intent.putExtra("id", e.getId());
        intent.putExtra("nom", e.getNom());
        intent.putExtra("tipus", e.getTipus());
        intent.putExtra("descripcio", e.getDescripcio());
        intent.putExtra("puntuacio", e.getPuntuacio());
        startActivity(intent);

    }


    public class GetEtakemonTask extends AsyncTask<Void, Void, String> {

        GetEtakemonTask() {

        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            try {
                WebTarget target = client.target(BASE_URI);
                Response response = target.request().get();
                result = response.readEntity(String.class);
            } catch (ProcessingException ex) {
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            if(!result.equals("")) {
                etakemonCollection = (new Gson()).fromJson(result, EtakemonCollection.class);
                if(etakemonCollection.getEtakemons().size() > 0) {
                    setListAdapter(new EtakemonArrayAdapter(MainActivity.this, etakemonCollection));
                } else {
                    Toast.makeText(MainActivity.this, "Llista buida, crea un Etakemon!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "Error en la conexió, torna a provar-ho", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
