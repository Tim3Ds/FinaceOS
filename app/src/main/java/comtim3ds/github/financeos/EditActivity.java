package comtim3ds.github.financeos;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

import comtim3ds.github.financeos.data.financeOSContract;
import comtim3ds.github.financeos.data.financeOSDbHelper;

/**
 * Created by TinMa on 12/10/2017.
 */

public class EditActivity extends AppCompatActivity {

    Long _ID;
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
        _ID = intent.getLongExtra("_Id", 0);
        TYPE = intent.getStringExtra("Type");
        insertHeader = this.findViewById(R.id.tv_insert_display);
        insertHeader.setText("Edit " + TYPE + " Item");

        getItem(_ID);

        Button btn_addItem = findViewById(R.id.btn_insertItem);
        btn_addItem.setText("Update Item");
        btn_addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateItemInList(v, _ID);
                Intent BackToList = new Intent(EditActivity.this, ItemActivity.class);
                BackToList.putExtra("Type", TYPE);
                startActivity(BackToList);
                finish();
            }
        });
    }

    public void updateItemInList(View view, long id){
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

        long result = update_item(id, item_name.getText().toString(), newItemValue, item_dueDate.getSelectedItem().toString());

        if(result>0){
            Toast.makeText(context, "Item Update Success", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context, "Item Update Fail", Toast.LENGTH_LONG).show();
        }

    }

    private long update_item(long id, String name, Double value, String dueDate){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(financeOSContract.financeOSEntry.COLUMN_Item_Name, name);
        cv.put(financeOSContract.financeOSEntry.COLUMN_Item_Value, value);
        cv.put(financeOSContract.financeOSEntry.COLUMN_Expected_Date, dueDate);

        String where = financeOSContract.financeOSEntry._ID+"=?";
        String[] whereArgs = {Long.toString(id)};

        return db.update(financeOSContract.financeOSEntry.TABLE_NAME, cv, where, whereArgs);
    }

    private void getItem(long _id){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = financeOSContract.financeOSEntry._ID + " =?";
        String[] args = { String.valueOf(_id) };
        Cursor result = db.query(
                financeOSContract.financeOSEntry.TABLE_NAME,
                null,
                selection,
                args,
                null,
                null,
                null
        );
        if(result != null){
            result.moveToFirst();

            item_name = this.findViewById(R.id.et_insert_name);
            item_name.setText(
                    result.getString(
                            result.getColumnIndex(financeOSContract.financeOSEntry.COLUMN_Item_Name)
                    ));

            item_value  = this.findViewById(R.id.et_insert_value);
            item_value.setText(
                    String.valueOf(
                            result.getDouble(
                                result.getColumnIndex(financeOSContract.financeOSEntry.COLUMN_Item_Value)
                    )));

            item_dueDate = this.findViewById(R.id.sp_select_expectedDate);
            String day = result.getString(
                    result.getColumnIndex(financeOSContract.financeOSEntry.COLUMN_Expected_Date));
            day = day.substring(0, day.length()-2);
            int dayID = Integer.parseInt(day);
            item_dueDate.setSelection(dayID-1);

        }else{
            Toast.makeText(EditActivity.this, "item not found please report problem dbx01", Toast.LENGTH_LONG).show();
            Intent BackToList = new Intent(EditActivity.this, ItemActivity.class);
            BackToList.putExtra("Type", TYPE);
            startActivity(BackToList);
            finish();
        }
    }

}