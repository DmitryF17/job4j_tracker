package ru.job4j_tracker;

public interface Input {
    String askStr(String question);

    int askInt(String question);

    int askInt(String question, int max);
}