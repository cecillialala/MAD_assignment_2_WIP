package comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

import comAu549640_ErdaNazCecilliaKocamanBal_MAD22_spring_assignment2.example.au549640_erdanazcecilliakocamanbal_a1.model.DrinkModel;

@Dao
public interface DrinkDAO {

    @Query("SELECT * FROM DrinkModel")
    LiveData<List<DrinkModel>> getAll();

    @Query("SELECT * FROM DrinkModel WHERE idDrink LIKE :uid LIMIT 1")
    ListenableFuture<DrinkModel> findDrink(int uid);


    @Query("SELECT * FROM DrinkModel WHERE strDrink LIKE :name LIMIT 1")
    ListenableFuture<DrinkModel> findDrink(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDrink(DrinkModel drink);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAll(DrinkModel... drink);

    @Update
    void updateDrink(DrinkModel drink);

    @Delete
    void deleteDrink(DrinkModel drink);

    @Delete
    void deleteAll(List<DrinkModel> drinkModelList);

    @Query("DELETE FROM drinkmodel")
    public void deleteAll();

}
