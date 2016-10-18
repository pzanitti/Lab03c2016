package ggz.dam.frsf.utn.edu.ar.lab03c2016;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Pablo Zanitti (pzanitti) on 10/14/2016.
 * This activity allows posting a new job opening.
 */

public class PostActivity extends AppCompatActivity implements View.OnClickListener {
    EditText postDescription;
    Button postButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        postDescription = (EditText) findViewById(R.id.jobPostingDescription);
        postButton = (Button) findViewById(R.id.postButton);

        postButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postButton:
                Intent intent = new Intent();
                intent.putExtra("jobDescription", postDescription.getText().toString());
                setResult(RESULT_OK, intent);
                finish();
        }
    }
}
