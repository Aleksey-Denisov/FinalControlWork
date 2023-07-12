package module;

public class AnimalBuild {
    public Animals getAnimal(AnimalType animalType){
        Animals animal = null;
        switch (animalType) {
            case CATS:
                animal = new Cat();
                break;
            case DOGS:
                animal = new Dog();
                break;
            case HAMSTERS:
                animal = new Hamster();
                break;
        }
        return animal;
    }
}
