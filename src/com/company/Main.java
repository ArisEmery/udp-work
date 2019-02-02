package com.company;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.Scanner;
import com.company.Message;

public class Main {
    public void putString(String str,ByteBuffer buffer)
    {
        for(int i=0;i<str.length();i++)
        {
            //buffer.p
        }
    }

    public static void main(String[] args) throws UnknownHostException {
        short message_type=1;
        String A_number="a01984177";//maybe no a
        String Last_Name="Emery";
        String First_Name="Aris";
        String Alias="Sphwen";
        byte[] sendbuffer=new byte[1024];
        //sendbuffer= new byte[]{((byte) message_type)};
        ByteBuffer buffer = ByteBuffer.allocate(256);
//        sendbuffer[0]=(byte)message_type;
//        sendbuffer[1]=A_number.getBytes();

        buffer.putShort(message_type);
        buffer.put(A_number.getBytes());
        buffer.put(Last_Name.getBytes());
        buffer.put(First_Name.getBytes());
        buffer.put(Alias.getBytes());



        
        // get a datagram socket
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }


        // send request
       // byte[] buf = new byte[2+A_number.getBytes().length+Last_Name.getBytes().length+First_Name.getBytes().length+Alias.getBytes().length];

        //Message name=new Message(First_Name);

        int test=A_number.getBytes().length;
        InetAddress address=InetAddress.getLocalHost();

        DatagramPacket new_game = new DatagramPacket(buffer.array(),buffer.array().length, address, 4445);
        try {
            socket.send(new_game);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get response
        byte[] newbuf = new byte[256];
        DatagramPacket game_def = new DatagramPacket(newbuf, newbuf.length);

        try {
            socket.receive(game_def);
            System.out.println(game_def.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // display response
        String received = new String(game_def.getData(), 0, game_def.getLength());
        System.out.println("Quote of the Moment: " + received);

        socket.close();

//	    int port = 12001; //check this
//        UDPclient client = new UDPclient(port);
//        System.out.println("were running");
//        client.run();
    }
}
