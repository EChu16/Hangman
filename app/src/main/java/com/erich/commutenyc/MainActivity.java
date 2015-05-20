package com.erich.commutenyc;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    int tries = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String word = chooseWord();
        checkLetter(word);

    }

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

    public String chooseWord(){
        List<String> words = Arrays.asList("hello", "cool", "test", "first", "app", "awesome", "Erich", "Hangman");
        int num = (int)(Math.floor(Math.random() * 8));
        String chosen = words.get(num);
        TextView output = (TextView) findViewById(R.id.solution);
        for(int i = 0; i < chosen.length();i++)
            output.setText(output.getText() + "_ ");
        return chosen;
    }

    private TextWatcher inputTextWatcher = new TextWatcher() {
        public void afterTextChanged(Editable s) {}
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
        public void onTextChanged(CharSequence s, int start, int before, int count) {}
    };

    public void checkLetter(String phrase)
    {
        final String word = phrase;
        TextView in = (TextView)(findViewById(R.id.input));
        in.addTextChangedListener(inputTextWatcher);
        in.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() != KeyEvent.ACTION_DOWN)
                    return false;
                char letter = (char) (event.getUnicodeChar());
                int corr = 0;
                ImageView img = (ImageView) (findViewById(R.id.stand));
                img.setImageResource(R.drawable.gallows2);
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == letter) {

                        TextView output = (TextView) (findViewById(R.id.solution));
                        String currWord = output.getText().toString();
                        output.setText(currWord.substring(0, 2 * i) + letter + currWord.substring(2 * i + 1));
                    }
                }
                return true;
            }
        });
    }
}
