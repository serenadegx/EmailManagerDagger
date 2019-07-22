package com.example.emailmanagerdagger.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class Configuration implements Parcelable {
    @Id
    private long categoryId;
    private String name;

    private String receiveProtocol;
    private String receiveHostKey;
    private String receiveHostValue;
    private String receivePortKey;
    private String receivePortValue;
    private String receiveEncryptKey;
    private boolean receiveEncryptValue;

    private String sendProtocol;
    private String sendHostKey;
    private String sendHostValue;
    private String sendPortKey;
    private String sendPortValue;
    private String sendEncryptKey;
    private boolean sendEncryptValue;
    private String authKey;
    private boolean authValue;

    @Generated(hash = 121756130)
    public Configuration(long categoryId, String name, String receiveProtocol,
            String receiveHostKey, String receiveHostValue, String receivePortKey,
            String receivePortValue, String receiveEncryptKey,
            boolean receiveEncryptValue, String sendProtocol, String sendHostKey,
            String sendHostValue, String sendPortKey, String sendPortValue,
            String sendEncryptKey, boolean sendEncryptValue, String authKey,
            boolean authValue) {
        this.categoryId = categoryId;
        this.name = name;
        this.receiveProtocol = receiveProtocol;
        this.receiveHostKey = receiveHostKey;
        this.receiveHostValue = receiveHostValue;
        this.receivePortKey = receivePortKey;
        this.receivePortValue = receivePortValue;
        this.receiveEncryptKey = receiveEncryptKey;
        this.receiveEncryptValue = receiveEncryptValue;
        this.sendProtocol = sendProtocol;
        this.sendHostKey = sendHostKey;
        this.sendHostValue = sendHostValue;
        this.sendPortKey = sendPortKey;
        this.sendPortValue = sendPortValue;
        this.sendEncryptKey = sendEncryptKey;
        this.sendEncryptValue = sendEncryptValue;
        this.authKey = authKey;
        this.authValue = authValue;
    }

    @Generated(hash = 365253574)
    public Configuration() {
    }

    protected Configuration(Parcel in) {
        categoryId = in.readLong();
        name = in.readString();
        receiveProtocol = in.readString();
        receiveHostKey = in.readString();
        receiveHostValue = in.readString();
        receivePortKey = in.readString();
        receivePortValue = in.readString();
        receiveEncryptKey = in.readString();
        receiveEncryptValue = in.readByte() != 0;
        sendProtocol = in.readString();
        sendHostKey = in.readString();
        sendHostValue = in.readString();
        sendPortKey = in.readString();
        sendPortValue = in.readString();
        sendEncryptKey = in.readString();
        sendEncryptValue = in.readByte() != 0;
        authKey = in.readString();
        authValue = in.readByte() != 0;
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel in) {
            return new Configuration(in);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReceiveProtocol(String receiveProtocol) {
        this.receiveProtocol = receiveProtocol;
    }

    public void setReceiveHostKey(String receiveHostKey) {
        this.receiveHostKey = receiveHostKey;
    }

    public void setReceiveHostValue(String receiveHostValue) {
        this.receiveHostValue = receiveHostValue;
    }

    public void setReceivePortKey(String receivePortKey) {
        this.receivePortKey = receivePortKey;
    }

    public void setReceivePortValue(String receivePortValue) {
        this.receivePortValue = receivePortValue;
    }

    public void setReceiveEncryptKey(String receiveEncryptKey) {
        this.receiveEncryptKey = receiveEncryptKey;
    }

    public void setReceiveEncryptValue(boolean receiveEncryptValue) {
        this.receiveEncryptValue = receiveEncryptValue;
    }

    public void setSendProtocol(String sendProtocol) {
        this.sendProtocol = sendProtocol;
    }

    public void setSendHostKey(String sendHostKey) {
        this.sendHostKey = sendHostKey;
    }

    public void setSendHostValue(String sendHostValue) {
        this.sendHostValue = sendHostValue;
    }

    public void setSendPortKey(String sendPortKey) {
        this.sendPortKey = sendPortKey;
    }

    public void setSendPortValue(String sendPortValue) {
        this.sendPortValue = sendPortValue;
    }

    public void setSendEncryptKey(String sendEncryptKey) {
        this.sendEncryptKey = sendEncryptKey;
    }

    public void setSendEncryptValue(boolean sendEncryptValue) {
        this.sendEncryptValue = sendEncryptValue;
    }

    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }

    public void setAuthValue(boolean authValue) {
        this.authValue = authValue;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getReceiveProtocol() {
        return receiveProtocol;
    }

    public String getReceiveHostKey() {
        return receiveHostKey;
    }

    public String getReceiveHostValue() {
        return receiveHostValue;
    }

    public String getReceivePortKey() {
        return receivePortKey;
    }

    public String getReceivePortValue() {
        return receivePortValue;
    }

    public String getReceiveEncryptKey() {
        return receiveEncryptKey;
    }

    public boolean isReceiveEncryptValue() {
        return receiveEncryptValue;
    }

    public String getSendProtocol() {
        return sendProtocol;
    }

    public String getSendHostKey() {
        return sendHostKey;
    }

    public String getSendHostValue() {
        return sendHostValue;
    }

    public String getSendPortKey() {
        return sendPortKey;
    }

    public String getSendPortValue() {
        return sendPortValue;
    }

    public String getSendEncryptKey() {
        return sendEncryptKey;
    }

    public boolean isSendEncryptValue() {
        return sendEncryptValue;
    }

    public String getAuthKey() {
        return authKey;
    }

    public boolean isAuthValue() {
        return authValue;
    }

    public boolean getReceiveEncryptValue() {
        return this.receiveEncryptValue;
    }

    public boolean getSendEncryptValue() {
        return this.sendEncryptValue;
    }

    public boolean getAuthValue() {
        return this.authValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(categoryId);
        dest.writeString(name);
        dest.writeString(receiveProtocol);
        dest.writeString(receiveHostKey);
        dest.writeString(receiveHostValue);
        dest.writeString(receivePortKey);
        dest.writeString(receivePortValue);
        dest.writeString(receiveEncryptKey);
        dest.writeByte((byte) (receiveEncryptValue ? 1 : 0));
        dest.writeString(sendProtocol);
        dest.writeString(sendHostKey);
        dest.writeString(sendHostValue);
        dest.writeString(sendPortKey);
        dest.writeString(sendPortValue);
        dest.writeString(sendEncryptKey);
        dest.writeByte((byte) (sendEncryptValue ? 1 : 0));
        dest.writeString(authKey);
        dest.writeByte((byte) (authValue ? 1 : 0));
    }
}
