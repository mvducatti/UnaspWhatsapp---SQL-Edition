package com.example.marcos.unaspwhatsapp_sqledition.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.marcos.unaspwhatsapp_sqledition.Adapter.NewsRecyclerAdapter;
import com.example.marcos.unaspwhatsapp_sqledition.Database.DBNoticias;
import com.example.marcos.unaspwhatsapp_sqledition.Model.Noticia;
import com.example.marcos.unaspwhatsapp_sqledition.R;
import com.example.marcos.unaspwhatsapp_sqledition.UserSession;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppCompatActivity activity = MainActivity.this;
    private RecyclerView recyclerViewNews;
    private List<Noticia> listNoticias;
    private NewsRecyclerAdapter newsRecyclerAdapter;
    private DBNoticias databaseHelper;
    private Button btnLogout;
    private LinearLayoutManager mLayoutManager;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userSession = new UserSession(getApplicationContext());

        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        btnLogout = findViewById(R.id.btlogout);

        TextView usuario = findViewById(R.id.textView5);


        /**
         * Olá mundo by Alciomar
         */
        SharedPreferences sharedPreferences = getSharedPreferences("Reg", Context.MODE_PRIVATE);
        String  uName = sharedPreferences.getString("Name", "");
        usuario.setText(uName.toUpperCase());


        try {
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userSession.logoutUser();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        initStuff();

        FloatingActionButton fab = findViewById(R.id.fabNews);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PostNews.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onResume(){
        super.onResume();
        getDataFromPostgres();
    }


    /**
     * This method is to initialize objects to be used
     */
    private void initStuff() {

        try {
            listNoticias = new ArrayList<>();
            newsRecyclerAdapter = new NewsRecyclerAdapter(listNoticias);

            mLayoutManager = new LinearLayoutManager(getApplicationContext());

            //Invert the view of the list
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);

            recyclerViewNews.setLayoutManager(mLayoutManager);
            recyclerViewNews.setItemAnimator(new DefaultItemAnimator());
            recyclerViewNews.setHasFixedSize(true);
            recyclerViewNews.setAdapter(newsRecyclerAdapter);
            databaseHelper = new DBNoticias(activity);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getDataFromPostgres() {
        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listNoticias.clear();
                for (DBNoticias dbNoticias : databaseHelper.getNewsList()) {
                    Noticia noticia = new Noticia();
                    noticia.setUser_id(dbNoticias.getId());
                    noticia.setNewsTitle(dbNoticias.getNewsTitle());
                    noticia.setNewsMessage(dbNoticias.getNewsPost());

                    listNoticias.add(noticia);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                newsRecyclerAdapter.notifyDataSetChanged();

                //Scroll ot position 0, but use size -1 to go to the "top" of the list
                recyclerViewNews.getLayoutManager().scrollToPosition(listNoticias.size()-1);
            }
        }.execute();
    }
}
