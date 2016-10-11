package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by user on 10/11/2016.
 */

public class JobsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private DecimalFormat df = new DecimalFormat("#.##");
    View row;
    private List<Trabajo> jobsList;
    Trabajo currentJob;
    Context context;

    JobsAdapter(Context context, List<Trabajo> jobsList) {
        super();
        inflater = LayoutInflater.from(context);
        this.jobsList = jobsList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.jobsList.size();
    }

    @Override
    public Object getItem(int position) {
        return jobsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.jobsList.get(position).getId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        currentJob = (Trabajo) this.getItem(position);
        row = inflater.inflate(R.layout.row, parent, false);

        TextView jobPosition = (TextView) row.findViewById(R.id.jobPosition);
        jobPosition.setText(currentJob.getCategoria().getDescripcion());

        TextView jobDescription = (TextView) row.findViewById(R.id.jobDescription);
        jobDescription.setText(currentJob.getDescripcion());

        TextView jobEstimate = (TextView) row.findViewById(R.id.jobEstimate);
        jobEstimate.setText(context.getResources().getString((R.string.hours_and_rate),
                currentJob.getHorasPresupuestadas(),
                currentJob.getPrecioMaximoHora()));

        TextView jobDueDate = (TextView) row.findViewById(R.id.jobDueDate);
        String jobDueDateString = DateFormat.format("yyyy.MM.dd", currentJob.getFechaEntrega()).toString();
        jobDueDate.setText(context.getResources().getString((R.string.due_date),
                jobDueDateString));

        CheckBox jobIsInEnglish = (CheckBox) row.findViewById(R.id.jobIsInEnglish);
        jobIsInEnglish.setChecked((currentJob.getRequiereIngles() == true)? true : false);

        ImageView jobCurrency = (ImageView) row.findViewById(R.id.jobCurrency);
        switch (currentJob.getMonedaPago()) {
            case 1:
                jobCurrency.setImageResource(R.drawable.flag_us);
                break;
            case 2:
                jobCurrency.setImageResource(R.drawable.flag_eu);
                break;
            case 3:
                jobCurrency.setImageResource(R.drawable.flag_ar);
                break;
            case 4:
                jobCurrency.setImageResource(R.drawable.flag_uk);
                break;
            case 5:
                jobCurrency.setImageResource(R.drawable.flag_br);
                break;
        }
        return(row);
    }
}