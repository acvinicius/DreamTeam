package com.acv.finalproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.acv.finalproject.dao.TeamDAO;
import com.acv.finalproject.model.Team;
import com.acv.finalproject.model.User;
import com.acv.finalproject.adapter.ListTeamAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private TeamDAO teamDAO;
    private List<Team> myTeams;
    private User user;
    private ListView listView;
    private ListTeamAdapter teamArrayAdapter;
    private EditText nameEditable;
    private EditText emailEditable;
    private TextView tvID;
    private Team updateTeam;
    private Dialog updateTeamD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addTeamBtn = (FloatingActionButton) findViewById(R.id.addTeamBtn);
        addTeamBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainViewActivity.this, RegisterTeamActivity.class));
                MainViewActivity.this.finish();
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateListItem();

        if (myTeams != null) {
            teamArrayAdapter = new ListTeamAdapter(this, this.myTeams);
            listView.setAdapter(teamArrayAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Team team = teamArrayAdapter.getItem(position);
                savePreferences(team.getId());
                MainViewActivity.this.finish();
                startActivity(new Intent(MainViewActivity.this, TeamViewActivity.class));
                MainViewActivity.this.finish();
            }
        });
        updateTeamD = new Dialog(this);
        updateTeamD.setTitle(R.string.update_data);
        updateTeamD.setContentView(R.layout.dialog_edit_team);
        tvID = (TextView) updateTeamD.findViewById(R.id.tvTeamID);
        nameEditable = (EditText) updateTeamD.findViewById(R.id.etTeamNameEditable);
        emailEditable = (EditText) updateTeamD.findViewById(R.id.etTeamEmailEditable);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_logout) {
            logout();
        } else if (id == R.id.action_return) {
            this.finish();
            startActivity(new Intent(this, MainViewActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_reg_team) {
            startActivity(new Intent(this, RegisterTeamActivity.class));
            MainViewActivity.this.finish();
//        } else if (id == R.id.nav_alter_team) {
//
        } else if (id == R.id.nav_reg_player) {
            startActivity(new Intent(this, TeamViewActivity.class));
            MainViewActivity.this.finish();
//        } else if (id == R.id.nav_alter_player) {

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(this, AboutActivity.class));
            MainViewActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.remove("userID");
        e.remove("continueConnected");
        e.commit();
        startActivity(new Intent(this, LoginActivity.class));
        MainViewActivity.this.finish();
    }

    private Long getPreferences(String userID) {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        String userIDSaved = preferences.getString(userID, null);
        return Long.parseLong(userIDSaved);
    }

    private void updateListItem() {
        listView = (ListView) findViewById(R.id.activityTeamList);
        myTeams = new ArrayList<>();
        try {
            user = new User();
            user.setId(this.getPreferences("userID"));
            teamDAO = new TeamDAO(this);
            teamDAO.open();
            List<Team> teams = teamDAO.selectAll();
            if (teams != null) {
                myTeams = teams;
            }
            teamDAO.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload(View v) {
        finish();
        startActivity(getIntent());
    }

    public void editTeamDialog (Team teamSelected) {
        nameEditable.setText(teamSelected.getName());
        emailEditable.setText(teamSelected.getEmail());
        tvID.setText(String.valueOf(teamSelected.getId()));
        updateTeamD.show();
    }

    public void updateTeam(View view) {
        Boolean allowed = Boolean.TRUE;
        if (nameEditable.equals(null) || nameEditable.getText().toString().equals("")) {
            nameEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (emailEditable.equals(null) || emailEditable.getText().toString().equals("")) {
            emailEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (allowed) {
            updateTeam = new Team();
            updateTeam.setId(Long.parseLong(tvID.getText().toString()));
            updateTeam.setName(nameEditable.getText().toString());
            updateTeam.setEmail(emailEditable.getText().toString());
            try {
                teamDAO.open();
                teamDAO.update(updateTeam);
                teamDAO.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeAndReload();
        }
    }

    public DialogInterface.OnClickListener closeDialog(View view) {
        tvID.setText("");
        nameEditable.setText("");
        emailEditable.setText("");
        updateTeamD.dismiss();
        return null;
    }

    private void closeAndReload() {
        updateTeamD.dismiss();
        finish();
        startActivity(getIntent());
    }

    private void savePreferences(Long teamID) {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.putString("teamID", String.valueOf(teamID));
        e.commit();
    }

}