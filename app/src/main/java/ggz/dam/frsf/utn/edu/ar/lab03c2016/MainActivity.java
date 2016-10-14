package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemLongClickListener {
    ListView jobsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Trabajo> jobs;

        jobs = new ArrayList<>();
        jobs.addAll(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        jobsListView = (ListView) findViewById(R.id.jobsListView);
        JobsAdapter jobsAdapter = new JobsAdapter(MainActivity.this, jobs);
        jobsListView.setAdapter(jobsAdapter);
        jobsListView.setOnItemLongClickListener(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mainMenuCreate) {
            Toast.makeText(MainActivity.this, "CREATE", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(MainActivity.this, ((TextView) view.findViewById(R.id.jobDescription)).getText().toString(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
