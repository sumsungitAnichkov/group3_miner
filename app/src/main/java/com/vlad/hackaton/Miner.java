package com.vlad.hackaton;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.Random;

public class Miner {
    private static final String TAG = "MINER";

    int fieldW, fieldH;

    Cell[][] cells;

    int minesCount = 5;
    ArrayList<Pair<Integer, Integer>> mineField = new ArrayList<>();


    TableLayout field;

    Context context;

    void generateMines() {
        Random generator = new Random();
        for (int i = 0; i < minesCount; i++) {
            int x = generator.nextInt(6), y = generator.nextInt(6);

            if (!mineField.contains(new Pair<>(x, y)))
                mineField.add(new Pair<>(x, y));
            else
                i--;
        }
    }

    public Miner(Button[][] buttons, Context context) {
        Log.d(TAG, "game init");

        this.context = context;

        generateMines();

        fieldH = buttons.length;
        fieldW = buttons[0].length;

        cells = new Cell[fieldW][fieldH];

        for (int i = 0; i < fieldH; i++) {
            for (int j = 0; j < fieldW; j++) {

                cells[i][j] = new Cell(buttons[j][i]);

                if (mineField.contains(new Pair<>(j, i)))
                    cells[i][j].mine = true;

                cells[i][j].button.setOnClickListener(this::cellListener);
            }
        }
    }

    void cellListener(View v) {
        Log.d(TAG, "Clicked " + v.getId());

        ((Button) v).setEnabled(false);

        int posx = (int) v.getId() % 10,
                posy = (int) v.getId() / 10;

        Log.d(TAG, "Position " + posx + "- x ; y -" + posy);

        if (cells[posx][posy].mine) {
            ((Button) v).setBackgroundColor(Color.RED);
            ((Button) v).setText("Ж");

            Log.d(TAG, "Detonate on " + v.getId());

            for (int i = 0; i < fieldH; i++) {
                for (int j = 0; j < fieldW; j++) {

                    if (cells[i][j].mine) {
                        cells[i][j].button.setBackgroundColor(Color.RED);
                        cells[i][j].button.setText("Ж");
                    }
                    cells[i][j].button.setEnabled(false);
                }
            }
        } else {
            ((Button) v).setBackgroundColor(Color.GRAY);

            hint(posx, posy);

            Log.d(TAG, "Free on " + v.getId());
        }
    }

    void hint(int x, int y) {
        int mine_counter = 0;

        Log.d(TAG, "hint called:");

        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {

                Log.d(TAG, "Checking cell on x :" + (x + i) + " on y :" + (y + j));

                if (x + i > 0 && x + i < fieldW
                        && y + j > 0 && y + j < fieldH) {
                    if (cells[x + i][y + j].mine) {
                        mine_counter++;
                    }
                }
            }
        }

        cells[x][y].button.setText(Integer.toString(mine_counter));
    }
}


