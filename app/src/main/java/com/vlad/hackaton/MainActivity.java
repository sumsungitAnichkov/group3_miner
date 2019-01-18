package com.vlad.hackaton;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN";

    Button[][] buttons = new Button[6][6];

    int startX = 20, startY = 50, step = 80;

    TableLayout field;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        field = (TableLayout) findViewById(R.id.field);

        boolean switcher = false;
        for (int i = 0; i < 6; i++) {

            TableRow row = new TableRow(this);

            for (int j = 0; j < 6; j++) {
                Button cell = new Button(this);

                if (switcher)
                    cell.setBackgroundColor(Color.GREEN);
                else
                    cell.setBackgroundColor(Color.BLUE);

                switcher = !switcher;

                row.addView(cell, new TableRow.LayoutParams(step, step));

                cell.setId(i * 10 + j);

                buttons[i][j] = cell;
            }

            switcher = !switcher;

            row.setX(startX);

            field.addView(row, TableLayout.LayoutParams.WRAP_CONTENT);
        }

        Miner game = new Miner(buttons, this);

        ((Button) findViewById(R.id.refresh)).setOnClickListener(this::refresh);
    }

    void refresh(View v) {
        boolean switcher = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                Button button = buttons[i][j];

                button.setText("");
                button.setEnabled(true);

                if (switcher)
                    button.setBackgroundColor(Color.GREEN);
                else
                    button.setBackgroundColor(Color.BLUE);

                switcher = !switcher;
            }
            switcher = !switcher;
        }

        Miner game = new Miner(buttons, this);
    }

//    public void dpsRequest(int x, int y) {
//        Log.d(TAG, "dps on " + x + " " + y);
//
//        if (x >= 1 && x <= 6 && y >= 1 && y <= 6 && buttons[x][y].isEnabled()) {
//            for (Pair mine : mines) {
//                if (x != (Integer) mine.first && y != (Integer) mine.second) {
//                    buttons[x - 1][y - 1].setBackgroundColor(Color.GRAY);
//                    buttons[x - 1][y -1 ].setEnabled(false);
//                }
//            }
//        }
//        else return;
//
//        dpsRequest(x - 1, y - 1);
//        dpsRequest(x, y - 1);
//        dpsRequest(x + 1, y - 1);
//
//        dpsRequest(x - 1, y);
//        dpsRequest(x + 1, y);
//
//        dpsRequest(x - 1, y + 1);
//        dpsRequest(x, y + 1);
//        dpsRequest(x + 1, y + 1);
//
//    }
}
