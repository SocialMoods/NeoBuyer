package ru.SocialMoods.model;

import cn.nukkit.item.Item;

public class ShopItem {

    private int itemId;
    private int count;
    private int price;

    public ShopItem(int itemId, int count, int price) {
        this.itemId = itemId;
        this.count = count;
        this.price = price;
    }

    public Item getItem() {
        return Item.get(itemId, 0, count);
    }

    public int getPrice() {
        return price;
    }

    public int getCount() {
        return count;
    }

    public String getItemName() {
        return Item.get(this.itemId).getName();
    }
}