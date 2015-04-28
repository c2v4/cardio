package org.c2v4.cardbot.action;

import lombok.Getter;

import org.c2v4.cardbot.model.Board;
import org.c2v4.cardbot.model.Entity;
import org.c2v4.cardbot.model.models.Ped;

/**
 * @author c2v4 The activity itself - inner part of Action
 * @param <S>
 *            class of the source of activity
 * @param <T>
 *            class of the target of activity
 */
public abstract class Activity<S extends Ped, T extends Entity> {
    @Getter
    TargetType type;

    public Activity(final TargetType type) {
        this.type = type;
    }

    /**
     * What it does - do not insert Action into the board here, until you're
     * absolutely sure that this is the right way
     * 
     * @param board
     * @param source
     * @param target
     */
    public abstract void act(final Board board, final S source, final T target);

    public static enum TargetType {

        /**
         * Ability targets own hero
         */
        OWN_HERO,
        /**
         * Ability targets enemy hero
         */
        OPPONENT_HERO,
        /**
         * Ability targets enemy hero or minion
         */
        ENEMY,
        /**
         * Ability targets enemy minion
         */
        ENEMY_MINION,
        /**
         * Ability targets any minion
         */
        MINION,
        /**
         * Ability targets own minion
         */
        OWN_MINION,
        /**
         * Ability targets own minion or own hero
         */
        OWN_SIDE,
        /**
         * Ability targets any minion or hero
         */
        ANY,
        /**
         * Ability don't have specific target, is casted into air
         */
        BOARD
    }
}
