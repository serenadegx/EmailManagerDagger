package com.example.emailmanagerdagger.data;

import android.os.Parcel;
import android.os.Parcelable;

public class EmailParams implements Parcelable {
    private int category;

    private int id;

    private int function;

    private int index;

    public EmailParams() {
    }

    protected EmailParams(Parcel in) {
        category = in.readInt();
        id = in.readInt();
        function = in.readInt();
        index = in.readInt();
    }

    public static final Creator<EmailParams> CREATOR = new Creator<EmailParams>() {
        @Override
        public EmailParams createFromParcel(Parcel in) {
            return new EmailParams(in);
        }

        @Override
        public EmailParams[] newArray(int size) {
            return new EmailParams[size];
        }
    };

    public void setCategory(int category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFunction(int function) {
        this.function = function;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public int getFunction() {
        return function;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(category);
        dest.writeInt(id);
        dest.writeInt(function);
        dest.writeInt(index);
    }

    public static class Category {
        public static final int INBOX = 1;
        public static final int SENT = 2;
        public static final int DRAFTS = 3;
    }

    public static class Function {
        public static final int NORMAL_SEND = 1;
        public static final int REPLY = 2;
        public static final int FORWARD = 3;
    }
}
