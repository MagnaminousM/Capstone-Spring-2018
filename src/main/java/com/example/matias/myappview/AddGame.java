package com.example.matias.myappview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class AddGame extends AppCompatActivity
{

    private Button wishlistButton;
    String gamename;
    EditText gameinput;
    String a;
    String b;
    String c;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        gameinput = (EditText) findViewById(R.id.gameInput);


        wishlistButton = (Button) findViewById(R.id.addToWishlist);
        wishlistButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                gamename = gameinput.getText().toString();
                wrapper();
                Intent intent = new Intent(AddGame.this, MainActivity.class);
                intent.putExtra("title",a);
                intent.putExtra("price", b);
                intent.putExtra("url", c);
                startActivity(intent);
            }
        });


    }

    protected void urlMagic()
    {
        String id = "";
        int closest = 1000000;
        int actual = gamename.length();
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("steamlibary.txt")));
            String geimu;
            while ((geimu = reader.readLine()) != null)
            {
                if (geimu.contains(gamename))
                {
                    String temp = "";
                    String[] geimuInfo = geimu.split(" ");
                    for (int i = 0; i < geimuInfo.length - 1; i++)
                    {
                        temp += geimuInfo[i];
                        if(i != geimuInfo.length - 2) {
                            temp += "";
                        }
                    }
                    if (temp.length() <= closest)
                    {
                        closest = temp.length();
                        id = geimuInfo[geimuInfo.length - 1];
                    }

                }
            }
        }
        catch (IOException e)
        {

        }
        finally
        {
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {

                }
            }
        }
        try
        {
            String url = "https://store.steampowered.com/api/appdetails/?appids=" + id;
            URL steamy = new URL(url);
            HttpURLConnection con = (HttpURLConnection) steamy.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null)
            {
                response.append(inputLine);
            }
            in.close();
            a = "" + id.length();
            JSONObject theGoods = new JSONObject(response.toString());
            a = "We made it this far";
            b = "Sure did";

            //a = theGoods.getJSONObject("262060").getJSONObject("data").getString("name");
            //b = theGoods.getJSONObject("262060").getJSONObject("data").getJSONObject("price_overview").getString("final");
            //c = "http://store.steampowered.com/app/" + theGoods.getJSONObject("262060").getJSONObject("data").getString("steam_appid");
            //a = url;
            a = theGoods.getJSONObject(id).getJSONObject("data").getString("name");
            b = theGoods.getJSONObject(id).getJSONObject("data").getJSONObject("price_overview").getString("final");
            c = "http://store.steampowered.com/app/" + id;
        }
        catch (Exception e)
        {

            b = id;
            c = e.getClass().getCanonicalName();
        }
    }

    void wrapper()
    {
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run()
            {
                try
                {
                    urlMagic();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        thread.run();
        //thread.start();
    }


}
