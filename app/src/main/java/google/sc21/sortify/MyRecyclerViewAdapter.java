/*
 * Sample code from Stack Overflow:
 *  https://stackoverflow.com/a/40584425
 */

package google.sc21.sortify;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private List<Junk> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    //Data is Passed into the Constructor
    MyRecyclerViewAdapter(Context context, List<Junk> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    //Inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_row, parent, false);
        return new ViewHolder(view);
    }

    //Binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String junkName = mData.get(position).returnName();
        List<String> junkAlias = mData.get(position).returnAliases();
        String junkInstruction = mData.get(position).returnInfo();
        holder.nameField.setText(junkName);
        String aliasTemp = junkAlias.toString().substring(1);
        aliasTemp = aliasTemp.substring(0, aliasTemp.length()-1);
        holder.aliasField.setText(aliasTemp);
        if (junkInstruction != null) {
            holder.descField.setText("Please dispose of me in the " + junkInstruction + " bin");
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    //Stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameField;
        TextView aliasField;
        TextView descField;
        ViewHolder(View itemView) {
            super(itemView);
            nameField = itemView.findViewById(R.id.junkName);
            aliasField = itemView.findViewById(R.id.junkAlias);
            descField = itemView.findViewById(R.id.junkInstruction);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mData.get(id).returnName();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}