package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.BottomNavigationViewHelper;

import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapterPosts;

import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;



import com.google.zxing.client.android.BeepManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class qrActivity extends AppCompatActivity implements View.OnClickListener
{

    //View Objects
    private Button buttonScan;
    private TextView emptyText;
    private Boolean firstTimeFlag = true;

    //qr code scanner object
    private IntentIntegrator qrScan;
    private BeepManager beepManager;
    private String sResult;
    private final String URL = "http://www.examplesite.com";
    private ArrayList<Post> alPrevScan = new ArrayList<>();
    private ArrayList<Post> postArrayList = POI.getAllPost();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);


        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setBeepEnabled(true);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);




        //Menu bar at the bottom
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavView_Bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId()) {
                    case R.id.ic_map:
                        Intent intent = new Intent(getApplicationContext(), MapsActivityNew.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_Profile:
                        Intent intent1 = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent1);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_qr:
                        Intent intent2 = new Intent(getApplicationContext(), qrActivity.class);
                        startActivity(intent2);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_shop:
                        Intent intent3 = new Intent(getApplicationContext(), Tickets.class);
                        startActivity(intent3);
                        overridePendingTransition(0, 0);
                        finish();
                        break;

                    case R.id.ic_Rec:
                        Intent intent4 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent4);
                        overridePendingTransition(0, 0);
                        finish();
                        break;
                }
                return false;
            }
        });

        for(int i = 0; i < DatabaseRetrieval.prevPost.size(); i++)
        {
            alPrevScan.add(DatabaseRetrieval.prevPost.get(i));
        }

        if(alPrevScan.size()==0 && firstTimeFlag)
        {
            emptyText = (TextView) findViewById(R.id.emptyText);
            emptyText.setVisibility(View.VISIBLE);
//            firstTimeFlag = false;
//            qrScan.initiateScan();
        }
        else
        {
            mRecyclerView = (RecyclerView) findViewById(R.id.rvQR);
            mLayoutManager = new LinearLayoutManager(qrActivity.this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new MyRecyclerViewAdapterPosts(qrActivity.this, alPrevScan);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null)
        {
            //if qrcode has nothing in it
            if (result.getContents() == null)
            {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else
                {
                //if qr contains data

                try
                {
                    //converting the data to json
                    //JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    sResult = result.getContents();
                    System.out.println("First Flag: " + sResult);
                    sResult = sResult.substring(URL.length()+1);
                    System.out.println("Second Flag: " + sResult);
                    int nResult = Integer.parseInt(sResult);
                    Intent info = new Intent(qrActivity.this,MultiMedia.class);
                    info.putExtra("locCode", nResult);
                    Post found = FindPost(nResult);
                    if(found.getScanCount() != 0)
                    {
                        Toast.makeText(this,"You've already scanned " + found.getAddressInfo().getFeatureName() ,Toast.LENGTH_LONG).show();
                    }
                    RecordPost(found);
                    startActivity(info);

                } catch (Exception e)
                {
                    e.printStackTrace();

                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this,"Unrecognisable QR code" + result.getContents() ,Toast.LENGTH_LONG).show();
                }
            }
        } else
        {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View v)
    {
        //initiating the qr code scan
        qrScan.initiateScan();
    }

    public void RecordPost(Post post)
    {
        post.setScanned();
        alPrevScan.add(post);
        DatabaseRetrieval.prevPost.add(0, post);
    }

    public Post FindPost(int nPostId)
    {
        for(int i = 0; i < postArrayList.size(); i++)
        {
            if(nPostId == postArrayList.get(i).getId())
            {
                return postArrayList.get(i);
            }
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
        finish();
    }
}
