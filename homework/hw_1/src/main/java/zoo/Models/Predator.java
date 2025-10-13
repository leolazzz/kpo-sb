package zoo.Models;

public class Predator extends Animal {
    public Predator(String name, int food) {
        super(name, food);
    }
    public boolean isInteractive() {
        return false;
    }
}
