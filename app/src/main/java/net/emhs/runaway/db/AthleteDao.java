package net.emhs.runaway.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface AthleteDao {

    // Gets all athletes currently in database in one list
    @Query("SELECT * FROM athletes")
    List<Athlete> getAllAthletes();

    // Adds athlete to database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAthlete(Athlete... athlete);

    // Deletes athlete from database
    @Delete
    void delete(Athlete athlete);

    // Updates name of athlete if in database
    @Query("UPDATE athletes SET name = :name WHERE uid = :uid")
    void updateName(String name, int uid);

    @Query("UPDATE athletes SET description = :description WHERE uid = :uid")
    void updateDescription(String description, int uid);

    // Updates records of athlete
    @Query("UPDATE athletes SET records = :records WHERE uid = :uid")
    void updateRecords(String records, int uid);

    // Loads single athlete by ID
}
