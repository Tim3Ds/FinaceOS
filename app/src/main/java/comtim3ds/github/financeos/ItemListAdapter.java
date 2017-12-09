package comtim3ds.github.financeos;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Date;

import comtim3ds.github.financeos.data.financeOSContract;

/**
 * Created by TinMa on 11/30/2017.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    private Context adapterContext;

    private Cursor result;

    public ItemListAdapter(Context context, Cursor results){
        this.adapterContext = context;
        this.result = results;
    }

    public void swapCursor(Cursor newResult){
        if(result != null){ result.close(); }
        result = newResult;
        if(newResult != null){
            this.notifyDataSetChanged();
        }
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        View view = inflater.inflate(R.layout.content_list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position){
        if(!result.moveToPosition(position))
            return;
        Long id = result.getLong(result.getColumnIndex(financeOSContract.financeOSEntry._ID));
        String item_name =  result.getString(result.getColumnIndex(financeOSContract.financeOSEntry.COLUMN_Item_Name));
        double item_value = result.getDouble(result.getColumnIndex(financeOSContract.financeOSEntry.COLUMN_Item_Value));
        String item_due = result.getString(result.getColumnIndex(financeOSContract.financeOSEntry.COLUMN_Expected_Date));

        holder.itemView.setTag(id);
        holder.nameTextView.setText(item_name);
        holder.valueTextView.setText("$"+String.valueOf(item_value));
        holder.dueDateTextView.setText(item_due);
    }

    @Override
    public int getItemCount(){
        return result.getCount();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView nameTextView;
        TextView valueTextView;
        TextView dueDateTextView;

        /**
         * @param itemView The view inflated
         *                 {@link ItemListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public ItemViewHolder(View itemView){
            super(itemView);
            nameTextView = itemView.findViewById(R.id.list_item_name);
            valueTextView = itemView.findViewById(R.id.list_item_value);
            dueDateTextView = itemView.findViewById(R.id.list_item_dueDate);
        }
    }
}
