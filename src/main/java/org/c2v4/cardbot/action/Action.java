package org.c2v4.cardbot.action;

import java.util.Set;

import lombok.Data;

import org.c2v4.cardbot.model.Board;
import org.c2v4.cardbot.model.Entity;
import org.c2v4.cardbot.model.models.Ped;

@Data
public class Action<S extends Ped, T extends Entity> {
    private Board board;
    private S source;
    private T target;
    private Activity<S, T> activity;
    private Set<ActionType> actionTypes;

    public Action(final S source, final T target, final Board board,
            final Activity<S, T> activity, final Set<ActionType> actionTypes) {
        super();
        this.source = source;
        this.target = target;
        this.activity = activity;
        this.actionTypes = actionTypes;
        this.board = board;
    }

    public static enum ActionType {
        DAMAGING,
        HEALING,
        SPELL,
        HERO_POWER,
        BUFF,
        SUMMON,
        DEATHRATLE
    }

    public void act() {
        activity.act(board, source, target);

    }
}
