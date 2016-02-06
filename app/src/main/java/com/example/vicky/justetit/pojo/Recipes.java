
package com.example.vicky.justetit.pojo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Recipes {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("version")
    @Expose
    private Float version;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("results")
    @Expose
    private List<Result> results = new ArrayList<>();

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The version
     */
    public Float getVersion() {
        return version;
    }

    /**
     * @param version The version
     */
    public void setVersion(Float version) {
        this.version = version;
    }

    /**
     * @return The href
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href The href
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @return The results
     */
    public List<Result> getResults() {
        return results;
    }

    /**
     * @param results The results
     */
    public void setResults(List<Result> results) {
        this.results = results;
    }

}
