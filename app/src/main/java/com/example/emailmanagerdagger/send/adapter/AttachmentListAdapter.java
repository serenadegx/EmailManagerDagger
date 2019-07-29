package com.example.emailmanagerdagger.send.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emailmanagerdagger.R;
import com.example.emailmanagerdagger.data.Attachment;
import com.example.emailmanagerdagger.data.Email;

import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class AttachmentListAdapter extends RecyclerView.Adapter<AttachmentListAdapter.WrapperViewHolder> {

    private final ItemListener mItemListener;
    private List<Attachment> mData;

    public AttachmentListAdapter(ItemListener mItemListener, List<Attachment> mData) {
        this.mItemListener = mItemListener;
        this.mData = mData;
    }

    @NonNull
    @Override
    public WrapperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WrapperViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_attachment, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WrapperViewHolder holder, final int position) {
        final Attachment attachment = mData.get(position);
        holder.filename.setText(attachment.getFileName());
        holder.size.setText(attachment.getSize());
        holder.bt.setText(attachment.isDownload() ? "打开" : "下载");
        holder.bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemListener.onEmailItemClick(position, attachment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setNewData(List<Attachment> data) {
        mData = checkNotNull(data);
        notifyDataSetChanged();
    }


    interface ItemListener {
        void onEmailItemClick(int position, Attachment data);
    }

    class WrapperViewHolder extends RecyclerView.ViewHolder {
        private final TextView filename;
        private final TextView size;
        private final Button bt;

        public WrapperViewHolder(@NonNull View itemView) {
            super(itemView);
            filename = itemView.findViewById(R.id.tv_filename);
            size = itemView.findViewById(R.id.tv_size);
            bt = itemView.findViewById(R.id.bt);
        }
    }
}
