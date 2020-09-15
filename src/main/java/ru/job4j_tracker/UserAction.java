package ru.job4j_tracker;

import java.util.function.Consumer;

public interface UserAction {
    String name();

    boolean execute(Input input, Store store, Consumer<String> output);
}