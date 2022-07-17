package trivia;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;

public class GameBoard {
    private final Set<Category> categories;
    private final Set<Place> places;

    public GameBoard(List<String> categoryNames) {
        categories = categoryNames.stream()
                .map(categoryName -> new Category(categoryName, createCategoryQuestions(categoryName)))
                .collect(Collectors.toSet());
        Map<Category, List<Integer>> placesForCategoryPairs = Map.of(
                getCategoryByName("Pop"), List.of(0, 4, 8),
                getCategoryByName("Science"), List.of(1, 5, 9),
                getCategoryByName("Sports"), List.of(2, 6, 10),
                getCategoryByName("Rock"), List.of(3, 7, 11)
        );
        places = IntStream.range(0, 12)
                .mapToObj(index -> new Place(index, getCategoryByPlaceIndex(index, placesForCategoryPairs)))
                .collect(Collectors.toSet());
    }

    private Category getCategoryByPlaceIndex(int placeIndex, Map<Category, List<Integer>> placesForCategoryPairs) {
        return placesForCategoryPairs.entrySet().stream()
                .filter(entry -> entry.getValue().contains(placeIndex))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find a Category for the Place index=" + placeIndex))
                .getKey();
    }

    private Deque<String> createCategoryQuestions(String name) {
       return IntStream.range(0, 50)
               .mapToObj(index -> name + " Question " + index)
               .collect(Collectors.toCollection(LinkedList::new));
    }

    private Category getCategoryByName(String categoryName) {
        return categories.stream()
                .filter(category -> category.getName().equals(categoryName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find category for categoryName=" + categoryName));
    }

    public void movePlayer(Player player, int diceRoll) {
        Place newPlace = providePlaceByDiceRoll(player.getPlace(), diceRoll);
        player.setPlace(newPlace);
    }

    private Place providePlaceByDiceRoll(Place currentPlace, int diceRoll) {
        int playerInitialPlace = (nonNull(currentPlace) ? currentPlace.getIndex() : 0);

        int placeAfterRoll = playerInitialPlace + diceRoll;
        if (placeAfterRoll > (places.size() - 1)) {
            placeAfterRoll -= places.size();
        }

        int finalPlaceAfterRoll = placeAfterRoll;
        return places.stream()
                .filter(place -> place.getIndex() == finalPlaceAfterRoll)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Could not find Place for placeIndex=" + finalPlaceAfterRoll));
    }
}
