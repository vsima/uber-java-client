package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Uber User Activity model obj
 */
public class UserActivity {

    @Expose
    @SerializedName("offset")
    private int offset;
    @Expose
    @SerializedName("limit")
    private int limit;
    @Expose
    @SerializedName("count")
    private int count;
    @Expose
    @SerializedName("history")
    private List<UserHistory> history;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<UserHistory> getHistory() {
        return history;
    }

    public void setHistory(List<UserHistory> history) {
        this.history = history;
    }
}
