package com.mickeprado.pelischidasxd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mickeprado.adapters.MoviesAdapter;
import com.mickeprado.json_utilities.JsonParser;
import com.mickeprado.listeners.NetworkConnectionInterface;
import com.mickeprado.models.Movie;
import com.mickeprado.network.NetworkConnection;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NetworkConnectionInterface
{

    private final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lista = (RecyclerView)findViewById(R.id.listaPeliculas);
        lista.setLayoutManager(new GridLayoutManager(this,2));
        lista.setHasFixedSize(true);

        NetworkConnection connection = new NetworkConnection(this,this);
        connection.execute();
    }

    @Override
    public void OnSuccesfullyResponse(String response)
    {
        ArrayList<Movie> movies = JsonParser.getMovies(this,response);
        MoviesAdapter adapter = new MoviesAdapter(this,movies);
        lista.setAdapter(adapter);
    }

    @Override
    public void OnFailedResponse()
    {

    }
}
