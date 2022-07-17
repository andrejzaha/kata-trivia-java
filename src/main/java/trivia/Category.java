package trivia;

import java.util.Deque;

public class Category {
    private final String name;
    private final Deque<String> questions;

    public Category(String name, Deque<String> questions) {
        this.name = name;
        this.questions = questions;
    }

    public String getName() {
        return name;
    }

    public String consumeQuestion() {
        return questions.removeFirst();
    }
}
