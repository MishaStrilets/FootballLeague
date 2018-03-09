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
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class WriteResultActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String LEAGUE_EXIST = "The league already exist.";
    ListView listMatch;
    DBManager db;
    MatchAdapter matchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_result);
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

        List<Match> matchesList = db.getAllMatches();
        matchAdapter = new MatchAdapter(this, matchesList);
        listMatch  = (ListView) findViewById(R.id.listMatches);
        listMatch.setAdapter(matchAdapter);
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

}
