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

public class AccountListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int NORMAL_VIEW_TYPE = 1;
    private static final int FOOTER_VIEW_TYPE = 2;

    private final ItemListener mItemListener;
    private List<Account> mData;

    public AccountListAdapter(ItemListener mItemListener, List<Account> mData) {
        this.mItemListener = mItemListener;
        this.mData = mData;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == mData.size()) {
            return FOOTER_VIEW_TYPE;
        } else {
            return NORMAL_VIEW_TYPE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == NORMAL_VIEW_TYPE) {
            viewHolder = new WrapperViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_account, parent, false));
        } else {
            viewHolder = new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_account, parent, false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof WrapperViewHolder){
            WrapperViewHolder viewHolder = (WrapperViewHolder) holder;
            final Account account = mData.get(position);
            viewHolder.tv.setText(account.getAccount());
            viewHolder.iv.setVisibility(account.isCur() ? View.VISIBLE : View.INVISIBLE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onEmailItemClick(account);
                }
            });
        }else {
            FooterViewHolder viewHolder = (FooterViewHolder) holder;
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mItemListener.onAddAccountClick();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return mData.size() + 1;
    }

    public void setNewData(List<Account> data) {
        mData = checkNotNull(data);
        notifyDataSetChanged();
    }


    public interface ItemListener {
        void onEmailItemClick(Account account);

        void onAddAccountClick();
    }

    class WrapperViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;
        private final ImageView iv;

        public WrapperViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            iv = itemView.findViewById(R.id.iv);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv;
        private final ImageView iv;

        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_from);
            iv = itemView.findViewById(R.id.iv);
        }
    }
}
