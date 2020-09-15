package ru.job4j_tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class StartUI {

    private static Consumer<String> output = System.out::println;

    public void init(Input input, Store store, UserAction[] actions, Consumer<String> output) {
        boolean run = true;
        while (run) {
            this.showMenu(actions, output);
            int select = input.askInt("Select: ", actions.length);
            UserAction action = actions[select];
            run = action.execute(input, store, output);
        }
    }

    private void showMenu(UserAction[] actions, Consumer<String> output) {
        output.accept("Menu.");
        int index = 0;
        for (UserAction user : actions) {
            output.accept(index++ + ". " + user.name());
        }
    }

    public void createrunup() {
        Input input = new ConsoleInput();
        Input validate = new ValidateInput(input);
        try (Store tracker = new SqlTracker()) {
            tracker.init();
            UserAction[] actions = {
                    new CreateAction(),
                    new ReplaceAction(),
                    new DeleteAction(),
                    new FindAllAction(),
                    new FindNameAction(),
                    new FindIdAction(),
                    new ExitAction()
            };
            new StartUI().init(validate, tracker, actions, output);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StartUI start = new StartUI();
        start.createrunup();
    }
}


