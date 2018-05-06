package com.example.matias.myappview;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TableRow.LayoutParams;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity{

    private Button addGameButton;
    TableLayout gameTitle;
    TableLayout gamePrice;
    TableLayout gameUrl;
    String a;
    String b;
    String c;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gamePrice = (TableLayout) findViewById(R.id.price);
        gameUrl = (TableLayout) findViewById(R.id.url);
        gameTitle = (TableLayout) findViewById(R.id.title);


        //wrapper();
        if (savedInstanceState == null)
        {
            Bundle extras = getIntent().getExtras();
            if(extras == null)
            {

            }
            else
            {
                String gameTitleString = extras.getString("title");
                String gamePriceString = "$";
                for (int i = 0; i < extras.getString("price").length(); i++)
                {
                    if (i == extras.getString("price").length() - 2)
                    {
                        gamePriceString += ".";
                        gamePriceString += extras.getString("price").charAt(i);
                        gamePriceString += extras.getString("price").charAt(i + 1);
                        break;
                    }
                    else
                    {
                        gamePriceString += extras.getString("price").charAt(i);
                    }
                }
                String gameUrlString = extras.getString("url");
                addRow(gameTitleString, gamePriceString, gameUrlString);
            }
        }
        else
        {
            addRow("ayy","wheres", "themayo");
            String gameTitleString = (String) savedInstanceState.getSerializable("title");
            String gamePriceString = (String) savedInstanceState.getSerializable("price");
            String gameUrlString = (String) savedInstanceState.getSerializable("url");
            addRow(gameTitleString, gamePriceString, gameUrlString);
        }

        addGameButton = (Button) findViewById(R.id.addGameButton);
        addGameButton.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                openAddGame();
            }
        });
    }

    public void openAddGame()
    {
        Intent intent = new Intent(this, AddGame.class);
        startActivity(intent);
    }

    public void addRow(String gameTitleString, String gamePriceString, final String gameUrlString)
    {
        //Create and initialize table rows
        TableRow gameTitleRow;
        TableRow gamePriceRow;
        TableRow gameUrlRow;

        gameTitleRow = new TableRow(this);
        gamePriceRow = new TableRow(this);
        gameUrlRow = new TableRow(this);

        //Add the rows to the table
        gameTitle.addView(gameTitleRow);
        gamePrice.addView(gamePriceRow);
        gameUrl.addView(gameUrlRow);

        //Create and set
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        Button t3 = new Button(this);

        t1.setText(gameTitleString);
        t2.setText(gamePriceString);
        t3.setText("STEAM");

        t3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(gameUrlString));
                startActivity(intent);
            }
        });

        gameTitleRow.addView(t1);
        gamePriceRow.addView(t2);
        gameUrlRow.addView(t3);
    }


}