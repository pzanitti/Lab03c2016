package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pablo Zanitti (pzanitti) on 10/14/2016.
 * This activity allows posting a new job opening.
 */

public class PostActivity extends AppCompatActivity implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    EditText postDescription, postEstimate, postHourlyRate;
    Button postDueDateButton, postButton;
    Spinner categorySpinner;
    ArrayAdapter categorySpinnerAdapter;
    RadioGroup postCurrency;
    RadioButton lastButton;
    Trabajo newJob;
    TextView postDueDate;

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
        postDueDate = (TextView) findViewById(R.id.postDueDate);
        postDueDateButton = (Button) findViewById(R.id.postDueDateButton);

        postButton.setOnClickListener(this);
        postDueDateButton.setOnClickListener(this);

        categorySpinnerAdapter = new ArrayAdapter<>(this,
                                                    android.R.layout.simple_spinner_item,
                                                    Categoria.CATEGORIAS_MOCK);
        categorySpinner.setAdapter(categorySpinnerAdapter);

        postCurrency.setOnCheckedChangeListener(this);

        newJob = new Trabajo();

        final Calendar c = Calendar.getInstance();

        String postDueDateString = DateFormat.format("yyyy-MM-dd", c).toString();
        postDueDate.setText(getResources().getString((R.string.due_date),
                            postDueDateString));
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putLong("dueDate", newJob.getFechaEntrega().getTime());
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        newJob.setFechaEntrega(new Date(savedInstanceState.getLong("dueDate")));

        Calendar c = Calendar.getInstance();

        c.setTime(newJob.getFechaEntrega());
        String postDueDateString = DateFormat.format("yyyy-MM-dd", c).toString();
        postDueDate.setText(getResources().getString((R.string.due_date),
                postDueDateString));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postDueDateButton:
                showDatePickerDialog(v);
                break;
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
                } else if (Double.parseDouble(postHourlyRate.getText().toString()) == 0) {
                    postHourlyRate.setError(getText(R.string.failure_hourlyrate_zero));
                    hasErrors = true;
                }

                Calendar c = Calendar.getInstance();
                if (c.getTime().after(newJob.getFechaEntrega())) {
                    postDueDate.requestFocus();
                    postDueDate.setError(getText(R.string.failure_due_date));
                    hasErrors = true;
                } else {
                    postDueDate.setError(null);
                }

                if (!hasErrors) {
                    Intent intent = new Intent();

                    newJob.setDescripcion(postDescription.getText().toString());
                    newJob.setCategoria((Categoria) categorySpinner.getSelectedItem());
                    newJob.setHorasPresupuestadas(Integer.parseInt(postEstimate.getText().toString()));
                    newJob.setPrecioMaximoHora(Double.parseDouble(postHourlyRate.getText().toString()));

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

    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year, month, day);
            Date d = c.getTime();
            newJob.setFechaEntrega(d);

            String jobDueDateString = DateFormat.format("yyyy-MM-dd", d).toString();
            TextView postDueDate = (TextView) getActivity().findViewById(R.id.postDueDate);
            postDueDate.setText(getResources().getString((R.string.due_date),
                                jobDueDateString));
        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }
}
