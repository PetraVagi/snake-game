package com.codecool.snake;

import com.codecool.snake.entities.GameEntity;
import com.codecool.snake.entities.Animatable;
import com.codecool.snake.entities.Interactable;
import com.codecool.snake.entities.snakes.Snake;

import java.util.List;

public class GameLoop {
    private Snake snake;
    private Snake snake_2;
    private boolean running = false;

    public GameLoop(Snake snake) { this.snake = snake; }

    public GameLoop(Snake snake, Snake snake_2) {
        this.snake = snake;
        this.snake_2 = snake_2;
    }

    public void start() {
        running = true;
    }

    public void stop() {
        running = false;
    }

    public void step() {
        if(running) {
            if (Utils.getChance() < 0.8)
                Globals.getInstance().game.spawnLengthPowerUps(1);

            if (Utils.getChance() < 0.3)
                Globals.getInstance().game.spawnHealthPowerUps(1);

            if (Utils.getChance() < 0.1)
                Globals.getInstance().game.spawnSpeedPowerUps(1);

            if (Utils.getChance() < 0.45)
                Globals.getInstance().game.spawnSimpleEnemies(1);

            if (Utils.getChance() < 0.5)
                Globals.getInstance().game.spawnRunningEnemies(2);

            if (Utils.getChance() < 0.3)
                Globals.getInstance().game.spawnFlyingEnemies(1);

            snake.step();
            snake_2.step();
            for (GameEntity gameObject : Globals.getInstance().display.getObjectList()) {
                if (gameObject instanceof Animatable) {
                    ((Animatable) gameObject).step();
                }
            }
            checkCollisions();
        }

        Globals.getInstance().display.frameFinished();
    }

    private void checkCollisions() {
        List<GameEntity> gameObjs = Globals.getInstance().display.getObjectList();
        for (int idxToCheck = 0; idxToCheck < gameObjs.size(); ++idxToCheck) {
            GameEntity objToCheck = gameObjs.get(idxToCheck);
            if (objToCheck instanceof Interactable) {
                for (int otherObjIdx = idxToCheck + 1; otherObjIdx < gameObjs.size(); ++otherObjIdx) {
                    GameEntity otherObj = gameObjs.get(otherObjIdx);
                    if (otherObj instanceof Interactable){
                        if(objToCheck.getBoundsInParent().intersects(otherObj.getBoundsInParent())){
                            ((Interactable) objToCheck).apply(otherObj);
                            ((Interactable) otherObj).apply(objToCheck);
                        }
                    }
                }
            }
        }
    }
}
