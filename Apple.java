package com.javarush.games.snake;
import com.javarush.engine.cell.*;
import java.util.List;

import java.util.Random;

public class Apple extends GameObject {


    private static final String SHRIMP = "\uD83C\uDF64";
    private static final String CAKE = "\uD83C\uDF70";
    private static final String LOLLY = "\uD83C\uDF6D";
    public boolean isAlive = true;

    public Apple(int _x, int _y) {
        super(_x, _y);
    }

    public void draw(Game obj) {
        obj.setCellValueEx(x, y, Color.NONE, SHRIMP, Color.BROWN, 75);
    }
}
