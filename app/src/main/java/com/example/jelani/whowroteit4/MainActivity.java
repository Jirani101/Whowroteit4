package com.example.jelani.whowroteit4;

import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {


    private TextView bookData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookData = (TextView) findViewById(R.id.bookJsonItem);


        //Setup button onclick listener to activate the task
        ((Button) findViewById(R.id.searchButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchQuery = ((TextView) findViewById(R.id.bookInput)).getText().toString();
                new JSONTask().execute(searchQuery + "https://jsonparsingdemo-cec5b.firebaseapp.com/jsonData/moviesDemoList.txt");

            }
        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {


                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);

                }
                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("movies");
                StringBuffer finalBufferedData = new StringBuffer();

                for (int i = 0; i<parentArray.length(); i++) {

                    JSONObject finalObject = parentArray.getJSONObject(i);

                    String movieName = finalObject.getString("movie");
                    int year = finalObject.getInt("year");
                    finalBufferedData.append(movieName + " - " + year + "\n");


                }
                return finalBufferedData.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            bookData.setText(result);
        }
    }

    
}




