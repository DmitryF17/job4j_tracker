package ru.job4j_tracker;

import java.util.function.Consumer;

public class CreateAction implements UserAction {

    @Override
    public String name() {
        return "Create a new Item";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        output.accept("Enter name: ");
        String name = input.askStr(" ");
        Item item = new Item(name);
        store.add(item);
        return true;
    }
}