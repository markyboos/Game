package com.game.thrones.activity;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.ListActivity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.game.thrones.R;
import com.game.thrones.engine.GameController;
import com.game.thrones.model.Quest;
import com.game.thrones.model.hero.Hero;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 01/01/2018.
 */
public class HeroViewActivity extends DialogFragment implements AdapterView.OnItemClickListener {

    private Hero hero = GameController.getInstance().getPlayer();

    /**
     * Called when the activity is first created.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.heroview, container, false);

        ImageView heroImageView = (ImageView)view.findViewById(R.id.heroImage);

        heroImageView.setImageDrawable(getResources().getDrawable(R.mipmap.barb));

        TextView heroTitleView = (TextView)view.findViewById(R.id.heroTitle);

        heroTitleView.setText(getResourceString(hero.getName(), getContext()));

        TextView heroTextView = (TextView)view.findViewById(R.id.heroDescription);

        heroTextView.setText(getResourceString(hero.getName() + ".description", getContext()));

        TextView heroSpecialTitle1View = (TextView)view.findViewById(R.id.heroSpecialTitle1);

        heroSpecialTitle1View.setText(getResourceString(hero.getName() + ".special1title", getContext()));

        TextView heroSpecialText1View = (TextView)view.findViewById(R.id.heroSpecialText1);

        heroSpecialText1View.setText(getResourceString(hero.getName() + ".special1", getContext()));

        TextView heroSpecialTitle2View = (TextView)view.findViewById(R.id.heroSpecialTitle2);

        heroSpecialTitle2View.setText(getResourceString(hero.getName() + ".special2title", getContext()));

        TextView heroSpecialText2View = (TextView)view.findViewById(R.id.heroSpecialText2);

        heroSpecialText2View.setText(getResourceString(hero.getName() + ".special2", getContext()));

        TextView heroSpecialTitle3View = (TextView)view.findViewById(R.id.heroSpecialTitle3);

        heroSpecialTitle3View.setText(getResourceString(hero.getName() + ".special3title", getContext()));

        TextView heroSpecialText3View = (TextView)view.findViewById(R.id.heroSpecialText3);

        heroSpecialText3View.setText(getResourceString(hero.getName() + ".special3", getContext()));

        ListView heroCompletedQuestView = (ListView)view.findViewById(R.id.heroCompletedQuests);

        heroCompletedQuestView.setAdapter(new QuestAdapter(hero.getCompletedQuests()));
        heroCompletedQuestView.setOnItemClickListener(this);

        return view;
    }

    public static String getResourceString(String name, Context context) {
        int nameResourceID = context.getResources().getIdentifier(name,
                "string", context.getApplicationInfo().packageName);
        if (nameResourceID == 0) {
            throw new IllegalArgumentException(
                    "No resource string found with name " + name);
        } else {
            return context.getString(nameResourceID);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Quest selected = (Quest) parent.getItemAtPosition(position);

        final Quest quest = selected;

        //show a toast, with the option to use the card.
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        StringBuilder builder = new StringBuilder();
        builder.append("Quest: " + quest.toString());
        builder.append("Travel to " + quest.getTerritory() + " and " + quest.getRequirement().toString());
        builder.append("Receive : " + quest.getReward().toString());

        alertDialogBuilder.setMessage(builder.toString());
        alertDialogBuilder.show();
    }


    private class QuestAdapter extends ArrayAdapter<Quest> {

        public QuestAdapter(List<Quest> options) {
            super(getActivity().getApplicationContext(),
                    android.R.layout.simple_list_item_1, options);
        }
    }
}
