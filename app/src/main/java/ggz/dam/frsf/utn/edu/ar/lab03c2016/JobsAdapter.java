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

    private static class ViewHolderItem {
        TextView jobPosition;
        TextView jobDescription;
        TextView jobEstimate;
        TextView jobDueDate;
        CheckBox jobIsInEnglish;
        ImageView jobCurrency;
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
        ViewHolderItem viewHolderItem;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, parent, false);
            viewHolderItem = new ViewHolderItem();
            viewHolderItem.jobPosition = (TextView) convertView.findViewById(R.id.jobPosition);
            viewHolderItem.jobDescription = (TextView) convertView.findViewById(R.id.jobDescription);
            viewHolderItem.jobEstimate = (TextView) convertView.findViewById(R.id.jobEstimate);
            viewHolderItem.jobDueDate = (TextView) convertView.findViewById(R.id.jobDueDate);
            viewHolderItem.jobIsInEnglish = (CheckBox) convertView.findViewById(R.id.jobIsInEnglish);
            viewHolderItem.jobCurrency = (ImageView) convertView.findViewById(R.id.jobCurrency);
            convertView.setTag(viewHolderItem);
        } else {
            viewHolderItem = (ViewHolderItem) convertView.getTag();
        }

        currentJob = (Trabajo) this.getItem(position);

        viewHolderItem.jobPosition.setText(currentJob.getCategoria().getDescripcion());
        viewHolderItem.jobDescription.setText(currentJob.getDescripcion());
        viewHolderItem.jobEstimate.setText(context.getResources().getString((R.string.hours_and_rate),
                currentJob.getHorasPresupuestadas(),
                currentJob.getPrecioMaximoHora()));

        String jobDueDateString = DateFormat.format("yyyy.MM.dd", currentJob.getFechaEntrega()).toString();
        viewHolderItem.jobDueDate.setText(context.getResources().getString((R.string.due_date),
                jobDueDateString));

        viewHolderItem.jobIsInEnglish.setChecked(currentJob.getRequiereIngles());

        switch (currentJob.getMonedaPago()) {
            case 1:
                viewHolderItem.jobCurrency.setImageResource(R.drawable.flag_us);
                viewHolderItem.jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_us));
                break;
            case 2:
                viewHolderItem.jobCurrency.setImageResource(R.drawable.flag_eu);
                viewHolderItem.jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_eu));
                break;
            case 3:
                viewHolderItem.jobCurrency.setImageResource(R.drawable.flag_ar);
                viewHolderItem.jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_ar));
                break;
            case 4:
                viewHolderItem.jobCurrency.setImageResource(R.drawable.flag_uk);
                viewHolderItem.jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_uk));
                break;
            case 5:
                viewHolderItem.jobCurrency.setImageResource(R.drawable.flag_br);
                viewHolderItem.jobCurrency.setContentDescription(context.getResources().getString(R.string.flag_br));
                break;
        }
        return(convertView);
    }
}