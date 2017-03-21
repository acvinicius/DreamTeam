package com.acv.finalproject;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.acv.finalproject.adapter.ListPlayerAdapter;
import com.acv.finalproject.dao.PlayerDAO;
import com.acv.finalproject.dao.TeamDAO;
import com.acv.finalproject.model.Player;
import com.acv.finalproject.model.Team;

import java.util.ArrayList;
import java.util.List;

public class TeamViewActivity extends AppCompatActivity {

    private PlayerDAO playerDAO;
    private Dialog updatePlayerD;
    private List<Player> playerList;
    private ListView listView;
    private ListPlayerAdapter listPlayerAdapter;
    private EditText nameEditable;
    private EditText emailEditable;
    private EditText phoneEditable;
    private EditText positionEditable;
    private EditText numberEditable;
    private TextView tvTeamID;
    private TextView tvPlayerID;
    private TextView myTeamName;
    private Team team;
    private Player updatePlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);
        updateListItem();
        if (playerList != null) {
            listPlayerAdapter = new ListPlayerAdapter(this, this.playerList);
            listView.setAdapter(listPlayerAdapter);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                Player player= listPlayerAdapter.getItem(position);
                Toast.makeText(TeamViewActivity.this, player.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        myTeamName = (TextView) findViewById(R.id.myTeamName);
        setTeamName();

        updatePlayerD = new Dialog(this);
        updatePlayerD.setTitle(R.string.update_data);
        updatePlayerD.setContentView(R.layout.dialog_edit_player);
        tvPlayerID = (TextView) updatePlayerD.findViewById(R.id.tvPlayerID);
        tvTeamID = (TextView) updatePlayerD.findViewById(R.id.tvTeamID);
        nameEditable = (EditText) updatePlayerD.findViewById(R.id.etPlayerNameD);
        emailEditable = (EditText) updatePlayerD.findViewById(R.id.etPlayerEmailD);
        phoneEditable = (EditText) updatePlayerD.findViewById(R.id.etPlayerPhoneD);
        positionEditable = (EditText) updatePlayerD.findViewById(R.id.etPlayerPositionD);
        numberEditable = (EditText) updatePlayerD.findViewById(R.id.etPlayerNumberD);
    }

    private Long getPreferencesTeamID(String teamID) {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        String teamIDSaved = preferences.getString(teamID, null);
        return Long.parseLong(teamIDSaved);
    }

    private void updateListItem() {
        listView = (ListView) findViewById(R.id.activityPlayersList);
        playerList = new ArrayList<>();
        try {
            playerDAO = new PlayerDAO(this);
            playerDAO.open();
            List<Player> players = playerDAO.selectAll(getPreferencesTeamID("teamID"));
            if (players != null) {
                playerList = players;
            }
            playerDAO.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reload(View v) {
        finish();
        startActivity(getIntent());
    }

    public void editPlayerDialog (Player playerSelected) {
        nameEditable.setText(playerSelected.getName());
        emailEditable.setText(playerSelected.getEmail());
        phoneEditable.setText(playerSelected.getPhone());
        positionEditable.setText(playerSelected.getPosition());
        numberEditable.setText(playerSelected.getNumber().toString());
        tvTeamID.setText(String.valueOf(playerSelected.getTeamID()));
        tvPlayerID.setText(String.valueOf(playerSelected.getId()));
        updatePlayerD.show();
    }

    public void updatePlayer(View view) {
        Boolean allowed = Boolean.TRUE;
        if (nameEditable.equals(null) || nameEditable.getText().toString().equals("")) {
            nameEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (emailEditable.equals(null) || emailEditable.getText().toString().equals("")) {
            emailEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (phoneEditable.equals(null) || phoneEditable.getText().toString().equals("")) {
            phoneEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (positionEditable.equals(null) || positionEditable.getText().toString().equals("")) {
            positionEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (numberEditable.equals(null) || numberEditable.getText().toString().equals("")) {
            numberEditable.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (allowed) {
            updatePlayer = new Player();
            updatePlayer.setId(Long.parseLong(tvPlayerID.getText().toString()));
            updatePlayer.setName(nameEditable.getText().toString());
            updatePlayer.setEmail(emailEditable.getText().toString());
            updatePlayer.setPhone(phoneEditable.getText().toString());
            updatePlayer.setPosition(positionEditable.getText().toString());
            updatePlayer.setNumber(Integer.parseInt(numberEditable.getText().toString()));
            try {
                playerDAO.open();
                playerDAO.update(updatePlayer);
                playerDAO.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            updatePlayerD.dismiss();
            finish();
            startActivity(getIntent());
        }
    }

    public DialogInterface.OnClickListener closeDialog(View view) {
        tvPlayerID.setText("");
        tvTeamID.setText("");
        nameEditable.setText("");
        emailEditable.setText("");
        phoneEditable.setText("");
        positionEditable.setText("");
        numberEditable.setText("");
        updatePlayerD.dismiss();
        return null;
    }

    public void registerPlayer(View view) {
        startActivity(new Intent(this, RegisterPlayerActivity.class));
        this.finish();
    }

    public void returnTeamList(View view) {
        startActivity(new Intent(this, MainViewActivity.class));
        this.finish();
    }

    private void setTeamName() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        String teamIDSaved = preferences.getString("teamID", null);
        TeamDAO teamDAO = new TeamDAO(this);
        teamDAO.open();
        team = teamDAO.select(Long.parseLong(teamIDSaved));
        teamDAO.close();
        myTeamName.setText(team.getName());
    }
}
