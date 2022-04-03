package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a2.model.DrinkModel;

@Dao
public interface DrinkDAO {

    @Query("SELECT * FROM DrinkModel")
    LiveData<List<DrinkModel>> getAll();

    @Query("select exists(select * from DrinkModel where strDrink=:name)")
    ListenableFuture<Boolean> drinkExistsList(String name);

    @Query("SELECT * FROM DrinkModel WHERE idDrink LIKE :uid")
    ListenableFuture<DrinkModel> findDrink(int uid);

    @Query("SELECT * FROM DrinkModel WHERE strDrink LIKE :name")
    ListenableFuture<DrinkModel> findDrink(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDrink(DrinkModel drink);

    @Update
    void updateDrink(DrinkModel drink);

    @Delete
    void deleteDrink(DrinkModel drink);

}
