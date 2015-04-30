package org.c2v4.cardio.model;

import org.c2v4.cardio.model.action.Action;
import org.c2v4.cardio.model.action.Activity;

import java.util.Set;

/**
 * Created by c2v4 on 30.04.15.
 */
public enum Classes {
    ;

    private Set<Action.ActionType> actionTypes;
    private Activity<Player, Entity> activity;

    public Set<Action.ActionType> getActionTypes() {

        return actionTypes;
    }

    public Activity<Player, Entity> getActivity() {
        return activity;
    }
}
