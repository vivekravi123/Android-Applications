// HOME ASSIGNMENT 1
//HW1
//VIVEK RAVI, SAHIL CHAUHAN




package com.example.vivek.hw1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity_bac extends AppCompatActivity {
    float lbsweight=0;
    float alcoholcontval=5;
    String gender = "F";
    float genderval = (float)0.55;
    double Ounces=1;
    float bacval=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_bac);

       RadioGroup drinksize = (RadioGroup)findViewById(R.id.radioGroup);
         EditText weight   = (EditText)findViewById(R.id.editTextweight);
        final Switch gender = (Switch) findViewById(R.id.switch1gender);
       final Button savebtn = (Button) findViewById(R.id.buttonsave);
        final SeekBar alcoholcont = (SeekBar) findViewById(R.id.seekBaralcohol);
        final EditText seekvalue = (EditText)findViewById(R.id.editTextalcoholindicator);
        final Button resetbtn = (Button)findViewById(R.id.button2reset);
        final Button drinkbtn = (Button) findViewById(R.id.buttonadddrink);
final ProgressBar bacbar = (ProgressBar)findViewById(R.id.progressBarbac);
        EditText baclabel = (EditText)findViewById(R.id.editTextbac);


        savebtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        //to retrieve the weightvalue
        EditText weight   = (EditText)findViewById(R.id.editTextweight);


        if (weight.length()==0) {

            Toast.makeText(getApplicationContext(), "Weight Cannot be zero or empty", Toast.LENGTH_SHORT).show();
            weight.setError("Weight cannot be empty");
        } else {

            String weights = weight.getText().toString();
            Switch gen = (Switch) findViewById(R.id.switch1gender);


            lbsweight = Float.parseFloat(weights);
            Log.d("demo", String.valueOf(lbsweight));
        }


        // log.d("demo", lbsweight);
        //to retrieve the gender value


    }
});


        drinksize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                RadioButton ounce = (RadioButton) findViewById(checkedId);
                if (ounce.getText().equals("1 oz")) {
                    Ounces = 1.0;
                }
                if (ounce.getText().equals("5 oz")) {
                    Ounces = 5.0;
                }
                if (ounce.getText().equals("12 oz")) {
                    Ounces = 12.0;
                }

            }
        });

        //switch gadget logiic

        gender.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    genderval = (float) 0.68;
                } else {
                    genderval = (float) 0.55;

                }
            }
        });


//rset button
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bacval=(float) 0.0;
                EditText weight   = (EditText)findViewById(R.id.editTextweight);
                weight.setText("");
                gender.setChecked(false);

                //Add drink button enabled.
                drinkbtn.setEnabled(true);
                alcoholcont.setProgress(5);
                RadioButton oneoz= (RadioButton) findViewById(R.id.radioButtononeoz);
                oneoz.setChecked(true);
                RadioButton fiveoz= (RadioButton) findViewById(R.id.radioButton2fiveoz);
                fiveoz.setChecked(false);
                RadioButton twelveoz= (RadioButton) findViewById(R.id.radioButton3twelveoz);
                twelveoz.setChecked(false);
                EditText bacvaluereset= (EditText) findViewById(R.id.editTextbac);
                bacvaluereset.setText("0.00");
                EditText bacstatusreset= (EditText) findViewById(R.id.editTextstatuslabel);
                bacstatusreset.setText("You are safe");
                bacstatusreset.setBackgroundColor(Color.GREEN);
                bacbar.setProgress(0);
                seekvalue.setText("5 %");
            }
        });



        alcoholcont.setProgress(1);
        alcoholcont.incrementProgressBy(5);
        alcoholcont.setMax(5);
       // EditText alcoholindicator   = (EditText)findViewById(R.id.editTextalcoholindicator);
        //alcoholindicator.setText("5 %");


        alcoholcont.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

               //progress = progress/5;
                progress = progress*5;



                seekvalue.setText(String.valueOf(progress)+"%");
                alcoholcontval = progress;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




//drink button logic





        drinkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText status = (EditText) findViewById(R.id.editTextstatuslabel);


                try {
                    if (lbsweight > 0 || lbsweight != 0) {

                        bacval = (float) ((float) bacval + (((alcoholcontval / 100) * Ounces * 6.24) / (lbsweight * genderval)));
                    } else
                        throw new ArithmeticException();
                } catch (ArithmeticException e) {
                    Toast.makeText(getApplicationContext(), "Weight Cannot be zero or empty", Toast.LENGTH_SHORT).show();

                }

                EditText bac = (EditText) findViewById(R.id.editTextbac);
                bac.setText("" + bacval);
                bacbar.setMax(25);
                int bacbarvalue = (int) (bacval * 100);
                bacbar.setProgress((int) bacbarvalue);


                // Log.d("demo","weightvalue is"+ String.valueOf(weightvalue));
                //Log.d("demo", "alcohol value is"+String.valueOf(alcohol_content));
                //Log.d("demo", "Ounces valuses is" +String.valueOf(ounces));
                //Log.d("demo", "gendervalue is"+String.valueOf(gendervalue));

                if (bacval <= 0.08) {
                    status.setBackgroundColor(Color.GREEN);
                    status.setText("You are safe");

                }
                if (bacval > 0.08 && bacval <= 0.20) {
                    status.setBackgroundColor(Color.YELLOW);
                    status.setText("Be Careful");
                }
                if (bacval > 0.20) {
                    status.setBackgroundColor(Color.RED);
                    status.setText("Over the Limit");
                }

                if (bacval >= 0.25) {
                    drinkbtn.setEnabled(false);

                    Toast.makeText(getApplicationContext(), "No more drinks for you", Toast.LENGTH_SHORT).show();
                }


            }
        });



    //oncreate close
    }
}
