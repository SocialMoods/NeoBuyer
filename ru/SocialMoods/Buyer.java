package ru.SocialMoods;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import ru.SocialMoods.command.BuyerCommand;
import ru.SocialMoods.model.ShopItem;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Buyer extends PluginBase {

    private Map<Integer, ShopItem> shopItems = new HashMap<>();
    private Config config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config(new File(getDataFolder(), "config.yml"), Config.YAML);

        loadShopItems();

        this.getServer().getCommandMap().register("buyer", new BuyerCommand(this));
    }

    public Map<Integer, ShopItem> getShopItems() {
        return shopItems;
    }

    private void loadShopItems() {
        shopItems.clear();

        for (Object keyObj : config.getKeys(false)) {
            String key = String.valueOf(keyObj);

            int itemId = config.getInt(key + ".itemId");
            int count = config.getInt(key + ".count");
            int price = config.getInt(key + ".price");

            int slot;
            try {
                slot = Integer.parseInt(key);
            } catch (NumberFormatException e) {
                getLogger().warning("Ключ конфигурации не является числом: " + key);
                continue;
            }

            ShopItem shopItem = new ShopItem(itemId, count, price);
            shopItems.put(slot, shopItem);
        }
    }
}