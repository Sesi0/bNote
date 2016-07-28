package bg.bobby.bnote.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bg.bobby.bnote.Information.DatabaseHelper;
import bg.bobby.bnote.Information.Model.Note;
import bg.bobby.bnote.R;

public class MainActivity extends AppCompatActivity {
    private EditText etTitle, etNote;
    private Button btnSubmit;
    private DatabaseHelper helper;
    private List<Note> dbList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        dbList = new ArrayList<Note>();
        etTitle = (EditText) findViewById(R.id.etTitle);
        etNote = (EditText) findViewById(R.id.etNote);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        if (isInDatabase(etTitle.getText().toString())) {
            Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
        } else {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String title = etNote.getText().toString();
                    String note = etTitle.getText().toString();


                    if (title.equals("") || note.equals("")) {
                        Toast.makeText(MainActivity.this, "Please fill all the fields", Toast.LENGTH_LONG).show();
                    } else {
                        if (dbList.contains(title)) {
                            Toast.makeText(MainActivity.this, "\"" + title + "\" is already added.", Toast.LENGTH_LONG).show();
                        } else {
                            helper = new DatabaseHelper(MainActivity.this);
                            helper.insertIntoDB(title, note);
                            Toast.makeText(MainActivity.this, "Note added", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_list:
                startActivity(new Intent(MainActivity.this, ListActivity.class));
                return true;

            case R.id.action_clear:
                etNote.setText("");
                etTitle.setText("");
                Toast.makeText(MainActivity.this, "Text cleared", Toast.LENGTH_SHORT).show();
                return true;


        }

        return super.onOptionsItemSelected(item);
    }
    private boolean isInDatabase(String title){
        for (int i = 0; i < dbList.size(); i++){
            if (dbList.get(i).getTitle() == title){
                return true;
            }
        }
        return false;
    }
}
