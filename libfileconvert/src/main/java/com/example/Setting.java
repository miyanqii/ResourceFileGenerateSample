
package com.example;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Setting {
    @SerializedName("key")
    @Expose
    private String key;
    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("tools-title")
    @Expose
    private String toolsTitle;
    @SerializedName("tools-description")
    @Expose
    private String toolsDescription;
    @SerializedName("tools-default")
    @Expose
    private String toolsDefault;
    @SerializedName("tools-category")
    @Expose
    private String toolsCategory;

    @Override
    public String toString() {
        return "Setting{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
