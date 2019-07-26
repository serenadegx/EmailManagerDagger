package com.example.emailmanagerdagger.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Attachment implements Parcelable {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private long attachmentId;
    private String fileName;
    private String path;
    private String size;
    private long total;
    private boolean isDownload;
    @Transient
    private boolean enable = true;

    public Attachment(String fileName, String path, String size, long total) {
        this.fileName = fileName;
        this.path = path;
        this.size = size;
        this.total = total;
    }

    public Attachment(Parcel in) {
        fileName = in.readString();
        path = in.readString();
        size = in.readString();
        total = in.readLong();
        isDownload = in.readByte() != 0;
        enable = in.readByte() != 0;
    }

    @Generated(hash = 819056581)
    public Attachment(Long id, long attachmentId, String fileName, String path,
            String size, long total, boolean isDownload) {
        this.id = id;
        this.attachmentId = attachmentId;
        this.fileName = fileName;
        this.path = path;
        this.size = size;
        this.total = total;
        this.isDownload = isDownload;
    }

    @Generated(hash = 1924760169)
    public Attachment() {
    }

    public static final Creator<Attachment> CREATOR = new Creator<Attachment>() {
        @Override
        public Attachment createFromParcel(Parcel in) {
            return new Attachment(in);
        }

        @Override
        public Attachment[] newArray(int size) {
            return new Attachment[size];
        }
    };

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public void setDownload(boolean download) {
        isDownload = download;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setAttachmentId(long attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath() {
        return path;
    }

    public String getSize() {
        return size;
    }

    public long getTotal() {
        return total;
    }

    public boolean isDownload() {
        return isDownload;
    }

    public boolean isEnable() {
        return enable;
    }

    public long getAttachmentId() {
        return attachmentId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fileName);
        dest.writeString(path);
        dest.writeString(size);
        dest.writeLong(total);
        dest.writeByte((byte) (isDownload ? 1 : 0));
        dest.writeByte((byte) (enable ? 1 : 0));
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getIsDownload() {
        return this.isDownload;
    }

    public void setIsDownload(boolean isDownload) {
        this.isDownload = isDownload;
    }
}
