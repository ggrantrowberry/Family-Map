package com.example.grantrowberry.fmclient.Models.Model;

/**
 * Created by GrantRowberry on 4/12/17.
 */

public class FilterItem {
    String filterTitle;
    Boolean isON = true;
    String filterType;

    public String getFilterTitle() {
        return filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public Boolean getON() {
        return isON;
    }

    public void setON(Boolean ON) {
        isON = ON;
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }
}
