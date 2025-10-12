package zoo.services;

import zoo.Models.Animal;
import org.springframework.stereotype.Service;

@Service
public class VetClinic {
    public boolean isHealth(Animal animal){//tested
        if (animal.getName().length() > 2){
            animal.setHealth(true);
            System.out.println("animal " + animal.getName() + " is health");
            return true;
        }
        System.out.println("animal " + animal.getName() + " is not health");
        return false;
    }
}
