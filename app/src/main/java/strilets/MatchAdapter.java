package strilets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MatchAdapter extends BaseAdapter {

    private Context context;
    private List<Match> matchesList;
    DBManager db;
    final String MATCH_SAVE = "Result match save.";
    final String INVALID_INPUT = "Invalid input.";

    public MatchAdapter(Context context, List<Match> matchesList) {
        this.context = context;
        this.matchesList = matchesList;
    }

    @Override
    public int getCount() {
        return matchesList.size();
    }

    @Override
    public Object getItem(int position) {
        return matchesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Match currentMatch = (Match) getItem(position);
        viewHolder.textNameTeam1.setText(currentMatch.getNameTeam1());
        viewHolder.textNameTeam2.setText(currentMatch.getNameTeam2());
        viewHolder.editGoalTeam1.setText(currentMatch.getGoalTeam1());
        viewHolder.editGoalTeam2.setText(currentMatch.getGoalTeam2());
        viewHolder.textNumberMatch.setText(String.valueOf(currentMatch.getNumberMatch()));
        viewHolder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewHolder.editGoalTeam1.getText().toString().matches("|0|[1-9]+") && viewHolder.editGoalTeam2.getText().toString().matches("|0|[1-9]+")) {
                    db = new DBManager(context);

                    Match editMatch = new Match();
                    editMatch.setNumberMatch(currentMatch.getNumberMatch());
                    editMatch.setGoalTeam1(viewHolder.editGoalTeam1.getText().toString());
                    editMatch.setGoalTeam2(viewHolder.editGoalTeam2.getText().toString());

                    db.updateTask(editMatch);

                    Toast.makeText(context, MATCH_SAVE, Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(context, INVALID_INPUT, Toast.LENGTH_LONG).show();
            }
        });

        return convertView;
    }

    private class ViewHolder {
        TextView textNumberMatch;
        TextView textNameTeam1;
        TextView textNameTeam2;
        EditText editGoalTeam1;
        EditText editGoalTeam2;
        Button btnSave;

        public ViewHolder(View view) {
            textNumberMatch = (TextView)view.findViewById(R.id.textNumberMatch);
            textNameTeam1 = (TextView)view.findViewById(R.id.textNameTeam1);
            textNameTeam2 = (TextView)view.findViewById(R.id.textNameTeam2);
            editGoalTeam1 = (EditText)view.findViewById(R.id.editGoalTeam1);
            editGoalTeam2 = (EditText)view.findViewById(R.id.editGoalTeam2);
            btnSave = (Button)view.findViewById(R.id.btnSave);
        }
    }
}
