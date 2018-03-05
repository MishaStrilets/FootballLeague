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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class AddTeamsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button btnAdd;
    EditText editTeams;
    TextView textMessage, textTitle;
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
        int countMatches = 0, match = 1;
        textMessage = (TextView) findViewById(R.id.textMessage);
        textMessage.setText("");

        if (teams.length == 0 || teams.length == 1) {
            textMessage.setText("INVALID INPUT");
            return;
        }

        for (int i = 0; i < teams.length - 1; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                if (teams[i].equals(teams[j])) {
                    textMessage.setText("INVALID INPUT");
                    return;
                }
            }
        }

        for (int i = 1; i < teams.length; i++)
            countMatches += i;

        ArrayList<Match> matches = new ArrayList<Match>(countMatches);

        for (int i = 0; i < teams.length - 1; i++) {
            for (int j = i + 1; j < teams.length; j++) {
                Match m = new Match(teams[i], teams[j], -1, -1, match);
                matches.add(m);
            }
        }

        Collections.shuffle(matches);

        for (int y = 0; y < matches.size(); y++) {
            matches.get(y).setMatch(match);
            match++;
        }

        db.addMatches(matches);

        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
