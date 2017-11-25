package comtim3ds.github.financeos;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IntroActivity extends AppCompatActivity {

    Button goMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        goMain = (Button) findViewById(R.id.btn_goToMain);

        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = IntroActivity.this;

                Class destination = MainActivity.class;

                Intent startMain = new Intent(context, destination);

                startActivity(startMain);

            }
        });
    }
}
