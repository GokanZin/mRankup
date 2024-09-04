package br.com.gokan.utils;

import net.minecraft.util.com.mojang.authlib.GameProfile;
import net.minecraft.util.com.mojang.authlib.properties.Property;
import net.minecraft.util.org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


    public class ItemBuilder {
        private ItemStack itemStack;
        private ItemMeta itemMeta;

        public ItemBuilder(Material material) {
            this.itemStack = new ItemStack(material);
            this.itemMeta = itemStack.getItemMeta();
        }

        public ItemBuilder(String idData) {
            if (idData.contains(":")) {
                String[] parts = idData.split(":");
                int id = Integer.parseInt(parts[0]);
                short data = parts.length > 1 ? Short.parseShort(parts[1]) : 0;
                Material material = Material.getMaterial(id);
                if (material == null) {
                    throw new IllegalArgumentException("Invalid item ID: " + id);
                }
                this.itemStack = new ItemStack(material, 1, data);
            } else {
                Material material = Material.matchMaterial(idData);
                if (material == null) {
                    throw new IllegalArgumentException("Invalid item ID or name: " + idData);
                }
                this.itemStack = new ItemStack(material);
            }
            this.itemMeta = itemStack.getItemMeta();
        }

        public ItemBuilder setType(Material material) {
            itemStack.setType(material);
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
            itemMeta.setDisplayName(displayName.replace("&", "§"));
            return this;
        }

        public ItemBuilder setLore(List<String> lore) {
            if (lore == null || lore.isEmpty()) return this;
            itemMeta.setLore(lore);
            return this;
        }

        public ItemBuilder addLine(String line) {
            List<String> lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
            lore.add(line);
            itemMeta.setLore(lore);
            return this;
        }

        public ItemBuilder setLine(int index, String line) {
            if (!itemMeta.hasLore()) {
                itemMeta.setLore(new ArrayList<>());
            }
            List<String> lore = itemMeta.getLore();
            if (index >= lore.size()) {
                for (int i = lore.size(); i <= index; i++) {
                    lore.add(""); // Add empty lines if index is beyond current list size
                }
            }
            lore.set(index, line);
            itemMeta.setLore(lore);
            return this;
        }

        public ItemBuilder setSkullOwner(String owner) {
            if (owner == null || owner.isEmpty()) return this;
            if (itemStack.getType() == Material.SKULL_ITEM) {
                SkullMeta skullMeta = (SkullMeta) itemMeta;
                skullMeta.setOwner(owner);
                itemMeta = skullMeta;
            }
            return this;
        }

        public ItemBuilder setSkullTexture(String texture) {
            if (texture == null || texture.isEmpty()) return this;
            if (itemStack.getType() == Material.SKULL_ITEM) {
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
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

        public ItemStack getSkull(String texture) {
            if (texture == null || texture.isEmpty()) return null;
            ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
            ItemMeta skullMeta = head.getItemMeta();
            if (texture == null || texture.isEmpty()) {
                return head;
            }
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", texture).getBytes());
            profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            head.setItemMeta(skullMeta);
            return head;
        }

        public ItemStack build() {
            itemStack.setItemMeta(itemMeta);
            return itemStack;
        }
    }


