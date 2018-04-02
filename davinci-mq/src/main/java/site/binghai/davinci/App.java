package site.binghai.davinci;

import site.binghai.davinci.mq.socket.ServerListener;

/**
 * Created by IceSea on 2018/3/31.
 * GitHub: https://github.com/IceSeaOnly
 */

public class App {
    public static void main(String[] args) {
        try {
            new ServerListener().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
