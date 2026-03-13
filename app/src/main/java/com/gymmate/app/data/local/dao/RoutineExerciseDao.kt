package com.gymmate.app.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.gymmate.app.data.local.entity.RoutineExerciseEntity

@Dao
interface RoutineExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(routineExercises: List<RoutineExerciseEntity>)
}