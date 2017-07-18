package com.example.w028006g.regnlogin.helper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.w028006g.regnlogin.activity.MultiMedia;
import com.example.w028006g.regnlogin.R;

import com.example.w028006g.regnlogin.helper.MarkerClasses.Post;


import java.util.ArrayList;


public class MyRecyclerViewAdapterPosts extends RecyclerView.Adapter<MyRecyclerViewAdapterPosts.CustomViewHolder> {
    private ArrayList<Post> feedItemList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    private ArrayList<Integer> ints = new ArrayList<Integer>();


    public MyRecyclerViewAdapterPosts(Context context, ArrayList<Post> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.history_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder customViewHolder, int i) {
        final Post feedItem = feedItemList.get(i);

        ints.add(feedItemList.get(i).getId());


/*        if (!TextUtils.isEmpty(feedItem.getThumbnail())) {
            Picasso.with(mContext).load(feedItem.getThumbnail())
                    .error(R.drawable.lock)
                    .placeholder(R.drawable.lock)
                    .into(customViewHolder.imageView);
        }*/

        //Setting text view title
        customViewHolder.textViewE.setText((feedItem.getAddressInfo().getFeatureName()));
        customViewHolder.textViewD.setText((feedItem.getSummary()));
        customViewHolder.textViewP.setText((feedItem.getLatestScanTime()));
        customViewHolder.textViewID.setText(String.valueOf(feedItem.getId()));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //onItemClickListener.onItemClick(feedItem);
                //if (v.getId() == feedItem.getId()){

                //Toast.makeText(v.getContext(), "ITEM PRESSED = " + String.valueOf(customViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();
                //} else {
                Toast.makeText(v.getContext(), "ROW PRESSED = " + String.valueOf(customViewHolder.getAdapterPosition()), Toast.LENGTH_SHORT).show();

                Intent myIntent1 = new Intent(v.getContext(), MultiMedia.class);
                    myIntent1.putExtra("id", feedItem.getId());


                System.out.println(ints.get(customViewHolder.getAdapterPosition()));
                mContext.startActivity(myIntent1);


                //}
            }
        };

        customViewHolder.imageView.setOnClickListener(listener);

        //customViewHolder.textViewE.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (feedItemList.size());
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected ImageView imageView;
        protected TextView textViewE;
        protected TextView textViewD;
        protected TextView textViewP;
        protected TextView textViewID;

        public CustomViewHolder(View view) {
            super(view);

            this.imageView = (ImageView) view.findViewById(R.id.img_vid);
            this.textViewE = (TextView) view.findViewById(R.id.title);
            this.textViewD = (TextView) view.findViewById(R.id.histDesc);
            this.textViewP = (TextView) view.findViewById(R.id.lastScan);
            this.textViewID = (TextView) view.findViewById(R.id.txtPostID);
            //myInt = feedItemList.get(view.findViewById(getAdapterPosition()));

            //this.textViewD = (TextView) view.findViewById(R.id.tDesc);
            //this.textViewP = (TextView) view.findViewById(R.id.tPrice);
        }
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


}