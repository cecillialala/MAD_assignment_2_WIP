package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.utils;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class ApplicationClass  extends Application {

    private static Context app;
    @Override
    public void onCreate() {
        super.onCreate();
        app=this;
    }

    public static Context getContext(){
        return app.getApplicationContext();
    }
}