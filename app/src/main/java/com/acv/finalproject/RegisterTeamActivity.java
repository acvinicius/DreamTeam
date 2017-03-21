package com.acv.finalproject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.acv.finalproject.dao.TeamDAO;
import com.acv.finalproject.model.Team;

public class RegisterTeamActivity extends AppCompatActivity {

    private TeamDAO teamDAO;
    private Dialog dTeam;
    private TextView nameD;
    private TextView emailD;
    private Team team;

    private EditText name;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);
        name = (EditText) findViewById(R.id.etTeamName);
        email = (EditText) findViewById(R.id.etTeamEmail);
    }

    public void reserveDialog(View view) {
        Boolean allowed = Boolean.TRUE;
        if (name.equals(null) || name.getText().toString().equals("")) {
            name.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (email.equals(null) || email.getText().toString().equals("")) {
            email.setError(getString(R.string.required_field));
            allowed = Boolean.FALSE;
        }
        if (allowed) {
            dTeam = new Dialog(this);
            dTeam.setTitle(R.string.confirmation);
            dTeam.setContentView(R.layout.dialog_team);
            nameD = (TextView) dTeam.findViewById(R.id.tvNameD);
            nameD.setText(name.getText().toString());
            emailD = (TextView) dTeam.findViewById(R.id.tvEmailD);
            emailD.setText(email.getText().toString());
            dTeam.show();
        }
    }

    public void onClickConfirmD(View view) {
        this.team = new Team();
        team.setEmail(emailD.getText().toString());
        team.setName(nameD.getText().toString());
        this.teamDAO = new TeamDAO(this);
        this.teamDAO.open();
        this.team = teamDAO.insert(this.team);
        this.teamDAO.close();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(R.string.sucess_team_saved);
        alert.setPositiveButton(R.string.close, onClickClean(view));
        alert.show();
    }

    public DialogInterface.OnClickListener onClickClean(View view) {
        name.setText("");
        email.setText("");
        dTeam.dismiss();
        this.finish();
        startActivity(new Intent(this,RegisterTeamActivity.class));
        return null;
    }

    public DialogInterface.OnClickListener closeDialog(View view) {
        nameD.setText("");
        emailD.setText("");
        dTeam.dismiss();
        return null;
    }

    public void cancel(View view) {
        finish();
        startActivity(new Intent(this, MainViewActivity.class));
    }
}
