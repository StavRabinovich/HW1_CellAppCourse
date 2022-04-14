package com.example.hw1_stavrabinovich;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textview.MaterialTextView;

public class MainActivity extends AppCompatActivity {

    private ImageView[] game_IMG_hearts;
    private ImageView[] game_IMG_board;
    private ImageButton[] game_BTN_arrows;
    private MaterialTextView game_LBL_score;
    GameManager gm;

    final Handler handler = new Handler();
    final int DELAY = 1000; // 1sec
    Runnable r = new Runnable() {
        public void run() {
            if (gm.getLife() > 0){
                handler.postDelayed(this, DELAY);
            }
            else{
                finishGame();
            }
            updateUI();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();

        buttonPressing();
        scoreTimer();
    }

    // Inits
    private void findViews(){
        gm = new GameManager();
        game_LBL_score = findViewById(R.id.game_LBL_score);
        game_IMG_hearts = new ImageView[]{
                findViewById(R.id.game_IMG_heart1),
                findViewById(R.id.game_IMG_heart2),
                findViewById(R.id.game_IMG_heart3),
        };
        game_IMG_board = new ImageView[]{
                findViewById(R.id.game_IMG_cell0),
                findViewById(R.id.game_IMG_cell1),
                findViewById(R.id.game_IMG_cell2),
                findViewById(R.id.game_IMG_cell3),
                findViewById(R.id.game_IMG_cell4),
                findViewById(R.id.game_IMG_cell5),
                findViewById(R.id.game_IMG_cell6),
                findViewById(R.id.game_IMG_cell7),
                findViewById(R.id.game_IMG_cell8),
                findViewById(R.id.game_IMG_cell9),
                findViewById(R.id.game_IMG_cell10),
                findViewById(R.id.game_IMG_cell11),
                findViewById(R.id.game_IMG_cell12),
                findViewById(R.id.game_IMG_cell13),
                findViewById(R.id.game_IMG_cell14),
        };
        game_BTN_arrows = new ImageButton[]{
                findViewById(R.id.game_BTN_arrowUp),
                findViewById(R.id.game_BTN_arrowDown),
                findViewById(R.id.game_BTN_arrowLeft),
                findViewById(R.id.game_BTN_arrowRight),
        };

        // First Placement
        game_IMG_board[gm.getCurrentMousePlacement()].setImageResource(R.drawable.ic_mouse);
        game_IMG_board[gm.getCurrentCatPlacement()].setImageResource(R.drawable.ic_cat);

    }
    private void buttonPressing(){
        game_BTN_arrows[0].setOnClickListener(view -> moveMouse(Direction.UP));
        game_BTN_arrows[1].setOnClickListener(view -> moveMouse(Direction.DOWN));
        game_BTN_arrows[2].setOnClickListener(view -> moveMouse(Direction.LEFT));
        game_BTN_arrows[3].setOnClickListener(view -> moveMouse(Direction.RIGHT));
    }

    // Update UI
    private void updateUI(){
        for (int i = 0 ; i < game_IMG_hearts.length ; i++)
            game_IMG_hearts[i].setVisibility(gm.getLife() > i ? View.VISIBLE : View.INVISIBLE);
        gm.updateScore(1);
        game_LBL_score.setText(""+ (gm.getScore()));
        moveCat();
    }

    // Movement on Board
    private void moveCat(){
        Direction direction = Direction.values()[(int) (Math.random() *  Direction.values().length)];
        int newPlacement;
        do {
            newPlacement = gm.figureMovement(direction, gm.getCurrentCatPlacement());
        } while (newPlacement < 0);
        // The move itself
        game_IMG_board[gm.getCurrentCatPlacement()].setImageResource(0);
        game_IMG_board[newPlacement].setImageResource(R.drawable.ic_cat);
        gm.setCurrentCatPlacement(newPlacement);
    }
    private void moveMouse(Direction direction){
        int newPlacement = gm.figureMovement(direction, gm.getCurrentMousePlacement());
        if (gm.getCurrentMousePlacement() != newPlacement)
        {
            game_IMG_board[gm.getCurrentMousePlacement()].setImageResource(0);
            game_IMG_board[newPlacement].setImageResource(R.drawable.ic_mouse);
            gm.setCurrentMousePlacement(newPlacement);
        }
    }

    // Timer and Scores
    private void scoreTimer(){
        if (gm.isDead()) {
            stopScoring();
        } else {
            startScoring();
        }
    }
    private void startScoring(){
        handler.postDelayed(r, DELAY);
    }
    private void stopScoring(){
        handler.removeCallbacks(r);
    }

    // Finish Game
    private void finishGame(){
        scoreTimer();
        // Will present the final score
        Toast.makeText(getApplicationContext(), "Result is : "+gm.getScore(), Toast.LENGTH_LONG).show();
        finish();
    }
}