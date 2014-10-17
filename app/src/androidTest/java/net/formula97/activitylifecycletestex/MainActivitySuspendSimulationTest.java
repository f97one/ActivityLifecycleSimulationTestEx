package net.formula97.activitylifecycletestex;

import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import junit.framework.Test;

import org.w3c.dom.Text;

/**
 * Activityのライフサイクルメソッドの動きを
 * Created by HAJIME on 2014/10/17.
 */
public class MainActivitySuspendSimulationTest extends ActivityUnitTestCase<MainActivity> {

    /**
     * コンストラクタ。
     */
    public MainActivitySuspendSimulationTest() {
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

    public void test01_Homeボタンを模擬() throws Throwable {
        // Activity起動
        MainActivity activity = startActivity(new Intent(), null, null);

        // onResume実行後まで移動
        Bundle bundleInOut = new Bundle();
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnPostCreate(activity, bundleInOut);
        getInstrumentation().callActivityOnResume(activity);

        // 1. EditTextに「now testing by JUnit」を入力
        // 2. buttonのクリックイベントを発行
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getEditText().setText("now testing by JUnit");
                getButton().performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        String tv1String = getTextView1().getText().toString();
        String tv2String = getTextView2().getText().toString();

        assertEquals("上のTextViewの文字が「now testing by JUnit」になっている",
                "now testing by JUnit", tv1String);
        assertEquals("EditTextが空になっている", "", getEditText().getText().toString());

        // Homeボタンを押下 -> 復帰をシミュレート
        // 1. UIに新しい値を投入.
        // 　1-1. EditTextに「I will fall in back stack」を入力
        // 　1-2. buttonのクリックイベントを発行
        // 　1-3. Spinnerの選択位置を3に変更
        // 　1-4. 再びEditTextに「input again」を入力
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getEditText().setText("I will fall in back stack");
                getButton().performClick();
                getSpinner().setSelection(3, true);
                getEditText().setText("input again");
            }
        });
        getInstrumentation().waitForIdleSync();

        // 2. Homeボタン押下からバックスタックへ引っ込むまで
        getInstrumentation().callActivityOnUserLeaving(activity);
        getInstrumentation().callActivityOnPause(activity);
        getInstrumentation().callActivityOnSaveInstanceState(activity, bundleInOut);
        getInstrumentation().callActivityOnStop(activity);

        String tvResult = bundleInOut.getString(MainActivity.BUNDLE_TEXTVIEW1);
        String etResult = bundleInOut.getString(MainActivity.BUNDLE_EDITTEXT);
        int spPos = bundleInOut.getInt(MainActivity.BUNDLE_SPINNER);

        assertEquals("上のTextViewの値が「I will fall in back stack」で保存された",
                "I will fall in back stack", tvResult);
        assertEquals("EditTextの値が「input again」で保存された",
                "input again", etResult);
        assertEquals("Spinnerの選択位置が3で保存された",
                3, spPos);

        // 3. バックスタックへ引っ込んだ状態から、ホーム画面より立ち上げなおす
        getInstrumentation().callActivityOnRestart(activity);
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnResume(activity);

        String tvResumedResult = getTextView1().getText().toString();
        String etResumedResult = getEditText().getText().toString();
        int spResumedPos = getSpinner().getSelectedItemPosition();

        assertEquals("上のTextViewの値が復元されている", tvResult, tvResumedResult);
        assertEquals("EditTextの値が復元されている", etResult, etResumedResult);
        assertEquals("Spinnerの選択位置が復元されている", spPos, spResumedPos);
    }

    public void test02_スリープで長時間放置後の復帰を模擬() throws Throwable {
        // Activity起動
        MainActivity activity = startActivity(new Intent(), null, null);

        // onResume実行後まで移動
        Bundle bundleInOut = new Bundle();
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnPostCreate(activity, bundleInOut);
        getInstrumentation().callActivityOnResume(activity);

        // 1. EditTextに「now testing by JUnit」を入力
        // 2. buttonのクリックイベントを発行
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getEditText().setText("now testing by JUnit");
                getButton().performClick();
            }
        });
        getInstrumentation().waitForIdleSync();

        String tv1String = getTextView1().getText().toString();
        String tv2String = getTextView2().getText().toString();

        assertEquals("上のTextViewの文字が「now testing by JUnit」になっている",
                "now testing by JUnit", tv1String);
        assertEquals("EditTextが空になっている", "", getEditText().getText().toString());

        // スリープ発動 -> 長時間放置されActivity破棄 -> 復帰をシミュレート
        // 1. UIに新しい値を投入.
        // 　1-1. EditTextに「I will fall in back stack」を入力
        // 　1-2. buttonのクリックイベントを発行
        // 　1-3. Spinnerの選択位置を3に変更
        // 　1-4. 再びEditTextに「input again」を入力
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getEditText().setText("I will fall in back stack");
                getButton().performClick();
                getSpinner().setSelection(3, true);
                getEditText().setText("input again");
            }
        });
        getInstrumentation().waitForIdleSync();

        // 2. Homeボタン押下からバックスタックへ引っ込むまで
        getInstrumentation().callActivityOnPause(activity);
        getInstrumentation().callActivityOnSaveInstanceState(activity, bundleInOut);
        getInstrumentation().callActivityOnStop(activity);

        String tvResult = bundleInOut.getString(MainActivity.BUNDLE_TEXTVIEW1);
        String etResult = bundleInOut.getString(MainActivity.BUNDLE_EDITTEXT);
        int spPos = bundleInOut.getInt(MainActivity.BUNDLE_SPINNER);

        assertEquals("上のTextViewの値が「I will fall in back stack」で保存された",
                "I will fall in back stack", tvResult);
        assertEquals("EditTextの値が「input again」で保存された",
                "input again", etResult);
        assertEquals("Spinnerの選択位置が3で保存された",
                3, spPos);

        // 3. 長時間放置によりActivity破棄 -> 復帰まで
        getInstrumentation().callActivityOnCreate(activity, bundleInOut);
        getInstrumentation().callActivityOnStart(activity);
        getInstrumentation().callActivityOnRestoreInstanceState(activity, bundleInOut);
        getInstrumentation().callActivityOnPostCreate(activity, bundleInOut);
        getInstrumentation().callActivityOnResume(activity);

        String tvResumedResult = getTextView1().getText().toString();
        String etResumedResult = getEditText().getText().toString();
        int spResumedPos = getSpinner().getSelectedItemPosition();

        assertEquals("上のTextViewの値が復元されている", tvResult, tvResumedResult);
        assertEquals("EditTextの値が復元されている", etResult, etResumedResult);
        assertEquals("Spinnerの選択位置が復元されている", spPos, spResumedPos);
    }
}
