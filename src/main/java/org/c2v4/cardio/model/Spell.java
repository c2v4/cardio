package org.c2v4.cardio.model;


import org.c2v4.cardio.controller.Board;

public interface Spell {
    void use(final Board board, final Ped target);
}
