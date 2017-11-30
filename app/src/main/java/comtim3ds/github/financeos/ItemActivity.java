package comtim3ds.github.financeos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ItemActivity extends AppCompatActivity {

    private  ItemListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // recyclerView Stuff
        RecyclerView itemRecyclerView;
        itemRecyclerView = this.findViewById(R.id.rv_item_list);
        itemRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        listAdapter = new ItemListAdapter(this);
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

}
