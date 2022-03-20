package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModelRepository;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.utils.ApplicationClass;

/*
* The code here has been greatly inspired and copied from the demo DemoServices 1.3 from lesson L5
* I will be touching up on it again once I've worked out some context issues.
* And then put the string values into an actual string value.
* */

public class DrinkService extends Service {
    private static final String TAG = "ForegroundService";
    private static final String SERVICE_CHANNEL = "serviceChannel";
    private static final int NOTIFICATION_ID = 444;
    ExecutorService execService;
    boolean started = false;
    private List<DrinkModel> drinklist;
    DrinkModel randomDrink;
    int count;

    public DrinkService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        count = 0;

    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        if(Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(SERVICE_CHANNEL, "Foreground Service", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }


        Notification notification = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                .setContentTitle("Try a nice drink!")
                .setContentText("Try a random drink such as a Gin Tonic and much more!")
                .setSmallIcon(R.drawable.molotov)
                .setTicker("It's pretty good.")
                .setTimeoutAfter(60000)
                .build();

        startForeground(NOTIFICATION_ID, notification);
        doBackgroundStuff();
        return START_STICKY;
    }


    private void doBackgroundStuff() {
        if(!started) {
            started = true;
            doRecursiveWork();
        }
    }
    private void doRecursiveWork(){
        if(execService == null) {
            execService = Executors.newSingleThreadExecutor();
        }
        execService.submit(() -> {
            Log.d(TAG, "Count: " + count);
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                Log.e(TAG, "run: EROOR", e);
            }
            if(started) {
                doRecursiveWork();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}