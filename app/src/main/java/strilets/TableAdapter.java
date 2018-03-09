package strilets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TableAdapter extends BaseAdapter {

    private Context context;
    private List<Table> table;

    public TableAdapter(Context context, List<Table> table) {
        this.context = context;
        this.table = table;
    }

    @Override
    public int getCount() {
        return table.size();
    }

    @Override
    public Object getItem(int position) {
        return table.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Table team = (Table) getItem(position);
        viewHolder.textPosition.setText(String.valueOf(position + 1));
        viewHolder.textNameTeam.setText(team.getNameTeam());
        viewHolder.textCountGames.setText(String.valueOf(team.getCountGames()));
        viewHolder.textMissedBalls.setText(String.valueOf(team.getMissedBalls()));
        viewHolder.textScoredBalls.setText(String.valueOf(team.getScoredBalls()));
        viewHolder.textDifferenceBalls.setText(String.valueOf(team.getDifferenceBalls()));
        viewHolder.textPoints.setText(String.valueOf(team.getPoints()));

        return convertView;
    }

    private class ViewHolder {
        TextView textPosition;
        TextView textNameTeam;
        TextView textCountGames;
        TextView textMissedBalls;
        TextView textScoredBalls;
        TextView textDifferenceBalls;
        TextView textPoints;

        public ViewHolder(View view) {
            textPosition = (TextView)view.findViewById(R.id.textPosition);
            textNameTeam = (TextView)view.findViewById(R.id.textNameTeam);
            textCountGames = (TextView)view.findViewById(R.id.textCountGames);
            textMissedBalls = (TextView)view.findViewById(R.id.textMissedBalls);
            textScoredBalls = (TextView)view.findViewById(R.id.textScoredBalls);
            textDifferenceBalls = (TextView)view.findViewById(R.id.textDifferenceBalls);
            textPoints = (TextView)view.findViewById(R.id.textPoints);
        }
    }
}
