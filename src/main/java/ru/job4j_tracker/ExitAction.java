package ru.job4j_tracker;

import java.util.function.Consumer;

public class ExitAction implements UserAction {
    @Override
    public String name() {
        return "Exit program";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        try {
            store.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}