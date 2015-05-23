package com.erich.commutenyc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private int tries = 0;
    private String sol;
    private int[] drawables = new int[]{R.drawable.gallows1, R.drawable.gallows2, R.drawable.gallows3, R.drawable.gallows4, R.drawable.gallows5, R.drawable.gallows6, R.drawable.gallows7};
    private String used = "";
    private boolean game = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sol = chooseWord();
        EditText in = (EditText) (findViewById(R.id.input));
        in.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0)
                    return;
                check(s.charAt(0));
                ((EditText) (findViewById(R.id.input))).setText("");
            }
        });
    }

    /* //useless code

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    */

    public String chooseWord() {
        List<String> words = Arrays.asList("hello", "cool", "test", "first", "app", "awesome", "Erich", "Hangman");
        int num = (int) (Math.floor(Math.random() * 8));
        String chosen = words.get(num);
        TextView output = (TextView) findViewById(R.id.solution);
        for (int i = 0; i < chosen.length(); i++)
            output.setText(output.getText() + "_ ");
        return chosen;
    }


    /*
    private TextWatcher inputTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };
    */

    private void changeImage() {
        if (!game)
            return;
        if (tries > 6) {
            Toast.makeText(this, "you lose", Toast.LENGTH_SHORT).show();
            game = false;
            return;
        }
        ((ImageView) findViewById(R.id.stand)).setImageResource(drawables[tries]);
    }

    public void check(char ch) {
        if (!game)
            return;
        if (ch < 97 || ch > 122) {
            Toast.makeText(this, "please enter a valid character", Toast.LENGTH_SHORT).show();
            return;
        }
        if (used.indexOf(ch) != -1) {
            Toast.makeText(this, "you have already used that character", Toast.LENGTH_SHORT).show();
            return;
        }
        boolean cor = false;
        TextView solution = (TextView) (findViewById(R.id.solution));
        for (int i = 0; i < sol.length(); ++i) {
            char character = sol.charAt(i);
            if (((character < 97) ? character + 32 : character) == ch) {
                String txt = solution.getText().toString();
                solution.setText(txt.substring(0, 2 * i) + ch + txt.substring((2 * i + 1)));
                cor = true;
            }
        }
        used += ch;
        if (cor) {
            if (solution.getText().toString().indexOf('_') == -1) {
                Toast.makeText(this, "you win", Toast.LENGTH_SHORT).show();
                game = false;
            }
            return;
        }
        ++tries;
        changeImage();
    }

    /*
    public void checkLetter(String phrase)
    {
        in.setOnKeyListener(new View.OnKeyListener() { //this method won't catch key strokes from the virtual keyboard.
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return false;
                char letter = (char) (event.getUnicodeChar());
               //int corr = 0;
                ImageView img = (ImageView) (findViewById(R.id.stand));
                img.setImageResource(R.drawable.gallows2);
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == letter) {
                        TextView output = (TextView) (findViewById(R.id.solution));
                        String currWord = output.getText().toString();
                        output.setText(currWord.substring(0, 2 * i) + letter + currWord.substring(2 * (i + 1)));
                    }
                }
                return true;
            }
        });
    }
        */
}
