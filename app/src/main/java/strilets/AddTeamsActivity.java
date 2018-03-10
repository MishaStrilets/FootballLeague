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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class AddTeamsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String INVALID_INPUT = "Invalid input.";
    final String NO_LEAGUE = "No league.";
    final String LEAGUE_CREATE = "League created.";
    Button btnAdd;
    EditText editTeams;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teams);
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
        editTeams = (EditText) findViewById(R.id.editTeams);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTeams(editTeams.getText().toString().split("; "));
            }
        });
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

        if (id == R.id.nav_write) {
            if (!db.checkMatches()) {
                Intent saveResult = new Intent(this, WriteResultActivity.class);
                startActivity(saveResult);
            }
            else
                Toast.makeText(this, NO_LEAGUE, Toast.LENGTH_LONG).show();
        } else if (id == R.id.nav_show) {
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else if (id == R.id.nav_delete) {
            db.deleteMatches();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void addTeams(String[] teams ) {
        int countMatches = 0;
        int numberMatches = 1;

        if (teams.length == 0 || teams.length == 1) {
            Toast.makeText(this, INVALID_INPUT, Toast.LENGTH_LONG).show();
            return;
        }

        for (int i = 0; i < teams.length - 1; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                if (teams[i].equals(teams[j])) {
                    Toast.makeText(this, INVALID_INPUT, Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }

        for (int i = 1; i < teams.length; i++)
            countMatches += i;

        ArrayList<Match> matchesList = new ArrayList<Match>(countMatches);

        for (int i = 0; i < teams.length - 1; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                Match m = new Match(teams[i], teams[j],  "", "", numberMatches);
                matchesList.add(m);
            }
        }

        Collections.shuffle(matchesList);

        for(int i = 0; i < matchesList.size(); i++) {
            matchesList.get(i).setNumberMatch(numberMatches);
            numberMatches++;
            db.addMatch(matchesList.get(i));
        }

        Toast.makeText(this, LEAGUE_CREATE, Toast.LENGTH_LONG).show();

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
