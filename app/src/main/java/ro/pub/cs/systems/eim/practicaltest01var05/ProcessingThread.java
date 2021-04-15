package ro.pub.cs.systems.eim.practicaltest01var05;
import java.util.Date;
import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private String text;

    public ProcessingThread(Context context, String text) {
        this.context = context;
        this.text = text;
    }

    @Override
    public void run() {
        while (isRunning) {
            String[] array;
            array = text.split(", ");
            for(String s: array) {
                if (!s.equals("")) {
                    sendMessage(s);
                }
            }
            sleep();
        }
    }

    private void sendMessage(String word) {
        Intent intent = new Intent();
        intent.setAction("button.text");
        intent.putExtra("message", word);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}