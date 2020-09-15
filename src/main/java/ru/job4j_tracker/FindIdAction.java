package ru.job4j_tracker;

import java.util.function.Consumer;

public class FindIdAction implements UserAction {

    @Override
    public String name() {
        return "Find Item by ID";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        String id = input.askStr("Enter id: ");
        Item item = store.findById(id);
        if (item != null) {
            output.accept(String.format("%s %s", item.getId(), item.getName()));
        } else {
            output.accept("Item was not found");
        }
        return true;
    }
}