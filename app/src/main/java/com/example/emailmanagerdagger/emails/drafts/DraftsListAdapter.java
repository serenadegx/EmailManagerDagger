package com.example.emailmanagerdagger.emails.drafts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Email;

import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class DraftsListAdapter extends RecyclerView.Adapter<DraftsListAdapter.WrapperViewHolder> {

    private final ItemListener mItemListener;
    private List<Email> mData;

    public DraftsListAdapter(ItemListener mItemListener, List<Email> mData) {
        this.mItemListener = mItemListener;
        this.mData = mData;
    }

    @NonNull
    @Override
    public WrapperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WrapperViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_drafts, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WrapperViewHolder holder, int position) {
        final Email email = mData.get(position);
        holder.iv.setVisibility(email.isHasAttach() ? View.VISIBLE : View.INVISIBLE);
        holder.from.setText(email.getTo());
        holder.subject.setText(email.getSubject());
        holder.date.setText(email.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onEmailItemClick(email);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<Email> data) {
        mData = checkNotNull(data);
        notifyDataSetChanged();
    }


    interface ItemListener {
        void onEmailItemClick(Email data);
    }

    class WrapperViewHolder extends RecyclerView.ViewHolder {

        private final TextView from;
        private final TextView subject;
        private final TextView date;
        private final ImageView iv;

        public WrapperViewHolder(@NonNull View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.tv_from);
            iv = itemView.findViewById(R.id.iv);
            subject = itemView.findViewById(R.id.tv_subject);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
}
