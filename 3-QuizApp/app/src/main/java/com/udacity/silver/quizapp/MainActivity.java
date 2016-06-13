package com.udacity.silver.quizapp;

import android.animation.ObjectAnimator;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindColor(R.color.correct)
    int correctColor;

    @BindColor(R.color.wrong)
    int wrongColor;

    @BindView(R.id.question_1)
    View question1;

    @BindView(R.id.question_2)
    View question2;

    @BindView(R.id.question_2_correct_option)
    RadioButton question2correctButton;

    @BindView(R.id.question_3)
    View question3;

    @BindView(R.id.question_3_option_1)
    CheckBox question3option1;

    @BindView(R.id.question_3_option_2)
    CheckBox question3option2;

    @BindView(R.id.question_3_option_3)
    CheckBox question3option3;

    @BindView(R.id.question_3_option_4)
    CheckBox question3option4;

    @BindView(R.id.question_4)
    View question4;

    @BindView(R.id.question_5)
    View question5;

    @BindView(R.id.answer_1)
    EditText answer1;

    @BindView(R.id.answer_4)
    EditText answer4;

    @BindView(R.id.answer_5)
    EditText answer5;

    @BindView(R.id.grade_button)
    Button gradeButton;

    @BindString(R.string.answer_1)
    String answer1String;

    @BindString(R.string.answer_4)
    String answer4String;

    @BindString(R.string.answer_5)
    String answer5String;

    @BindString(R.string.allCorrect)
    String allCorrect;

    @BindString(R.string.notAllCorrect)
    String notAllCorrect;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        toast = Toast.makeText(this, notAllCorrect, Toast.LENGTH_LONG);


    }

    public void grade(View view) {


        Boolean question2Correct = question2correctButton.isChecked();
        if (question2Correct) {
            markCorrect(question2);
        } else {
            markWrong(question2);
        }

        Boolean question3Correct =
                question3option1.isChecked() &&
                        question3option3.isChecked() &&
                        !question3option2.isChecked() &&
                        !question3option4.isChecked();

        if (question3Correct) {
            markCorrect(question3);
        } else {
            markWrong(question3);
        }

        Boolean question1Correct = gradeTextQuestion(question1, answer1, answer1String);
        Boolean question4Correct = gradeTextQuestion(question4, answer4, answer4String);
        Boolean question5Correct = gradeTextQuestion(question5, answer5, answer5String);


        int toastResource = R.string.notAllCorrect;

        if (question1Correct && question2Correct && question3Correct && question4Correct && question5Correct) {
            toastResource = R.string.allCorrect;
        }

        toast.setText(toastResource);
        toast.show();


        ObjectAnimator spinAnimation = ObjectAnimator.ofFloat(gradeButton, "rotation", 0, 1080);
        spinAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        spinAnimation.setDuration(500);
        spinAnimation.start();
    }

    private boolean gradeTextQuestion(View question, EditText editText, String answer) {

        String userAnswer = editText.getText().toString();

        Boolean correct = false;
        if (userAnswer.toLowerCase().contains(answer)) {
            markCorrect(question);
            correct = true;
        } else {
            markWrong(question);
            correct = false;
        }

        return correct;
    }

    private void markCorrect(View view) {
        setFadingBackground(view, R.drawable.correct);
    }

    private void markWrong(View view) {
        setFadingBackground(view, R.drawable.wrong);
    }

    private void setFadingBackground(View view, @DrawableRes int resourceId) {
        view.setBackgroundResource(resourceId);
        Drawable background = view.getBackground();
        background.setAlpha(255);

        ObjectAnimator alphaAnimator = ObjectAnimator.ofInt(background, "alpha", 255, 0);
        alphaAnimator.setDuration(500);
        alphaAnimator.setStartDelay(500);

        alphaAnimator.start();

    }


}
