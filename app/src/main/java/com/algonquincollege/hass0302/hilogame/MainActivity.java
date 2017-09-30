package com.algonquincollege.hass0302.hilogame;

import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    private Button startButton;
    private Button guessBtn;
    private EditText guessInput;
    private TextView display;
    hilow game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = (Button) findViewById(R.id.button);
        guessBtn = (Button) findViewById(R.id.guessBtn);
        guessInput = (EditText) findViewById(R.id.guessInput);
        display = (TextView) findViewById(R.id.msgDisplay);

        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                game = new hilow();
                game.init();
                display.setText("COUNT: " + game.getCounter());
                guessBtn.setEnabled(true);
                guessInput.setEnabled(true);
                startButton.setText("Reset");
            }

        });

        startButton.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View view) {
                try {
                    Toast.makeText(getApplicationContext(), Integer.toString(game.getNum()), Toast.LENGTH_LONG).show();
                } catch (RuntimeException e) {
                    Toast.makeText(getApplicationContext(), "Start a game first", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

        guessBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                int num = -1;

                try {
                    num = Integer.parseInt(guessInput.getText().toString());
                } catch (NumberFormatException e) {
                    guessInput.setError("input must be integer");
                    guessInput.requestFocus();
                } finally {
                    guessInput.setText("");
                }

                if (num < 1 || num > 1000) {
                    guessInput.setError("The range must be between 1 - 1000 inclusive");
                } else {
                    if (game.getCounter() == 10) {
                        display.setText("GAME OVER");
                        Toast.makeText(getApplicationContext(), "Please Reset!", Toast.LENGTH_LONG).show();
                        guessBtn.setEnabled(false);
                        guessInput.setEnabled(false);
                    } else {
                        game.setCounter();
                        Toast.makeText(getApplicationContext(), game.getHiLow(num), Toast.LENGTH_SHORT).show();
                        display.setText("COUNT: " + game.getCounter());

                        if (game.getWinFlag() == true) {
                            display.setText("You've Won!\nGAME OVER");

                            startButton.setText("New Game");
                            guessBtn.setEnabled(false);
                            guessInput.setEnabled(false);

                        }
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

