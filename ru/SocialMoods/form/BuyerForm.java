package ru.SocialMoods.form;

import cn.nukkit.Player;
import cn.nukkit.item.Item;
import com.formconstructor.form.SimpleForm;
import com.formconstructor.form.element.simple.Button;
import java.util.Iterator;
import java.util.Map.Entry;
import me.onebone.economyapi.EconomyAPI;
import ru.SocialMoods.Buyer;
import ru.SocialMoods.model.ShopItem;

public class BuyerForm {
    public static void openBuyerForm(Player player, Buyer plugin) {
        SimpleForm form = new SimpleForm("§l§aСкупщик");
        EconomyAPI economy = EconomyAPI.getInstance();
        Iterator var4 = plugin.getShopItems().entrySet().iterator();

        while(var4.hasNext()) {
            Entry<Integer, ShopItem> entry = (Entry)var4.next();
            ShopItem shopItem = (ShopItem)entry.getValue();
            String itemName = shopItem.getItemName();
            String buttonText = itemName + " (" + shopItem.getCount() + " шт.) за §a" + shopItem.getPrice() + " монет";
            Button button = new Button(buttonText);
            button.onClick((pl, b) -> {
                if (pl.getInventory().getItemInHand().getCount() < shopItem.getCount()) {
                    player.sendMessage("§fLost§aGolem§r§f | У вас недостаточно предметов для продажи!");
                } else {
                    Item itemInHand = pl.getInventory().getItemInHand();
                    if (itemInHand.getName().equals(shopItem.getItemName())) {
                        itemInHand.setCount(itemInHand.getCount() - shopItem.getCount());
                        pl.getInventory().setItemInHand(itemInHand);
                        economy.addMoney(player, shopItem.getPrice());
                        player.sendMessage("§fLost§aGolem§r§f | Вы успешно продали предметы!");
                    } else {
                        player.sendMessage("§fLost§aGolem§r§f | Вы взяли не тот предмет в руку!");
                    }
                }

            });
            form.addButton(button);
        }
        form.addContent("Возьмите в руку предмет, который хотите §aпродать!");
        form.setNoneHandler((pl) -> {});
        form.send(player);
    }
}