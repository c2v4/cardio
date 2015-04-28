package org.c2v4.kardio.model.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.c2v4.kardio.action.Action;
import org.c2v4.kardio.action.Activity;
import org.c2v4.kardio.action.Activity.TargetType;
import org.c2v4.kardio.model.Board;
import org.c2v4.kardio.model.Damageable;
import org.c2v4.kardio.model.Entity;
import org.c2v4.kardio.model.Player.Side;
import org.c2v4.kardio.model.buffs.Buff;

@Setter
@Getter
@ToString(callSuper = true)
@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class Ped extends Entity implements Damageable {
    protected int health;
    protected int attackValue;
    protected List<Buff> buffs = new ArrayList<Buff>();
    protected Map<Abilities, List<Activity>> abilities = new HashMap<Abilities, List<Activity>>();
    protected Side side;

    @Override
    public void damage(final int value) {
        health -= value;
        if (checkForDeath(getModifiedHealth())) {
            died();
        }
    }
    
    public void play(){
        getBoard().resolveAction(
                new Action<Ped, Entity>(this, null, getBoard(),
                        new Activity<Ped, Entity>(TargetType.BOARD) {

                            @Override
                            public void act(final Board board,
                                    final Ped source, final Entity target) {
                                board.removeMinion(source);

                            }
                        }, null));
    }
    
    private void died() {
        getBoard().resolveAction(
                new Action<Ped, Entity>(this, null, getBoard(),
                        new Activity<Ped, Entity>(TargetType.BOARD) {

                            @Override
                            public void act(final Board board,
                                    final Ped source, final Entity target) {
                                board.removeMinion(source);

                            }
                        }, null));
    }

    protected boolean checkForDeath(final int modifiedHealth) {
        if (modifiedHealth < 1) {
            return true;
        }
        return false;
    }

    public abstract void isAttackedBy(final Ped source);

    public abstract boolean attack(final Ped target);

    public abstract boolean onPlay();

    public void silence(final Entity source) {
        buffs.clear();
    };

    public void addBuff(final Buff buff) {
        buffs.add(buff);
        buff.onCast(this, getBoard());
    }

    public void removeBuff(final Buff buff) {
        buffs.remove(buff);
        buff.onRemoval(this, getBoard());
    }

    /**
     * @return health value with buff modifications
     */
    public int getModifiedHealth() {
        int modifier = 0;
        for (final Buff buff : buffs) {
            modifier += buff.getHealthModification(this, getBoard());
        }
        return health + modifier;
    }

    /**
     * @return attack value with buff modifications
     */
    public int getModifiedAttackValue() {
        int modifier = 0;
        for (final Buff buff : buffs) {
            modifier += buff.getAttackModification(this, getBoard());
        }
        return attackValue + modifier;
    }

    public void endTurn() {
        for (final Buff buff : getBuffs()) {
            buff.onEndTurn(this, getBoard());
        }
    }

}
