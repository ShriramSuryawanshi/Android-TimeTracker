package shree.e.timetracker;



import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter1 extends RecyclerView
        .Adapter<MyRecyclerViewAdapter1
        .DataObjectHolder>  {

    private ArrayList<DataObject1> mDataset;
    private static MyClickListener myClickListener;

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView task;
        TextView time;
        TextView duration;
        TextView note;

        public DataObjectHolder(View itemView) {
            super(itemView);
            task = (TextView) itemView.findViewById(R.id.textView);
            time = (TextView) itemView.findViewById(R.id.textView2);
            duration = (TextView) itemView.findViewById(R.id.textViewDuration);
            note = (TextView) itemView.findViewById(R.id.textViewNote);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public MyRecyclerViewAdapter1(ArrayList<DataObject1> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_row1, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.task.setText(mDataset.get(position).getmText1());
        holder.time.setText(mDataset.get(position).getmText2());
        holder.duration.setText(mDataset.get(position).getmText3());
        holder.note.setText(mDataset.get(position).getmText4());
    }

    public void addItem(DataObject1 dataObj, int index) {
        mDataset.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}