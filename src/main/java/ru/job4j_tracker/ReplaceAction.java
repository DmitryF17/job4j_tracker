package ru.job4j_tracker;

import java.util.function.Consumer;

public class ReplaceAction implements UserAction {
    @Override
    public String name() {
        return "Replace Item";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        String id = input.askStr("Enter Id: ");
        String name = input.askStr("Enter Name: ");
        Item item = new Item(name);
        if (store.replace(id, item)) {
            output.accept("Item was replaced");
        } else {
            output.accept("Item was not detected");
        }
        return true;
    }
}