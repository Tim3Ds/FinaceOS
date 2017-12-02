package comtim3ds.github.financeos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import comtim3ds.github.financeos.data.financeOSDbHelper;
import comtim3ds.github.financeos.data.financeOSContract;


public class ItemActivity extends AppCompatActivity {

    private  ItemListAdapter listAdapter;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // recyclerView Stuff
        RecyclerView itemRecyclerView = this.findViewById(R.id.rv_item_list);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = getItems();

        listAdapter = new ItemListAdapter(this, cursor.getCount());

        // link adapter to recycler view
        itemRecyclerView.setAdapter(listAdapter);



        Button fab = (Button) findViewById(R.id.dtn_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startIncomeList = new Intent(ItemActivity.this, InsertActivity.class);
                startActivity(startIncomeList);
            }
        });



    }

    private Cursor getItems(){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        db = dbHelper.getReadableDatabase();
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
