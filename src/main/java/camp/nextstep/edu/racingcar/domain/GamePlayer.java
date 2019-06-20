package camp.nextstep.edu.racingcar.domain;

import camp.nextstep.edu.util.AssertUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GamePlayer {
    private Game game;
    private MovingStrategy movingStrategy;

    private GamePlayer(Game game, MovingStrategy movingStrategy) {
        AssertUtils.notNull(movingStrategy, "'movingStrategy' must not be null");
        this.game = game;
        this.movingStrategy = movingStrategy;
    }

    public static GamePlayer from(MovingStrategy movingStrategy) {
        return new GamePlayer(null, movingStrategy);
    }

    public void initializeGame(List<String> names, int numberOfRounds) {
        final List<CarName> carNameList = names.stream()
                .map(CarName::from)
                .collect(Collectors.toList());
        final CarNames carNames = CarNames.from(carNameList);
        game = Game.of(carNames, numberOfRounds);
    }

    public void playAllRounds() {
        this.checkGameInitialized();
        while (!game.hasFinished()) {
            game.playOneRound(movingStrategy);
        }
    }

    public Rounds getGameResult() {
        this.checkGameInitialized();
        return game.getResult();
    }

    private void checkGameInitialized() {
        if (game == null) {
            throw new IllegalStateException("Game is not initialized.");
        }
    }

    @Override
    public String toString() {
        return "GamePlayer{" +
                "game=" + game +
                ", movingStrategy=" + movingStrategy +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GamePlayer that = (GamePlayer) o;
        return Objects.equals(game, that.game) &&
                Objects.equals(movingStrategy, that.movingStrategy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, movingStrategy);
    }
}