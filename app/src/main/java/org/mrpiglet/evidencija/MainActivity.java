package org.mrpiglet.evidencija;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int eurAmount, dinAmount;
    private TextView eurAmountTextView, dinAmountTextView;
    private Switch negativeSwitch;
    private boolean positiveChange = true;

    public enum Currency { EUR, DIN };

    private void updateAmountTextViews() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        eurAmount = sharedPreferences.getInt(getString(R.string.pref_eur_amount_key), 0);
        dinAmount = sharedPreferences.getInt(getString(R.string.pref_din_amount_key), 0);

        eurAmountTextView.setText(Integer.toString(eurAmount));
        dinAmountTextView.setText(Integer.toString(dinAmount));
    }

    void updateAmount(int amount, Currency currency) {
        //write to shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int pref_key = ((currency==Currency.EUR)? (R.string.pref_eur_amount_key) : (R.string.pref_din_amount_key));
        int currAmount = sharedPreferences.getInt(
                getString(pref_key),
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!positiveChange) amount = -amount;
        int totalAmount = currAmount + amount;
        if (totalAmount <= 0) totalAmount = 0;
        editor.putInt(getString(pref_key), totalAmount);
        editor.apply();

        updateAmountTextViews();
    }

    void resetAmounts() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_reset_title))
                .setMessage(getString(R.string.dialog_reset_message))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(R.string.dialog_reset_yes_option, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        //write to shared preferences
                        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt(getString(R.string.pref_eur_amount_key), 0);
                        editor.putInt(getString(R.string.pref_din_amount_key), 0);
                        editor.apply();

                        updateAmountTextViews();
                    }})
                .setNegativeButton(R.string.dialog_reset_no_option, null).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eurAmountTextView = findViewById(R.id.eurAmountTextView);
        dinAmountTextView = findViewById(R.id.dinAmountTextView);

        negativeSwitch = findViewById(R.id.switchNegative);

        negativeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                positiveChange = !isChecked; //if not checked, change is positive
            }
        });

        updateAmountTextViews();

        addButtonListeners();

    }

    @Override
    protected void onResume() {
        super.onResume();

        updateAmountTextViews();
    }

    private void addButtonListeners() {
        final Button resetButton = findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                resetAmounts();
            }
        });


        final Button button25 = (Button) findViewById(R.id.button25);
        button25.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAmount(25, Currency.EUR);
            }
        });

        final Button button35 = (Button) findViewById(R.id.button35);
        button35.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAmount(35, Currency.EUR);
            }
        });

        final Button button45 = (Button) findViewById(R.id.button45);
        button45.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAmount(45, Currency.EUR);
            }
        });

        final Button buttonEurOk = (Button) findViewById(R.id.buttonEurOK);
        buttonEurOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int enteredAmount = 0;
                EditText entryEurEditText = null;
                try {
                    entryEurEditText = (EditText) findViewById(R.id.entryEurEditText);
                    enteredAmount = Integer.parseInt(entryEurEditText.getText().toString());
                } catch (Exception e) {
                    if (entryEurEditText != null) {
                        entryEurEditText.setText("0");
                    }
                }

                updateAmount(enteredAmount, Currency.EUR);
            }
        });

        final Button button3000 = (Button) findViewById(R.id.button3000);
        button3000.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAmount(3000, Currency.DIN);
            }
        });

        final Button button4200 = (Button) findViewById(R.id.button4200);
        button4200.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAmount(4200, Currency.DIN);
            }
        });

        final Button button5400 = (Button) findViewById(R.id.button5400);
        button5400.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateAmount(5400, Currency.DIN);
            }
        });

        final Button buttonDinOk = (Button) findViewById(R.id.buttonDinOK);
        buttonDinOk.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int enteredAmount = 0;
                try {
                    EditText entryDinEditText = (EditText) findViewById(R.id.entryDinEditText);
                    enteredAmount = Integer.parseInt(entryDinEditText.getText().toString());
                } catch (Exception e) {}

                updateAmount(enteredAmount, Currency.DIN);
            }
        });

    }


}
