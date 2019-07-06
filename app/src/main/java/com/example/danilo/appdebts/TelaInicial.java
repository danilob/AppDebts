package com.example.danilo.appdebts;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.example.danilo.appdebts.classes.Category;
import com.example.danilo.appdebts.classes.Debts;
import com.example.danilo.appdebts.dao.CategoryDAO;
import com.example.danilo.appdebts.dao.DebtsDAO;
import com.example.danilo.appdebts.database.DatabaseHelper;


public class TelaInicial extends AppCompatActivity {

    private SQLiteDatabase mConection;
    private DatabaseHelper mDataHelper;
    private ConstraintLayout mLayout;
    private CategoryDAO mCategoryDAO;
    private DebtsDAO mDebtsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        mLayout = findViewById(R.id.layout);

        createConnection();
        //testando
//        Category cat = new Category("Energia");
//        Category cat2 = new Category("Lanches");
        CategoryDAO catDAO = new CategoryDAO(mConection);
        Category cat = catDAO.getCategory(2);
        //Log.d("CategoryDAO",cat);
        Debts debt = new Debts(cat,10,"Sanduíche Natural","12/07/2019","02/07/2019");
        DebtsDAO debDAO = new DebtsDAO(mConection);
        debDAO.insert(debt);

//        catDAO.insert(cat);
//        catDAO.insert(cat2);
//        catDAO.listCategories();

    }

    private void createConnection() {
        try {
            mDataHelper = new DatabaseHelper(this);
            mConection = mDataHelper.getWritableDatabase();
            Snackbar.make(mLayout, R.string.sucess_conection, Snackbar.LENGTH_LONG).show();
            mCategoryDAO = new CategoryDAO(mConection);
            mDebtsDAO = new DebtsDAO(mConection);

        } catch (SQLException e) {
            Snackbar.make(mLayout, e.toString(), Snackbar.LENGTH_LONG).show();
        }
    }

    public void populateDatabase(){
        createConnection();
        Category cat1 = new Category("Quitanda");
        cat1 = mCategoryDAO.insert(cat1);

        Debts debt1 = new Debts(cat1,(float)79.81,"produtos de limpeza","31/07/2019","");
        debt1 = mDebtsDAO.insert(debt1);
    }


}
