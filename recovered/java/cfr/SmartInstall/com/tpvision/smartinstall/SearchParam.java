/*
 * Decompiled with CFR 0.152.
 */
package com.tpvision.smartinstall;

public class SearchParam {
    private String filterFields;
    private int currentPage;
    private int rowCount;
    private String searchPhrase;
    private String sort;

    public String getFilterFields() {
        return this.filterFields;
    }

    public void setFilterFields(String filterFields) {
        this.filterFields = filterFields;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getRowCount() {
        return this.rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public String getSearchPhrase() {
        return this.searchPhrase;
    }

    public void setSearchPhrase(String searchPhrase) {
        this.searchPhrase = searchPhrase;
    }

    public String getSort() {
        return this.sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}

