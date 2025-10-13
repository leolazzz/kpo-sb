package zoo.Models;

public abstract class Herbo extends Animal {
    protected int kindness;
    public Herbo(String name, int food, int kindness) {
        super(name, food);
        this.kindness = kindness;
    }

    public int getKindness() {
        return kindness;
    }

    @Override
    public boolean isInteractive() {
        return isHealthy() && this.getKindness() >= 5;
    }
}
