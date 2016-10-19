package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

/**
 * Created by Pablo Zanitti (pzanitti) on 10/14/2016.
 * This activity allows posting a new job opening.
 */

public class PostActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    EditText postDescription, postEstimate, postHourlyRate;
    Button postButton;
    Spinner categorySpinner;
    ArrayAdapter categorySpinnerAdapter;
    RadioGroup postCurrency;
    RadioButton lastButton;
    Trabajo newJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postDescription = (EditText) findViewById(R.id.postDescription);
        categorySpinner = (Spinner) findViewById(R.id.postCategorySpinner);
        postCurrency = (RadioGroup) findViewById(R.id.postCurrency);
        lastButton = (RadioButton) findViewById(R.id.postCurrencyUSD);
        postButton = (Button) findViewById(R.id.postButton);
        postEstimate = (EditText) findViewById(R.id.postEstimate);
        postHourlyRate = (EditText) findViewById(R.id.postHourlyRate);

        postButton.setOnClickListener(this);

        categorySpinnerAdapter = new ArrayAdapter<>(this,
                                                    android.R.layout.simple_spinner_item,
                                                    Categoria.CATEGORIAS_MOCK);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        postCurrency.setOnCheckedChangeListener(this);

        newJob = new Trabajo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postButton:
                boolean hasErrors = false;

                if (TextUtils.isEmpty(postDescription.getText().toString())) {
                    postDescription.setError(getText(R.string.failure_description_empty));
                    hasErrors = true;
                }

                if (postCurrency.getCheckedRadioButtonId() == -1) {
                    lastButton.setError(getText(R.string.failure_currency_unchecked));
                    hasErrors = true;
                }

                if (TextUtils.isEmpty(postEstimate.getText().toString())) {
                    postEstimate.setError(getText(R.string.failure_estimate_empty));
                    hasErrors = true;
                } else if(Integer.parseInt(postEstimate.getText().toString()) == 0) {
                    postEstimate.setError(getText(R.string.failure_estimate_zero));
                    hasErrors = true;
                }

                if(TextUtils.isEmpty(postHourlyRate.getText().toString())) {
                    postHourlyRate.setError(getText(R.string.failure_hourlyrate_empty));
                    hasErrors = true;
                } else if (Float.parseFloat(postHourlyRate.getText().toString()) == 0) {
                    postHourlyRate.setError(getText(R.string.failure_hourlyrate_zero));
                    hasErrors = true;
                }

                if (!hasErrors) {
                    Intent intent = new Intent();
                    intent.putExtra("newJob", newJob);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        lastButton.setError(null);
        switch (checkedId) {
            case R.id.postCurrencyARS:
                newJob.setMonedaPago(Currency.ARS);
                break;
            case R.id.postCurrencyBRL:
                newJob.setMonedaPago(Currency.BRL);
                break;
            case R.id.postCurrencyEUR:
                newJob.setMonedaPago(Currency.EUR);
                break;
            case R.id.postCurrencyGBP:
                newJob.setMonedaPago(Currency.GBP);
                break;
            case R.id.postCurrencyUSD:
                newJob.setMonedaPago(Currency.USD);
                break;
        }
    }
}
