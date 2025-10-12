package Zoo;

import zoo.Models.*;
import zoo.interfaces.IInvetory;
import zoo.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ZooServiceTest {
    private Zoo zoo;
    private VetClinic clinic;

    @BeforeEach
    void setUp(){
        clinic = new VetClinic();
        zoo = new Zoo(clinic);
    }

    @Test
    void addingHealthAnimal(){
        Monkey mk = new Monkey("qwe", 3, 7);
        boolean res = zoo.addAnimal(mk);
        assertTrue(res, "Adding health");
        assertEquals(1, zoo.getAnimalsCount(), "Adding health");
    }

    @Test
    void addingNotHealthAnimal(){
        Monkey mk = new Monkey("a", 3, 7);
        boolean res = zoo.addAnimal(mk);
        assertFalse(res, "Adding health");
        assertEquals(0, zoo.getAnimalsCount(), "Adding not health");
    }

    @Test
    void addingThing(){
        Table tb = new Table(0);
        Computer cmp = new Computer(10);
        zoo.addThing(tb);
        zoo.addThing(cmp);
        List<Thing> tot = zoo.getAllThings();
        assertEquals(2, tot.size(), "adding Things");
        assertEquals(tb, tot.get(0), "adding Things table");
        assertEquals(cmp, tot.get(1), "adding Things computer");
    }


    @Test
    void gettingAnimalsCount(){
        Monkey mk = new Monkey("qwe", 3, 7);
        Rabbit rb = new Rabbit("zxfacseferqwtr", 20, 7);
        Tiger tg = new Tiger("kyukr", 9);
        zoo.addAnimal(mk);
        zoo.addAnimal(rb);
        zoo.addAnimal(tg);
        int tot = zoo.getAnimalsCount();
        assertEquals(3, tot, "getAnimalsCount");
    }


    @Test
    void gettingAllAnimals(){
        Monkey mk = new Monkey("qwe", 3, 7);
        Rabbit rb = new Rabbit("zxfacseferqwtr", 20, 7);
        Tiger tg = new Tiger("kyukr", 9);
        zoo.addAnimal(mk);
        zoo.addAnimal(rb);
        zoo.addAnimal(tg);
        List<Animal> tot = zoo.getAllAnimals();
        assertEquals(mk, tot.get(0), "getAllAnimal mk");
        assertEquals(rb, tot.get(1), "getAllAnimal rb");
        assertEquals(tg, tot.get(2), "getAllAnimal tg");
    }

    @Test
    void calculatingFood(){
        Monkey mk = new Monkey("qwe", 3, 7);
        Rabbit rb = new Rabbit("zxfacseferqwtr", 20, 7);
        Tiger tg = new Tiger("kyukr", 9);
        zoo.addAnimal(mk);
        zoo.addAnimal(rb);
        zoo.addAnimal(tg);
        int tot = zoo.getAmountFood();
        assertEquals(32, tot, "getAmountFood");
    }



    @Test
    void checkingContactZoo(){
        Monkey mk = new Monkey("qwe", 3, 7);
        Rabbit rb = new Rabbit("zxfacseferqwtr", 20, 2);
        Tiger tg = new Tiger("kyukr", 9);
        zoo.addAnimal(mk);
        zoo.addAnimal(rb);
        zoo.addAnimal(tg);
        List<Animal> tot = zoo.getAnimalsForContact();
        assertEquals(1, tot.size(), "count Animals for contact");
        assertEquals(mk, tot.get(0), "Animals for contact mk");
    }

    @Test
    void gettingInvent(){
        Table tb = new Table(0);
        Monkey mk = new Monkey("qwe", 3, 7);
        zoo.addThing(tb);
        zoo.addAnimal(mk);
        List<IInvetory> tot = zoo.getInventory();
        assertEquals(2, tot.size(), "getting Inventory");
    }

}
