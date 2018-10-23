package com.ivanmorett.simpletodo.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;


import com.ivanmorett.simpletodo.R;
import com.ivanmorett.simpletodo.activities.MainActivity;
import com.ivanmorett.simpletodo.constants.DateConstants;
import com.ivanmorett.simpletodo.database.AppDatabase;
import com.ivanmorett.simpletodo.fragments.EditItemFragment;
import com.ivanmorett.simpletodo.interfaces.OnCloseDialog;
import com.ivanmorett.simpletodo.database.Item;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<Item> mItems;
    private Context context;

    public ItemAdapter(List<Item> mItems, Context context){
        this.mItems = mItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View postView = inflater.inflate(R.layout.todo_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(postView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item item = mItems.get(position);

        String todoItem = item.getText();
        Date dueDate = item.getDueDate();
        String dueDateText = DateConstants.SIMPLE_DATE_FORMAT.format(dueDate);

        holder.tvDueDate.setText(dueDateText);
        holder.tvItem.setText(todoItem);

        holder.cbStatus.setChecked(item.getState());

        if(item.getState()){
            holder.tvItem.setPaintFlags(holder.tvItem.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            holder.tvItem.setPaintFlags(holder.tvItem.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
        }

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(dueDate);
        cal.add(Calendar.DATE, -2);
        Date dueDateMinus2 = cal.getTime();
        cal.add(Calendar.DATE, -3);
        Date dueDateMinus5 = cal.getTime();

        if(date.compareTo(dueDateMinus2)>0){
            holder.rlItem.setBackgroundColor(Color.RED);
        } else if(date.compareTo(dueDateMinus5)>0){
            holder.rlItem.setBackgroundColor(Color.YELLOW);
        }
        else{
            holder.rlItem.setBackgroundColor(Color.WHITE);
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tvDueDate) public TextView tvDueDate;
        @BindView(R.id.tvItem) public TextView tvItem;
        @BindView(R.id.cbStatus) public CheckBox cbStatus;
        @BindView(R.id.rlItem) public RelativeLayout rlItem;
        @BindView(R.id.rlBackground) public RelativeLayout rlBackground;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.rlItem)
        public void openEdit(){
            FragmentManager fm = ((AppCompatActivity)context).getSupportFragmentManager();

            EditItemFragment editNameDialogFragment = EditItemFragment.newInstance(mItems.get(getAdapterPosition()), new OnCloseDialog() {
                @Override
                public void beforeClose(Item item) {
                    notifyItemChanged(getAdapterPosition());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Item item = mItems.get(getAdapterPosition());
                            AppDatabase.getDatabase(context).itemDao().update(item);
                        }
                    }).start();
                }
            });

            editNameDialogFragment.show(fm, "fragment");
        }

        @OnClick(R.id.cbStatus)
        public void changeState(){
            Item item = mItems.get(getAdapterPosition());
            item.changeState();
            notifyItemChanged(getAdapterPosition());
        }

    }


}
