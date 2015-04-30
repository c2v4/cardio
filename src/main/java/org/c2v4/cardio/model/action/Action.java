package org.c2v4.cardio.model.action;

import lombok.Data;
import org.c2v4.cardio.controller.Board;
import org.c2v4.cardio.model.Entity;
import org.c2v4.cardio.model.Ped;

import java.util.EnumSet;
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
                   final Activity<S, T> activity, final Set<ActionType> actionTypes, Action cause) {
        super();
        this.source = source;
        this.target = target;
        this.activity = activity;
        this.actionTypes = actionTypes;
        this.board = board;
        this.cause = cause;

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

    public static class Builder<S extends Ped, T extends Entity> {
        private Board board;
        private S source;
        private T target;
        private Activity<S, T> activity;
        private Set<ActionType> actionTypes = EnumSet.noneOf(ActionType.class);
        private Action cause;

        public Builder(Board board, Activity activity) {
            this.board = board;
            this.activity = activity;

        }

        public Builder setActionTypes(Set<ActionType> actionTypes) {
            this.actionTypes = actionTypes;
            return this;
        }

        public Builder setTarget(T target) {
            this.target = target;
            return this;
        }

        public Builder setSource(S source) {
            this.source = source;
            return this;
        }

        public Builder addActionType(ActionType actionType) {
            actionTypes.add(actionType);
            return this;
        }

        public Builder setCause(Action cause) {
            this.cause = cause;
            return this;
        }

        public Action build() {
            return new Action(source, target, board, activity, actionTypes, cause);
        }

        public Builder addActionType(Set<ActionType> actionTypes) {
            this.actionTypes.addAll(actionTypes);
            return this;
        }
    }
}
