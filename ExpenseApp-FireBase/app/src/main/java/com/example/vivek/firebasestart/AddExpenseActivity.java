package com.example.vivek.firebasestart;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class AddExpenseActivity extends AppCompatActivity {




    EditText Expense_name;
    Spinner category;
    EditText amount;
    Button addexpense;
    Button cancel;

    ArrayList<Expenses> expensesList = new ArrayList<>();


    String category_text;
    String expense_name_text;
    String Amount_text;


    final static String NEW_EXPENSE_STRING = "New Expense";


    Expenses expensesNew;
    private DatabaseReference mDatabase;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        assignViews1();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        userId = getIntent().getExtras().getString("userId");


    }

    private void assignViews1() {

        Expense_name = (EditText) findViewById(R.id.e_expensename);
        category = (Spinner) findViewById(R.id.e_spinner);
        amount = (EditText) findViewById(R.id.e_amount);
        addexpense = (Button) findViewById(R.id.button_add_expense);
        cancel = (Button) findViewById(R.id.button_Cancel_expense);
    }

    public void expenseAddAction(View view) {

        expense_name_text = Expense_name.getText().toString();

        category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category_text = category.getSelectedItem().toString();
                Log.d("demo","catoregory" + category_text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        Amount_text = amount.getText().toString();

        expensesNew = new Expenses(expense_name_text, category_text, Amount_text);


        mDatabase.child(userId).child(expense_name_text).setValue(expensesNew);

        Toast.makeText(getApplicationContext(), "Expense Added", Toast.LENGTH_SHORT).show();

        Intent start = new Intent();

        start.putExtra(NEW_EXPENSE_STRING, (Parcelable) expensesNew);
        setResult(RESULT_OK, start);
        finish();


    }


    public void CancelAction(View view) {

    }
}
