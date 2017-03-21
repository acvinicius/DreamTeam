package com.acv.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.acv.finalproject.dao.PlayerDAO;
import com.acv.finalproject.dao.TeamDAO;
import com.acv.finalproject.model.Player;
import com.acv.finalproject.model.Team;

public class RegisterPlayerActivity extends AppCompatActivity {

    private Player newPlayer;
    private Dialog playerDialog;
    private PlayerDAO playerDAO;
    private Team team;
    private TextView nameD;
    private TextView emailD;
    private TextView phoneD;
    private TextView positionD;
    private TextView numberD;
    private TextView teamNameD;

    private TextView teamName;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText position;
    private EditText number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);
        teamName = (TextView) findViewById(R.id.tvTeamNameValue);
        name = (EditText) findViewById(R.id.etPlayerName);
        email = (EditText) findViewById(R.id.etPlayerEmail);
        phone = (EditText) findViewById(R.id.etPlayerPhone);
        number = (EditText) findViewById(R.id.etPlayerNumber);
        position = (EditText) findViewById(R.id.etPlayerPosition);
        getTeamName();
    }

    private String getTeamName() {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        String teamIDSaved = preferences.getString("teamID", null);
        TeamDAO teamDAO = new TeamDAO(this);
        teamDAO.open();
        team = teamDAO.select(Long.parseLong(teamIDSaved));
        teamName.setText(team.getName());
        teamDAO.close();
        return team.getName();
    }

    public void playerDialog(View view) {
        Boolean allowed = Boolean.TRUE;
        if (teamName.equals(null) || teamName.getText().toString().equals("")) {
            teamName.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (name.equals(null) || name.getText().toString().equals("")) {
            name.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (email.equals(null) || email.getText().toString().equals("")) {
            email.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (phone.equals(null) || phone.getText().toString().equals("")) {
            phone.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (position.equals(null) || position.getText().toString().equals("")) {
            position.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (number.equals(null) || number.getText().toString().equals("")) {
            number.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (allowed) {
            playerDialog = new Dialog(this);
            playerDialog.setTitle(R.string.confirmation);
            playerDialog.setContentView(R.layout.dialog_player);
            teamNameD = (TextView) playerDialog.findViewById(R.id.tvTeamNameD);
            teamNameD.setText(teamName.getText().toString());
            nameD = (TextView) playerDialog.findViewById(R.id.tvPlayerNameD);
            nameD.setText(name.getText().toString());
            emailD = (TextView) playerDialog.findViewById(R.id.tvPlayerEmailD);
            emailD.setText(email.getText().toString());
            phoneD = (TextView) playerDialog.findViewById(R.id.tvPlayerPhoneD);
            phoneD.setText(phone.getText().toString());
            positionD = (TextView) playerDialog.findViewById(R.id.tvPlayerPositionD);
            positionD.setText(position.getText().toString());
            numberD = (TextView) playerDialog.findViewById(R.id.tvPlayerNumberD);
            numberD.setText(number.getText().toString());
            playerDialog.show();
        }
    }

    public void onClickConfirmD(View view) {
        this.newPlayer = new Player();
        newPlayer.setEmail(emailD.getText().toString());
        newPlayer.setName(nameD.getText().toString());
        newPlayer.setTeamID(team.getId());
        newPlayer.setPhone(phoneD.getText().toString());
        newPlayer.setPosition(positionD.getText().toString());
        newPlayer.setNumber(Integer.parseInt(numberD.getText().toString()));
        this.playerDAO = new PlayerDAO(this);
        this.playerDAO.open();
        this.newPlayer = playerDAO.insert(this.newPlayer);
        this.playerDAO.close();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.player_saved);
        alert.setPositiveButton(R.string.close, onClickClean(view));
        alert.show();
    }

    public DialogInterface.OnClickListener onClickClean(View view) {
        name.setText("");
        email.setText("");
        phone.setText("");
        position.setText("");
//        number.setText("");
        playerDialog.dismiss();
        startActivity(getIntent());
        finish();
        return null;
    }

    public DialogInterface.OnClickListener closeDialog(View view) {
        nameD.setText("");
        emailD.setText("");
        phoneD.setText("");
        positionD.setText("");
        numberD.setText("");
        playerDialog.dismiss();
        return null;
    }

    public void cancelNewPlayer(View view) {
        finish();
        startActivity(new Intent(this, TeamViewActivity.class));
    }
}
