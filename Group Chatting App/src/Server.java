

import java.io.*;
import java.net.*;
import java.util.*;



public class Server implements Runnable{
    
    Socket socket;
    public static Vector client = new Vector();
    public Server(Socket skt){
        
        try{
            
            socket = skt;
        }
        catch(Exception e){
            e.printStackTrace();
        
        }
    
    }
    
    //override method in runnable interface
    public void run(){
        try{
            
            BufferedReader read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            
            client.add(writer);
            
            while(true){
                
                String data = read.readLine().trim();
                System.out.println("Recieved " + data);
                
                for(int i=0;i<client.size();i++){
                    
                    BufferedWriter bw =  (BufferedWriter) client.get(i);
                    bw.write(data);
                    bw.write("\r\n");
                    bw.flush();
                
                }
            
            }
        
        }catch(Exception e){
            e.printStackTrace();
        
        }
        
    }
    
    public static void main(String[] args) throws Exception{
        
      
        ServerSocket s = new ServerSocket(2003);
        while(true){
            Socket skt = s.accept();
            Server svr = new Server(skt);
            Thread thread = new Thread(svr);
            thread.start(); //internally call run method 
        
        }
    
    
    }
    
}




