package br.com.gokan.mtemplate.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

public class ItemBuilder {
    private ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemBuilder itemBuilder) {
        if (itemBuilder == null){
            this.itemStack = new ItemStack(Material.AIR);
            this.itemMeta = itemStack.getItemMeta();
        }
        this.itemStack = itemBuilder.itemStack;
        this.itemMeta = itemBuilder.itemMeta;
    }
    public ItemBuilder(String idData) {
        if (idData.contains(":")) {
            String[] parts = idData.split(":");
            int id = Integer.parseInt(parts[0]);
            short data = parts.length > 1 ? Short.parseShort(parts[1]) : 0;
            Material material = Material.getMaterial(id);
            if (material == null) {
                this.itemStack = new ItemStack(Material.AIR);
            }
            this.itemStack = new ItemStack(material, 1, data);
        } else {
            Material material = Material.matchMaterial(idData);
            if (material == null) {
                this.itemStack = new ItemStack(Material.AIR);
            }
            this.itemStack = new ItemStack(material);
        }
        this.itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder setType(Material material) {
        if (material != Material.AIR){
            itemStack.setType(material);
        }
        return this;
    }

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDamage(int damage) {
        itemStack.setDurability((short) damage);
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addEnchantments(Enchantment[] enchantments, int[] levels) {
        for (int i = 0; i < enchantments.length; i++) {
            itemStack.addEnchantment(enchantments[i], levels[i]);
        }
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        if (displayName == null || displayName.isEmpty()) return this;
        if (itemStack.getType() == Material.AIR) return this;
        itemMeta.setDisplayName(displayName.replace("&", "§"));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (lore == null || lore.isEmpty()) return this;
        if (itemStack.getType() == Material.AIR) return this;
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder addLine(String line) {
        if (itemStack.getType() == Material.AIR) return this;
        List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        lore.add(line);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setLine(int index, String line) {
        if (itemStack.getType() == Material.AIR) return this;
        if (!itemMeta.hasLore()) {
            itemMeta.setLore(new ArrayList<>());
        }
        List<String> lore = itemMeta.getLore();
        if (index >= lore.size()) {
            for (int i = lore.size(); i <= index; i++) {
                lore.add("");
            }
        }
        lore.set(index, line);
        itemMeta.setLore(lore);
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        if (itemStack.getType() == Material.AIR) return this;
        if (owner == null || owner.isEmpty()) return this;
        if (itemStack.getType() == Material.SKULL_ITEM) {
            SkullMeta skullMeta = (SkullMeta) itemMeta;
            skullMeta.setOwner(owner);
            itemMeta = skullMeta;
        }
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if (itemStack.getType() == Material.AIR) return this;
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }


    public ItemBuilder setSkullTexture(String texture) {
        if (texture == null || texture.isEmpty()) return this;
        if (itemStack.getType() == Material.SKULL_ITEM) {
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            try {
                Field profileField = itemMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(itemMeta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public ItemBuilder replace(String target, String replacement) {
        if (itemStack.getType() == Material.AIR) return this;
        if (itemMeta.hasDisplayName()) {
            String displayName = itemMeta.getDisplayName();
            itemMeta.setDisplayName(displayName.replace(target, replacement));
        }

        if (itemMeta.hasLore()) {
            List<String> lore = itemMeta.getLore();
            List<String> replacedLore = new ArrayList<>();
            for (String line : lore) {
                replacedLore.add(line.replace(target, replacement));
            }
            itemMeta.setLore(replacedLore);
        }

        return this;
    }



    public ItemStack build() {
        if (itemStack.getType() != Material.AIR) {
            itemStack.setItemMeta(itemMeta);
        }
        return itemStack;
    }



}
