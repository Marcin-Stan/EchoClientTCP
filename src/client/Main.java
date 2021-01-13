package client;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {

    private static Socket clientSocket;


    public static void main(String[] args) {

       Main test = new Main();
       boolean isConnect = test.connect();
        String request="" ;

        if(isConnect)
        {
            Scanner sc = new Scanner(System.in);
            System.out.println("Wpisz co chcesz wyslac do serwera");
            String data = sc.nextLine();

            test.send(data);
            test.get();


            while (!request.equals("Disconnect") ){
                System.out.println("Jeśli chcesz wyslac inna wiadomosc wpisz Send");
                System.out.println("Jeśli chcesz rozlaczyc sie z serwerem wpisz Disconnect");
                request= sc.nextLine();

                if(!request.equals("Send")&&!request.equals("Disconnect")){
                    System.out.println("Niestety nie ma takiej komendy");
                }

                if(request.equals("Send")){
                    System.out.println("Wpisz co chcesz wyslac do serwera");
                    data = sc.nextLine();
                    test.send(data);
                }else if(request.equals("Disconnect")){
                    try{
                        clientSocket.close();
                        System.out.println("Polaczenie zostalo zamkniete");
                        }catch (IOException exe){
                           System.out.println("Polaczenie nie zostalo zamkniete");
                    }
                }

            }

        }else {
            System.out.println("Sprawdź czy wprowadziles poprawnie dane");

        }
    }


    private boolean connect(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Wprowadz adres serwera, do ktorego chcesz sie polaczyc: ");
            String adress = sc.nextLine();
            System.out.println("Podaj numer portu:");
            Integer port = sc.nextInt();
            clientSocket = new Socket(adress,port);
            System.out.println("Udało się połączyć z serwerem");
            return true;
        }catch (IOException e){
            System.out.println("Nie udalo sie polaczyc z serwerem");
            return false;
        }
    }

    private void send(String data){
        try {
            DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
            PrintWriter writer = new PrintWriter(output,true);
            Scanner sc = new Scanner(System.in);
            writer.println(data);
            System.out.println("Informacja wyslana pomyslnie");
        }catch (IOException ex){
            System.out.println("Nie udalo sie wyslac informacji");
        }

    }

    private void get(){
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = in.readLine();
            System.out.println("Informacja z serwera:"+ message);
        }catch (IOException e){
            System.out.println("Nie otrzymano wiadomosci z serwera ");
        }
    }
}
