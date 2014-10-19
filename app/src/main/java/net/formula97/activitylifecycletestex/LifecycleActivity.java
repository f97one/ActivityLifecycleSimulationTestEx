package net.formula97.activitylifecycletestex;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

/**
 * ライフサイクルメソッドの通過を確認するActivity。
 *
 */
public class LifecycleActivity extends Activity {

    private void logger(String methodName) {
        String tag = "net.formula97.activitylcletestex";
        Log.d(tag, "LifecycleActivity : entered " + methodName);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle);

        logger("onCreate");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        logger("onSaveInstanceState");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        logger("onPostResume");
    }

    @Override
    protected void onPause() {
        super.onPause();

        logger("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();

        logger("onStop");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        logger("onUserLeaveHint");
    }

    @Override
    protected void onResume() {
        super.onResume();

        logger("pnResume");
    }

    @Override
    protected void onStart() {
        super.onStart();

        logger("onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        logger("onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        logger("onDestroy");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        logger("onNewIntent");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        logger("onUserInteraction");
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);

        logger("onTrimMemory");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lifecycle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
