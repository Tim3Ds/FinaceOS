package comtim3ds.github.financeos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import comtim3ds.github.financeos.data.financeOSContract;
import comtim3ds.github.financeos.data.financeOSDbHelper;

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

        Cursor expenseResults = getItems("Expense");
        Cursor incomeReults = getItems("Income");




        goToExpenseList = findViewById(R.id.btn_goToExpenses);
        goToExpenseList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startExpenseList = new Intent( MainActivity.this, ItemActivity.class);
                startExpenseList.putExtra("Type", "Expense");
                startActivity(startExpenseList);
            }
        });

        goTOIncomeList = findViewById(R.id.btn_goToIncome);
        goTOIncomeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIncomeList = new Intent(MainActivity.this, ItemActivity.class);
                startIncomeList.putExtra("Type", "Income");
                startActivity(startIncomeList);
            }
        });

    }
    private Cursor getItems(String type){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        return db.query(
                financeOSContract.financeOSEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                financeOSContract.financeOSEntry.COLUMN_Expected_Date
        );
    }
}
