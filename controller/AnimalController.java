package controller;
import java.util.*;
import services.*;
import module.*;

public class AnimalController {
    private ActionDB db;
    private EnteringData userData;
    private Counter count;
    public AnimalController(){
        db = new ActionDB();
        userData = new EnteringData();
        count = new Counter();
    }
    public void addAnimal(int type){
        try{
            Animals animal = new AnimalBuild().getAnimal(AnimalType.values()[type - 1]);
            animal.setAnimalName(userData.consoleEnter("Введите имя животного: "));
            animal.setAnimalBDay(userData.consoleEnterDate("Введите дату рождения: "));
            db.addAnimals(animal);
            count.add();
        }catch(RuntimeException ex){
            System.out.println(ex);
        }
    }
    public int[] getAnimals(){
        List<Animals> pet = db.getAll();
        if(pet.size() == 0){
            System.out.println("В реестре нет ни одного животного");
            return null;
        }else{
            for(Animals animal : pet){
                System.out.println(animal);
            }
        }
        return new int[]{pet.get(0).getAnimalId(), pet.get(pet.size()-1).getAnimalId()+1};
    }
    public void updateAnimal(int idAnimal){
        Animals animal = db.getById(idAnimal);
        if(animal == null){
            return;
        }
        try{
            System.out.println("Укажите данные для изменения:\n1 - Изменить все данные животного\n" +
            "2 - Изменить только имя\n3 - Изменить только дату рождения\n0 - Ничего не менять");
            switch(userData.consoleEnterInt("Укажите номер пункта: ", 0, 4)){
                case 1:
                    animal.setAnimalName(userData.consoleEnter("Введите имя животного: "));
                    animal.setAnimalBDay(userData.consoleEnterDate("Введите дату рождения: "));
                    break;
                case 2:
                    animal.setAnimalName(userData.consoleEnter("Введите имя животного: "));
                    break;
                case 3:
                    animal.setAnimalBDay(userData.consoleEnterDate("Введите дату рождения: "));
                    break;
                default:
                    break;
            }
            db.updateAnimal(animal);
        }catch(RuntimeException ex){
            System.out.println(ex);
        }
    }
    public void getCommands(int idAnimal){
        List<String> commands = db.getCommands(idAnimal);
        for(String command : commands){
            System.out.println(command);
        }
    }
    public void setCommands(int idAnimal){
        List<String> commands = db.getCommands(idAnimal);
        String command = userData.consoleEnter("Введите команду для изучения животным: ");
        if(commands.contains(command)){
            System.out.println("Такую команду животное умеет выполнять");
        }else{
            db.setCommands(idAnimal, command);
        }
    }
    public void deleteAnimal(int idAnimal){
        db.deleteAnimal(idAnimal);
    }
}
