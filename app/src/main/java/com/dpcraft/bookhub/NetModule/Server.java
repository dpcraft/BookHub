package com.dpcraft.bookhub.NetModule;

/**
 * Created by DPC on 2017/3/15.
 *
 * Address and other parameters of the server
 *
 */
public class Server {
    public static String getServerAddress() {

        return serverAddress;

    }

    private static String serverAddress = "http://112.74.19.3:80/BooksServer/";
//    private static String serverAddress = "https://www.bookp2p.top/BooksServer/";

}
