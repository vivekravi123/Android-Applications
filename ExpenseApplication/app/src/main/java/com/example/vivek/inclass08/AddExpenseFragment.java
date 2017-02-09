package com.example.vivek.inclass08;

/*
InClass08
InClass08
Karthikeyan TKR

*/

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddExpenseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.

 */
public class AddExpenseFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    static ArrayList<Expense> expense_added=new ArrayList<>();
    Expense expenseObject;
    int selectedSpinner = 0;
    View view;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
String name;
    String amount;
    private OnFragmentInteractionListener mListener;

    public AddExpenseFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_add_expense, container, false);
        Button add= (Button) view.findViewById(R.id.button_addexpense);
        expense_added=mListener.getexpensedetails();
        Button cancel= (Button) view.findViewById(R.id.buttonCancelExpense);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container_main, new ExpenseAppFragment(), "second").commit();

            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText expensename = (EditText) view.findViewById(R.id.editText_expenseName);
                name = expensename.getText().toString();
                EditText expenseamount = (EditText) view.findViewById(R.id.editText_amount);
                amount = "$"+expenseamount.getText().toString();
                Spinner categoryspin = (Spinner) view.findViewById(R.id.spinnerCategory);


                categoryspin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedSpinner = position - 1;
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                if (!(name.equals("")) && !(amount .equals(""))) {
                    expenseObject = new Expense(name, amount, categoryspin.getSelectedItem().toString().trim());
                    expense_added.add(expenseObject);
                    mListener.setexpensedetails(expense_added);
                    Toast.makeText(getContext(), "Added Expense", Toast.LENGTH_SHORT).show();

                    getFragmentManager().beginTransaction().replace(R.id.container_main, new ExpenseAppFragment(), "second").commit();
                }
                else
                {
                    Toast.makeText(getContext(), "Fill Missing Fields", Toast.LENGTH_SHORT).show();

                }
            }       });
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
        ArrayList<Expense> getexpensedetails();
        void setexpensedetails(ArrayList<Expense> expense_add);
    }
}
