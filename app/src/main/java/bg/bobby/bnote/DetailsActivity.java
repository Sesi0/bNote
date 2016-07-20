package bg.bobby.bnote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {
    DatabaseHelpher helpher;
    List<DatabaseModel> dbList;
    int position;
    TextView tvtitle,tvnote;
    Button buttonDelete, buttonShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        // 5. get status value from bundle
        position = bundle.getInt("position");

        tvtitle =(TextView)findViewById(R.id.title);
        tvnote =(TextView)findViewById(R.id.note);

        helpher = new DatabaseHelpher(this);
        dbList= new ArrayList<DatabaseModel>();
        dbList = helpher.getDataFromDB();

        if(dbList.size()>0){
            String title= dbList.get(position).getTitle();
            String note=dbList.get(position).getNote();
            tvtitle.setText(title);
            tvnote.setText(note);

        }

        Toast.makeText(DetailsActivity.this, dbList.toString(), Toast.LENGTH_LONG).show();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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

    private void deleteNote(){
        helpher.deleteARow(dbList.get(position).getTitle());
        startActivity(new Intent(DetailsActivity.this, ListActivity.class));
    }

    private void shareNote(){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Title:\n" + tvtitle.getText() + "\nNote:\n" + tvnote.getText());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
