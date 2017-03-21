package com.acv.finalproject.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.acv.finalproject.R;
import com.acv.finalproject.TeamViewActivity;
import com.acv.finalproject.dao.PlayerDAO;
import com.acv.finalproject.dao.TeamDAO;
import com.acv.finalproject.model.Player;
import com.acv.finalproject.model.Team;

import java.util.List;

/**
 * Created by vinicius.castro on 15/03/2017.
 */

public class ListPlayerAdapter extends ArrayAdapter<Player> {

    private Context context;
    private List<Player> playerList = null;
    private PlayerDAO playerDAO;
    private Team team;

    public ListPlayerAdapter(Context context, List<Player> resource) {
        super(context, 0, resource);
        this.playerList = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Player player = playerList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_players, null);
        TextView textViewName = (TextView) convertView.findViewById(R.id.tvPlayerNameItem);
        textViewName.setText(player.getName().toString());
        TextView textViewNumber = (TextView) convertView.findViewById(R.id.tvPlayerNumberItem);
        textViewNumber.setText(player.getNumber().toString());
//        TextView textViewTeamName = (TextView) convertView.findViewById(R.id.tvMyTeamName);
//        textViewTeamName.setText(selectTeamName().toString());
        final Player playerSelected = player;
        (convertView.findViewById(R.id.ivDeletePlayer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    playerDAO = new PlayerDAO(context);
                    playerDAO.open();
                    playerDAO.delete(playerSelected.getId());
                    playerDAO.close();
                    if (context instanceof TeamViewActivity) {
                        ((TeamViewActivity) context).reload(v);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        (convertView.findViewById(R.id.ivEditPlayer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof TeamViewActivity) {
                    ((TeamViewActivity) context).editPlayerDialog(playerSelected);
                }

            }
        });
        return convertView;
    }

    private String selectTeamName() {
        TeamDAO teamDAO = new TeamDAO(context);
        teamDAO.open();
        team = teamDAO.select(getPreferencesTeamID("teamID"));
        teamDAO.close();
        return team.getName();
    }

    private Long getPreferencesTeamID(String teamID) {
        SharedPreferences preferences = context.getSharedPreferences("PREFERENCIAS", Context.MODE_PRIVATE);
        String teamIDSaved = preferences.getString(teamID, null);
        return Long.parseLong(teamIDSaved);
    }
}
