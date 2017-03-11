package com.mickeprado.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.mickeprado.listeners.NetworkConnectionInterface;
import com.mickeprado.pelischidasxd.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by alumno on 11/03/2017.
 */

public class NetworkConnection extends AsyncTask<Void,Void,Boolean>
{
    private final String TAG = NetworkConnection.class.getSimpleName();
    private Context context;
    private String responseStr;
    private NetworkConnectionInterface listener;

    public NetworkConnection(Context context, NetworkConnectionInterface networkConnectionInterface)
    {
        this.context = context;
        this.listener = networkConnectionInterface;
    }

    @Override
    protected Boolean doInBackground(Void... params)
    {
        final String BASE_URL = "http://api.themoviedb.org/3/movie";
        final String POPULAR_PATH = "popular";
        final String API_KEY_PARAM = "api_key";

        //Construcción de URL
        Uri uriToAPI = Uri.parse(BASE_URL).buildUpon()
                .appendPath(POPULAR_PATH)
                .appendQueryParameter(API_KEY_PARAM,context.getString(R.string.api_key_value))
                .build();

        Log.d(TAG,uriToAPI.toString());

        HttpURLConnection urlConnection;
        BufferedReader reader;
        try
        {
            URL url = new URL(uriToAPI.toString());
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream == null)
            {
                return false; //Validación, cuando no realizó la conexión
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            //Lectura del InputStream
            String line;
            while ((line = reader.readLine()) != null)
            {
                buffer.append(line + "\n"); //Se anexa linea
            }

            if (buffer.length() == 0)
            {
                return false; //No tiene líneas, cadena vacía.
            }
            responseStr = buffer.toString();
            Log.d(TAG,"Server Response: "+ responseStr);
            return true;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            return false;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            Log.e(TAG,e.toString());
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
        {
            if (listener != null)
            {
                listener.OnSuccesfullyResponse(responseStr);
            }
        }
        else
        {
            if (listener != null)
            {
                listener.OnFailedResponse();
            }
        }
    }
}

