package edu.upc.eetac.dsa.minim2android;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class ThirdActivity extends AppCompatActivity {

    private ClientConfig clientConfig = null;
    private Client client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        clientConfig = new ClientConfig();
        clientConfig.register(GsonMessageBodyHandler.class);
        client = ClientBuilder.newClient(clientConfig);;
    }

    public void crearRegistre (View v) {

        EditText editNom = (EditText) findViewById(R.id.nom_new);
        EditText editTipus = (EditText) findViewById(R.id.tipus_new);
        EditText editDescripcio = (EditText) findViewById(R.id.descripcio_new);

        String nom = editNom.getText().toString();
        String tipus = editTipus.getText().toString().toLowerCase();
        String descripcio = editDescripcio.getText().toString();

        if((nom.isEmpty() || tipus.isEmpty() || descripcio.isEmpty())) {
            Toast.makeText(this, "Hi ha algún camp sense completar", Toast.LENGTH_SHORT).show();
        } else if (!tipus.equals("director") && !tipus.equals("professor") && !tipus.equals("alumne")) {
            Toast.makeText(getApplicationContext(), "El camp tipus és incorrecte", Toast.LENGTH_SHORT).show();
        } else {
            CreateEtakemonTask mCreateEtakemonTask = new CreateEtakemonTask(nom, tipus, descripcio);
            mCreateEtakemonTask.execute();
        }
    }


    public class CreateEtakemonTask extends AsyncTask<Void, Void, String> {

        String nom;
        String tipus;
        String descripcio;

        CreateEtakemonTask(String nom, String tipus, String descripcio) {
            this.nom = nom;
            this.tipus = tipus;
            this.descripcio = descripcio;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            try {
                WebTarget target = client.target(MainActivity.BASE_URI);
                Form form = new Form();
                form.param("nom", nom);
                form.param("tipus", tipus);
                form.param("descripcio", descripcio);
                Response response = target.request().post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));
                result = response.readEntity(String.class);
            } catch (ProcessingException ex) {
                ex.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(final String result) {
            if(!result.equals("")) {
                EtakemonCollection etakemonCollection = (new Gson()).fromJson(result, EtakemonCollection.class);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("etakemonCollection", new Gson().toJson(etakemonCollection));
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            } else {
                Toast.makeText(ThirdActivity.this, "Error en la conexió, no s'ha pogut crear el etakemon", Toast.LENGTH_SHORT).show();
            }
        }
    }




}
