package ru.job4j_tracker;

import java.util.function.Consumer;

public class DeleteAction implements UserAction {

    @Override
    public String name() {
        return "Delete Item";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        String id = input.askStr("Enter id: ");
        if (store.delete(id)) {
            output.accept("Item was deleted");
        } else {
            output.accept("Item was not detected");
        }
        return true;
    }
}
