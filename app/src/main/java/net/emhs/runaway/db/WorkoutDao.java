package net.emhs.runaway.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WorkoutDao {

    @Query("SELECT * FROM workouts")
    List<Workout> getAllWorkouts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertWorkout(Workout... workout);
}
