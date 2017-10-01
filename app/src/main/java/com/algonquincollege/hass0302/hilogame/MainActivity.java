package com.algonquincollege.hass0302.hilogame;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String ABOUT_DIALOG_TAG = "About Dialog";
    hilow game;
    private Button startButton;
    private Button guessBtn;
    private EditText guessInput;
    private TextView display;

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

                // this is start new game button / reset button.
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
                // nothing of importances here, just checking if its a new game, if so, it will reveal what the number is.
                // You cheater
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

                // Just trying to make sure its a valid input
                try {
                    num = Integer.parseInt(guessInput.getText().toString());
                } catch (NumberFormatException e) {
                    guessInput.setError("input must be integer");
                    guessInput.requestFocus();
                } finally {
                    guessInput.setText("");
                }

                // if its out of range then display error
                if (num < 1 || num > 1000) {
                    guessInput.setError("The range must be between 1 - 1000 inclusive");
                } else {
                    // if game over then reset the game, you can't continue to play anymore
                    if (game.getCounter() == 10) {
                        display.setText("GAME OVER");
                        Toast.makeText(getApplicationContext(), "Please Reset!", Toast.LENGTH_LONG).show();
                        guessBtn.setEnabled(false);
                        guessInput.setEnabled(false);
                    } else {
                        // if everything is good, counter ++ ; runs the game logic in hilow.
                        game.setCounter();
                        Toast.makeText(getApplicationContext(), game.getHiLow(num), Toast.LENGTH_SHORT).show();
                        display.setText("COUNT: " + game.getCounter());

                        // i just have a boolean flag to check if its a win. my buttons enable and disables depending on it
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

