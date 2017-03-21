package com.acv.finalproject;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.acv.finalproject.dao.PlayerDAO;
import com.acv.finalproject.model.Player;

import java.util.List;

public class TeamActivity extends ListActivity {

    private PlayerDAO playerDAO;
    public List<Player> players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);
        playerDAO =  new PlayerDAO(this);
        playerDAO.open();
        playerDAO.selectAll(getTeamID());
        playerDAO.close();
        ArrayAdapter<Player> adapter = new ArrayAdapter<>(this, android.R.layout.activity_list_item, players);
        setListAdapter(adapter);
    }

    public long getTeamID() {
        long teamID = Long.parseLong(String.valueOf(this.getApplicationContext().getSharedPreferences("teamID", MODE_PRIVATE)));
        return teamID;
    }
}
