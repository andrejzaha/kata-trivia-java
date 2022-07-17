package trivia;

public class Place {
    private final int index;
    private final Category category;

    public Place(int index, Category category) {
        this.index = index;
        this.category = category;
    }

    public int getIndex() {
        return index;
    }

    public Category getCategory() {
        return category;
    }
}
