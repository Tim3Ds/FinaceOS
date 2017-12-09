package comtim3ds.github.financeos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comtim3ds.github.financeos.data.financeOSContract;
import comtim3ds.github.financeos.data.financeOSDbHelper;

public class MainActivity extends AppCompatActivity {

    TextView Expense;
    TextView Income;
    TextView Over_Short;

    Button goToExpenseList;
    Button goTOIncomeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Expense = findViewById(R.id.tv_total_Expenses);
        Income = findViewById(R.id.tv_total_Income);
        Over_Short = findViewById(R.id.tv_over_short);

        double totalExpences = getSumItems("Expense");
        double totalIncome = getSumItems("Income");

        Expense.setText('$'+Double.toString(totalExpences));
        Income.setText('$'+Double.toString(totalIncome));
        Over_Short.setText('$'+Double.toString(totalIncome-totalExpences));

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
    private double getSumItems(String type){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "SELECT SUM("+financeOSContract.financeOSEntry.COLUMN_Item_Value+
                ") FROM "+financeOSContract.financeOSEntry.TABLE_NAME+" WHERE "+
                financeOSContract.financeOSEntry.COLUMN_Type + " =?";
        String[] args = { type };
        return android.database.DatabaseUtils.longForQuery(db, sql, args);
    }
}
