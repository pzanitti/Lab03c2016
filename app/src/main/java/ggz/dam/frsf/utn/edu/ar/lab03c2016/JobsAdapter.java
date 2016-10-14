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

import java.util.List;

/**
 * Created by Pablo Zanitti (pzanitti) on 10/11/2016.
 * JobsAdapter is used to create a Jobs Listview.
 */

class JobsAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Trabajo> jobsList;
    private Context context;

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
        Trabajo currentJob;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, parent, false);
        }
        currentJob = (Trabajo) this.getItem(position);

        TextView jobPosition = (TextView) convertView.findViewById(R.id.jobPosition);
        jobPosition.setText(currentJob.getCategoria().getDescripcion());

        TextView jobDescription = (TextView) convertView.findViewById(R.id.jobDescription);
        jobDescription.setText(currentJob.getDescripcion());

        TextView jobEstimate = (TextView) convertView.findViewById(R.id.jobEstimate);
        jobEstimate.setText(context.getResources().getString((R.string.hours_and_rate),
                currentJob.getHorasPresupuestadas(),
                currentJob.getPrecioMaximoHora()));

        TextView jobDueDate = (TextView) convertView.findViewById(R.id.jobDueDate);
        String jobDueDateString = DateFormat.format("yyyy.MM.dd", currentJob.getFechaEntrega()).toString();
        jobDueDate.setText(context.getResources().getString((R.string.due_date),
                jobDueDateString));

        CheckBox jobIsInEnglish = (CheckBox) convertView.findViewById(R.id.jobIsInEnglish);
        jobIsInEnglish.setChecked(currentJob.getRequiereIngles());

        ImageView jobCurrency = (ImageView) convertView.findViewById(R.id.jobCurrency);
        switch (currentJob.getMonedaPago()) {
            case 1:
                jobCurrency.setImageResource(R.drawable.flag_us);
                jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_us));
                break;
            case 2:
                jobCurrency.setImageResource(R.drawable.flag_eu);
                jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_eu));
                break;
            case 3:
                jobCurrency.setImageResource(R.drawable.flag_ar);
                jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_ar));
                break;
            case 4:
                jobCurrency.setImageResource(R.drawable.flag_uk);
                jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_uk));
                break;
            case 5:
                jobCurrency.setImageResource(R.drawable.flag_br);
                jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_br));
                break;
        }
        return(convertView);
    }
}