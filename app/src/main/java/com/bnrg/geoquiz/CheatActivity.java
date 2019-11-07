package com.bnrg.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    public static final String GROUND_ANSWER = "groundAnswer";
    public static final String ANSWER_SHOWN = "answerShown";
    private boolean mAnswerIsTrue;
    private TextView mAnswerTextView;
    private Button mShowMeAnswerButton;
    private boolean mIsCheater;

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
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(GROUND_ANSWER,false);

        mAnswerTextView = findViewById(R.id.answer_text_view);
        mShowMeAnswerButton = findViewById(R.id.show_answer_button);
        mShowMeAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                mAnswerTextView.setText(String.valueOf(mAnswerIsTrue));
                
                mIsCheater = true;

                setAnswerShownResult(mIsCheater);
            }
        });

    }

    private void setAnswerShownResult(boolean mIsCheater){
        Intent data = new Intent();
        data.putExtra(ANSWER_SHOWN, mIsCheater);
        setResult(RESULT_OK, data);
    }


}
