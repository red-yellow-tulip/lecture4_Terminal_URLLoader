package L4_Exeption.Server;

public class User implements Comparable {

    private int id;             // идентификация
    private double value;       // сумма на счету
    private String pin;         // ключ
    private boolean isBlock;    // заблокировать
    private int counterBlock;   // счетчик неверных вводов pin

    public User(int id, double value, String pin) {
        this.id = id;
        this.value = value;
        this.pin = pin;
        isBlock = false;
        counterBlock = 0;
    }

    @Override
    public int compareTo(Object o) {
        if (o instanceof User) {
            User u = (User) o;
            if (u.id == id)
                return 0;
            else if (u.id < id)
                return -1;
            else
                return 1;
        }
        return -1;
    }

    // получить id
    public int getId() {
        return id;
    }

    // состояние счета
    public double getValue(String pin) throws IllegalAccessError {

        //проверка блокировки и дополнительная проверка числа раз неверного ввода pin
        isBlock (pin);

        return this.value;
    }
    //проверка блокировки и дополнительная проверка числа раз неверного ввода pin
    private void isBlock(String pin)  throws IllegalAccessError {
        if (isBlock)
            throw new SecurityException("Счет заблокирован. Разблокируйте счет.");

        if (!this.pin.equals(pin)) {
            counterBlock++;
            Block();
            throw new IllegalAccessError(String.format("Неверный код pin [ block = %s (%d/3) ] , введите верный pin код." , isBlock ? "Yes" : "No", counterBlock));
        }
        else counterBlock = 0;
    }

    public boolean isPin(String pin){
        return this.pin.equals(pin);
    }



    // операция   1 - снять (-), 2 - положить (+)
    public boolean setValue(double v, int op, String pin) throws IllegalAccessError,
                                                                 IllegalArgumentException,
                                                                 IndexOutOfBoundsException,
                                                                 SecurityException,
                                                                 IllegalStateException {


        //проверка блокировки и дополнительная проверка числа раз неверного ввода pin
        isBlock (pin);

        // проверка корректности значения
        if (v <= 0  )
            throw new IllegalArgumentException("Невероне значения параметра: " + v + " введите другую сумму");

        // проверка типа операции
        if (op == 1) {
            value += v;
            return true;

        } else if (op == 2) {
            // проверка корректности значения
            if (v%100 != 0)
                throw new IllegalArgumentException("Сумма снятия должна быть кратна 100 руб., введите другую сумму." );

            // запрещено уходить в минус
            if (value < v)
                throw new IndexOutOfBoundsException("Недостаточно средств на счете, введите другую сумму.");
            value -= v;
            return true;
        } else    // нет такого типа операции
            throw new IllegalStateException("Недопустимая операция, выбирети другой тип операции.");

    }

    @Override
    public String toString() {
        return String.format("Состояние счета <id = %d> value = %f pin = %s block = %s (%d/3) \n", id, value, pin, isBlock ? "Yes" : "No", counterBlock);
    }
    // блокировка после 3 неверных попыток ввода
    private void Block() {

        if (counterBlock >= 3) {
            isBlock = true;
            //counterBlock = 0;
        }
    }
    // разблокировать
    void unBlock(String pin) {
        StringBuffer sb = new StringBuffer();
        sb.replace(0,pin.length(),pin);

        if (this.pin.equals(sb.toString())) {
            isBlock = false;
            counterBlock = 0;
        }
    }
}
