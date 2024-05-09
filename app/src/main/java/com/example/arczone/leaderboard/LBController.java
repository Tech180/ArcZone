package com.example.arczone.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.example.arczone.R;
import com.example.arczone.firebase.ArcZoneDatabase;
import com.example.arczone.firebase.ArcZoneUser;
import com.example.arczone.gameselectionscreen.GameSelectionScreen;
import com.example.arczone.pong.PongActivity;
import com.example.arczone.snakegame.Snake;
import com.example.arczone.spaceinvaders.InvActivity;
import com.example.arczone.universal.universal_methods;

import java.util.Map;

public class LBController extends universal_methods {

    String game;
    int newScore;
    FragmentActivity activity;
    Map<Integer, String>[] scores;

    ArcZoneUser user;

    TextView[] views = new TextView[20];
    TextView gameName;
    Button play;
    Button menu;

    ArcZoneDatabase db;
    LBController(FragmentActivity activity, String game, int newScore){
        this.game = game;
        this.newScore = newScore;
        this.activity = activity;
        this.user = user.getInstance();
        this.db = new ArcZoneDatabase();
        this.scores = db.getLeaderboard(game);

        //set all the frag views
        setViews();
        setBoard();
    }

    private void setBoard(){

        //check if the new score is going to change the board
        int changed = checkScores();

        int j = 0;

        //set the scores into the views
        for(int i = 0; i < scores.length; i++){

            //if you got a score on the board, signify this by drawing a box around the layout
            if(changed == i) {
                LinearLayout layout = (LinearLayout) views[j].getParent();
                layout.setBackground(getResources().getDrawable(R.drawable.validtextboxborder));
            }
            //all even index views are usernames
            views[j].setText(scores[i].get("username"));
            //all odd index views are scores
            views[j++].setText(Integer.parseInt(scores[i].get("score")));
            j++;
        }
    }

    /* This method should do the following:
     *      - Iterate through the entire board checking each rank
     *      - If a score in the board is found that is <= to newScore:
     *          - save that score
     *          - replace that score with the greater score data
     *          - save the replaced score into curr
     *          - continue looping and propagating the score down the board
     *          - once we get to the last position in the board, it should naturally
     *            throw out the last position
     */
    private int checkScores(){

        Boolean changed = false;
        int changedIndex = -1;

        String currUser = user.getUsername();
        int currScore = newScore;
        Map<Integer, String> temp;

        for(int i = 0; i < scores.length; i++){
            //check if current score is greater than present score
            if(Integer.parseInt(scores[i].get("score")) < currScore){
                //if greater...
                //save the present score
                temp = scores[i];
                //put curr in place of present
                scores[i].put(currScore, currUser);
                //set the replaced score to the curr score for next loop
                currScore = Integer.parseInt(temp.get("score"));
                currUser = temp.get("username");

                //if changed is false still, set as true
                if(!changed){
                    changed = true;
                    changedIndex = i;
                }
            }
        }

        //update the leaderboard with the new scores array if it was changed
        if(changed) db.updateLeaderboard(scores, game);

        return changedIndex;
    }

    private void setViews(){

        //set the leaderboard name according to the game that called it
        gameName = activity.findViewById(R.id.game);
        gameName.setText(this.game);

        //set all the leaderboard text views
        for(int i = 0; i < 20; i++){
            int textViewId = getResources().getIdentifier("position" + (i + 1), "id", getPackageName());
            views[i] = activity.findViewById(textViewId);
        }

        play.setOnClickListener(v -> {
            //for whatever game called leaderboard, start that game activity
            if(game.equals("Snake")){
                Intent intent = new Intent(activity, Snake.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
            else if(game.equals("Pong")){
                Intent intent = new Intent(activity, PongActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
            else{
                Intent intent = new Intent(activity, InvActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent);
            }
        });

        //if clicked menu button, move back to GameSelection Screen
        menu.setOnClickListener(v -> {
            Intent intent = new Intent(activity, GameSelectionScreen.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(intent);
        });
    }
}
