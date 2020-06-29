package com.javarush.games.snake;
import com.javarush.engine.cell.*;
import java.util.ArrayList;
import java.util.List;

public class Snake {

    private List<GameObject> snakeParts = new ArrayList<>();    //части змейки
    private final static String HEAD_SIGN = "\uD83D\uDE3A";
    private final static String BODY_SIGN = "\uD83C\uDF15\n";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;

    public void setDirection(Direction _direction) {
        if (direction == Direction.RIGHT && snakeParts.get(0).x != snakeParts.get(1).x ||
                direction == Direction.LEFT && snakeParts.get(0).x != snakeParts.get(1).x ||
                direction == Direction.UP && snakeParts.get(0).y != snakeParts.get(1).y ||
                direction == Direction.DOWN && snakeParts.get(0).y != snakeParts.get(1).y) {
            if (_direction == Direction.LEFT && direction != Direction.RIGHT ||
                    _direction == Direction.RIGHT && direction != Direction.LEFT ||
                    _direction == Direction.UP && direction != Direction.DOWN ||
                    _direction == Direction.DOWN && direction != Direction.UP) {
                direction = _direction;
            }
        }
    }

    public Snake(int _x, int _y) {
        GameObject sOne = new GameObject(_x, _y);
        GameObject sTwo = new GameObject(_x+1, _y);
        GameObject sThree = new GameObject(_x+2, _y);
        snakeParts.add(sOne);
        snakeParts.add(sTwo);
        snakeParts.add(sThree);
    }

    public void draw(Game game) {
        int size = snakeParts.size();
        if (isAlive) {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.BROWN, 75);
            for (int i = 1; i < size; ++i) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.BROWN, 75);
            }
        }
        else {
            game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE, HEAD_SIGN, Color.RED, 75);
            for (int i = 1; i < size; ++i) {
                game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, Color.RED, 75);
            }
        }
    }

    public void move(Apple apple) {
        GameObject head = createNewHead();
        if (!checkCollision(head)) {
            if (head.x < SnakeGame.WIDTH && head.y < SnakeGame.HEIGHT && head.x > -1 && head.y > -1) {
                if (head.x == apple.x && head.y == apple.y) {
                    apple.isAlive = false;
                    snakeParts.add(0, head);
                } else {
                    snakeParts.add(0, head);
                    removeTail();
                }
            } else {
                isAlive = false;
            }
        }
        else {
            isAlive = false;
        }
    }

    public GameObject createNewHead() {
        GameObject obj = null;
        if (direction == Direction.LEFT) {
            obj = new GameObject(snakeParts.get(0).x - 1, snakeParts.get(0).y);
        }
        if (direction == Direction.RIGHT) {
            obj = new GameObject(snakeParts.get(0).x + 1, snakeParts.get(0).y);
        }
        if (direction == Direction.UP) {
            obj = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        }
        if (direction == Direction.DOWN) {
            obj = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        }
        return obj;
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public boolean checkCollision(GameObject obj) {
        boolean ok = false;
        int i = 0;
        while (!ok && i < snakeParts.size()) {
            ok = obj.x == snakeParts.get(i).x && obj.y == snakeParts.get(i).y;
            ++i;
        }
        return ok;
    }

    public int getLength() {
        return snakeParts.size();
    }

}
