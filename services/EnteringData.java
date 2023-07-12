package services;
import java.time.*;
import java.time.format.*;
import java.util.*;

public class EnteringData {
    private Scanner in = new Scanner(System.in,"ibm866");
    public int consoleEnterInt(String message, int bmin, int bmax){
        int valueInt;
        while(true){
            System.out.print(message);
            if(in.hasNextInt()){
                valueInt = in.nextInt();
                if(bmin <= valueInt && valueInt < bmax){
                    in.nextLine();
                    break;
                }
                else
                    System.out.println("Записи под таким номером не существует");
            }else{
                System.out.println("Введены неверные данные");
                in.nextLine();
            }
        }
        return valueInt;
    }
    public String consoleEnter(String message){
        String userData;
        while(true){
            System.out.print(message);
            userData = in.nextLine();
            if(userData != ""){
                break;
            }else{
                System.out.println("Строка не может быть пустой!");
            }
        }
        return userData;
    }
    public LocalDate consoleEnterDate(String message){
        LocalDate userData;
        while(true){
            System.out.print(message);
            try{
                userData = LocalDate.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                if(userData != null){
                    break;
                }else{
                    System.out.println("Строка не может быть пустой!");
                }
            }catch (DateTimeParseException ex){
                System.out.println(ex.getMessage());
                System.out.println("Формат записи должен быть dd.MM.yyyy");
            }
        }
        return userData;
    }
    public void scannerClose(){
        in.close();
    }
}
