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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    TextView Expense;
//    TextView Income;
//    TextView Over_Short;

    Button goToExpenseList;
    Button goTOIncomeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToExpenseList = (Button) findViewById(R.id.btn_goToExpenses);

        goToExpenseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startExpenseList = new Intent(MainActivity.this, ExpenseActivity.class);
                startActivity(startExpenseList);
            }
        });

        goTOIncomeList = (Button) findViewById(R.id.btn_goToIncome);
        goTOIncomeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIncomeList = new Intent(MainActivity.this, IncomeActivity.class);
                startActivity(startIncomeList);
            }
        });

    }
}
