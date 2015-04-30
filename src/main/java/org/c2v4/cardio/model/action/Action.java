package org.c2v4.cardio.model.action;

import lombok.Data;
import org.c2v4.cardio.controller.Board;
import org.c2v4.cardio.model.Entity;
import org.c2v4.cardio.model.Ped;

import java.util.Set;

@Data
public class Action<S extends Ped, T extends Entity> {
    private Board board;
    private S source;
    private T target;
    private Activity<S, T> activity;
    private Set<ActionType> actionTypes;
    private Action cause;

    private Action(final S source, final T target, final Board board,
                   final Activity<S, T> activity, final Set<ActionType> actionTypes) {
        super();
        this.source = source;
        this.target = target;
        this.activity = activity;
        this.actionTypes = actionTypes;
        this.board = board;
    }

    public void act() {
        activity.act(board, source, target);

    }

    public enum ActionType {
        DAMAGING,
        HEALING,
        SPELL,
        HERO_POWER,
        BUFF,
        SUMMON,
        DEATHRATLE
    }

    public static class Builder {
        private Board board;
        private S source;
        private T target;
        private Activity<S, T> activity;
        private Set<ActionType> actionTypes;
        private Action cause;

        public Builder setActionTypes(Set<ActionType> actionTypes) {
            this.actionTypes = actionTypes;
            return this;
        }
    }
}
