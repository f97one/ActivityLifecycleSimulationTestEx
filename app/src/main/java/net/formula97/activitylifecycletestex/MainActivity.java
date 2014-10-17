package net.formula97.activitylifecycletestex;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    public static final String BUNDLE_TEXTVIEW1 = "BundleKeyTextView1";
    public static final String BUNDLE_TEXTVIEW2 = "BundleKeyTextView2";
    public static final String BUNDLE_SPINNER = "BundleKeySpinner";
    public static final String BUNDLE_EDITTEXT = "BundleKeyEditText";

    private TextView textView;
    private EditText editText;
    private Button button;
    private Spinner spinner;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        textView2 = (TextView) findViewById(R.id.textView2);
        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        spinner = (Spinner) findViewById(R.id.spinner);

        String[] spinnerItems = {
                "item 1",
                "item 2",
                "item 3",
                "item 4",
                "item 5"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        button.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(BUNDLE_TEXTVIEW1, textView.getText().toString());
        outState.putString(BUNDLE_TEXTVIEW2, textView2.getText().toString());
        outState.putString(BUNDLE_EDITTEXT, editText.getText().toString());
        outState.putInt(BUNDLE_SPINNER, spinner.getSelectedItemPosition());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        textView.setText(savedInstanceState.getString(BUNDLE_TEXTVIEW1));
        textView2.setText(savedInstanceState.getString(BUNDLE_TEXTVIEW2));
        editText.setText(savedInstanceState.getString(BUNDLE_EDITTEXT));
        spinner.setSelection(savedInstanceState.getInt(BUNDLE_SPINNER), true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String target = (String) (parent.getAdapter().getItem(position));
        textView2.setText("Selected : " + target);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        textView.setText(editText.getText());
        editText.setText("");
    }
}
