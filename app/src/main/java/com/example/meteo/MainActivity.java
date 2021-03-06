package com.example.meteo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.meteo.models.ApiWeather;
import com.example.meteo.utils.Constant;
import com.example.meteo.utils.FastDialog;
import com.example.meteo.utils.Network;
import com.example.meteo.utils.Preference;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private EditText editTextCity;
    private TextView textViewCity;
    private TextView textViewTemperature;
    private ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextCity = findViewById(R.id.editTextCity);
        textViewCity = findViewById(R.id.textViewCity);
        textViewTemperature = findViewById(R.id.textViewTemperature);
        imageViewIcon = findViewById(R.id.imageViewIcon);

        // affichage de la ville
        String city = Preference.getCity(MainActivity.this);
        if(city !=null){
            editTextCity.setText(city);
            submit(null);
        }
    }

    public void submit(View view) {
        // apres clické sur le button ok, nous deverons verifier que l'utilisateur nous a bien donner un nom de ville et n'est pas un champ vide
        if(editTextCity.getText().toString().isEmpty()){
            FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, "Vous devez renseigner une ville!");
            return;
        }
        if(!Network.isNetworkAvailable(MainActivity.this)){
            FastDialog.showDialog(MainActivity.this, FastDialog.SIMPLE_DIALOG, "Vous devez etre connecté!");
            return;
        }

        // HTTP library (volley) doc => https://developer.android.com/training/volley/simple
        // Copie le code JAVA de la partie (Use newRequestQueue) du site
        // importer les classe pour les erreurs fournis
        // enlever apres les 2 textView.setText du code

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Sur la constante URL dans la classe Constant nous passerons le nom de la ville à la place %s dans url de recherche
        // Au cas de plusieurs %s sur l'url, il faut préciser où l'ajouter, exemple metric
        String url = String.format(Constant.URL, editTextCity.getText().toString(), "metric");
        //String url = String.format(Constant.URL, editTextCity.getText().toString());

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Volley", "onResponse: " + response );
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", "onError: " + error);
                parseJson(new String(error.networkResponse.data));
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseJson(String json) {
        textViewCity.setText(null);
        textViewTemperature.setText(null);
        imageViewIcon.setImageBitmap(null);
        //Gson
        ApiWeather api = new Gson().fromJson(json,ApiWeather.class);

        if(api.getCod()==200){
            // Enregistrement de la ville dans les préferences
            Preference.setCity(MainActivity.this, api.getName());

            textViewCity.setText(api.getName());

            // deux méthodes pour afficher le même résultat -> %s °C
            // 1- textViewTemperature.setText(api.getMain().getTemp() + "°C");
            // 2-
            textViewTemperature.setText(String.format(getString(R.string.main_temperature),api.getMain().getTemp()));
            // TODO : afficher l'image Weather > icon
            String url = String.format(Constant.URL_IMAGE,api.getWeather().get(0).getIcon());
            Picasso.get().load(url).into(imageViewIcon);
        } else {
            // Enregistrement de la ville dans les préferences
            Preference.setCity(MainActivity.this, null);

            FastDialog.showDialog(MainActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    api.getMessage());
        }
    }
}