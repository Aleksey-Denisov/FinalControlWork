package view;
import controller.*;

public class UI {
    private MenuController menuController;
    private AnimalController animalController;
    public UI(){
        menuController = new MenuController();
        animalController = new AnimalController();
    }
    public void start(){
        boolean flag = true;
        while(flag){
            System.out.println("1 - Список всех животных\n2 - Завести новое животное\n" +
            "3 - Изменить данные о животном\n4 - Что умеет животное\n5 - Дрессировка\n6 - Удалить запись\n0 - Выход");
            int choiseUser;
            switch(menuController.menuStart()){
                case 1:
                    animalController.getAnimals();
                    break;
                case 2:
                    System.out.println("Какое животное добавить:\n1 - Кошка\n2 - Собака\n" + 
                    "3 - Хомяк\n0 - Возврат в основное меню");
                    choiseUser = menuController.addAnimal();
                    if(choiseUser > 0)
                        animalController.addAnimal(choiseUser);
                    break;
                case 3:
                    System.out.println("Введите номер животного, 0 для возврата в основное меню: ");

                    choiseUser = menuController.getInList(animalController.getAnimals());
                    if(choiseUser > 0)
                        animalController.updateAnimal(choiseUser);
                    break;
                case 4:
                    System.out.println("Введите номер животного, 0 для возврата в основное меню: ");
                    choiseUser = menuController.getInList(animalController.getAnimals());
                    if(choiseUser > 0)
                        animalController.getCommands(choiseUser);
                    break;
                case 5:
                    System.out.println("Введите номер животного, 0 для возврата в основное меню: ");
                    choiseUser = menuController.getInList(animalController.getAnimals());
                    if(choiseUser > 0)
                        animalController.setCommands(choiseUser);
                    break;
                case 6:
                    System.out.println("Введите номер животного, 0 для возврата в основное меню: ");
                    choiseUser = menuController.getInList(animalController.getAnimals());
                    if(choiseUser > 0)
                        animalController.deleteAnimal(choiseUser);
                    break;
                default:
                    flag = false;
                    break;
            }
        }
    }
}
