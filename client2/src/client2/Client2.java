/*
* Client2 connects with the server and receives files
*/

package client2;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client2 {

  public final static String SERVER = "127.0.0.1";  // localhost
  public final static String
       FILE_TO_RECEIVED = "/Users/robertterrin/Desktop/COMS/client2/source-downloaded.txt";  // you may change this, I give a
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
    
    int portNumber = Integer.parseInt(args[0]);
    String serverAddress = args[1];
      
    int bytesRead;
    int current = 0;
    FileOutputStream fos = null;
    BufferedOutputStream bos = null;
    Socket sock = null;
    try {
      sock = new Socket(InetAddress.getByName(serverAddress), portNumber);
      System.out.println("Connecting...");

      // receive file
      byte [] mybytearray  = new byte [FILE_SIZE];
      InputStream is = sock.getInputStream();
      
      fos = new FileOutputStream(FILE_TO_RECEIVED);
      bos = new BufferedOutputStream(fos);
      
      bytesRead = is.read(mybytearray,0,mybytearray.length);
      current = bytesRead;
      
      do {
         bytesRead =
            is.read(mybytearray, current, (mybytearray.length-current));
               System.out.println("last byte read was:" + current);

         if(bytesRead >= 0) current += bytesRead;
      } while(bytesRead > -1);

      System.out.println("bytes read. last byte was:" + current);

      bos.write(mybytearray, 0 , current);
      bos.flush();
      System.out.println("File " + FILE_TO_RECEIVED
          + " downloaded (" + current + " bytes read)");
    }
    finally {
      if (fos != null) fos.close();
      if (bos != null) bos.close();
      if (sock != null) sock.close();
    }
  }

}