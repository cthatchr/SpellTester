package microservice;

/**
 * Response object
 * Stores the data from retrobuild queries to our microservice
 */

public class Response {
    String spell;
    int level;

    public Response(String spell, int level) {
        this.spell = spell;
        this.level = level;
    }

    public String getSpell() { return spell; }

    public int getLevel() { return level; }
}
