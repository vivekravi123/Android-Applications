package com.example.vivek.firebasestart;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class ExpenseAdapter extends ArrayAdapter<Expenses> {
    Expenses expObj;
    List<Expenses> mExpenses;
    Context mContext;
    int mResource;
    String userid;
    DatabaseReference mDatabase;

    public ExpenseAdapter(Context context, int resource, List<Expenses> objects, String userId, DatabaseReference mDatabase) {


        super(context, resource, objects);

        this.mContext = context;
        this.mExpenses = objects;
        this.mResource = resource;
        this.userid = userId;
        this.mDatabase = mDatabase;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mResource, parent, false);
        }

        expObj = mExpenses.get(position);
        TextView expName = (TextView) convertView.findViewById(R.id.text_AddExpenseName);
        expName.setText(expObj.exp_name);


        TextView expAmount = (TextView) convertView.findViewById(R.id.text_AddExpenseAmount);
        expAmount.setText("$" + expObj.amount);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent start = new Intent(mContext, ShowExpenses.class);
                start.putExtra("Name", expObj.exp_name);
                start.putExtra("Category", expObj.category);
                start.putExtra("Amount", expObj.amount);
                start.putExtra("Date", expObj.date);
                mContext.startActivity(start);

            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Log.d("demo", "in adapter long lcikc");

                mDatabase.child(userid).child(expObj.exp_name).setValue(null);

                mExpenses.remove(position);

                return true;
            }
        });
        return convertView;
    }
}
