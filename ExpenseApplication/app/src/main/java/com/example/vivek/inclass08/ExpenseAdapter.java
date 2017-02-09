package com.example.vivek.inclass08;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class ExpenseAdapter extends ArrayAdapter<Expense> {
    List<Expense> expensedetails;
    Context mcontext;
    int mresource;



    public ExpenseAdapter(Context context, int resource, List<Expense> expensedetails) {
        super(context, resource,expensedetails);
        this.mcontext = context;
        this.expensedetails = expensedetails;
        this.mresource = resource;
        Log.d("demo", "adapter" + expensedetails.toString());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("demo", "name" + "demo");
        if(convertView==null) {
            LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(mresource, parent, false);
        }
        TextView expensename = (TextView) convertView.findViewById(R.id.textView_expensename_adapter);

        TextView expensecost = (TextView) convertView.findViewById(R.id.textView_expensecost_adapter);
        Expense expense = expensedetails.get(position);


        String name = expense.getExpName();

        String cost = expense.getAmount();
        expensename.setText(""+name);
        expensecost.setText(""+cost);
        return convertView;
    }
}
