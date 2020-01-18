package L4_Exeption.Terminal.Validator;

import java.util.Scanner;

public class PinValidator {

    private Scanner sc;

    public PinValidator (){

        sc = new Scanner(System.in);
    }


    // вывести на экран пользователя
    public void printLine (String line){
        System.out.println(line);

    }
    // считать введеные данные
    public String readLine (){

        return sc.nextLine();
    }
}
