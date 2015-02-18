import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    
  //public final static String FILE_TO_SEND = "/Users/robertterrin/Desktop/COMS/server/source.txt";  // you may change this
  public final static int FILE_SIZE = 6022386; // file size temporary hard coded

  public static void main (String [] args ) throws IOException {
    
    int portNumber = Integer.parseInt(args[0]);
    int bytesRead;
    int current = 0;
    byte [] recievedArray  = new byte [FILE_SIZE];

    FileInputStream fis = null;
    BufferedInputStream bis = null;
    OutputStream os = null;
    ServerSocket servsock = null;
    Socket sock = null;
    
    //Added for testing
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    //Remove after testing
    
    try {
      servsock = new ServerSocket(portNumber);
      while (true) {
        System.out.println("Waiting...");

//Try to accept connection and recieve input stream
        try {
          sock = servsock.accept();
          System.out.println("Accepted connection : " + sock);
                    
// receive stream
          
          //Added for testing
          fos = new FileOutputStream("/Users/robertterrin/Desktop/COMS/server/source-downloaded.txt");
          bos = new BufferedOutputStream(fos);
          //Removes after testing
          
          InputStream is = sock.getInputStream();
          bytesRead = is.read(recievedArray,0,recievedArray.length);
          current = bytesRead;

          do {
             bytesRead =
                is.read(recievedArray, current, (recievedArray.length-current));
             if(bytesRead >= 0) current += bytesRead;
          } while(bytesRead > -1);
          
          //Added for testing
          bos.write(recievedArray, 0 , current);
          bos.flush();
          System.out.println("File /Users/robertterrin/Desktop/COMS/server/source-downloaded.txt downloaded" + current + " bytes read)");
          //Remove after testing
          
          System.out.println("Recieved");
          
          }
          finally {
            //if (is != null) is.close();
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock!=null) sock.close();
          }
          
          sock = servsock.accept();
          System.out.println("Accepted connection to : " + sock);
            
//Try to accept connection and send output stream
          try {
            // send file
            //File myFile = new File (FILE_TO_SEND);
            byte [] mybytearray  = recievedArray; //new byte [(int)myFile.length()];
            //fis = new FileInputStream(myFile);
            //bis = new BufferedInputStream(fis);
            //bis.read(mybytearray,0,mybytearray.length);
            os = sock.getOutputStream();
            System.out.println("Sending " + "(" + mybytearray.length + " bytes)");
            os.write(mybytearray,0,mybytearray.length);
            os.flush();
            System.out.println("Done.");
          }
          finally {
            //if (is != null) is.close();
            if (bis != null) bis.close();
            if (os != null) os.close();
            if (sock!=null) sock.close();
        }
      }
    }
    finally {
      if (servsock != null) servsock.close();
    }
  }
}
