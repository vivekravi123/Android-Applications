package com.example.vivek.firebasestart;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ExpenseActivity extends AppCompatActivity {

    ArrayList<Expenses> expenses = new ArrayList<>();
    String userId;

    TextView noExpText;
    ListView listExpView;

    String strNoExp;
    private DatabaseReference mDatabase;



    private final static int CREATE_REQ_CODE = 100;
    private final static int DELETE_REQ_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        assignView();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        expenses = getIntent().getParcelableArrayListExtra("AddExpense");
        userId = getIntent().getExtras().getString("userId");

        GetDataFromDatabase();

        listExpView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Log.d("demo","longclick");

                Expenses o = expenses.get(i);


                mDatabase.child(userId).child(o.exp_name).setValue(null);

                expenses.remove(i);

                AddtoList();

                return false;
            }
        });

    }



    private void GetDataFromDatabase() {

// Attach a listener to read the data at our posts reference
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("Count ", "count child" + dataSnapshot.child(userId).getChildrenCount());

               for (DataSnapshot postSnapshot : dataSnapshot.child(userId).getChildren()) {
                   HashMap<String,String> map = (HashMap<String, String>) postSnapshot.getValue();

                    Expenses post = new Expenses(map);

                   expenses.add(post);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("demo", "The read failed: " + databaseError.getCode());
            }
        });
        AddtoList();
    }


    private void assignView() {

        noExpText = (TextView) findViewById(R.id.textViewNoexpense);
        listExpView = (ListView) findViewById(R.id.listView);
    }

    public void addExpenseaction(View view) {
        Intent start = new Intent(ExpenseActivity.this, AddExpenseActivity.class);
        start.putExtra("userId", userId);
        startActivityForResult(start, CREATE_REQ_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_REQ_CODE) {
            if (resultCode == RESULT_OK) {
                noExpText.setVisibility(View.INVISIBLE);
                expenses.add((Expenses) data.getParcelableExtra(AddExpenseActivity.NEW_EXPENSE_STRING));
                Toast.makeText(this, "Expense Added", Toast.LENGTH_SHORT).show();

                AddtoList();

            } else {
                Toast.makeText(getApplicationContext(), "Expense CANCELLED", Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void AddtoList() {


        ExpenseAdapter adapter = new ExpenseAdapter(ExpenseActivity.this, R.layout.listviewexpense, expenses,userId,mDatabase);
        listExpView.setAdapter(adapter);
        adapter.setNotifyOnChange(true);


    }



    }
