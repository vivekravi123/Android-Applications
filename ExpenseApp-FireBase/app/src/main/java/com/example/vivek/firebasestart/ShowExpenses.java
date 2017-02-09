package com.example.vivek.firebasestart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowExpenses extends AppCompatActivity {

    String date,category,exp_name,amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_expenses);

        TextView name = (TextView) findViewById(R.id.name_textview_s);
        TextView cate = (TextView) findViewById(R.id.category_text_s);
        TextView amt = (TextView) findViewById(R.id.amount_textview_s);
        TextView dt = (TextView) findViewById(R.id.date_textview_s);


        exp_name = getIntent().getExtras().getString("Name");
        category = getIntent().getExtras().getString("Category");
        amount = getIntent().getExtras().getString("Amount");
        date = getIntent().getExtras().getString("Date");


        name.setText(exp_name);
        cate.setText(category);
        amt.setText("$"+amount);
        dt.setText(""+date);






    }

    private void assignView() {



    }
}
