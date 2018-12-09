package org.mrpiglet.evidencija;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
        int pref_key = (currency==Currency.EUR)? R.string.pref_eur_amount_key : R.string.pref_din_amount_key;
        int currAmount = sharedPreferences.getInt(
                getString(pref_key),
                0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (!positiveChange) amount = -amount;
        editor.putInt(getString(R.string.pref_eur_amount_key), currAmount + amount);
        editor.apply();

        updateAmountTextViews();
    }

    void resetAmounts() {
        //write to shared preferences
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(getString(R.string.pref_eur_amount_key), 0);
        editor.putInt(getString(R.string.pref_din_amount_key), 0);
        editor.apply();

        updateAmountTextViews();
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
                try {
                    EditText entryEurEditText = (EditText) findViewById(R.id.entryEurEditText);
                    enteredAmount = Integer.parseInt(entryEurEditText.getText().toString());
                } catch (Exception e) {}

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

    }


}
