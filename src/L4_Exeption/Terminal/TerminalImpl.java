package L4_Exeption.Terminal;

import L4_Exeption.Terminal.Validator.PinValidator;
import L4_Exeption.Server.TerminalServer;

import java.net.UnknownHostException;
import java.util.NoSuchElementException;

public class TerminalImpl {

    // model
    private final TerminalServer server;      // удаленный сервер, все операции и проверки pin здесь

    // view
    private final PinValidator pinValidator;  // Работа с пользователем (получение данных, вывод сообщений)
                                              // работа с вводом-выводовм на консооль
    public TerminalImpl(){
        server = new TerminalServer();
        pinValidator = new PinValidator();
    }


    public void addUser(int id, double value, String pin) {
        server.addUser(id,value,pin);
    }

    public void run()  {

        // перечень хранящихся на сервере счетов
        pinValidator.printLine(server.toString());

        boolean isStop = true;
        String line = "", pin = "", param = "", op = "";
        int id = 0;
        String[] subLine = null;


        while (true) {
            try {
                pinValidator.printLine("Введите номер счета и pin код в формате '101 1234' или 'exit' для завершения");
                line = pinValidator.readLine();
                if (line.equals("exit"))
                    return;

                subLine = line.split(" ");
                id = Integer.parseInt(subLine[0]);
                pin = subLine[1];

                pinValidator.printLine("-> Введите тип операции в формате: " +
                        "\n'+ Х' - для внесения средств, где Х - сумма снятия " +
                        "\n'- Х' - для внесения средств, где Х - сумма внесения" +
                        "\n'= ' - для вывода состояния счета, " +
                        "\n'# ХХХХ' - для разблокировки счета, где ХХХХ - верный pin код в обратном порядке ");

                line = pinValidator.readLine();
                subLine = line.split(" ");
                op = subLine[0];

            } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
                pinValidator.printLine("\n->Неверный формат ввода данных, повторите ввод.");
                continue;
                //e.printStackTrace();
            }

            try {
                switch (op) {
                    case "+":
                        server.runOperation(1, id, Double.parseDouble(subLine[1]), pin);
                        pinValidator.printLine("-> Операция выполнена успешно, вас счет: " + server.getValue(id, pin));
                        break;
                    case "-":
                        server.runOperation(2, id, Double.parseDouble(subLine[1]), pin);
                        pinValidator.printLine("-> Операция выполнена успешно, вас счет: " + server.getValue(id, pin));
                        break;
                    case "=":
                        pinValidator.printLine("-> Состояние счета: " + server.getValue(id, pin));
                        break;
                    case "#":
                        server.unBlock(id,pin);
                        pinValidator.printLine("-> " + server.toStringUser(id));
                        break;
                    default:
                        System.out.println("-> Неверный формат операции, повторите ввод");
                        break;
                }
            } catch (NoSuchElementException | UnknownHostException | IllegalAccessError | IllegalArgumentException |
                    IndexOutOfBoundsException | IllegalStateException | SecurityException e) {

                pinValidator.printLine("->"+e.getMessage() + " Операция не может быть выполнена, сессия закрыта. ");
                //e.printStackTrace();
            }

        }
    }
}
