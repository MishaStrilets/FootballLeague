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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    final String LEAGUE_EXIST = "The league already exist.";
    final String NO_LEAGUE = "No league.";
    TextView textTable;
    DBManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        textTable = (TextView) findViewById(R.id.textTable);

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
            Toast.makeText(this, NO_LEAGUE, Toast.LENGTH_LONG).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showTable(){
        //TODO show table result of matches
    }
}
