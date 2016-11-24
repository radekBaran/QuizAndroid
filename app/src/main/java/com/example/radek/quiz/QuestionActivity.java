package com.example.radek.quiz;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuestionActivity extends AppCompatActivity {

    @BindView(R.id.question_text)
    protected TextView mTitle;
    @BindView(R.id.answers)
    protected RadioGroup mAnswers;
    @BindViews({R.id.answer_a, R.id.answer_b, R.id.answer_c})
    protected List<RadioButton> mAnswerButtons;

    private int mCurrentQuestion = 0;
    private List<Question> mQuestions;
    private int[] mAnswersArray;

    private boolean mFirstBackClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        ButterKnife.bind(this);

        mQuestions = (List<Question>) getIntent().getSerializableExtra("questions");
        mAnswersArray = new int[mQuestions.size()];
        refreshQuestionView();
    }

    @Override
    public void onBackPressed() {
        onBackTapped();
    }

    private void onBackTapped() {
        if (!mFirstBackClicked) {
            // Ustawić flage na true
            mFirstBackClicked = true;
            // pokazac toast
            Toast.makeText(this, "Kliknij ponownie, aby wyjść !", Toast.LENGTH_LONG).show();
            // uruchomi odliczanie (1-2 sek) i po tym czasie ustawic na flage ponownie na false
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mFirstBackClicked = false;
                }
            }, 2000);
        } else {
            // zamknac okno Activity
            finish();
        }
    }

    private void refreshQuestionView() {
        mAnswers.clearCheck();
        Question q1 = mQuestions.get(mCurrentQuestion);
        mTitle.setText(q1.getContent());
        for(int i = 0; i < 3; i++) {
            mAnswerButtons.get(i).setText(q1.getAnswers().get(i));
        }
        // Sprawdza czy dla danego pytania zostala udzielona odpowiedz, jezeli tak to zaznacz wybrana
        if (mAnswersArray[mCurrentQuestion] > 0) {
            mAnswers.check(mAnswersArray[mCurrentQuestion]);
        }
     }

    @OnClick(R.id.btn_previous)
    protected void onBackClick() {
        if (mCurrentQuestion == 0) {
            onBackTapped();
            return;
        }
        // zapisanie udzielonej odpowiedzi na aktualne pytanie
        mAnswersArray[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        mCurrentQuestion--;
        refreshQuestionView();
    }

    @OnClick(R.id.btn_next)
    protected void onNextClicl() {
        // zapisanie udzielonej odpowiedzi na aktualne pytanie
        mAnswersArray[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        if(mCurrentQuestion == mQuestions.size() - 1) {
            int correctAnswers = countCorrectAnswers();
            int totalAnswers = mAnswersArray.length;
            displayResults(correctAnswers, totalAnswers);
            return;
        }
        // Sprawdzamy czy użytkownik wybrał cokolwiek (getCheckRadioButtonId zwroci cos innego niz
        if(mAnswersArray[mCurrentQuestion] == -1) {
            Toast.makeText(this, "Wybierz odpowiedź", Toast.LENGTH_LONG).show();
            return;
        }
        mCurrentQuestion++;
        refreshQuestionView();
    }

    private void displayResults(int correctAnswers, int totalAnswers) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Wynik quizu")
                .setCancelable(false)
                .setMessage("Odpowiedziałeś poprawnie na " + correctAnswers + " pytań z " + totalAnswers)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .create();
        dialog.show();
    }

    private int countCorrectAnswers() {
        int sum = 0;

        for (int i = 0; i < mQuestions.size(); i++) {
            Question question = mQuestions.get(i);
            int userAnswerId = mAnswersArray[i];
            int correctAnswerId = mAnswerButtons.get(question.getCorrectAnswers()).getId();
            if (userAnswerId == correctAnswerId) {
                sum++;
            }
        }
        return sum;
    }
}
