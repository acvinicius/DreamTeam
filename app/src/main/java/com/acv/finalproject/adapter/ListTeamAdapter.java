package com.acv.finalproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.acv.finalproject.MainViewActivity;
import com.acv.finalproject.R;
import com.acv.finalproject.dao.TeamDAO;
import com.acv.finalproject.model.Team;

import java.util.List;

/**
 * Created by vinicius.castro on 15/03/2017.
 */

public class ListTeamAdapter extends ArrayAdapter<Team> {

    private Context context;
    private List<Team> teamList = null;
    private TeamDAO teamDAO;

    public ListTeamAdapter(Context context, List<Team> resource) {
        super(context, 0, resource);
        this.teamList = resource;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Team team = teamList.get(position);
        convertView = LayoutInflater.from(context).inflate(R.layout.item_list_team, null);
        TextView textViewName = (TextView) convertView.findViewById(R.id.tvNameTeamItem);
        textViewName.setText(team.getName());
        TextView textViewEmail = (TextView) convertView.findViewById(R.id.tvEmailTeamItem);
        textViewEmail.setText(team.getEmail());
        final Team teamSelected = team;
        (convertView.findViewById(R.id.ivDeleteTeam)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    teamDAO = new TeamDAO(context);
                    teamDAO.open();
                    teamDAO.delete(teamSelected.getId());
                    teamDAO.close();
                    if (context instanceof MainViewActivity) {
                        ((MainViewActivity) context).reload(v);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        (convertView.findViewById(R.id.ivEditTeam)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainViewActivity) {
                    ((MainViewActivity) context).editTeamDialog(teamSelected);
                }

            }
        });
        return convertView;
    }

}
