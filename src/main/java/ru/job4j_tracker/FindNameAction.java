package ru.job4j_tracker;

import java.util.List;
import java.util.function.Consumer;

public class FindNameAction implements UserAction {

    @Override
    public String name() {
        return "Find Items by Name";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        String name = input.askStr("Enter name: ");
        List<Item> item = store.findByName(name);
        for (int i = 0; i < item.size(); i++) {
            output.accept(String.format("%s %s",
                    item.get(i).getId(),
                    item.get(i).getName()));
        }
        return true;
    }
}