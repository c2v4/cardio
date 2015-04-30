package org.c2v4.cardio.model;

import org.c2v4.cardio.controller.Board;
import org.c2v4.cardio.model.action.Action;

/**
 * Created by c2v4 on 30.04.15.
 */
public interface EventObserver {
    boolean act(Action<? extends Ped, ? extends Entity> action, Board board);
}
