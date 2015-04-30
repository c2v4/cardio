package org.c2v4.cardio.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.c2v4.cardio.model.action.Action;

import java.util.Set;

import static org.c2v4.cardio.model.action.Action.ActionType;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Player extends Ped {
    private static final int PLAYERS_STARTING_HEALTH = 30;
    private final Classes clazz;
    @Setter
    @Getter
    private int armorValue;

    public Player(final Classes clazz, final Side side) {
        this.side = side;
        this.clazz = clazz;
        setHealth(PLAYERS_STARTING_HEALTH);
    }

    @Override
    public void damage(final int value) {
        final int restValue = value - armorValue;
        armorValue -= value;
        if (armorValue < 0) {
            armorValue = 0;
        }
        if (restValue > 0) {
            super.damage(restValue);
        }
    }

    @SuppressWarnings("unchecked")
    public void useSpecialAbility(final Ped target) {
        final Set<ActionType> actionTypes = clazz.getActionTypes();
        actionTypes.add(ActionType.HERO_POWER);
        Action action = new Action.Builder<Player, Entity>(getBoard(),
                clazz.getActivity())
                .setSource(this)
                .setTarget(target)
                .addActionType(clazz.getActionTypes())
                .addActionType(ActionType.HERO_POWER).build();
        getBoard().resolveAction(
                action);
    }

    @Override
    public void isAttackedBy(final Ped source) {
        damage(source.getAttackValue());

    }

    @Override
    public boolean attack(final Ped target) {
        // TODO Auto-generated method stub weapon and stuff
        return false;
    }

    @Override
    public boolean onPlay() {
        return false;
    }

}
