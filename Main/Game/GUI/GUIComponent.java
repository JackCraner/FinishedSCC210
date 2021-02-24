package Main.Game.GUI;

import org.jsfml.graphics.Drawable;

/**
 * The Generic Abstract class which defines a GUI Component
 * All GUI Components should inherit this
 * @param <T>  The Component (Game Component) the GUI element handles
 *           for example the GUIInventory will handle a backpack component
 */
public abstract class GUIComponent<T> implements Drawable
{
    private final T t;
    abstract public void update();

    /**
     * Gets the type of game component handled by the gui component
     * @param t
     */
    public GUIComponent(T t)
    {
        this.t = t;
    }

    /**
     * gets the component
     * @return the static typed component
     */
    public T getT()
    {
        return t;
    }
}
