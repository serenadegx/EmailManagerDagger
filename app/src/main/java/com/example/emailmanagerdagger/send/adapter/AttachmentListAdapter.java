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
import com.google.android.material.snackbar.Snackbar;

import java.text.DecimalFormat;
import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

public class AttachmentListAdapter extends RecyclerView.Adapter<AttachmentListAdapter.WrapperViewHolder> {

    private final ItemListener mItemListener;
    private List<Attachment> mData;
    private View inflate;

    public AttachmentListAdapter(ItemListener mItemListener, List<Attachment> mData) {
        this.mItemListener = mItemListener;
        this.mData = mData;
    }

    @NonNull
    @Override
    public WrapperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_attachment, parent, false);
        return new WrapperViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull WrapperViewHolder holder, final int position) {
        final Attachment attachment = mData.get(position);
        holder.filename.setText(attachment.getFileName());
        holder.size.setText(attachment.getSize());
        holder.bt.setText(attachment.isDownload() ? "删除" : "下载");
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

    public void downloadStart(int index) {
        mData.get(index).setEnable(false);
        notifyItemChanged(index);
    }

    public void updateProgress(int index, float percent) {
        DecimalFormat df = new DecimalFormat("0.00");
        Attachment attachment = mData.get(index);
        String size = attachment.getSize();
        String substring = size.substring(size.indexOf("/") == -1 ? 0 : size.indexOf("/") + 1);
        attachment.setSize(df.format(percent * 100) + "%/" + substring);
        notifyItemChanged(index);
    }

    public void downloadFinish(int index) {
        String size = mData.get(index).getSize();
        mData.get(index).setDownload(true);
        mData.get(index).setEnable(true);
        mData.get(index).setSize(size.substring(size.indexOf("/") + 1));
        notifyItemChanged(index);
    }

    public void downloadError(int index) {
        mData.get(index).setEnable(true);
        notifyItemChanged(index);
        Snackbar.make(inflate, mData.get(index).getFileName() + "下载失败", Snackbar.LENGTH_SHORT).show();
    }

    public void delete(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }


    public interface ItemListener {
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
