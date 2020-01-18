package L4_URLLoader;


public class Main {

    public static void main(String[] args) {

        URLLoader loader = new URLLoader();

        loader.load ("www.yandex.ru");    ///Не верный формат url, повтори ввод
        loader.load ("https://www.yandex.ru");
        loader.load ("https://www.sberbank.ru/ru/person");


    }
}
