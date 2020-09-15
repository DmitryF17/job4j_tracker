package ru.job4j_tracker;

import java.util.function.Consumer;

public class StubAction implements UserAction {

    private boolean call = false;

    @Override
    public String name() {
        return "Stub Action";
    }

    @Override
    public boolean execute(Input input, Store store, Consumer<String> output) {
        call = true;
        return false;
    }

    public boolean isCall() {
        return call;
    }
}