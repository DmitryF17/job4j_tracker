package ru.job4j_tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MemTracker {
    private final List<Item> items = new ArrayList<>();
    private int position = 0;

    public Item add(Item item) {
        items.add(item);
        return item;
    }

    public List<Item> findAll() {
        return items;
    }

    public List<Item> findByName(String key) {
        List<Item> nameskey = new ArrayList<>();
        for (Item item : items) {
            if (item.getName().equals(key)) {
                nameskey.add(item);
            }
        }
        return nameskey;
    }

    public Item findById(int id) {
        int i = indexOf(id);
        if (i != -1) {
            return items.get(i);
        }
        return null;
    }

    public boolean replace(int id, Item item) {
        boolean res = false;
        int pos = indexOf(id);
        if (pos != -1) {
            res = true;
            item.setId(items.get(pos).getId());
            items.set(pos, item);
            item.getName();
        }
        return res;
    }

    public boolean delete(int id) {
        boolean res = false;
        int pos = indexOf(id);
        if (pos != -1) {
            res = true;
            items.remove(pos);
        }
        return res;
    }

    private int indexOf(int id) {
        int rsl = -1;
        for (Item item : items) {
            int ind = Integer.parseInt(item.getId());
            if (ind > 0) {
                rsl++;
                break;
            }
        }
        return rsl;
    }
}

			


