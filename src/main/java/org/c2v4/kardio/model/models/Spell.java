package org.c2v4.cardbot.model.models;

import org.c2v4.kardio.model.Board;

public interface Spell {
    public void use(final Board board, final Ped target);
}
