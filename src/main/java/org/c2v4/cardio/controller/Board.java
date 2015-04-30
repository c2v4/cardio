package org.c2v4.cardio.controller;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.c2v4.cardio.model.*;
import org.c2v4.cardio.model.action.Action;

import java.util.*;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Board extends Entity {

    Side turn;
    private Set<Player> players = new HashSet<Player>();
    private Set<EventObserver> preExecutors = new HashSet<EventObserver>();
    private Set<EventObserver> postExecutors = new HashSet<EventObserver>();
    private Queue<Action<? extends Ped, ? extends Entity>> actions = new LinkedList<Action<? extends Ped, ? extends Entity>>();
    private List<Ped> minions = new LinkedList<Ped>();

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
    }

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

//    public <T extends Ped> void addMinion(final MinionBase base,
//                                          final Side side, final T source, final int position) {
//        final Ped toAdd = MinionFactory.createMinion(base, side);
//
//        resolveAction(new Action<T, Ped>(source, toAdd, board,
//                new Activity<T, Ped>(Activity.TargetType.BOARD) {
//
//                    @Override
//                    public void act(final Board board, final T source,
//                                    final Ped target) {
//                        if (position < 0) {
//                            board.getMinions().add(target);
//                        } else {
//                            board.getMinions().add(position, target);
//                        }
//                    }
//
//                }, EnumSet.of(Action.ActionType.SUMMON)));
//    }

    private Player getPlayer(final Side side) {
        for (final Player player : players) {
            if (player.getSide().equals(side)) {
                return player;
            }
        }
        throw new IllegalStateException("There is no player on " + side
                + " side");
    }

    public void removeMinion(Ped source) {
    }
}
