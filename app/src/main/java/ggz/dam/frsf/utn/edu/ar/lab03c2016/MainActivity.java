package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
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
            startActivity(new Intent(this, PostActivity.class));
        } else {
            Toast.makeText(MainActivity.this, R.string.not_implemented, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.contextMenuApply:
                Toast.makeText(this, R.string.application_registered, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.contextMenuShare:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_listing)));
                return true;
        }
        return false;
    }
}
