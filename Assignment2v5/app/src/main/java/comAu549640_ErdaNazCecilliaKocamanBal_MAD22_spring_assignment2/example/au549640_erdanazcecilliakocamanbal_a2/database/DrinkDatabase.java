package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;
import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.utils.ApplicationClass;

@Database(entities = {DrinkModel.class}, version = 3)
public abstract class DrinkDatabase extends RoomDatabase {

    public abstract DrinkDAO drinkDAO();  //mandatory DAO getter
    private static DrinkDatabase instance; //database instance for singleton


    public static DrinkDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (DrinkDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(ApplicationClass.getContext(), //context.getApplicationContext(),
                            DrinkDatabase.class, "drink_database")
                            .fallbackToDestructiveMigration()
                            //   .allowMainThreadQueries()   //Databases should always be accessed asynchronously in your apps! (see Repository class)
                            .build();
                }
            }
        }
        return instance;
    }
}
