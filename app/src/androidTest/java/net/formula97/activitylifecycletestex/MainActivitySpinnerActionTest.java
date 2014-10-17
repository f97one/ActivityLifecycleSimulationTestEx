package net.formula97.activitylifecycletestex;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * Created by HAJIME on 2014/10/17.
 */
public class MainActivitySpinnerActionTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivitySpinnerActionTest() {
        super(MainActivity.class);
    }


    /**
     * テスト対象ActivityのButtonを取得する。
     *
     * @return R.id.buttonのインスタンス
     */
    private Button getButton() {
        return (Button) getActivity().findViewById(R.id.button);
    }

    /**
     * テスト対象ActivityのtextViewを取得する。
     *
     * @return R.id.textViewのインスタンス
     */
    private TextView getTextView1() {
        return (TextView) getActivity().findViewById(R.id.textView);
    }

    /**
     * テスト対象ActivityのtextView2を取得する。
     *
     * @return R.id.textView2のインスタンス
     */
    private TextView getTextView2() {
        return (TextView) getActivity().findViewById(R.id.textView2);
    }

    /**
     * テスト対象ActivityのeditTextを取得する。
     *
     * @return R.id.editTextのインスタンス
     */
    private EditText getEditText() {
        return (EditText) getActivity().findViewById(R.id.editText);
    }

    /**
     * テスト対象Activityのspinnerを取得する。
     *
     * @return R.id.spinnerのインスタンス
     */
    private Spinner getSpinner() {
        return (Spinner) getActivity().findViewById(R.id.spinner);
    }

    public void test01_Spinnerの挙動確認() throws Throwable {
        MainActivity activity = getActivity();

        // 3. Spinnerの選択位置を0から2に変更
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getSpinner().setSelection(2, true);
            }
        });
        getInstrumentation().waitForIdleSync();

        String tv2String = getTextView2().getText().toString();

        assertEquals("下のTextViewの文字が「Selected : item 3」になっている",
                "Selected : item 3", tv2String);
    }

}
