package org.c2v4.cardbot.model.buffs;

import org.c2v4.cardbot.model.Board;
import org.c2v4.cardbot.model.models.Ped;

public interface Buff<T extends Ped> {

    public void onEndTurn(final T owner, final Board board);

    public void onCast(final T owner, final Board board);

}
