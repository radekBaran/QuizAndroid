package com.example.radek.quiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Spinner;

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

    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        mPrefs = getSharedPreferences("user", MODE_PRIVATE);
        mName.setText(mPrefs.getString("username", ""));
        mSurname.setText(mPrefs.getString("usersurname", ""));

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
        // TODO Zapamietywanie nazwy gracza i poziomu trudnosci
        mPrefs.edit().putString("username", name).putString("usersurname", surname).apply();
        // TODO Losowanie puli pytan
        // TODO Otwarcie nowego ekranu
    }
}
