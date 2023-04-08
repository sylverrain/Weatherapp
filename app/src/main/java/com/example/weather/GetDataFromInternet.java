package com.example.weather;

import android.os.AsyncTask;
import android.util.Log;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class GetDataFromInternet extends AsyncTask <URL, Void, String> {
    private static final String TAG="GetDataFromInternet";
    protected String getResponseFromHttpGetURL(URL url) throws IOException {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = urlConnection.getInputStream();
                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");
                boolean hasInput = scanner.hasNext();
                String result;
                if (hasInput) {
                result = scanner.next();
                return result;
                } else {
                return null;
                }
            } finally {
                urlConnection.disconnect();
            }
    }

    public interface AsyncResponse{
        void proccessFinish(String output);
    }
    public AsyncResponse delegate;
    public GetDataFromInternet (AsyncResponse delegate){
        this.delegate=delegate;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: called");
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(URL[] url) {
        Log.d(TAG, "doInBackground: called");
        String result = null;
        URL urlQuary = url[0];
        try {
            result = getResponseFromHttpGetURL(urlQuary);
        } catch(IOException e){
                e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d(TAG,"onPreExecute: called");
        Log.d(TAG,"onPostExecute:"+result);
        delegate.proccessFinish(result);
        //super.onPostExecute(o);
    }
}
