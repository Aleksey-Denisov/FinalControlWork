package module;
import java.time.*;

public abstract class Animals{
    protected int animalId;
    protected String animalName;
    protected LocalDate animalBDay;
    
    public void setAnimalId(int animalId){
        this.animalId = animalId;
    }
    public void setAnimalName(String animalName){
        this.animalName = animalName;
    }
    public void setAnimalBDay(LocalDate animalBDay){
        this.animalBDay = animalBDay;
    }
    public int getAnimalId(){
        return animalId;
    }
    public String getAnimalName(){
        return animalName;
    }
    public LocalDate getAnimalBDay(){
        return animalBDay;
    }
    @Override
    public String toString(){
        return String.format("%d. Класс %s: имя: %s, дата рождения: %s ", getAnimalId(), getClass().getSimpleName(), getAnimalName(), getAnimalBDay());
    }
}
class Cat extends Animals{
    public Cat(){

    }
}
class Dog extends Animals{
    public Dog(){

    }
}
class Hamster extends Animals{
    public Hamster(){
        
    }
}