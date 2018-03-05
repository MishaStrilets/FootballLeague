package strilets;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.util.ArrayList;

public class MatchAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Match> objects;

    MatchAdapter(Context context, ArrayList<Match> matchesList) {
        ctx = context;
        objects = matchesList;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.item, parent, false);
        }

        Match m = getMatch(position);

        ((TextView) view.findViewById(R.id.textNumber)).setText(m.getNumber());
        ((TextView) view.findViewById(R.id.textNameTeam1)).setText(m.getNameTeam1());
        ((TextView) view.findViewById(R.id.textNameTeam2)).setText(m.getNameTeam2());
        ((EditText) view.findViewById(R.id.textGoalTeam1)).setText(m.getGoalTeam1());
        ((EditText) view.findViewById(R.id.textGoalTeam2)).setText(m.getGoalTeam2());

        return view;
    }

    Match getMatch(int position) {
        return ((Match) getItem(position));
    }
}
