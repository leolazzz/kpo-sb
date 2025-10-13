package zoo.main;

import zoo.Models.*;
import zoo.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class ConsoleApplication implements CommandLineRunner{
    private final Zoo zoo;
    private final Scanner scan = new Scanner(System.in);

    public ConsoleApplication(Zoo zoo) {
        this.zoo = zoo;
    }

    @Override
    public void run(String... args){
        showMenu();
    }

    private void showMenu(){
        while (true){
            System.out.println("Application Moscow Zoo");
            System.out.println("1. New Animal");
            System.out.println("2. Get amount of food");
            System.out.println("3. Animal for contact zoo");
            System.out.println("4. Inventory");
            System.out.println("5. Exit");
            System.out.println("Choose:");
            int cmd = scan.nextInt();
            switch (cmd){
                case 1 -> addNewAnimal();
                case 2 -> showFood();
                case 3 -> showContactZoo();
                case 4 -> showInvent();
                case 5 ->{
                    return;
                }
                default -> System.out.println("error");
            }
        }
    }

    private void addNewAnimal(){
        System.out.println("1. Monkey");
        System.out.println("2. Rabbit");
        System.out.println("3. Tiger");
        System.out.println("4. Wolf");
        System.out.println("5. Exit");
        System.out.println("Choose:");
        int cmd = scan.nextInt();
        int fd, knd;
        String nm;
        Animal animal;
        switch (cmd){
            case 1 -> {
                System.out.println("Enter name:");
                nm = scan.nextLine();
                nm = scan.nextLine();
                System.out.println("Enter amount of food:");
                fd = scan.nextInt();
                System.out.println("Enter kind(1-10):");
                knd = scan.nextInt();
                animal = new Monkey(nm, fd, knd);
            }
            case 2 -> {
                System.out.println("Enter name:");
                nm = scan.nextLine();
                nm = scan.nextLine();
                System.out.println("Enter amount of food:");
                fd = scan.nextInt();
                System.out.println("Enter kind(1-10):");
                knd = scan.nextInt();
                animal = new Rabbit(nm, fd, knd);
            }
            case 3 -> {
                System.out.println("Enter name:");
                nm = scan.nextLine();
                nm = scan.nextLine();
                System.out.println("Enter amount of food:");
                fd = scan.nextInt();
                animal = new Tiger(nm, fd);
            }
            case 4 -> {
                System.out.println("Enter name:");
                nm = scan.nextLine();
                nm = scan.nextLine();
                System.out.println("Enter amount of food:");
                fd = scan.nextInt();
                animal = new Wolf(nm, fd);
            }
            case 5 -> {
                return;
            }
            default  ->{
                System.out.println("error");
                return;
            }
        }
        if(zoo.addAnimal(animal)) {
            System.out.println("Animal in zoo");
        } else {
            System.out.println("Animal not in zoo");
        }
    }

    private void showFood(){
        System.out.println("Total amount of food " + zoo.getAmountFood());
    }

    private void showContactZoo(){
        System.out.println("Animal for contact zoo:");
        zoo.getAnimalsForContact().forEach(animal -> System.out.println(animal.getName()));
    }

    private void showInvent(){
        System.out.println("Inventory:");
        zoo.getInventory().forEach(inv -> System.out.println(inv.getInventoryNumber()));
    }
}
