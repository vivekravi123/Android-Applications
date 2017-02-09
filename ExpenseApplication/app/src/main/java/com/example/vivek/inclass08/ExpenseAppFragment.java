package com.example.vivek.inclass08;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExpenseAppFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class ExpenseAppFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Expense> expenses_list = new ArrayList<Expense>();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ExpenseAdapter adapter;
    private OnFragmentInteractionListener mListener;

    public ExpenseAppFragment() {
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
        View view = inflater.inflate(R.layout.fragment_expense_app, container, false);
        expenses_list = mListener.getexpenselist();
        Log.d("demo", "list" + expenses_list.toString());
        ListView expenses = (ListView) view.findViewById(R.id.listView_expense_adapter);
        ImageView add = (ImageView) view.findViewById(R.id.imageView);

        if (expenses_list.size() > 0) {
            adapter = new ExpenseAdapter(getActivity(), R.layout.expenselistview, expenses_list);
            expenses.setAdapter(adapter);
            Log.d("demo", "ran");
            TextView remove = (TextView) view.findViewById(R.id.textviewrem);
            remove.setText("");
        }


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("demo", "clicked");
                getFragmentManager().beginTransaction().replace(R.id.container_main, new AddExpenseFragment(), "second").commit();
            }
        });
        expenses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("demo", "pos" + position);
                Bundle bundle = new Bundle();
                bundle.putString("pos", String.valueOf(position));
                ShowExpenseFragment fragment = new ShowExpenseFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.container_main, fragment, "second").addToBackStack(null).commit();

            }
        });
        expenses.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                expenses_list.remove(position);
                Toast.makeText(getContext(), "Deleted Expense", Toast.LENGTH_SHORT).show();
                getFragmentManager().beginTransaction().replace(R.id.container_main, new ExpenseAppFragment(), "second").addToBackStack(null).commit();
                return false;
            }
        });
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

        public ArrayList<Expense> getexpenselist();
    }
}

