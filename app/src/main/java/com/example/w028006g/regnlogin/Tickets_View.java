package com.example.w028006g.regnlogin;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.w028006g.regnlogin.activity.LoginActivity;
import com.facebook.FacebookSdk;
import com.facebook.share.widget.LikeView;
import com.facebook.share.widget.ShareButton;
import com.github.gorbin.asne.core.SocialNetwork;
import com.github.gorbin.asne.core.listener.OnPostingCompleteListener;
import com.inthecheesefactory.lib.fblike.widget.FBLikeView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.w028006g.regnlogin.R.id.container;

/**
 * Created by User on 4/15/2017.
 */

public class Tickets_View extends AppCompatActivity {

    private Button share;
    private String link = "https://wayfarer-app.com";
    private String message = "Test share:";
    private SocialNetwork socialNetwork;
    private int networkId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().requestFeature(android.view.Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ticket_view);

        Transition ts = new Explode();  //Slide(); //Explode();
        ts.setDuration(300);
        getWindow().setEnterTransition(ts);
        getWindow().setExitTransition(ts);

        TextView title;
        TextView event_time;
        TextView event_location;
        TextView event_organiser;
        TextView summary;

        FacebookSdk.sdkInitialize(getApplicationContext());

        title = (TextView) findViewById(R.id.title);
        event_time = (TextView) findViewById(R.id.title);
        event_location = (TextView) findViewById(R.id.title);
        event_organiser = (TextView) findViewById(R.id.title);
        summary = (TextView) findViewById(R.id.title);

        share = (Button) findViewById(R.id.twittershare);

        if (networkId != 0) {
            socialNetwork = LoginActivity.mSocialNetworkManager.getSocialNetwork(networkId);
//        socialNetwork.setOnRequestCurrentPersonCompleteListener(this);
            socialNetwork.requestCurrentPerson();
        } else {

        }


        FBLikeView fbLikeView = (FBLikeView) this.findViewById(R.id.fbLikeView);
        fbLikeView.getLikeView().setObjectIdAndType(
                "http://www.middleportpottery.org/visit-us/tearoom/?gclid=CjwKEAjw-LLKBRCdhqmwtYmX93kSJAAORDM68Ni9DTGRRCcT5IO0y_QUHL-JJexOqaGE5vDMlLXd3RoCWyXw_wcB",
                LikeView.ObjectType.OPEN_GRAPH);

        ImageView sharingButton = (ImageView) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = alertDialogInit("Would you like to share:", link);
                ad.setPositiveButton("Post link", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle postParams = new Bundle();
                        postParams.putString(SocialNetwork.BUNDLE_NAME, "Wayfarer");
                        postParams.putString(SocialNetwork.BUNDLE_LINK, link);
                        if(networkId != 0) {
                            socialNetwork.requestPostLink(postParams, message, postingComplete);
                        } else {

                        }
                    }
                });
                        ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.cancel();
                            }
                        });
                        ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                dialog.cancel();
                            }
                        });
                        ad.create().show();
                    }

                });

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
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }

            private View.OnClickListener shareClick = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder ad = alertDialogInit("Would you like to post Link:", link);
                    ad.setPositiveButton("Post link", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Bundle postParams = new Bundle();
                            postParams.putString(SocialNetwork.BUNDLE_NAME, "Simple and easy way to add social networks for android application");
                            postParams.putString(SocialNetwork.BUNDLE_LINK, link);
//                    if(networkId == GooglePlusSocialNetwork.ID) {
//                        socialNetwork.requestPostDialog(postParams, postingComplete);
//                    } else {
//                        socialNetwork.requestPostLink(postParams, message, postingComplete);
//                    }
                        }
                    });
                    ad.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            dialog.cancel();
                        }
                    });
                    ad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        public void onCancel(DialogInterface dialog) {
                            dialog.cancel();
                        }
                    });
                    ad.create().show();
                }
            };

            private OnPostingCompleteListener postingComplete = new OnPostingCompleteListener() {
                @Override
                public void onPostSuccessfully(int socialNetworkID) {
                    Toast.makeText(Tickets_View.this, "Sent", Toast.LENGTH_LONG).show();
                }


                @Override
                public void onError(int socialNetworkID, String requestID, String errorMessage, Object data) {
                    Toast.makeText(Tickets_View.this, "Error while sending: " + errorMessage, Toast.LENGTH_LONG).show();
                }
            };

            private AlertDialog.Builder alertDialogInit(String title, String message) {
                AlertDialog.Builder ad = new AlertDialog.Builder(Tickets_View.this);
                ad.setTitle(title);
                ad.setMessage(message);
                ad.setCancelable(true);
                return ad;
        }
}
