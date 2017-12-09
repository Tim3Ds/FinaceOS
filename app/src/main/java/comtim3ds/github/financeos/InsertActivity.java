package comtim3ds.github.financeos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.TestCase;

import java.util.Date;

import comtim3ds.github.financeos.data.financeOSContract;
import comtim3ds.github.financeos.data.financeOSDbHelper;

public class InsertActivity extends AppCompatActivity {

    String TYPE;
    TextView insertHeader;
    EditText item_name;
    EditText item_value;
    Spinner item_dueDate;
    SQLiteDatabase db;

    // error logging
    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        Intent intent = getIntent();
        TYPE = intent.getStringExtra("Type");
        insertHeader = this.findViewById(R.id.tv_insert_display);
        insertHeader.setText("Add " + TYPE + " Item");

        item_name = this.findViewById(R.id.et_insert_name);

        item_value  = this.findViewById(R.id.et_insert_value);

        item_dueDate = this.findViewById(R.id.sp_select_expectedDate);

        Button btn_addItem = findViewById(R.id.btn_insertItem);
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToItemList(v, TYPE);
                Intent BackToList = new Intent(InsertActivity.this, ItemActivity.class);
                BackToList.putExtra("Type", TYPE);
                startActivity(BackToList);
                finish();
            }
        });
    }

    public void addToItemList(View view, String type){
        if(item_name.getText().length() == 0 || item_value.getText().length() == 0){
            return;
        }

        Double newItemValue = 0.00;
        try {
            newItemValue = Double.parseDouble(item_value.getText().toString());
        } catch (NumberFormatException ex){
            Log.e(LOG_TAG, "Failed to parse Double value to number: " + ex.getMessage());
        }

        Context context = getApplicationContext();

        long result = insert_item(type, item_name.getText().toString(), newItemValue, item_dueDate.getSelectedItem().toString());

        if(true){
            Toast.makeText(context, "Item Add Success" + Long.toString(result), Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Item Add Fail", Toast.LENGTH_LONG).show();
        };

    }

    private long insert_item(String type, String name, Double value, String dueDate){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(financeOSContract.financeOSEntry.COLUMN_Type, type);
        cv.put(financeOSContract.financeOSEntry.COLUMN_Item_Name, name);
        cv.put(financeOSContract.financeOSEntry.COLUMN_Item_Value, value);
        cv.put(financeOSContract.financeOSEntry.COLUMN_Expected_Date, dueDate);

        return db.insert(financeOSContract.financeOSEntry.TABLE_NAME, null, cv);
    }


}