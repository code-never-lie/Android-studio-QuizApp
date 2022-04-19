package com.example.syedsubtainraza.quizapp;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    public static final String EXTRA_SCORE = "extraScore";
    private static final long COUNTDOWN_IN_MILLIS = 30000;

    private static final String KEY_SCORE = "keyScore";
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";
    private static final String KEY_ANSWERED = "keyAnswered";
    private static final String KEY_QUESTION_LIST = "keyQuestionList";

    private TextView textViewQuestion, textViewScore, textViewCountDown, textViewQuestionCount, textViewDifficulty, textViewCategory;
    private RadioGroup radioGroup;
    private RadioButton radioButton1, radioButton2, radioButton3;
    private Button buttonConfirm;

    private ArrayList<Question> questionList;

    private ColorStateList textColorDefaultRadioButton;
    private ColorStateList getTextColorDefaultCountDown;

    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private int questionCounter; //how many question we have shown
    private int questionCountTotal; //total number of question in arraylist
    private Question currentQuestion;
    private int score;
    private boolean answered;

    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);


    textViewQuestion = findViewById(R.id.text_view_question);
    textViewScore = findViewById(R.id.text_view_score);
    textViewCountDown = findViewById(R.id.text_view_countdown);
    textViewQuestionCount = findViewById(R.id.text_view_question_count);
    textViewDifficulty = findViewById(R.id.text_view_difficulty);
    textViewCategory = findViewById(R.id.text_view_category);

    radioGroup = findViewById(R.id.radio_group);

    radioButton1 = findViewById(R.id.radio_button1);
    radioButton2 = findViewById(R.id.radio_button2);
    radioButton3 = findViewById(R.id.radio_button3);

    buttonConfirm = findViewById(R.id.button_confirm_next);

    textColorDefaultRadioButton = radioButton1.getTextColors();
    getTextColorDefaultCountDown = textViewCountDown.getTextColors();


    Intent intent = getIntent();
    int categoryID = intent.getIntExtra(MainActivity.EXTRA_CATEGORY_ID, 0);
    String categoryName = intent.getStringExtra(MainActivity.EXTRA_CATEGORY_NAME);
    String difficulty = intent.getStringExtra(MainActivity.EXTRA_DIFFICULTY);

    textViewCategory.setText("Category: " + categoryName);
    textViewDifficulty.setText("Difficulty: " + difficulty);


    if (savedInstanceState == null) {
        QuizDbHelper dbHelper = QuizDbHelper.getInstance(this);
        questionList = dbHelper.getQuestions(categoryID, difficulty);


        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);

        showNextQuestion();
    }else
    {
        questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
        questionCountTotal = questionList.size();
        questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
        currentQuestion = questionList.get(questionCounter - 1);
        score = savedInstanceState.getInt(KEY_SCORE);
        timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
        answered = savedInstanceState.getBoolean(KEY_ANSWERED);

        if (!answered)
        {
            startCountDown();
        }
        else
        {
            updateCountDownText();
            showSolution();
        }
    }


    buttonConfirm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!answered)
            {
                if (radioButton1.isChecked() || radioButton2.isChecked() || radioButton3.isChecked())
                {
                    checkedAnswer();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please Select any option", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                showNextQuestion();
            }
        }
    });

    }

    private void showNextQuestion()
    {
        radioButton1.setTextColor(textColorDefaultRadioButton);
        radioButton2.setTextColor(textColorDefaultRadioButton);
        radioButton3.setTextColor(textColorDefaultRadioButton);

        radioGroup.clearCheck();

        if (questionCounter < questionCountTotal)
        {
            currentQuestion = questionList.get(questionCounter);
            textViewQuestion.setText(currentQuestion.getQuestion());
            radioButton1.setText(currentQuestion.getOption1());
            radioButton2.setText(currentQuestion.getOption2());
            radioButton3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false; //if we click next question then they show the results
            buttonConfirm.setText("Confirm");

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        }
        else
        {
            finishQuiz();
        }
    }

    private void startCountDown()
    {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

                timeLeftInMillis = 0;
                updateCountDownText();
                checkedAnswer();

            }
        }.start();
    }

    private void updateCountDownText()
    {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
        textViewCountDown.setText(timeFormatted);

        if(timeLeftInMillis < 10000) //if time is less than 10 second then
        {
            textViewCountDown.setTextColor(Color.RED);
        }
        else
        {
            textViewCountDown.setTextColor(getTextColorDefaultCountDown);
        }
    }

    private void checkedAnswer()
    {
        answered = true;
        countDownTimer.cancel();

        RadioButton radioButtonSelected = findViewById(radioGroup.getCheckedRadioButtonId());
        int answerNr = radioGroup.indexOfChild(radioButtonSelected) + 1;

        if (answerNr == currentQuestion.getAnswer_Nr())
        {
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution()
    {
        radioButton1.setTextColor(Color.RED);
        radioButton2.setTextColor(Color.RED);
        radioButton3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswer_Nr())
        {
            case 1:
                radioButton1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is Correct");
                break;
            case 2:
                radioButton2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is Correct");
                break;
            case 3:
                radioButton3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is Correct");
                break;
        }

        if(questionCounter < questionCountTotal)
        {
            buttonConfirm.setText("Next");
        }
        else
        {
            buttonConfirm.setText("Finish");
        }

    }

    private void finishQuiz()
    {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis())
        {
            finishQuiz();
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Press back again to finish", Toast.LENGTH_SHORT).show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null)
        {
            countDownTimer.cancel();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);

        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }
}
