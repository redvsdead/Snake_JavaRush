
package com.javarush.games.snake;
import com.javarush.engine.cell.*;
import java.io.*;

public class SnakeGame extends Game {

    public static final int WIDTH = 20;
    public static final int HEIGHT = WIDTH;
    private static final int GOAL = 28;
    private boolean isGameStopped;
    private int score;
    private int turnDelay;
    private Snake snake;
    private Apple apple;

    @java.lang.Override
    public void initialize() {
        //super.initialize();
        setScreenSize(WIDTH, HEIGHT);
        createGame();
    }

    private void createGame() {
        turnDelay = 300;
        score = 0;
        setScore(score);
        isGameStopped = false;
        snake = new Snake(WIDTH/2, HEIGHT/2);
        createNewApple();
        drawScene();
        setTurnTimer(turnDelay);
    }

    private void drawScene() {
        for (int i = 0; i < WIDTH; ++i) {
            for (int j = 0; j < HEIGHT; ++j) {
                setCellValueEx(i, j, Color.LIGHTGRAY, "");
            }
        }
        setCellValueEx(2, 3, Color.NONE, "s", Color.BLACK, 90);
        setCellValueEx(3, 3, Color.NONE, "c", Color.BLACK, 90);
        setCellValueEx(4, 3, Color.NONE, "o", Color.BLACK, 90);
        setCellValueEx(5, 3, Color.NONE, "r", Color.BLACK, 90);
        setCellValueEx(6, 3, Color.NONE, "e:", Color.BLACK, 90);
        setCellValueEx(7, 3, Color.NONE, Integer.toString(score), Color.BLACK, 90);
        snake.draw(this);
        apple.draw(this);
    }

    private void createNewApple() {
        Apple _apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
        boolean ok = snake.checkCollision(_apple);
        while (ok) {
            _apple = new Apple(getRandomNumber(WIDTH), getRandomNumber(HEIGHT));
            ok = snake.checkCollision(_apple);
        }
        apple = _apple;
    }

    private void win() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.DARKGREY, "Y0U D1D TH1S. 1 AM S0 PR0UD 0F Y0U", Color.RED, 25);

    }

    private void gameOver() {
        isGameStopped = true;
        stopTurnTimer();
        showMessageDialog(Color.PINK, "0H G0D. Y0U K1LLED HER. WHY HAVE Y0U D0NE TH1S", Color.RED, 25);
    }

    @Override
    public void onTurn(int step) {
        //super.onTurn(step);
        snake.move(apple);
        if (!apple.isAlive) {
            score += 5;
            turnDelay -= 7;
            setTurnTimer(turnDelay);
            setScore(score);
            createNewApple();
        }
        if (!snake.isAlive){
            gameOver();
        }
        if (snake.getLength() > GOAL) {
            win();
        }
        drawScene();
    }

    @Override
    public void onKeyPress(Key key) {
        switch (key) {
            case LEFT:
                snake.setDirection(Direction.LEFT);
                break;
            case RIGHT:
                snake.setDirection(Direction.RIGHT);
                break;
            case DOWN:
                snake.setDirection(Direction.DOWN);
                break;
            case UP:
                snake.setDirection(Direction.UP);
            default:
                break;
        }
        if (key == Key.SPACE && isGameStopped) {
            createGame();
        }
    }
}
