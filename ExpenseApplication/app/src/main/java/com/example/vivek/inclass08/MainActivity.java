package com.example.vivek.inclass08;

/*
InClass08
Vivek Ravi

*/

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExpenseAppFragment.OnFragmentInteractionListener, AddExpenseFragment.OnFragmentInteractionListener,ShowExpenseFragment.OnFragmentInteractionListener {
    ArrayList<Expense> expensedetails = new ArrayList<Expense>();
    ImageView add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager(

        ).beginTransaction().add(R.id.container_main, new ExpenseAppFragment(), "first").commit();
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount()>0)

        {
         getFragmentManager().popBackStack();
        }
    else
        {
            super.onBackPressed();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public ArrayList<Expense> getexpensedetails() {
        return expensedetails;
    }

    @Override
    public void setexpensedetails(ArrayList<Expense> expense_add) {
        expensedetails = expense_add;
    }

    @Override
    public ArrayList<Expense> getexpenselist() {
        return expensedetails;
    }
}
