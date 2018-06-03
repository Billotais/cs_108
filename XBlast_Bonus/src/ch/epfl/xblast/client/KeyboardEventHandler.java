package ch.epfl.xblast.client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;


import ch.epfl.xblast.PlayerAction;

/**
 * Class KeyboardEventHandler
 * 
 * @author Loïs Bilat (258560)
 * @author Nicolas Jeitziner (258692)
 *
 */

public final class KeyboardEventHandler extends KeyAdapter
{
    private final Map<Integer, PlayerAction> map;
    private final Consumer<PlayerAction> consumer;
    
    /**
     * Create a KeyboardEventHandler with a given map associating the index of the key with a PlayerAction
     *          and with a given consumer
     * @param map
     *          the map associating keys with player actions
     * @param consumer
     *          the consumer that will react to the action
     */
    public KeyboardEventHandler(Map<Integer, PlayerAction> map, Consumer<PlayerAction> consumer)
    {
       this.map = Collections.unmodifiableMap(map);
       this.consumer = consumer;
    }
    /**
     * Makes the consummer accept the event of pressing a key
     * @param event
     *          the keyboard event
     */
    
    @Override
    public void keyPressed(KeyEvent event)
    {
        if (map.containsKey(event.getKeyCode()))
        {
            consumer.accept(map.get(event.getKeyCode()));
        } 
    }
}
