package bg.bobby.bnote.Information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import bg.bobby.bnote.Activities.DetailsActivity;
import bg.bobby.bnote.Information.Model.Note;
import bg.bobby.bnote.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    static List<Note> dbList;
    static Context context;

    public RecyclerAdapter(Context context, List<Note> dbList) {
        this.dbList = new ArrayList<Note>();
        this.context = context;
        this.dbList = dbList;

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_row, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        holder.title.setText(dbList.get(position).getTitle());
        holder.note.setText(dbList.get(position).getNote());

    }

    @Override
    public int getItemCount() {
        return dbList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView title, note;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView
                    .findViewById(R.id.rvtitle);
            note = (TextView) itemLayoutView.findViewById(R.id.rvnote);
            itemLayoutView.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, DetailsActivity.class);

            Bundle extras = new Bundle();
            extras.putInt("position", getAdapterPosition());
            intent.putExtras(extras);

            context.startActivity(intent);
        }
    }
}
