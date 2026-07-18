package sleep.event.bonus;

public interface Bonus {
    /**
     * Gets the String corresponding to all active bonuses for the ongoing event, assuming there are any
     * @return a String containing one line per bonus added through the Decorator
     */

    String getBonusString();
}
