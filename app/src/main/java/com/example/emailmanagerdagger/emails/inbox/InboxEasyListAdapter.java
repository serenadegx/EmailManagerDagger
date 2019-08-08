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
import com.example.emailmanagerdagger.data.Email;
import com.example.emailmanagerdagger.emails.FooterViewHolder;
import com.example.emailmanagerdagger.utils.EasyListAdapter;
import com.example.emailmanagerdagger.utils.LoadMoreViewHolder;

import java.util.List;

public class InboxEasyListAdapter extends EasyListAdapter<Email> {
    public InboxEasyListAdapter(List<Email> data, RecyclerView recyclerView, LoadMoreListener loadMoreListener) {
        super(data, recyclerView, loadMoreListener);
    }

    @Override
    protected LoadMoreViewHolder getLoadMoreViewHolder(ViewGroup viewGroup) {
        return new FooterViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_footer,viewGroup,false));
    }

    @Override
    protected RecyclerView.ViewHolder getNormalViewHolder(ViewGroup viewGroup) {
        return new WrapperViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_email,viewGroup,false));
    }

    @Override
    protected void onNormalBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        WrapperViewHolder holder = (WrapperViewHolder) viewHolder;
        final Email email = mData.get(position);
        holder.v.setVisibility(email.isRead() ? View.GONE : View.VISIBLE);
        holder.iv.setVisibility(email.isHasAttach() ? View.VISIBLE : View.INVISIBLE);
        holder.from.setText(TextUtils.isEmpty(email.getPersonal()) ? email.getFrom() : email.getPersonal());
        holder.subject.setText(email.getSubject());
        holder.date.setText(email.getDate());
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
