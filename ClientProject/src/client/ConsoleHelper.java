package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ANYA on 20.09.2016.
 */
public class ConsoleHelper {
    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static String readString(){
        String s = null;
        do
        {
            try
            {
                return reader.readLine();
            }
            catch (IOException e)
            {
                writeMessage("Произошла ошибка при попытке ввода текста. Попробуйте еще раз.");
            }
        } while (true);
    }
    public static int readInt(){
        do
        {
            try
            {
                return Integer.parseInt(readString());
            }
            catch (NumberFormatException e)
            {
                writeMessage("Произошла ошибка при попытке ввода числа. Попробуйте еще раз.");
            }
        }while (true);
    }
}
