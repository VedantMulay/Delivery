/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuPage {

    private String id;
    private String title;
    private int rows;
    private List<MenuItem> items = new ArrayList<>();

    public MenuPage(String title, int rows) {
        this.title = title;
        this.rows = rows;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void addItem(MenuItem item) {
        this.items.add(item);
    }

    public String getTitle() {
        return title;
    }

    public int getRows() {
        return rows;
    }
}
