package com.example.matias.myappview;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HaychTeeTeePee extends AsyncTask<String, String, String>
{
    public String a;
    public String b;
    public String c;
    private Exception exception;
    protected String doInBackground(String... urls)
    {
        try {
            String url = "http://store.steampowered.com/api/appdetails/?appids=262060";
            URL steamy = new URL(url);
            HttpURLConnection con = (HttpURLConnection) steamy.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println(response.toString());
            JSONObject theGoods = new JSONObject(response.toString());
            if (theGoods.getString("final") != null && theGoods.getString("initial") != null) {
                a = theGoods.getString("name");
                b = theGoods.getString("final");
                b = "http://store.steampowered.com/app/" + theGoods.getString("steam_appid");
            }
            return "we good";
        }
        catch (Exception e)
        {
            exception = e;
            a = "Oh";
            b = "Noes";
            c = exception.getClass().getCanonicalName();
            return "e";
        }
    }


    protected void onPostExecute(String s)
    {
        if (exception == null)
        {
            a = "it is";
            b = "wednesday";
            c = "my dudes";

        }
        else
        {
            a = "Other shizz is fizzed";
        }
    }
}
