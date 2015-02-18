/*
* Client1 is the client that sends a file to the server
 */
package client1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author robertterrin
 */
public class Client1 {
 public final static String FILE_TO_SEND = "/Users/robertterrin/Desktop/COMS/client1/source.txt";  // you may change this
                                                            // different name because i don't want to
                                                            // overwrite the one used by server...

  public final static int FILE_SIZE = 6022386; // file size temporary hard coded
                                               // should bigger than the file to be downloaded

  public static void main (String [] args ) throws IOException {
    
    int portNumber = Integer.parseInt(args[0]);
    String serverAddress = args[1];
    
          FileInputStream fis = null;
          BufferedInputStream bis = null;
          OutputStream os = null;
          
          Socket sock = new Socket(InetAddress.getByName(serverAddress), portNumber);
          System.out.println("Connecting..." + sock);
          try{
                // send file
                File myFile = new File (FILE_TO_SEND);
                byte [] mybytearray  = new byte [(int)myFile.length()];
                fis = new FileInputStream(myFile);
                bis = new BufferedInputStream(fis);
                bis.read(mybytearray,0,mybytearray.length);
                os = sock.getOutputStream();
                System.out.println("Sending " + FILE_TO_SEND + "(" + mybytearray.length + " bytes)");
                os.write(mybytearray,0,mybytearray.length);
                os.flush();
                System.out.println("Done.");
                }
          finally {
                if (bis != null) bis.close();
                if (os != null) os.close();
                if (sock!=null) sock.close();
          }
    }
    
  }