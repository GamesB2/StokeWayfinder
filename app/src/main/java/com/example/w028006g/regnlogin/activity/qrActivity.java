package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.History;
import com.example.w028006g.regnlogin.MultiMedia;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.MyRecyclerViewAdapterPosts;
import com.example.w028006g.regnlogin.helper.Post;
import com.example.w028006g.regnlogin.helper.ScannedPost;
import com.google.zxing.client.android.BeepManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.journeyapps.barcodescanner.CaptureManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class qrActivity extends AppCompatActivity implements View.OnClickListener
{

    //View Objects
    private Button buttonScan;

    //qr code scanner object
    private IntentIntegrator qrScan;
    private BeepManager beepManager;
    private String sResult;
    private final String URL = "www.examplesite.com";
    private ArrayList<Post> alPrevScan = new ArrayList<>();
    private ArrayList<Post> postArrayList = DatabaseRetrieval.postsAl;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);
        for(int i = 0; i < DatabaseRetrieval.prevPost.size(); i++)
        {
            alPrevScan.add(DatabaseRetrieval.prevPost.get(i));
        }
        mRecyclerView = (RecyclerView) findViewById(R.id.rvQR);
        mLayoutManager = new LinearLayoutManager(qrActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapterPosts(qrActivity.this, alPrevScan);
        mRecyclerView.setAdapter(mAdapter);


        //View objects
        buttonScan = (Button) findViewById(R.id.buttonScan);

        //intializing scan object
        qrScan = new IntentIntegrator(this);
        qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        qrScan.setBeepEnabled(true);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);
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
                    if(checkNew(found.getIdI()))
                    {
                        Toast.makeText(this,"You've already scanned " + found.getName() ,Toast.LENGTH_LONG).show();
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
        post = new ScannedPost(post);
        alPrevScan.add(post);
        DatabaseRetrieval.prevPost.add(0, post);
    }

    public boolean checkNew(int nPostId)
    {
        for(int i = 0; i<postArrayList.size(); i++)
        {
            Post pTemp = postArrayList.get(i);
            if(pTemp.getIdI()==nPostId)
            {
                return false;
            }
        }
        return true;
    }

    public Post FindPost(int nPostId)
    {
        for(int i = 0; i < postArrayList.size(); i++)
        {
            if(nPostId == postArrayList.get(i).getIdI())
            {
                return postArrayList.get(i);
            }
        }
        return null;
    }
}
