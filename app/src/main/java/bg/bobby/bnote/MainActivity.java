package bg.bobby.bnote;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
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

public class MainActivity extends AppCompatActivity {
    EditText etTitle,etNote;
    Button btnSubmit,btngetdata;
    DatabaseHelpher helpher;
    List<DatabaseModel> dbList;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.tool_bar); // Attaching the layout to the toolbar object
        setSupportActionBar(toolbar);
        dbList= new ArrayList<DatabaseModel>();
        etTitle = (EditText)findViewById(R.id.etTitle);
        etNote = (EditText)findViewById(R.id.etNote);

        btnSubmit  =(Button)findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title=etNote.getText().toString();
                String note=etTitle.getText().toString();


                if(title.equals("") || note.equals("")){
                    Toast.makeText(MainActivity.this,"Please fill all the fields",Toast.LENGTH_LONG).show();
                }else {
                    helpher = new DatabaseHelpher(MainActivity.this);
                    helpher.insertIntoDB(title, note);
                }


                Toast.makeText(MainActivity.this, "insert value", Toast.LENGTH_LONG);

            }
        });
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
                return true;


        }

        return super.onOptionsItemSelected(item);
    }

}
