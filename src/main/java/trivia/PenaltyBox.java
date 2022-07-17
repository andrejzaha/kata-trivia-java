package trivia;

import java.util.*;

public class PenaltyBox {
    private final Set<Player> prisoners = new HashSet<>();

    public void addPrisoner(Player player) {
        prisoners.add(player);
    }

    public void releasePrisoner(Player player) {
        prisoners.remove(player);
    }

    public boolean isGivenPlayerPrisoner(Player player) {
        return prisoners.contains(player);
    }
}
