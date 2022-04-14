package com.example.hw1_stavrabinovich;

import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.textview.MaterialTextView;


public class GameManager {

    private int score;
    private int life = 3; // Lives
    private int COLS = 3; // Columns
    private int ROWS = 5; // Rows

    private int currentMousePlacement;
    private int currentCatPlacement;

    public GameManager() {
        currentCatPlacement = (COLS/2); // First row in the middle
        currentMousePlacement = ((ROWS-2)*COLS) + (COLS/2); // Middle last row
        score = 0;
    }

    // Scores
    public int getScore() {
        return score;
    }
    public void updateScore(int points) {
        score += points;
    }

    // Life
    public int getLife() {
        return life;
    }
    public void reduceLife() {
        life--;
    }
    public boolean isDead() {
        return life <= 0;
    }

    // Movement
    public int figureMovement(Direction direction, int currentPlacement){
        if (direction == Direction.UP){
            if (currentPlacement < COLS)
                return currentPlacement;
            return currentPlacement - COLS;
        }
        else if (direction == Direction.DOWN){
            if (currentPlacement > (ROWS*(COLS-1))+1)
                return currentPlacement;
            return currentPlacement + COLS;
        }
        else if (direction == Direction.LEFT){
            if (currentPlacement % COLS == 0)
                return currentPlacement;
            return currentPlacement - 1;
        }
        else if (direction == Direction.RIGHT){
            if (currentPlacement % COLS == 2)
                return currentPlacement;
            return currentPlacement + 1;
        }
        return currentPlacement;
    }
    private void doesCatTouchedMouse(){
        if (currentMousePlacement == currentCatPlacement)
            reduceLife();
    }

    public int getCurrentMousePlacement() {
        return currentMousePlacement;
    }
    public void setCurrentMousePlacement(int currentMousePlacement) {
        this.currentMousePlacement = currentMousePlacement;
        doesCatTouchedMouse();
    }

    public int getCurrentCatPlacement() {
        return currentCatPlacement;
    }
    public void setCurrentCatPlacement(int currentCatPlacement) {
        this.currentCatPlacement = currentCatPlacement;
        doesCatTouchedMouse();
    }







}
