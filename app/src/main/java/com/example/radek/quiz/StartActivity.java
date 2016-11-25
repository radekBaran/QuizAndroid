package com.example.radek.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    @BindView(R.id.player_name)
    protected EditText mName;
    @BindView(R.id.player_surname)
    protected EditText mSurname;
    @BindView(R.id.difficulty)
    protected Spinner mSpinner;

    private UserPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        Log.d("StartActivity", "onCreate");

        mPrefs = new UserPreferences(this);
        mName.setText(mPrefs.getUsername());
        mSurname.setText(mPrefs.getUsersurname());
        mSpinner.setSelection(mPrefs.getLevel());

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("StartActivity", "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("StartActivity", "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("StartActivity", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("StartActivity", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("StartActivity", "onDestroy");
    }

    @OnClick(R.id.btn_play)
    protected void onNextClick() {
        // Sprawdzenie czy w ogole wpisano cos w pole imie
        String name = mName.getText().toString();
        String surname = mSurname.getText().toString();
        if (name.trim().isEmpty()) {
            mName.setError("No name !");
        } else if (surname.trim().isEmpty()) {
            mSurname.setError("No surname");
            return;
        }

        // Sprawdzenie czy wybrano poziom trudności
        int selectedLevel = mSpinner.getSelectedItemPosition();
        if (selectedLevel == 0) {
            Toast.makeText(this, "Check level", Toast.LENGTH_LONG).show();
            return;
        }

        // Zapamietywanie nazwy gracza i poziomu trudnosci
        mPrefs.setUsername(name);
        mPrefs.setUsersurname(surname);
        mPrefs.setLevel(selectedLevel);


        // Losowanie puli pytan
        QuestionDatabase db = new MemoryQuestionDatabase();
        List<Question> questions = db.getQuestions(selectedLevel);
        Random random = new Random();
        while (questions.size() > 5) {
            // Usuwamy losowe pytania az zostanie nam ich tylko 5
            questions.remove(random.nextInt(questions.size()));
        }
        // Przetasowujemy kolekcje pozostalych pytan aby kolejnosc rowniez byla losowa
        Collections.shuffle(questions);

        // TODO: Przekazanie lub zapisanie wylosowanych pytan na potrzeby nastepnego ekranu

        // Otwarcie nowego ekranu
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        questionIntent.putExtra("questions", new ArrayList<>(questions));
        startActivity(questionIntent);
    }
}
