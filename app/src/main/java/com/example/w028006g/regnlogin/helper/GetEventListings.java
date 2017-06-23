package com.example.w028006g.regnlogin.helper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.w028006g.regnlogin.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class GetEventListings extends AppCompatActivity {

    private static final String TAG = "Thing";
    private Button getBtn;
    private Button getBtnV;
    private String events;
    private TextView result;
    private int event;
    //Page 1
    private ArrayList<String> evental1 = new ArrayList();
    private ArrayList<String> eventalD1 = new ArrayList();
    private ArrayList<String> eventalL1 = new ArrayList();

    //Page 2
    private ArrayList<String> evental2 = new ArrayList();
    private ArrayList<String> eventalD2 = new ArrayList();
    private ArrayList<String> eventalL2 = new ArrayList();

    //Page 3
    private ArrayList<String> evental3 = new ArrayList();
    private ArrayList<String> eventalD3 = new ArrayList();
    private ArrayList<String> eventalL3 = new ArrayList();

    //Page 4
    private ArrayList<String> evental4 = new ArrayList();
    private ArrayList<String> eventalD4 = new ArrayList();
    private ArrayList<String> eventalA4 = new ArrayList();
    private ArrayList<String> eventalL4 = new ArrayList();

    //Page 5
    private ArrayList<String> evental45 = new ArrayList();
    private ArrayList<String> eventalD5 = new ArrayList();
    private ArrayList<String> eventalA5 = new ArrayList();
    private ArrayList<String> eventalL5 = new ArrayList();

    //Page 6
    private ArrayList<String> evental6 = new ArrayList();
    private ArrayList<String> eventalD6 = new ArrayList();
    private ArrayList<String> eventalA6 = new ArrayList();
    private ArrayList<String> eventalL6 = new ArrayList();

    //ALL PAGES
    private ArrayList<String> evental = new ArrayList();
    private ArrayList<String> eventalD = new ArrayList();
    private ArrayList<String> eventalA = new ArrayList();
    private ArrayList<String> eventalL = new ArrayList();

    public ArrayList<Events> eventsAl = new ArrayList(); //Main events list.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events);

        final StringBuilder builder = new StringBuilder();

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite1(1);
                getWebsite2(2);
                getWebsite3(3);
            }
        });

        getBtnV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                sortData();
                display();
            }
        });


}
    private void addData() {
        for (int i=0; i<evental1.size(); i++)
        {
            evental.add(evental1.get(i));
        }
        for (int i=0; i<evental2.size(); i++)
        {
            if(evental2.get(i).equalsIgnoreCase("Stuff") || evental2.get(i).equalsIgnoreCase("Stuff1"))
            {

            }
            else
            {
                evental.add(evental2.get(i));
            }
        }
        for (int i=0; i<evental3.size(); i++)
        {
            if(evental3.get(i).equalsIgnoreCase("Stuff") || evental3.get(i).equalsIgnoreCase("Stuff1"))
            {

            }
            else
            {
                evental.add(evental3.get(i));
            }
        }

        for (int i=0; i<eventalD1.size(); i++)
        {
            eventalD.add(eventalD1.get(i));
        }
        for (int i=0; i<eventalD2.size(); i++)
        {
            eventalD.add(eventalD2.get(i));
        }
        for (int i=0; i<eventalD3.size(); i++)
        {
            eventalD.add(eventalD3.get(i));
        }

        for (int i=0; i<eventalL1.size(); i++)
        {
            eventalL.add(eventalL1.get(i));
        }
        for (int i=0; i<eventalL2.size(); i++)
        {
            eventalL.add(eventalL2.get(i));
        }
        for (int i=0; i<eventalL3.size(); i++)
        {
            eventalL.add(eventalL3.get(i));
        }
    }

    private void sortData(){
        final StringBuilder builder = new StringBuilder();
        int num=0;
        int j=0;

        for (int i=2; i<evental.size(); i++)
        {
            Events ev = new Events();
            ev.setName(evental.get(i));
            String des = "";
            String s1 = evental.get(i);
            String s2 = eventalL.get(j);
            des = s2.replace(s1, "");
            i++;
            ev.setDate(evental.get(i));
            String s1D = evental.get(i);
            des = des.replace(s1D, "");

            ev.setDesc(des);
            eventsAl.add(ev);
            j++;
        }
        System.out.println(num);
    }

    private void display(){
       try {
            final StringBuilder builder = new StringBuilder();
            int num=1;
            builder.setLength(0);

            for (int i=2; i<eventsAl.size(); i++)
            {
                builder.append("Event Name: " + eventsAl.get(i).getName() + "\n");
                builder.append("Event Desc: " + eventsAl.get(i).getDesc() + "\n");
                builder.append("Event Date/Time: " + eventsAl.get(i).getDate() + "\n");
                builder.append("\n\n");
                num+=1;
            }
            builder.append("\n\n" + num);
           result.setText(builder.toString());
            //result.setText("Arrays: " +  evental.size() + " - " + eventalD.size() + " - " + eventalL.size());
       } catch (Exception e){
            Log.e(TAG,"Err: " + e + "Arrays: " +  evental.size() + " - " + eventalD.size() + " - " + eventalA.size());
        }
    }

    private void getWebsite1(final int pageNo) {

        evental1.add("Stuff");
        evental1.add("Stuff1");
        //final int nu = num;
        new Thread(new Runnable() {
            @Override
            public void run() { //Get Name and Date
                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    //Elements links = doc.select("div.dms1120.thedmsFeatEvent.regularScreenOnly"); - WHOLE THING!
                    //Elements links = doc.getElementsByClass("address"); - //WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); - //WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {
                            if (evental1.get(evental1.size() - 2).equalsIgnoreCase(link.text()) || evental1.get(evental1.size() - 1).equalsIgnoreCase(link.text())) {

                            } else {
                                evental1.add(link.text());
                                event += 1;
                            }
                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() { // Get Address
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    //Elements links = doc.select("div.thedmsContentHolder"); //- WHOLE THING!
                    Elements links = doc.getElementsByClass("address"); //- //WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); //- WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    //Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    //Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");
                    //evental.add("Stuff");
                    //evental.add("Stuff1");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {

                            eventalD1.add(link.text());
                            //event+=1;

                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() { //Get Desc

                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    Elements links = doc.select("div.thedmsBrowseRow.dynamicRow.dynamicCols3"); //- WHOLE THING!
                    //Elements links = doc.getElementsByClass("address"); //- WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); //- WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    //Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    //Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");
                    //evental.add("Stuff");
                    //evental.add("Stuff1");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {
                            //if (evental.get(evental.size()-2).equalsIgnoreCase(link.text()) || evental.get(evental.size()-1).equalsIgnoreCase(link.text())){

                            //}else {
                            eventalL1.add(link.text());
                            //event+=1;
                            //}
                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

    private void getWebsite2(final int pageNo) {
        evental2.add("Stuff");
        evental2.add("Stuff1");

        //final int nu = num;
        new Thread(new Runnable() {
            @Override
            public void run() { //Get Name and Date
                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    //Elements links = doc.select("div.dms1120.thedmsFeatEvent.regularScreenOnly"); - WHOLE THING!
                    //Elements links = doc.getElementsByClass("address"); - //WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); - //WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {
                            if (evental2.get(evental2.size() - 2).equalsIgnoreCase(link.text()) || evental2.get(evental2.size() - 1).equalsIgnoreCase(link.text())) {

                            } else {
                                evental2.add(link.text());
                                event += 1;
                            }
                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() { // Get Address
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    //Elements links = doc.select("div.thedmsContentHolder"); //- WHOLE THING!
                    Elements links = doc.getElementsByClass("address"); //- //WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); //- WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    //Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    //Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");
                    //evental.add("Stuff");
                    //evental.add("Stuff1");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {

                            eventalD2.add(link.text());
                            //event+=1;

                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() { //Get Desc

                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    Elements links = doc.select("div.thedmsBrowseRow.dynamicRow.dynamicCols3"); //- WHOLE THING!
                    //Elements links = doc.getElementsByClass("address"); //- WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); //- WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    //Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    //Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");
                    //evental.add("Stuff");
                    //evental.add("Stuff1");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {
                            //if (evental.get(evental.size()-2).equalsIgnoreCase(link.text()) || evental.get(evental.size()-1).equalsIgnoreCase(link.text())){

                            //}else {
                            eventalL2.add(link.text());
                            //event+=1;
                            //}
                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

    private void getWebsite3(final int pageNo) {
        evental3.add("Stuff");
        evental3.add("Stuff1");
        //final int nu = num;
        new Thread(new Runnable() {
            @Override
            public void run() { //Get Name and Date
                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    //Elements links = doc.select("div.dms1120.thedmsFeatEvent.regularScreenOnly"); - WHOLE THING!
                    //Elements links = doc.getElementsByClass("address"); - //WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); - //WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {
                            if (evental3.get(evental3.size() - 2).equalsIgnoreCase(link.text()) || evental3.get(evental3.size() - 1).equalsIgnoreCase(link.text())) {

                            } else {
                                evental3.add(link.text());
                                event += 1;
                            }
                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() { // Get Address
            @Override
            public void run() {

                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    //Elements links = doc.select("div.thedmsContentHolder"); //- WHOLE THING!
                    Elements links = doc.getElementsByClass("address"); //- //WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); //- WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    //Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    //Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");
                    //evental.add("Stuff");
                    //evental.add("Stuff1");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {

                            eventalD3.add(link.text());
                            //event+=1;

                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() { //Get Desc

                try {
                    Document doc = Jsoup.connect("http://www.visitstoke.co.uk/thedms.aspx?pvieflag=E&dms=12&advancedsearchtab=e&advancedsearchpage=%2fadvanced-search.aspx&showadvancedsearchlink=2#!page=" + pageNo).get();
                    //String title = doc.title();
                    //Elements links = doc.select("#thedmsListings");
                    //Elements links = doc.select("");
                    Elements links = doc.select("div.thedmsBrowseRow.dynamicRow.dynamicCols3"); //- WHOLE THING!
                    //Elements links = doc.getElementsByClass("address"); //- WORKS - ADDRESS
                    //Elements links = doc.getElementsByClass("thedmsDescription"); //- WORKS - DESC
                    //Elements links = doc.getElementsByClass("thedmsEventDate"); - //WORKS -DATE
                    //Element content = doc.getElementById("thedmsListings"); //- Name & DATE
                    //Elements links = content.getElementsByTag("a");
                    //Elements links = doc.select("#thedmsListings > div:nth-child(1) > div.dms1111 > div.dms1120.thedmsFeatEvent.regularScreenOnly > div.thedmsContentHolder > div.thedmsBrowseH2Background > h2");

                    //builder.append(title).append("\n");
                    //evental.add("Stuff");
                    //evental.add("Stuff1");

                    for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("a href"));
                        //builder.append("\n").append("Event : ").append(link.text()+"\n\n");

                        if (link.text().isEmpty()) {

                        } else {
                            //if (evental.get(evental.size()-2).equalsIgnoreCase(link.text()) || evental.get(evental.size()-1).equalsIgnoreCase(link.text())){

                            //}else {
                            eventalL3.add(link.text());
                            //event+=1;
                            //}
                        }
                    }
                } catch (IOException e) {

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }).start();
    }

}