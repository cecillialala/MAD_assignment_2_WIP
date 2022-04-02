package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.database;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.utils.ApplicationClass;

/*
* The database was made by looking at the demo roomdemoasynch from L5
* */
@Database(entities = {DrinkModel.class}, version = 8)
public abstract class DrinkDatabase extends RoomDatabase {

    public abstract DrinkDAO drinkDAO();  //mandatory DAO getter
    private static DrinkDatabase instance; //database instance for singleton


    public static DrinkDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (DrinkDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(ApplicationClass.getContext(),
                            DrinkDatabase.class, "drink_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }
}