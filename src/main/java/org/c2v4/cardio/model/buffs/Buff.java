package org.c2v4.cardio.model.buffs;


import org.c2v4.cardio.controller.Board;
import org.c2v4.cardio.model.Ped;

public interface Buff<T extends Ped> {

    void onEndTurn(final T owner, final Board board);

    void onCast(final T owner, final Board board);

    void onRemoval(Ped ped, Board board);

    int getHealthModification(Ped ped, Board board);

    int getAttackModification(Ped ped, Board board);
}
