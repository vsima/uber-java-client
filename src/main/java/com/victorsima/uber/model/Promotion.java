package com.victorsima.uber.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Promotion model obj
 */
public class Promotion {

    @Expose
    @SerializedName("display_text")
    private String displayText;
    @Expose
    @SerializedName("localized_value")
    private String localizedValue;
    @Expose
    @SerializedName("type")
    private String type;

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public String getLocalizedValue() {
        return localizedValue;
    }

    public void setLocalizedValue(String localizedValue) {
        this.localizedValue = localizedValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
