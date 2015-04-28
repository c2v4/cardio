package org.c2v4.cardbot.model;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.c2v4.cardbot.action.Action;
import org.c2v4.cardbot.action.Action.ActionType;
import org.c2v4.cardbot.action.Activity;
import org.c2v4.cardbot.action.Activity.TargetType;
import org.c2v4.cardbot.model.Player.Side;
import org.c2v4.cardbot.model.minions.MinionBase;
import org.c2v4.cardbot.model.minions.MinionFactory;
import org.c2v4.cardbot.model.models.Ped;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Board extends Entity {

    private Set<Player> players = new HashSet<Player>();
    private Set<EventObserver> preExecutors = new HashSet<EventObserver>();
    private Set<EventObserver> postExecutors = new HashSet<EventObserver>();
    private Queue<Action<? extends Ped, ? extends Entity>> actions = new LinkedList<Action<? extends Ped, ? extends Entity>>();
    private List<Ped> minions = new LinkedList<Ped>();

    Side turn;

    public Board(final Classes player, final Classes opponent,
            final boolean playerStarts) {
        players.add(new Player(player, Side.PLAYER));
        players.add(new Player(opponent, Side.OPPONENT));
        if (playerStarts) {
            turn = Side.PLAYER;
        } else {
            turn = Side.OPPONENT;
        }
        setBoards();
    };

    private void setBoards() {
        for (final Ped player : players) {
            player.setBoard(this);
        }

    }

    public Ped getPlayer() {
        for (final Ped player : players) {
            if (player.getSide().equals(Side.PLAYER)) {
                return player;
            }
        }
        throw new IllegalStateException();
    }

    public Ped getPlayer(final Entity player) {
        for (final Ped innerPlayer : players) {
            if (innerPlayer.equals(player)) {
                return innerPlayer;
            }
        }
        throw new IllegalArgumentException("no such player");
    }

    public Ped getOpponent(final Ped ent) {
        // only for 2 players
        for (final Ped player : players) {
            if (!player.equals(ent)) {
                return player;
            }
        }
        throw new IllegalStateException(
                "List of players contain less than 2 players");
    }

    public void resolveAction(
            final Action<? extends Ped, ? extends Entity> action) {

        actions.add(action);
        boolean toRemove = false;
        for (final EventObserver obs : preExecutors) {
            if (actions.peek() == action && obs.act(action, this)) {
                toRemove = true;
                break;

            }
        }
        if (toRemove) {
            actions.peek();
        } else {
            action.act();
            for (final EventObserver obs : preExecutors) {
                if (actions.peek() == action && obs.act(action, this)) {
                    toRemove = true;

                }
            }
        }
    }

    public <T extends Ped> void addMinion(final MinionBase base,
            final Side side, final T source, final int position) {
        final Ped toAdd = MinionFactory.createMinion(base, side);

        resolveAction(new Action<T, Ped>(source, toAdd, board,
                new Activity<T, Ped>(TargetType.BOARD) {

                    @Override
                    public void act(final Board board, final T source,
                            final Ped target) {
                        if (position < 0) {
                            board.getMinions().add(target);
                        } else {
                            board.getMinions().add(position, target);
                        }
                    }

                }, EnumSet.of(ActionType.SUMMON)));
    }

    private Player getPlayer(final Side side) {
        for (final Player player : players) {
            if (player.getSide().equals(side)) {
                return player;
            }
        }
        throw new IllegalStateException("There is no player on " + side
                + " side");
    }
}
