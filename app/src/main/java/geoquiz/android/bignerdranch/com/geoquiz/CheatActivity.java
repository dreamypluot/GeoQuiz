package geoquiz.android.bignerdranch.com.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {

    private static final String TAG = "CheatActivity";
    public static final String CHEATED_INDEX = "cheated";
    public static final String EXTRA_ANSWER_IS_TRUE = "tfquiz.ANSWER_IS_TRUE";
    public static final String EXTRA_ANSWER_SHOWN = "tfquiz.ANSWER_SHOWN";

    boolean mAnswerIsTrue; //answer from QuizActivity
    boolean mIsCheater = false;
    TextView mAnswerTextView;
    Button mShowAnswer;

    // Tell the other activity the answer is shown
    private void setAnswerShownResult(boolean isAnswerShown) {
        mIsCheater = true;
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        // RESULT_OK or RESULT_CANCELED by default
        // Don't need to set the result code with setRsult() if you
        // don't need to inform the calling activity about the results
        setResult(RESULT_OK, data);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate() called");
        setContentView(R.layout.activity_cheat);

        if (savedInstanceState != null) {
            // first startup, so the answer must not be shown yet
            mIsCheater = savedInstanceState.getBoolean(CHEATED_INDEX, false);
        }
        setAnswerShownResult(mIsCheater);
        // Get answer from QuizActivity
        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        mAnswerTextView = (TextView)findViewById(R.id.answerTextView);

        mShowAnswer = (Button)findViewById(R.id.showAnswerButton);
        mShowAnswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                setAnswerShownResult(true);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState: " + mIsCheater);
        savedInstanceState.putBoolean(CHEATED_INDEX, mIsCheater);
    }
}
