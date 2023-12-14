package com.example.quizapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView tq, question;

    Button next, bt1, bt2, bt3, bt4;

    int score = 0;
    int totalQuestion = questionAnswer.questionss.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        question = findViewById(R.id.question);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);
        bt3 = findViewById(R.id.bt3);
        bt4 = findViewById(R.id.bt4);
        next = findViewById(R.id.button);
        tq = findViewById(R.id.tq);

        bt1.setOnClickListener(this);
        bt2.setOnClickListener(this);
        bt3.setOnClickListener(this);
        bt4.setOnClickListener(this);
        next.setOnClickListener(this);

        tq.setText("Total Questions: " + totalQuestion);

        loadNewQuestion();
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View view) {
        // Reset background color of all buttons
        bt1.setBackgroundColor(Color.WHITE);
        bt2.setBackgroundColor(Color.WHITE);
        bt3.setBackgroundColor(Color.WHITE);
        bt4.setBackgroundColor(Color.WHITE);

        Button clickedButton = (Button) view;

        if (clickedButton.getId() == R.id.button) {
            // Check the selected answer when Next button is clicked
            if (selectedAnswer.equals(questionAnswer.CorrectAnswer[currentQuestionIndex])) {
                // Correct answer, increment score
                score++;
            } else {
                // Incorrect answer, show correct answer in green and selected answer in red
                showCorrectAnswer();
            }

            // Move to the next question
            currentQuestionIndex++;
            loadNewQuestion();
        } else {
            // User selected an answer option
            selectedAnswer = clickedButton.getText().toString();
            if (selectedAnswer.equals(questionAnswer.CorrectAnswer[currentQuestionIndex])) {
                // Correct answer, make the button green
                clickedButton.setBackgroundColor(Color.GREEN);
            } else {
                // Incorrect answer, make the selected button red and show correct answer in green
                clickedButton.setBackgroundColor(Color.RED);
                showCorrectAnswer();
            }
        }
    }

    private void showCorrectAnswer() {
        // Find the correct answer button and make it green
        String correctAnswer = questionAnswer.CorrectAnswer[currentQuestionIndex];
        if (bt1.getText().toString().equals(correctAnswer)) {
            bt1.setBackgroundColor(Color.GREEN);
        } else if (bt2.getText().toString().equals(correctAnswer)) {
            bt2.setBackgroundColor(Color.GREEN);
        } else if (bt3.getText().toString().equals(correctAnswer)) {
            bt3.setBackgroundColor(Color.GREEN);
        } else if (bt4.getText().toString().equals(correctAnswer)) {
            bt4.setBackgroundColor(Color.GREEN);
        }
    }

    void loadNewQuestion() {

        bt1.setBackgroundColor(Color.WHITE);
        bt2.setBackgroundColor(Color.WHITE);
        bt3.setBackgroundColor(Color.WHITE);
        bt4.setBackgroundColor(Color.WHITE);

        if (currentQuestionIndex == totalQuestion) {
            finishQuiz();
            return;
        }
        question.setText(questionAnswer.questionss[currentQuestionIndex]);
        bt1.setText(questionAnswer.choices[currentQuestionIndex][0]);
        bt2.setText(questionAnswer.choices[currentQuestionIndex][1]);
        bt3.setText(questionAnswer.choices[currentQuestionIndex][2]);
        bt4.setText(questionAnswer.choices[currentQuestionIndex][3]);
    }

    void finishQuiz() {
        String passStatus = "";
        if (score > totalQuestion * 0.60) {
            passStatus = "passed";
        } else {
            passStatus = "failed";
        }

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
    }
}
