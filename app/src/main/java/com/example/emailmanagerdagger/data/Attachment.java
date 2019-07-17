package com.example.emailmanagerdagger.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Attachment implements Parcelable {
    private String fileName;
    private String path;
    private String size;
    private long total;
    private boolean isDownload;
    private boolean enable;

    protected Attachment(Parcel in) {
        fileName = in.readString();
        path = in.readString();
        size = in.readString();
        total = in.readLong();
        isDownload = in.readByte() != 0;
        enable = in.readByte() != 0;
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
}
