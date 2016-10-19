package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ListView jobsListView;
    ArrayList<Trabajo> jobs;
    JobsAdapter jobsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobs = new ArrayList<>();
        jobs.addAll(Arrays.asList(Trabajo.TRABAJOS_MOCK));

        jobsListView = (ListView) findViewById(R.id.jobsListView);
        jobsAdapter = new JobsAdapter(MainActivity.this, jobs);
        jobsListView.setAdapter(jobsAdapter);
        registerForContextMenu(jobsListView);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mainMenuCreate) {
            startActivityForResult(new Intent(this, PostActivity.class), 0);
        } else {
            Toast.makeText(MainActivity.this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            int newJobId = jobs.size() + 1;
            Trabajo newJob = (Trabajo) data.getSerializableExtra("newJob");
            newJob.setId(newJobId);
            jobs.add(newJob);
            jobsAdapter.notifyDataSetChanged();

            Toast.makeText( MainActivity.this,
                            getString(R.string.post_success),
                            Toast.LENGTH_LONG).show();
        }
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo itemInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.contextMenuApply:
                Toast.makeText(this, R.string.application_registered, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.contextMenuShare:
                Trabajo job = jobs.get(itemInfo.position);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String message = String.format(getResources().getString(R.string.share_message),
                            job.getCategoria().getDescripcion(),
                            job.getPrecioMaximoHora());
                sendIntent.putExtra(Intent.EXTRA_TEXT, message);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_listing)));
                return true;
        }
        return false;
    }
}
