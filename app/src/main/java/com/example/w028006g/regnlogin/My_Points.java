package com.example.w028006g.regnlogin;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.activity.MainActivity;

import com.example.w028006g.regnlogin.activity.StartScreen;

import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapter;
import com.example.w028006g.regnlogin.helper.Ticket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.example.w028006g.regnlogin.app.AppConfig.CONNECTION_TIMEOUT;
import static com.example.w028006g.regnlogin.app.AppConfig.READ_TIMEOUT;

/**
 * Created by User on 4/15/2017.
 */

public class My_Points extends AppCompatActivity {

    private CardView btnCard1;
    public ArrayList<Ticket> t = DatabaseRetrieval.ticketsAl;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter adapter;
    private Person p;
    private String points="";
    private ArrayList<Ticket> pointsList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tickets);



        new My_Points.AsyncLogin().execute(MainActivity.userDetails.getEmail());





/*        p = MainActivity.userDetails;


        String tickets = p.getTickets();
        tickets = tickets.substring(1);
        String[] ticketParts = tickets.split(",");
        ArrayList<Ticket> ticketList = new ArrayList<>();
        for(int i=0;i<ticketParts.length;i++)
        {
            for (int j=0;j<DatabaseRetrieval.ticketsAl.size();j++)
            {
                Integer check = Integer.parseInt(ticketParts[i]);

                if(check == DatabaseRetrieval.ticketsAl.get(j).getId())
                {
                    ticketList.add(DatabaseRetrieval.ticketsAl.get(j));
                }
            }
        }*/




/*        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_map:
                        Intent intent0 = new Intent(Tickets.this, MapsActivityNew.class);
                        startActivity(intent0);

                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(Tickets.this, Profile.class);
                        startActivity(intent1);
                        startActivity(intent1,
                                ActivityOptions.makeSceneTransitionAnimation(Tickets.this).toBundle());
                        break;

                    case R.id.ic_Adventures:
                        Intent intent2 = new Intent(Tickets.this, Adventures.class);
                        startActivity(intent2);
                        break;

                    case R.id.ic_Tickets:

                        break;
                }
                return false;

            }
        });*/



    }





    private class AsyncLogin extends AsyncTask<String, String, String> {
        //ProgressDialog pdLoading = new ProgressDialog(DatabaseQ.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            //pdLoading.setMessage("\tLoading...");
            //pdLoading.setCancelable(false);
            //pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("https://concussive-shirt.000webhostapp.com/get_details_user_points.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("email", params[0]);
                //.appendQueryParameter("password", params[1]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    return (result.toString());

                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }

        }

        @Override
        protected void onPostExecute(String response) {
            //this method will be running on UI thread
            // pdLoading.dismiss();

            try {
                JSONObject jObj = new JSONObject(response);
                boolean error = jObj.getInt("success") == 1 ? false : true;
                System.out.println(response);

                // Check for error node in json
                if (!error) {
                    JSONArray contacts = jObj.getJSONArray("points");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        points = c.getString("points");
                    }


                    //Now Sort

                    System.out.println(points);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(My_Points.this);
                    alertDialogBuilder.setMessage("You Have: " + points + " in your account! :-)\nUse these points?");
                            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                        public void onClick(DialogInterface dialog, int which) {
                                            Toast.makeText(My_Points.this,"Purchased",Toast.LENGTH_LONG).show();
                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();



                    // Launch main activity

                } else {
                    // Error in login. Get the error message
                    String errorMsg = jObj.getString("error_msg");
                    //Toast.makeText(getApplicationContext(),
                    //errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                // JSON error
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }


        }
    }


}
