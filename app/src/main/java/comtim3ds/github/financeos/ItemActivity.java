package comtim3ds.github.financeos;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.SwipeDismissBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
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

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackDelete = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                    final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundEdit;

                    getDefaultUIUtil().onSelected(foregroundView);
                    getDefaultUIUtil().onSelected(backgroundEditView);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundEdit;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
                getDefaultUIUtil().onDrawOver(c, recyclerView, backgroundEditView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundEdit;
                getDefaultUIUtil().clearView(foregroundView);
                getDefaultUIUtil().clearView(backgroundEditView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundEdit;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
                getDefaultUIUtil().onDraw(c, recyclerView, backgroundEditView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final Long id = (long)viewHolder.itemView.getTag();
                removeItem(id);
                listAdapter.swapCursor(getItems(TYPE));
            }

        };

        ItemTouchHelper.SimpleCallback itemTouchHelperCallbackEdit = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT ) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                    final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundDelete;

                    getDefaultUIUtil().onSelected(foregroundView);
                    getDefaultUIUtil().onSelected(backgroundEditView);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundDelete;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
                getDefaultUIUtil().onDrawOver(c, recyclerView, backgroundEditView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundDelete;
                getDefaultUIUtil().clearView(foregroundView);
                getDefaultUIUtil().clearView(backgroundEditView);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewForeground;
                final View backgroundEditView = ((ItemListAdapter.ItemViewHolder) viewHolder).viewBackgroundDelete;

                getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                        actionState, isCurrentlyActive);
                getDefaultUIUtil().onDraw(c, recyclerView, backgroundEditView, dX, dY,
                        actionState, isCurrentlyActive);
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                final Long id = (long)viewHolder.itemView.getTag();
                Intent startEdit = new Intent(ItemActivity.this, EditActivity.class);
                startEdit.putExtra("_Id", id);
                startEdit.putExtra("Type", TYPE);
                startActivity(startEdit);
                finish();
            }

        };
        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallbackDelete).attachToRecyclerView(itemRecyclerView);
        new ItemTouchHelper(itemTouchHelperCallbackEdit).attachToRecyclerView(itemRecyclerView);


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
