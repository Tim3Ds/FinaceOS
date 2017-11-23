package comtim3ds.github.financeos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mTextEntry;
    private Button mBTNsendText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // link variables to View
        mBTNsendText = (Button) findViewById(R.id.btn_send_entry_to_child);
        mTextEntry = (EditText) findViewById(R.id.et_text_entry);

        // set listener
        mBTNsendText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = MainActivity.this;

            }
        });
    }
}
