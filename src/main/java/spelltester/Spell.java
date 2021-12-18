package spelltester;

/**
 * Spell Object
 * Used by retrofit to hold to easily extract and store the data queried from the external api
 */


public class Spell {

    private String name;
    private int level;

    public Spell(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() { return name; }

    public int getLevel() { return level; }

}
