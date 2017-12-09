package comtim3ds.github.financeos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import comtim3ds.github.financeos.data.financeOSDbHelper;
import comtim3ds.github.financeos.data.financeOSContract;


public class ItemActivity extends AppCompatActivity {

    String TYPE;

    TextView tv_activity_header;

    private  ItemListAdapter listAdapter;

    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_item);

        Intent intent = getIntent();
        TYPE = intent.getStringExtra("Type");

        tv_activity_header = this.findViewById(R.id.tv_activity_label);
        tv_activity_header.setText(TYPE + " List");

        // recyclerView Stuff
        RecyclerView itemRecyclerView = this.findViewById(R.id.rv_item_list);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = getItems(TYPE);

        listAdapter = new ItemListAdapter(this, cursor);

        // link adapter to recycler view
        itemRecyclerView.setAdapter(listAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(1, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long)viewHolder.itemView.getTag();

                removeItem(id);

                listAdapter.swapCursor(getItems(TYPE));
            }
        }).attachToRecyclerView(itemRecyclerView);


        Button fab = (Button)findViewById(R.id.dtn_add_item);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startInsert = new Intent(ItemActivity.this, InsertActivity.class);
                startInsert.putExtra("Type", TYPE);
                startActivity(startInsert);
                finish();
            }
        });
    }

//    @Override
//    protected void onStart(){
//        super.onStart();
//        listAdapter.swapCursor(getItems(TYPE));
//    }

    private Cursor getItems(String type){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = financeOSContract.financeOSEntry.COLUMN_Type + " =?";
        String[] args = { type };
        return db.query(
                financeOSContract.financeOSEntry.TABLE_NAME,
                null,
                selection,
                args,
                null,
                null,
                null
        );
    }

    private boolean removeItem(long id){
        financeOSDbHelper dbHelper = new financeOSDbHelper(this);
        db = dbHelper.getReadableDatabase();
        return db.delete(financeOSContract.financeOSEntry.TABLE_NAME,
                financeOSContract.financeOSEntry._ID + "=" + id, null) > 0;
    }
}
