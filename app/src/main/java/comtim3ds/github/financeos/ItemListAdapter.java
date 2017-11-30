package comtim3ds.github.financeos;

import android.content.Context;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by TinMa on 11/30/2017.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemViewHolder>{
    private Context adapterContext;
    public ItemListAdapter(Context context){
        this.adapterContext = context;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater inflater = LayoutInflater.from(adapterContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position){

    }

    @Override
    public int getItemCount(){
        return 0;
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
