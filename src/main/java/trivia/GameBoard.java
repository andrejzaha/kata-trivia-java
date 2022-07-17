package trivia;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameBoard {
    private final Set<Category> categories;

    public GameBoard(List<String> categoryNames) {
        categories = categoryNames.stream()
                .map(categoryName -> new Category(categoryName, createCategoryQuestions(categoryName)))
                .collect(Collectors.toSet());
    }

    private Deque<String> createCategoryQuestions(String name) {
       return IntStream.range(0, 50)
               .mapToObj(index -> name + " Question " + index)
               .collect(Collectors.toCollection(LinkedList::new));
    }

    public Category getCategoryByPlace(int place) {
        if (place == 0) return getCategoryByName("Pop");
        if (place == 4) return getCategoryByName("Pop");
        if (place == 8) return getCategoryByName("Pop");
        if (place == 1) return getCategoryByName("Science");
        if (place == 5) return getCategoryByName("Science");
        if (place == 9) return getCategoryByName("Science");
        if (place == 2) return getCategoryByName("Sports");
        if (place == 6) return getCategoryByName("Sports");
        if (place == 10) return getCategoryByName("Sports");
        return getCategoryByName("Rock");
    }

    private Category getCategoryByName(String categoryName) {
        return categories.stream()
                .filter(category -> category.getName().equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find category for categoryName=" + categoryName));
    }
}
