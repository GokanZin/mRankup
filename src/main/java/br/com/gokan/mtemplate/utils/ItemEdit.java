package br.com.gokan.mtemplate.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ItemEdit {

    private ItemStack item;
    private List<String> lore;
    private String display;
    private Map<String, String> placeholders;

    public ItemEdit(ItemStack item) {
        this.item = item.clone(); 
        ItemMeta meta = this.item.getItemMeta();

        if (meta != null && meta.hasLore()) {
            this.lore = new ArrayList<>(meta.getLore());
        } else {
            this.lore = new ArrayList<>();
        }

        if (meta != null && meta.hasDisplayName()) {
            this.display = meta.getDisplayName();
        } else {
            this.display = "";
        }

        this.placeholders = new HashMap<>();
    }

    public static ItemEdit of(ItemStack item) {
        return new ItemEdit(item);
    }

    public ItemEdit editLore(List<String> lore) {
        this.lore = new ArrayList<>(lore);
        return this;
    }

    public ItemEdit clearLines() {
        this.lore.clear();
        return this;
    }

    public ItemEdit addLine(String line) {
        this.lore.add(line);
        return this;
    }

    public ItemEdit setLine(int index, String line) {
        if (index >= 0 && index < this.lore.size()) {
            this.lore.set(index, line);
        }
        return this;
    }

    public ItemEdit addPlaceholder(String key, String value) {
        this.placeholders.put(key, value);
        return this;
    }

    public ItemEdit editDisplay(String display) {
        this.display = display;
        return this;
    }

    public ItemStack build() {
        ItemStack item = this.item.clone();
        ItemMeta meta = item.getItemMeta();

        // Substituir placeholders na lore
        List<String> modifiedLore = this.lore.stream()
                .map(line -> replacePlaceholder(line.replace("&", "§"), this.placeholders))
                .collect(Collectors.toList());

        // Aplicar a lore modificada
        if (meta != null) {
            meta.setLore(modifiedLore);
            meta.setDisplayName(replacePlaceholder(this.display.replace("&", "§"), this.placeholders));
            item.setItemMeta(meta);
        }

        return item;
    }


    private String replacePlaceholder(String text, Map<String, String> placeholders) {
        return placeholders.entrySet().stream()
                .reduce(text, (t, e) -> t.replace("{" + e.getKey() + "}", e.getValue()), (t1, t2) -> t1);
    }
}
