// Source: https://gist.github.com/Shilo/207c7ba4a604b7811b77ff17be8580f3
package com.ntdat.chatapp.utilities;

public class SetTimeOut {
    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }
}
