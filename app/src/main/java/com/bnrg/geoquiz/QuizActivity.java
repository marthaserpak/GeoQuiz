package com.bnrg.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index";
    private static final int REQUEST_CODE_CHEAT = 0;
    private static final String KEY_CHEATER = "cheater";

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private TextView mQuestionTextView;
    private Button mCheatButton;

    private Question[] mQuestionBank = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true)
    };

    private int mCurrentIndex = 0;

    String farewell = "That's all folks!";
    private boolean mIsCheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
            Log.d(TAG, "Restored mIsCheater " + mIsCheater);
        }

        mFalseButton = findViewById(R.id.false_button);
        mTrueButton = findViewById(R.id.true_button);
        mNextButton = findViewById(R.id.next_button);
        mQuestionTextView = findViewById(R.id.question_text);
        mCheatButton = findViewById(R.id.cheat_button);

        updateQuestion();

        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Current question index: " + mCurrentIndex);
                // mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mCurrentIndex = (mCurrentIndex + 1);
                Log.d(TAG, "Index updated");
                Log.d(TAG, "Current question index: " + mCurrentIndex);
                updateQuestion();
            }
        });

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAnswer(true);
            }
        });
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                mCheatButton.setClickable(true);
                mIsCheater = false;
                updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(QuizActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            Log.d(TAG, "In onActivity mIsCheat " + mIsCheater);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");

        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_CHEATER, mIsCheater);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }

    private void updateQuestion() {

        mTrueButton.setClickable(true);
        mFalseButton.setClickable(true);

        Question question;

        try {
            question = mQuestionBank[mCurrentIndex];
            int questionId = mQuestionBank[mCurrentIndex].getTextResId();
            mQuestionTextView.setText(questionId);
        } catch (ArrayIndexOutOfBoundsException ex) {
            // Регистрация сообщений с уровнем отладки "error"
            // вместе с трассировкой стека исключений
            Log.e(TAG, "Index was out of bounds", ex);
            mQuestionTextView.setText(farewell);
            onStop();
        }


    }

    private void checkAnswer(boolean userPressTrue) {
        boolean answerTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

        int messageRestId = 0;

        if (mIsCheater) {
            Toast toast = Toast.makeText(this,R.string.judgment_toast, Toast.LENGTH_SHORT);

            toast.setGravity(Gravity.TOP, 0, 75);

            toast.show();

            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);
            mCheatButton.setClickable(false);


        } else {
            if (userPressTrue == answerTrue) {
                messageRestId = R.string.correct_toast;

                mTrueButton.setClickable(false);
                mFalseButton.setClickable(false);


            } else messageRestId = R.string.incorrect_toast;

            Toast t = Toast.makeText(this, messageRestId, Toast.LENGTH_SHORT);

            t.setGravity(Gravity.TOP, 0, 75);

            t.show();

            mTrueButton.setClickable(false);
            mFalseButton.setClickable(false);
            mCheatButton.setClickable(false);
        }
    }
}
