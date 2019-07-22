package com.example.emailmanagerdagger.account.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Configuration;

import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;


public class EmailCategoryListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final ItemListener mItemListener;
    private List<Configuration> mData;

    public EmailCategoryListAdapter(List<Configuration> data,ItemListener itemListener) {
        this.mData = data;
        this.mItemListener = itemListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email_category, parent, false);
        return new WrapperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        WrapperViewHolder viewHolder = (WrapperViewHolder) holder;
        final Configuration config = mData.get(position);
        viewHolder.tv.setText(config.getName());
        viewHolder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onCategoryClick(config);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<Configuration> data) {
        mData = checkNotNull(data);
        notifyDataSetChanged();
    }

    public interface ItemListener {
        void onCategoryClick(Configuration config);
    }

    class WrapperViewHolder extends RecyclerView.ViewHolder {

        TextView tv;

        public WrapperViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }
}
