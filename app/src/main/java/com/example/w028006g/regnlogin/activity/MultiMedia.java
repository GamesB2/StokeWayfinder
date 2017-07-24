package com.example.w028006g.regnlogin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.BackgroundTaskPosts;
import com.example.w028006g.regnlogin.R;
import com.example.w028006g.regnlogin.app.AppConfig;
import com.example.w028006g.regnlogin.helper.DatabaseRetrieval;


import com.example.w028006g.regnlogin.helper.MarkerClasses.POI;
import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.PlayerStyle;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class MultiMedia extends YouTubeBaseActivity implements
        YouTubePlayer.OnInitializedListener {

    private static final int RECOVERY_DIALOG_REQUEST = 1;

    // YouTube player view
    private YouTubePlayerView youTubeView;
    private Button btnClose;
    private TextView txtSummary;
    private TextView txtMoreInfo;
    private TextView txtName;
    private boolean open = false;
    private Post p;
    private boolean qr = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.more_infomation);

        youTubeView = (YouTubePlayerView) findViewById(R.id.youtube_view);
        txtSummary = (TextView) findViewById(R.id.txtAbout);
        txtMoreInfo = (TextView) findViewById(R.id.txtMoreInfo);
        txtName = (TextView) findViewById(R.id.txtInfoName);
        final String method="register";


        Intent mIntent1 = getIntent();
        int intValue = mIntent1.getIntExtra("id", 0);
        Intent qrIntent = getIntent();
        int qrValue = qrIntent.getIntExtra("locCode", 0);

        if(intValue > 0)
        {
            ArrayList<Post> postsAl = POI.getAllPost();
            for(int i =0;i< postsAl.size();i++)
            {
                if(postsAl.get(i).getId() == intValue)
                {
                    p = postsAl.get(i);
                }
            }
        }
        else
        {
            qr = true;
            for(int i =0;i< DatabaseRetrieval.postsAl.size();i++)
            {
                if(DatabaseRetrieval.postsAl.get(i).getId() == qrValue)
                {
                    p = DatabaseRetrieval.postsAl.get(i);
                }
            }
        }

        if (p!= null) {
            txtSummary.setText(p.getSummary());
            txtName.setText(p.getAddressInfo().getFeatureName());
        }


        // Initializing video player with developer key
        youTubeView.initialize(AppConfig.DEVELOPER_KEY, this);
        btnClose = (Button)findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if(qr)
                {
                    BackgroundTaskPosts backgroundTask = new BackgroundTaskPosts(MultiMedia.this);

                    backgroundTask.execute(method, MainActivity.userDetails.getEmail(), String.valueOf(p.getId()));

                    Toast.makeText(getApplicationContext(),

                            "You Sucessfully Added This Post To Your Collection:\n" + p.getAddressInfo().getFeatureName(),
                            Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        txtMoreInfo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (open){
                    txtMoreInfo.setText("see more...");
                    open = false;
                }else {
                    txtMoreInfo.setText(p.getDescription());
                    open = true;
                }
            }
        });

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                        YouTubeInitializationResult errorReason) {
        if (errorReason.isUserRecoverableError()) {
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            String errorMessage = String.format(
                    getString(R.string.error_player), errorReason.toString());
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                        YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            //player.loadVideo(AppConfig.YOUTUBE_VIDEO_CODE)
            if(p!= null) {
                player.loadVideo(p.getVideo());
            }

            // Hiding player controls
            player.setPlayerStyle(PlayerStyle.DEFAULT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RECOVERY_DIALOG_REQUEST) {
            // Retry initialization if user performed a recovery action
            getYouTubePlayerProvider().initialize(AppConfig.DEVELOPER_KEY, this);
        }
    }

    private YouTubePlayer.Provider getYouTubePlayerProvider() {
        return (YouTubePlayerView) findViewById(R.id.youtube_view);
    }

}