package net.emhs.runaway.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AthleteDao {

    @Query("SELECT * FROM athletes")
    List<Athlete> getAllAthletes();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAthlete(Athlete... athlete);

    @Delete
    void delete(Athlete athlete);

    @Query("UPDATE athletes SET name = :name WHERE uid = :uid")
    void updateName(String name, int uid);

    @Query("UPDATE athletes SET records = :records WHERE uid = :uid")
    void updateRecords(String records, int uid);

    @Query("SELECT * FROM athletes WHERE uid =:uid")
    Athlete loadSingle(int uid);
}
