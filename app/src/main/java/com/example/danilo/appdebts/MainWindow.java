package com.example.danilo.appdebts;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.danilo.appdebts.adapters.DebtsAdapter;
import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.dao.CategoryDAO;
import com.example.danilo.appdebts.dao.DebtsDAO;
import com.example.danilo.appdebts.database.DatabaseHelper;

public class MainWindow extends AppCompatActivity {

    RecyclerView mListDebts;
    DebtsAdapter mDebtsAdapter;
    DebtsDAO mDebtsDAO;
    CategoryDAO mCategoryDAO;

    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;
    private ConstraintLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_window);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        mListDebts = findViewById(R.id.recycler_view_debts);
        mLayout = findViewById(R.id.layout);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent cad = new Intent(MainWindow.this,InsertDebts.class);
                    startActivity(cad);

                }
        });
        createConnection();
        populateDatabase();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mListDebts.setLayoutManager(linearLayoutManager);

        mDebtsAdapter = new DebtsAdapter(mDebtsDAO.listDebts());

        mListDebts.setAdapter(mDebtsAdapter);

        mListDebts.setHasFixedSize(true);
    }

    private void createConnection() {
        try {
            mDataHelper = new DatabaseHelper(this);
            mConection = mDataHelper.getWritableDatabase();
            Snackbar.make(mLayout, R.string.sucess_conection, Snackbar.LENGTH_LONG).show();
            mDebtsDAO = new DebtsDAO(mConection);
            mCategoryDAO = new CategoryDAO(mConection);

        } catch (SQLException e) {
            Snackbar.make(mLayout, e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }

    public void populateDatabase(){
        if(mDebtsDAO.listDebts().size()>0) return;
        Category cat1 = new Category("Quitanda");
        Category cat2 = new Category("Lanches");
        Category cat3 = new Category("Empréstimo");
        Category cat4 = new Category("Atividade Física");
        cat1 = mCategoryDAO.insert(cat1);
        cat2 = mCategoryDAO.insert(cat2);
        cat3 = mCategoryDAO.insert(cat3);
        cat4 = mCategoryDAO.insert(cat4);
        mDebtsDAO.insert(new Debts(cat1,(float)79.81,"produtos de limpeza","31/07/2019",""));
        mDebtsDAO.insert(new Debts(cat1,(float)5.7,"óleo de cozinha","31/07/2019","04/07/2019"));
        mDebtsDAO.insert(new Debts(cat1,(float)15.5,"chinelo","31/07/2019",""));
        mDebtsDAO.insert(new Debts(cat1,(float)2.5,"pão","31/07/2019","04/07/2019"));
        mDebtsDAO.insert(new Debts(cat4,(float)70,"natação mensalidade","20/08/2019",""));
        mDebtsDAO.insert(new Debts(cat2,(float)5,"coxinha","25/07/2019",""));
        mDebtsDAO.insert(new Debts(cat2,(float)2,"café","05/07/2019","05/07/2019"));
        mDebtsDAO.insert(new Debts(cat1,(float)31.9,"produtos de higiene","21/07/2019",""));
        mDebtsDAO.insert(new Debts(cat1,(float)3.5,"café solúvel","31/08/2019","04/07/2019"));
        mDebtsDAO.insert(new Debts(cat1,(float)15.5,"chinelo","31/07/2019",""));
        mDebtsDAO.insert(new Debts(cat1,(float)5.5,"pão e chilito","31/08/2019","05/07/2019"));
        mDebtsDAO.insert(new Debts(cat4,(float)70,"natação mensalidade","20/08/2019",""));
        mDebtsDAO.insert(new Debts(cat2,(float)6,"pastel com suco","18/07/2019",""));
        mDebtsDAO.insert(new Debts(cat2,(float)1.5,"suco","09/08/2019","07/07/2019"));

    }

}
