package com.example.emailmanagerdagger.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

import java.util.ArrayList;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Email implements Parcelable, Comparable<Email> {
    @Id(autoincrement = true)
    private Long id;
    private int messageId;
    private int category;
    private boolean isRead;
    private String subject;
    private String date;
    private String from;
    private String personal;
    private String to;
    private String cc;
    private String bcc;
    private String content;
    @Transient
    private String append;
    private boolean hasAttach;
    @Transient
    public List<Attachment> attachments = new ArrayList<>();


    protected Email(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        messageId = in.readInt();
        category = in.readInt();
        isRead = in.readByte() != 0;
        subject = in.readString();
        date = in.readString();
        from = in.readString();
        personal = in.readString();
        to = in.readString();
        cc = in.readString();
        bcc = in.readString();
        content = in.readString();
        append = in.readString();
        hasAttach = in.readByte() != 0;
        attachments = in.createTypedArrayList(Attachment.CREATOR);
    }

    @Generated(hash = 1678077930)
    public Email(Long id, int messageId, int category, boolean isRead,
            String subject, String date, String from, String personal, String to,
            String cc, String bcc, String content, boolean hasAttach) {
        this.id = id;
        this.messageId = messageId;
        this.category = category;
        this.isRead = isRead;
        this.subject = subject;
        this.date = date;
        this.from = from;
        this.personal = personal;
        this.to = to;
        this.cc = cc;
        this.bcc = bcc;
        this.content = content;
        this.hasAttach = hasAttach;
    }

    @Generated(hash = 272676561)
    public Email() {
    }

    public static final Creator<Email> CREATOR = new Creator<Email>() {
        @Override
        public Email createFromParcel(Parcel in) {
            return new Email(in);
        }

        @Override
        public Email[] newArray(int size) {
            return new Email[size];
        }
    };

    public void setId(Long id) {
        this.id = id;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAppend(String append) {
        this.append = append;
    }

    public void setHasAttach(boolean hasAttach) {
        this.hasAttach = hasAttach;
    }

    public Long getId() {
        return id;
    }

    public int getMessageId() {
        return messageId;
    }

    public int getCategory() {
        return category;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public boolean isRead() {
        return isRead;
    }

    public String getSubject() {
        return subject;
    }

    public String getDate() {
        return date;
    }

    public String getFrom() {
        return from;
    }

    public String getPersonal() {
        return personal;
    }

    public String getTo() {
        return to;
    }

    public String getCc() {
        return cc;
    }

    public String getBcc() {
        return bcc;
    }

    public String getContent() {
        return content;
    }

    public String getAppend() {
        return append;
    }

    public boolean isHasAttach() {
        return hasAttach;
    }

    @Override
    public int compareTo(Email o) {
        return o.getId().compareTo(this.getId());
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeInt(messageId);
        dest.writeInt(category);
        dest.writeByte((byte) (isRead ? 1 : 0));
        dest.writeString(subject);
        dest.writeString(date);
        dest.writeString(from);
        dest.writeString(personal);
        dest.writeString(to);
        dest.writeString(cc);
        dest.writeString(bcc);
        dest.writeString(content);
        dest.writeString(append);
        dest.writeByte((byte) (hasAttach ? 1 : 0));
        dest.writeTypedList(attachments);
    }

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean getHasAttach() {
        return this.hasAttach;
    }
}
