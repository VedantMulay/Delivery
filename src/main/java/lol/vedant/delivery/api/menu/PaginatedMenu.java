package lol.vedant.delivery.menu;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public abstract class PaginatedMenu extends Menu {

    protected List<Inventory> pages;
    protected int currentPage;

    public PaginatedMenu(String title, int size) {
        super(title, size);
        this.pages = new ArrayList<>();
        this.currentPage = 0;
        createNewPage();
    }

    protected void createNewPage() {
        Inventory page = Bukkit.createInventory(this, size, title + " - Page " + (pages.size() + 1));
        pages.add(page);
    }

    public void openPage(int pageNumber) {
        if (pageNumber < 0 || pageNumber >= pages.size()) return;
        currentPage = pageNumber;
        // Open the page for the player, implementation of this method should handle this
    }

    @Override
    public Inventory getInventory() {
        return pages.get(currentPage);
    }

    @Override
    public abstract void handleMenuClick(InventoryClickEvent event);
}