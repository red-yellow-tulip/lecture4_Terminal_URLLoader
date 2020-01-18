package L4_Exeption.Server;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;

public class TerminalServer {
    //
    private Map<Integer, User> listUser;
    private Random rand;

    public TerminalServer (){

        listUser = new HashMap<>();
        rand = new Random();
    }

    // добавить нового владельца счета
    public boolean addUser(int id, double value, String pin) throws IllegalArgumentException {

        if (listUser.containsKey(id)) {
            throw new IllegalArgumentException("Указанный номер счета уже сущестует: " + id + "введите номер счета верно");
            //return false;
        }
        User u = new User(id, value, pin);
        listUser.put(id, u);
        return true;
    }

    // проверка верности pin
    public boolean isPin(int id, String pin) throws NoSuchElementException {
        if (listUser.containsKey(id)) {
            return listUser.get(id).isPin(pin);
        } else
            throw new NoSuchElementException("Указанный счет не найден:" + id  + ". Введите номер счета верно. ");
    }

    public double getValue (int id, String pin) throws  UnknownHostException,
                                                        NoSuchElementException,
                                                        SecurityException,
                                                        IllegalAccessError {
        // имитация сетевой недоступности
        if (rand.nextInt(100) < 10)
            throw new UnknownHostException("Удаленный сервис недоступен, повторите операцию позднее. ");

        if (listUser.containsKey(id)) {
            try {
                return listUser.get(id).getValue(pin);

                // пробросить вверх exception
            } catch (IllegalAccessError ie) {
                throw new IllegalAccessError(ie.getMessage());

            } catch (SecurityException ie) {
                throw new SecurityException(ie.getMessage());
            }

        } else
            throw new NoSuchElementException("Указанный счет не найден:" + id  + ". Введите номер счета верно. ");
    }

    public void runOperation (int op, int id,  double value, String pin) throws  RuntimeException,
                                                                                    UnknownHostException {


        // имитация сетевой недоступности
        if (rand.nextInt(100) < 9)
            throw new UnknownHostException("Удаленный сервис недоступен, повторите операцию позднее.");

        // проверка наличия
        if (listUser.containsKey(id)) {
            try {
                listUser.get(id).setValue(value, op, pin);
             // пробросить вверх exception
            } catch (IllegalAccessError         | SecurityException         | IllegalArgumentException
                    |IndexOutOfBoundsException  | IllegalStateException ie                            ) {
                throw new IllegalAccessError(ie.getMessage());
            }
        } else
            throw new NoSuchElementException("Указанный счет не найден: " + id + ". Введите номер счета.");
    }

    public void unBlock(int id, String pin) throws NoSuchElementException{
        if (listUser.containsKey(id)) {
            listUser.get(id).unBlock(pin);
        } else
            throw new NoSuchElementException("Указанный счет не найден:" + id + "введите номер счета верно.");
    }

    @Override
    public String toString() {

        return listUser.entrySet().toString();
    }

    public String toStringUser(int id) {
        return listUser.get(id).toString();
    }




}
