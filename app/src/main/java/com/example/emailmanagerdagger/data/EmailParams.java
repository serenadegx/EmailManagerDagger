package com.example.emailmanagerdagger.data;

public class EmailParams {
    private int category;

    private int id;

    public void setCategory(int category) {
        this.category = category;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public static class Category {
        public static final int INBOX = 1;
        public static final int SENT = 2;
        public static final int DRAFTS = 3;
    }
}
