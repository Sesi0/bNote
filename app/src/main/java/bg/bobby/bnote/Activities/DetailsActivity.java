package bg.bobby.bnote.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bg.bobby.bnote.Information.DatabaseHelper;
import bg.bobby.bnote.Information.Model.Note;
import bg.bobby.bnote.R;

public class DetailsActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private List<Note> dbList;
    private int position;
    private TextView tvtitle, tvnote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                deleteNote();
                return true;
            case R.id.action_share:
                shareNote();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deleteNote() {
        helper.deleteARow(dbList.get(position).getTitle());
        Toast.makeText(DetailsActivity.this, dbList.get(position).getTitle() + " was deleted.", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(DetailsActivity.this, ListActivity.class));
    }

    private void shareNote() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Title:\n" + tvtitle.getText() + "\nNote:\n" + tvnote.getText());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void init() {
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        position = bundle.getInt("position");

        tvtitle = (TextView) findViewById(R.id.title);
        tvnote = (TextView) findViewById(R.id.note);

        helper = new DatabaseHelper(this);
        dbList = new ArrayList<Note>();
        dbList = helper.getDataFromDB();

        if (dbList.size() > 0) {
            String title = dbList.get(position).getTitle();
            String note = dbList.get(position).getNote();
            tvtitle.setText(title);
            tvnote.setText(note);
        }
    }
}
