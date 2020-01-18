package L4_Exeption;

import L4_Exeption.Terminal.TerminalImpl;

public class Main {

    public static void main(String[] args) {

        TerminalImpl term = new TerminalImpl();
        //id,       ,nal  ,     pin
        term.addUser(101,1000,"1234");
        term.addUser(102,1000,"1235");
        term.addUser(103,1000,"1236");
        term.addUser(104,1000,"1237");
        term.addUser(105,1000,"1238");

        term.run();
    }
}
