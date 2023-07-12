package controller;

import services.*;

public class MenuController {
    private EnteringData userData;
    public MenuController(){
        userData = new EnteringData();
    }
    public int menuStart(){
        return userData.consoleEnterInt("Укажите номер пункта: ", 0, 7);
    }
    public int addAnimal(){
        return userData.consoleEnterInt("Укажите номер пункта: ", 0, 4);
    }
    public int getInList(int[] size){
        if(size == null){
            return 0;
        }
        return userData.consoleEnterInt("Укажите номер пункта: ", size[0], size[1]);
    }
}
