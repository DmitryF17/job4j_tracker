package ru.job4j_tracker;

import java.util.List;
import java.util.function.Consumer;

public class FindAllAction implements UserAction {
    @Override
    public String name() {
        return "Show All Item";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        List<Item> item = store.findAll();
        for (int i = 0; i < item.size(); i++) {
            output.accept(String.format("%s %s",
                    item.get(i).getId(),
                    item.get(i).getName()));
        }
        return true;
    }
}