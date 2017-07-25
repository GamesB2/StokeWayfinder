package com.example.w028006g.regnlogin.activity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Transition;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.BackgroundTask;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.app.AppController;
import com.example.w028006g.regnlogin.helper.DownloadImageTask;
import com.facebook.share.widget.LikeView;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.github.gorbin.asne.core.listener.OnRequestSocialPersonCompleteListener;
import com.github.gorbin.asne.core.persons.SocialPerson;
import com.inthecheesefactory.lib.fblike.widget.FBLikeView;

import java.util.ArrayList;

import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;
import com.example.w028006g.regnlogin.helper.Ticket;
import com.squareup.picasso.Picasso;


/**
 * Created by User on 4/15/2017.
 */

public class Tickets_View extends AppCompatActivity {

    private Button share;
    private String link = "https://wayfarer-app.com";
    private String message = "Test share:";
    private SocialNetwork socialNetwork;
    public int NetID;
    private int networkId;
    private ArrayList<Ticket> feedItemList = DatabaseRetrieval.ticketsAl;
    private Button btnBuy;
    private Ticket t;
    private Context mContext;

    private String personName;
    private String personGivenName;
    private String personFamilyName;
    private String personEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_view);

        Intent mIntent = getIntent();
        final int intValue = mIntent.getIntExtra("id", 0);

        t = feedItemList.get(intValue);

        Transition ts = new Explode();  //Slide(); //Explode();
        ts.setDuration(300);
        getWindow().setEnterTransition(ts);
        getWindow().setExitTransition(ts);

        TextView title;
        TextView event_time;
        TextView event_location;
        TextView event_organiser;
        TextView summary;
        ImageView img;
        final String method="register";

//        share = (Button) findViewById(R.id.twittershare);

        SharedPreferences prefs = AppController.getInstance().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
        networkId  = prefs.getInt("SocialNet", -1);

        if (networkId == -1){

        } else {
            if (networkId == 4) {
                socialNetwork = LoginActivity.mSocialNetworkManager.getSocialNetwork(networkId);
                //socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
                socialNetwork.requestCurrentPerson();
            } else {

            }
        }

        title  = (TextView)findViewById(R.id.title);
        event_time  = (TextView)findViewById(R.id.event_time);
        event_location  = (TextView)findViewById(R.id.event_Location);
        event_organiser  = (TextView)findViewById(R.id.event_Organiser);
        summary  = (TextView)findViewById(R.id.summary);
        img  = (ImageView)findViewById(R.id.ticketView_id);
        btnBuy  = (Button)findViewById(R.id.btnBuy);

        title.setText(t.getName());
        event_time.setText(t.geteDate());
        summary.setText(t.getDesc());
        event_organiser.setText(t.getOrgan());
        event_location.setText(t.getPriceS());

        Picasso.with(mContext)
                .load(t.getThumbnail())
                .into(img);



        btnBuy.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {




                SharedPreferences prefs = AppController.getInstance().getSharedPreferences("MYPREFS", Context.MODE_PRIVATE);
                networkId = prefs.getInt("SocialNet", -1);

                switch (networkId) {
                    case 10:
                        BackgroundTask backgroundTask=new BackgroundTask(Tickets_View.this);
                        backgroundTask.execute(method, MainActivity.userDetails.getEmail(), String.valueOf(t.getId()));
                        Toast.makeText(getApplicationContext(),
                                "You Sucessfully Purchased a Ticket for: " + t.getName(),
                                Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case 3:
                        SharedPreferences login = AppController.getInstance().getSharedPreferences("GoogleLogin", Context.MODE_PRIVATE);
                        personEmail = login.getString("email", "WrongName");
                        personName = login.getString("name", "WrongName");
                        MainActivity.userDetails.setName(personName);
                        MainActivity.userDetails.setEmail(personEmail);
                        BackgroundTask backgroundTask1=new BackgroundTask(Tickets_View.this);
                        backgroundTask1.execute(method, MainActivity.userDetails.getEmail(), String.valueOf(t.getId()));
                        Toast.makeText(getApplicationContext(),
                                "You Sucessfully Purchased a Ticket for: " + t.getName(),
                                Toast.LENGTH_LONG).show();
                        finish();
                        break;

                    case 4:
                        socialNetwork = LoginActivity.mSocialNetworkManager.getSocialNetwork(networkId);
                        socialNetwork.setOnRequestCurrentPersonCompleteListener(new OnRequestSocialPersonCompleteListener() {
                            @Override
                            public void onRequestSocialPersonSuccess(int socialNetworkId, SocialPerson socialPerson) {
                                MainActivity.userDetails.setName(socialPerson.name);
                                MainActivity.userDetails.setEmail(socialPerson.email);
                                BackgroundTask backgroundTask=new BackgroundTask(Tickets_View.this);
                                backgroundTask.execute(method, MainActivity.userDetails.getEmail(), String.valueOf(t.getId()));
                                Toast.makeText(getApplicationContext(),
                                        "You Sucessfully Purchased a Ticket for: " + t.getName(),
                                        Toast.LENGTH_LONG).show();
                                finish();
                            }

                            @Override
                            public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
//                        Toast.makeText(getApplicationContext(), "something went oopsy", Toast.LENGTH_SHORT);
                            }
                        });
                        socialNetwork.requestCurrentPerson();
                        break;

                }

            }
        });

        FBLikeView fbLikeView = (FBLikeView) this.findViewById(R.id.fbLikeView);
        fbLikeView.getLikeView().setObjectIdAndType(t.getThumbnail(), LikeView.ObjectType.OPEN_GRAPH);

        ImageView sharingButton = (ImageView) findViewById(R.id.share);


//            share.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick (View v) {
//                    if (networkId != 4 && networkId != 1) {
//                        AlertDialog.Builder ad = alertDialogInit("You must be logged in via FaceBook or Google", link);
//                        ad.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//
//                            }
//                        });
//                        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int i) {
//                                dialog.cancel();
//                            }
//                        });
//                        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                            public void onCancel(DialogInterface dialog) {
//                                dialog.cancel();
//                            }
//                        });
//                        ad.create().show();
//                    } else {
//                        shareClick();
//                    }
//                }
//            });


        sharingButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick (View v){
                shareIt();
            }
        });

    }



            public void shareIt() {
                //sharing implementation here
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Day out at "+t.getName();
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }

//            private void shareClick() {
//                    AlertDialog.Builder ad = alertDialogInit("Would you like to post Link:", link);
//                    ad.setPositiveButton("Post link", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            Bundle postParams = new Bundle();
//                            postParams.putString(SocialNetwork.BUNDLE_NAME, "Share Social Network");
//                            postParams.putString(SocialNetwork.BUNDLE_LINK, link);
//                    if(networkId != 0) {
//                        socialNetwork.requestPostLink(postParams, message, postingComplete);
//                    } else {
//
//                    }
//                        }
//                    });
//                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int i) {
//                            dialog.cancel();
//                        }
//                    });
//                    ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                        public void onCancel(DialogInterface dialog) {
//                            dialog.cancel();
//                        }
//                    });
//                    ad.create().show();
//
//            };
//
//            private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
//                @Override
//                public void onPostSuccessfully(int socialNetworkID) {
//                    Toast.makeText(Tickets_View.this, "Sent", Toast.LENGTH_LONG).show();
//                }
//
//
//                @Override
//                public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
//                    Toast.makeText(Tickets_View.this, "Error while sending: " + errorMessage, Toast.LENGTH_LONG).show();
//                }
//            };
//
//            private AlertDialog.Builder alertDialogInit(String title, String message) {
//                AlertDialog.Builder ad = new AlertDialog.Builder(Tickets_View.this);
//                ad.setTitle(title);
//                ad.setMessage(message);
//                ad.setCancelable(true);
//                return ad;
//        }

}
