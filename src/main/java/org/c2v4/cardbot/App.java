package org.c2v4.cardbot;

import org.c2v4.cardbot.model.Board;
import org.c2v4.cardbot.model.Classes;

public class App {
    public static void main(final String[] args) {
        final Board board = new Board(Classes.PALADIN, Classes.DRUID, false);
        System.out.println(board);
    }
}
