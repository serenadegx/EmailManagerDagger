package com.example.emailmanagerdagger.emails.inbox;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.account.adapter.EmailCategoryListAdapter;
import com.example.emailmanagerdagger.data.Configuration;
import com.example.emailmanagerdagger.data.Email;

import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class InboxListAdapter extends RecyclerView.Adapter<InboxListAdapter.WrapperViewHolder> {

    private final ItemListener mItemListener;
    private List<Email> mData;

    public InboxListAdapter(ItemListener mItemListener, List<Email> mData) {
        this.mItemListener = mItemListener;
        this.mData = mData;
    }

    @NonNull
    @Override
    public WrapperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WrapperViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_email, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WrapperViewHolder holder, int position) {
        Email email = mData.get(position);
        holder.v.setVisibility(email.isRead() ? View.GONE : View.VISIBLE);
        holder.iv.setVisibility(email.isHasAttach() ? View.VISIBLE : View.INVISIBLE);
        holder.from.setText(TextUtils.isEmpty(email.getPersonal()) ? email.getFrom() : email.getPersonal());
        holder.subject.setText(email.getSubject());
        holder.date.setText(email.getDate());
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

        private final View v;
        private final TextView from;
        private final TextView subject;
        private final TextView date;
        private final ImageView iv;

        public WrapperViewHolder(@NonNull View itemView) {
            super(itemView);
            v = itemView.findViewById(R.id.v);
            from = itemView.findViewById(R.id.tv_from);
            iv = itemView.findViewById(R.id.iv);
            subject = itemView.findViewById(R.id.tv_subject);
            date = itemView.findViewById(R.id.tv_date);
        }
    }
}
