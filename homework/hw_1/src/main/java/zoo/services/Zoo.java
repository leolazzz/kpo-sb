package zoo.services;

import zoo.Models.Animal;
import zoo.Models.Thing;
import zoo.interfaces.IInvetory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

@Service
public class Zoo {
    private final List<Animal> animals = new ArrayList<>();
    private final List<Thing> things = new ArrayList<>();
    private final VetClinic clinic;
    private int num = 0;

    public Zoo(VetClinic clinic) {
        this.clinic = clinic;
    }

    public boolean addAnimal(Animal animal){//tested
        if(clinic.isHealth(animal)){
            animal.setInventoryNumber(animal.hashCode() + num);
            ++num;
            animals.add(animal);
            return true;
        }
        return false;
    }

    public void addThing(Thing thing){//tested
        thing.setInventoryNumber(thing.hashCode() + num);
        ++num;
        things.add(thing);
    }

    public List<Animal> getAllAnimals(){//tested
        return new ArrayList<Animal>(animals);
    }


    public int getAnimalsCount(){//tested
        return animals.toArray().length;
    }

    public List<Thing> getAllThings(){//tested
        return new ArrayList<Thing>(things);
    }

    public List<Animal> getAnimalsForContact(){//tested
        return animals.stream().filter(Animal::isInteractive).toList();
    }
    public int getAmountFood(){//tested
        return animals.stream().mapToInt(Animal::getFood).sum();
    }

    public List<IInvetory> getInventory(){//tested
        List<IInvetory> ar = new ArrayList<IInvetory>(getAllAnimals());
        ar.addAll(getAllThings());
        return ar;
    }
}
