package strilets;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String LEAGUE_EXIST = "The league already exist.";
    final String NO_LEAGUE = "No league.";
    ListView listTable;
    DBManager db;
    TableAdapter tableAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(R.string.title_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        db = new DBManager(this);
        listTable  = (ListView) findViewById(R.id.listTable);

        if(db.checkMatches())
            Toast.makeText(this, "No league.", Toast.LENGTH_LONG).show();
        else
            showTable();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            if (db.checkMatches()) {
                Intent addTeams = new Intent(this, AddTeamsActivity.class);
                startActivity(addTeams);
            }
            else
                Toast.makeText(this, LEAGUE_EXIST, Toast.LENGTH_LONG).show();
        }  else if (id == R.id.nav_show) {
            if (!db.checkMatches())
                showTable();
            else
                Toast.makeText(this, NO_LEAGUE, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_write) {
            if (!db.checkMatches()) {
                Intent saveResult = new Intent(this, WriteResultActivity.class);
                startActivity(saveResult);
            }
            else
                Toast.makeText(this, NO_LEAGUE, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_delete) {
            db.deleteMatches();
            listTable.setVisibility(View.INVISIBLE);
            Toast.makeText(this, NO_LEAGUE, Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showTable(){
        List<Match> matchesList = db.getAllMatches();
        List<Table> table = new ArrayList<Table>();

        List<String> nameTeam = new ArrayList<String>();
        for(int i = 0; i < matchesList.size(); i++){
            if(!nameTeam.contains(matchesList.get(i).getNameTeam1()))
                nameTeam.add(matchesList.get(i).getNameTeam1());

            if(!nameTeam.contains(matchesList.get(i).getNameTeam2()))
                nameTeam.add(matchesList.get(i).getNameTeam2());
        }

        for(int i = 0; i < nameTeam.size(); i++)
            table.add(new Table(nameTeam.get(i), 0, 0, 0, 0, 0));

        int indexTeam1 = 0, indexTeam2 = 0;
        for(int i = 0; i < matchesList.size(); i++) {
            indexTeam1 = nameTeam.indexOf(matchesList.get(i).getNameTeam1());
            indexTeam2 = nameTeam.indexOf(matchesList.get(i).getNameTeam2());

            if (matchesList.get(i).getGoalTeam1().matches("0|[1-9]+") && matchesList.get(i).getGoalTeam2().matches("0|[1-9]+")) {

                if (Integer.valueOf(matchesList.get(i).getGoalTeam1()) > Integer.valueOf(matchesList.get(i).getGoalTeam2()))
                    table.get(indexTeam1).setPoints(table.get(indexTeam1).getPoints() + 3);

                else if (Integer.valueOf(matchesList.get(i).getGoalTeam1()) < Integer.valueOf(matchesList.get(i).getGoalTeam2()))
                    table.get(indexTeam2).setPoints(table.get(indexTeam2).getPoints() + 3);

                else if (Integer.valueOf(matchesList.get(i).getGoalTeam1()) == Integer.valueOf(matchesList.get(i).getGoalTeam2())) {
                    table.get(indexTeam1).setPoints(table.get(indexTeam1).getPoints() + 1);
                    table.get(indexTeam2).setPoints(table.get(indexTeam2).getPoints() + 1);
                }

                table.get(indexTeam1).setCountGames(table.get(indexTeam1).getCountGames() + 1);
                table.get(indexTeam2).setCountGames(table.get(indexTeam2).getCountGames() + 1);

                table.get(indexTeam1).setMissedBalls(table.get(indexTeam1).getMissedBalls() + Integer.valueOf(matchesList.get(i).getGoalTeam2()));
                table.get(indexTeam2).setMissedBalls(table.get(indexTeam2).getMissedBalls() + Integer.valueOf(matchesList.get(i).getGoalTeam1()));

                table.get(indexTeam1).setScoredBalls(table.get(indexTeam1).getScoredBalls() + Integer.valueOf(matchesList.get(i).getGoalTeam1()));
                table.get(indexTeam2).setScoredBalls(table.get(indexTeam2).getScoredBalls() + Integer.valueOf(matchesList.get(i).getGoalTeam2()));
            }
        }

        for(int i = 0; i < table.size(); i++)
            table.get(i).setDifferenceBalls(table.get(i).getScoredBalls() - table.get(i).getMissedBalls());

        Collections.sort(table);

        tableAdapter = new TableAdapter(this, table);
        listTable.setAdapter(tableAdapter);
    }
}
