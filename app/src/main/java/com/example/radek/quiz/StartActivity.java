package com.example.radek.quiz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

        mPrefs = new UserPreferences(this);
        mName.setText(mPrefs.getUsername());
        mSurname.setText(mPrefs.getUsersurname());
        mSpinner.setSelection(mPrefs.getLevel());

    }

    @OnClick(R.id.next)
    protected void onNextClick() {
        // Sprawdzenie czy w ogole wpisano cos w pole imie
        String name = mName.getText().toString();
        String surname = mSurname.getText().toString();
        if (name.trim().isEmpty()) {
            mName.setError("Brak imienia !");
        } else if (surname.trim().isEmpty()) {
            mSurname.setError("Brak nazwiska !");
            return;
        }

        // Sprawdzenie czy wybrano poziom trudności
        int selectedLevel = mSpinner.getSelectedItemPosition();
        if (selectedLevel == 0) {
            Toast.makeText(this, "Wybierz poziom trudności", Toast.LENGTH_LONG).show();
            return;
        }

        // Zapamietywanie nazwy gracza i poziomu trudnosci
        mPrefs.setUsername(name);
        mPrefs.setUsersurname(surname);
        mPrefs.setLevel(selectedLevel);


        // TODO Losowanie puli pytan
        // TODO Otwarcie nowego ekranu
        Intent questionIntent = new Intent(this, QuestionActivity.class);
        startActivity(questionIntent);
    }
}
