package com.bnrg.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

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

    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    //private ImageButton mPrevButton;
    private TextView mQuestionTextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_quiz);

        if (savedInstanceState != null){
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
        }

        mFalseButton = (Button) findViewById(R.id.false_button);
        mTrueButton = (Button) findViewById(R.id.true_button);
        mNextButton = (Button) findViewById(R.id.next_button);
        //mPrevButton = (ImageButton) findViewById(R.id.prev_button);
        mQuestionTextView = (TextView) findViewById(R.id.question_text);

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
                updateQuestion();
            }
        });
        /*mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                updateQuestion();
            }
        });
*/
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onStop(){
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy(){
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
        }catch (ArrayIndexOutOfBoundsException ex){
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

        if (userPressTrue == answerTrue) {
            messageRestId = R.string.correct_toast;
        } else {
            messageRestId = R.string.incorrect_toast;
        }

        Toast t = Toast.makeText(this, messageRestId, Toast.LENGTH_SHORT);

        t.setGravity(Gravity.TOP, 0, 55);

        t.show();

        mTrueButton.setClickable(false);
        mFalseButton.setClickable(false);
    }
}
