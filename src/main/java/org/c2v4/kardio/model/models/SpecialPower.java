package org.c2v4.cardbot.model.models;

import org.c2v4.cardbot.model.Board;
import org.c2v4.cardbot.model.Ped;

public interface SpecialPower {

	public void use(final Board board, final Ped target);
}
