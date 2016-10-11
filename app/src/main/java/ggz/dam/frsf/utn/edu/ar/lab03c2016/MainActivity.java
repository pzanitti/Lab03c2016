package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView jobsListView;
    private ArrayList<Trabajo> jobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobs = new ArrayList<>();
        jobs.addAll(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        jobsListView = (ListView) findViewById(R.id.jobsListView);
        JobsAdapter jobsAdapter = new JobsAdapter(MainActivity.this, jobs);
        jobsListView.setAdapter(jobsAdapter);
    }
}
