package com.example.emailmanagerdagger.settings.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Account;
import com.example.emailmanagerdagger.data.Email;

import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class AccountListAdapter extends RecyclerView.Adapter<AccountListAdapter.WrapperViewHolder> {

    private final ItemListener mItemListener;
    private List<Account> mData;

    public AccountListAdapter(ItemListener mItemListener, List<Account> mData) {
        this.mItemListener = mItemListener;
        this.mData = mData;
    }

    @NonNull
    @Override
    public WrapperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WrapperViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WrapperViewHolder holder, int position) {
        final Account account = mData.get(position);
        holder.tv.setText(account.getAccount());
        holder.iv.setVisibility(account.isCur() ? View.VISIBLE : View.INVISIBLE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onEmailItemClick(account);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<Account> data) {
        mData = checkNotNull(data);
        notifyDataSetChanged();
    }


    public interface ItemListener {
        void onEmailItemClick(Account account);
    }

    class WrapperViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;
        private final ImageView iv;

        public WrapperViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_from);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
