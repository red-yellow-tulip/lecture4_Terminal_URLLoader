package L4_URLLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class URLLoader {


    public void load(String urlAddres)  {


        try {
            URL url = new URL(urlAddres);

            System.out.println(url.toString());

            System.out.println("Protocol:- " + url.getProtocol());
            System.out.println("Hostname:- " + url.getHost());
            System.out.println("Default port:- " + url.getDefaultPort());

            printWebContent(url);

        } catch (MalformedURLException e) {
            //e.printStackTrace();
            System.out.println("Не верный формат url, повтори ввод");
        } catch (IOException e){
            //e.printStackTrace();
            System.out.println("Ошибка чтения данных контекста");
        }


    }

    private void printWebContent(URL url) throws IOException {

        URLConnection uc = url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
        while (true) {
            String line = reader.readLine();
            if (line == null)
                break;
            System.out.println(line);
        }
    }
}
