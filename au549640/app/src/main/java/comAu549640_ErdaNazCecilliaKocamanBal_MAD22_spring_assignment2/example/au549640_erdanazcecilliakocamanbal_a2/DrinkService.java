package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.au549640_erdanazcecilliakocamanbal_a1.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModelRepository;

/*
* SOURCES:
* The code here has been greatly inspired and copied from the demo DemoServices 1.3 from lesson L5. Thank you!
* And the code for the random drink is from here: https://stackoverflow.com/questions/14703805/get-random-list-item-android
* */

public class DrinkService extends Service {
    private static final String TAG = "ForegroundService";
    private static final String SERVICE_CHANNEL = "serviceChannel";
    private static final int NOTIFICATION_ID = 444;
    ExecutorService execService;
    boolean started = false;
    List<DrinkModel> drinklist = new ArrayList<>();
    DrinkModelRepository repository;
    private NotificationCompat.Builder bulderNotif;
    private NotificationManagerCompat notificationManager;

    public DrinkService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    private DrinkModel getRandomDrink() {
       drinklist = repository.getInstance(getApplication()).getDrinklist().getValue();
        if(drinklist == null) return null;
       return drinklist.get(new Random().nextInt(drinklist.size()));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    SERVICE_CHANNEL,
                    "Drink Notification Service",
                    NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager = NotificationManagerCompat.from(this);
        DrinkModel randomDrink = getRandomDrink();

        if(randomDrink != null){
            bulderNotif = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                    .setContentTitle(getString(R.string.drinkNotificationMixTime))
                    .setContentText(getString(R.string.drinkNotificationTry) + randomDrink.getStrCategory() + getString(R.string.drinkNotificationSuch) + randomDrink.getStrDrink())
                    .setSmallIcon(R.drawable.molotov)
                    .setTicker(getString(R.string.drinkNotificationPrettyGood))
                    .setTimeoutAfter(60000);
        }else {
            bulderNotif = new NotificationCompat.Builder(this, SERVICE_CHANNEL)
                    .setContentTitle(getString(R.string.drinkNotificationMixTime))
                    .setContentText(getString(R.string.drinkNotificationRandomDefault))
                    .setSmallIcon(R.drawable.molotov)
                    .setTicker(getString(R.string.drinkNotificationDefaultGitGud))
                    .setTimeoutAfter(60000);

        }
        startForeground(NOTIFICATION_ID, bulderNotif.build());
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
            Log.d(TAG, "Submitting");
            DrinkModel randomDrink = getRandomDrink();
            if (randomDrink == null) {
                bulderNotif.setContentTitle("🤔😱"+ getString(R.string.drinkNotificationADD) + "🥶😜")
                        .setContentText(getString(R.string.drinkNotificationRandomDefault) + "👌✨😈");
            }else{
                Log.d(TAG, "Drink is not null");
                Log.d(TAG, "Drink to be shown: " + String.valueOf(randomDrink.getStrDrink()));

                bulderNotif.setContentTitle("🔥😍" + getString(R.string.drinkNotificationMixTime ) + "💯👌" +  " " + getString(R.string.drinkNotificationTry) +" "+ randomDrink.getStrCategory())
                        .setContentText( getString(R.string.drinkNotificationSuch) +" "+ randomDrink.getStrDrink() + " " + getString(R.string.drinkNotificationRating) + "🥳🎉")
                        .setSmallIcon(R.drawable.molotov);
            }
            notificationManager.notify(NOTIFICATION_ID, bulderNotif.build());

            try {
                Log.d(TAG, "Sleeping");
                Thread.sleep(60000);

            } catch (InterruptedException e) {
                Log.e(TAG, "run: ERROR", e);
            }
            if(started) {
                Log.d(TAG, "Recursion has commenced!");
                doRecursiveWork();
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }
}