package strilets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import java.util.ArrayList;

public class MatchAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Match> matchesList;

    public MatchAdapter(Context context, ArrayList<Match> matchesList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_match, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Match currentMatch = (Match) getItem(position);
        viewHolder.Number.setText(String.valueOf(currentMatch.getNumber()));
        viewHolder.NameTeam1.setText(currentMatch.getNameTeam1());
        viewHolder.NameTeam2.setText(currentMatch.getNameTeam2());

        if(currentMatch.getGoalTeam1() == -1)
            viewHolder.GoalTeam1.setText(" ");
        else
            viewHolder.GoalTeam1.setText(String.valueOf(currentMatch.getGoalTeam1()));

        if(currentMatch.getGoalTeam2() == -1)
            viewHolder.GoalTeam2.setText(" ");
        else
            viewHolder.GoalTeam2.setText(String.valueOf(currentMatch.getGoalTeam2()));

        return convertView;
    }

    private class ViewHolder {
        TextView Number;
        TextView NameTeam1;
        TextView NameTeam2;
        EditText GoalTeam1;
        EditText GoalTeam2;

        public ViewHolder(View view) {
            Number = (TextView)view.findViewById(R.id.textNumber);
            NameTeam1 = (TextView)view.findViewById(R.id.textNameTeam1);
            NameTeam2 = (TextView)view.findViewById(R.id.textNameTeam2);
            GoalTeam1 = (EditText)view.findViewById(R.id.textGoalTeam1);
            GoalTeam2 = (EditText)view.findViewById(R.id.textGoalTeam2);
        }
    }
}
