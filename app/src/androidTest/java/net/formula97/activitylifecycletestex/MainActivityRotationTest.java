package net.formula97.activitylifecycletestex;

import android.content.pm.ActivityInfo;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

/**
 * 画面回転でデータが保持されるか否かを検査する。
 *
 * Created by HAJIME on 2014/10/18.
 */
public class MainActivityRotationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public MainActivityRotationTest() {
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

    public void test01_縦から横へ画面回転() throws Throwable {
        MainActivity activity = getActivity();

        // 1. EditTextに「now testing by JUnit」を入力
        // 2. buttonのクリックイベントを発行
        // 3. Spinnerの選択位置を0から2に変更
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getEditText().setText("now testing by JUnit");
                getButton().performClick();
                getSpinner().setSelection(2, true);
            }
        });
        getInstrumentation().waitForIdleSync();

        String tv1String = getTextView1().getText().toString();
        String tv2String = getTextView2().getText().toString();

        assertEquals("上のTextViewの文字が「now testing by JUnit」になっている",
                "now testing by JUnit", tv1String);
        assertEquals("EditTextが空になっている", "", getEditText().getText().toString());
        assertEquals("下のTextViewの文字が「Selected : item 3」になっている",
                "Selected : item 3", tv2String);

        // EditTextに「i'll turn」をセットして画面を横へ回転
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getEditText().setText("i'll turn");
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        });
        getInstrumentation().waitForIdleSync();

        String tv1ResumeResult = getTextView1().getText().toString();
        String tv2ResumeString = getTextView2().getText().toString();
        String etResumeResult = getEditText().getText().toString();

        assertEquals("上のTextViewの文字が「now testing by JUnit」で保存されている",
                "now testing by JUnit", tv1ResumeResult);
        assertEquals("EditTextが「i'll turn」で保存されている",
                "i'll turn", etResumeResult);
        assertEquals("下のTextViewの文字が「Selected : item 3」になっている",
                "Selected : item 3", tv2ResumeString);

    }
}
