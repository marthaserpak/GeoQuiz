package com.bnrg.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String GROUND_ANSWER = "groundAnswer";
    public static final String ANSWER_SHOWN = "answerShown";
    private static final String KEY_INDEX = "index";
    private static final String TAG = "CheatActivity";

    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowMeAnswerButton;


    public static Intent newIntent (Context packageContext, boolean answerIsTrue){
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(GROUND_ANSWER, answerIsTrue);
        return intent;
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(ANSWER_SHOWN,false);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called");
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(GROUND_ANSWER,false);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowMeAnswerButton = findViewById(R.id.show_answer_button);
        mShowMeAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                mAnswerTextView.setText(String.valueOf(mAnswerIsTrue));
                
                boolean answerIsShown = true;

                Log.d(TAG, "Overrriden answerIsShown " + answerIsShown);
                setAnswerShownResult(answerIsShown);
            }
        });

    }

    private void setAnswerShownResult(boolean isAnswerShown){
        Intent data = new Intent();
        Log.d(TAG, "In setAnswerShownResult isAnswerShown " + isAnswerShown);
        data.putExtra(ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }
}
