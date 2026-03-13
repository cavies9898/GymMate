package com.gymmate.app.data.local.database

import com.gymmate.app.data.local.dao.ExerciseDao
import com.gymmate.app.data.local.dao.RoutineDao
import com.gymmate.app.data.local.dao.RoutineExerciseDao
import com.gymmate.app.data.local.dao.WorkoutSessionDao
import com.gymmate.app.data.local.entity.ExerciseEntity
import com.gymmate.app.data.local.entity.RoutineEntity
import com.gymmate.app.data.local.entity.RoutineExerciseEntity
import com.gymmate.app.data.local.entity.WorkoutSessionEntity
import java.time.LocalDateTime
import javax.inject.Inject

class DatabaseSeeder @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val routineDao: RoutineDao,
    private val routineExerciseDao: RoutineExerciseDao,
    private val sessionDao: WorkoutSessionDao
) {

    suspend fun seed() {
        seedExercises()
        seedRoutines()
        seedTestSession()
    }

    private suspend fun seedExercises() {
        val exercises = listOf(
            // CASA
            ExerciseEntity(id = 1, name = "Flexiones", description = "Ejercicio clásico de pecho y tríceps.", muscleGroup = "CHEST", workoutType = "HOME", difficulty = "BEGINNER", durationSeconds = null, reps = 15, sets = 3),
            ExerciseEntity(id = 2, name = "Sentadillas", description = "Movimiento fundamental de tren inferior.", muscleGroup = "LEGS", workoutType = "HOME", difficulty = "BEGINNER", durationSeconds = null, reps = 20, sets = 3),
            ExerciseEntity(id = 3, name = "Plancha", description = "Ejercicio isométrico de core.", muscleGroup = "CORE", workoutType = "HOME", difficulty = "BEGINNER", durationSeconds = 45, reps = null, sets = 3),
            ExerciseEntity(id = 4, name = "Burpee", description = "Movimiento explosivo de cuerpo completo.", muscleGroup = "FULL_BODY", workoutType = "HOME", difficulty = "INTERMEDIATE", durationSeconds = null, reps = 10, sets = 3),
            ExerciseEntity(id = 5, name = "Escalador", description = "Combina core y cardio.", muscleGroup = "CORE", workoutType = "HOME", difficulty = "INTERMEDIATE", durationSeconds = 30, reps = null, sets = 3),
            ExerciseEntity(id = 6, name = "Saltos de tijera", description = "Cardio de calentamiento cuerpo completo.", muscleGroup = "CARDIO", workoutType = "HOME", difficulty = "BEGINNER", durationSeconds = 45, reps = null, sets = 3),
            ExerciseEntity(id = 7, name = "Zancadas", description = "Fuerza de tren inferior en una pierna.", muscleGroup = "LEGS", workoutType = "HOME", difficulty = "BEGINNER", durationSeconds = null, reps = 12, sets = 3),
            ExerciseEntity(id = 8, name = "Flexión de pica", description = "Flexión enfocada en hombros.", muscleGroup = "SHOULDERS", workoutType = "HOME", difficulty = "INTERMEDIATE", durationSeconds = null, reps = 10, sets = 3),

            // GIMNASIO
            ExerciseEntity(id = 9, name = "Press de banca", description = "Press de barra en banco plano para pecho.", muscleGroup = "CHEST", workoutType = "GYM", difficulty = "INTERMEDIATE", durationSeconds = null, reps = 10, sets = 4),
            ExerciseEntity(id = 10, name = "Peso muerto", description = "Jalón compuesto de cuerpo completo.", muscleGroup = "BACK", workoutType = "GYM", difficulty = "ADVANCED", durationSeconds = null, reps = 6, sets = 4),
            ExerciseEntity(id = 11, name = "Jalón al pecho", description = "Ejercicio de amplitud de espalda en polea.", muscleGroup = "BACK", workoutType = "GYM", difficulty = "BEGINNER", durationSeconds = null, reps = 12, sets = 3),
            ExerciseEntity(id = 12, name = "Prensa de piernas", description = "Compuesto de tren inferior en máquina.", muscleGroup = "LEGS", workoutType = "GYM", difficulty = "BEGINNER", durationSeconds = null, reps = 15, sets = 3),
            ExerciseEntity(id = 13, name = "Press militar", description = "Press de hombros con barra.", muscleGroup = "SHOULDERS", workoutType = "GYM", difficulty = "INTERMEDIATE", durationSeconds = null, reps = 8, sets = 4),
            ExerciseEntity(id = 14, name = "Remo en polea", description = "Remo de espalda sentado en polea baja.", muscleGroup = "BACK", workoutType = "GYM", difficulty = "BEGINNER", durationSeconds = null, reps = 12, sets = 3),
            ExerciseEntity(id = 15, name = "Press inclinado con mancuernas", description = "Press de pecho superior con mancuernas.", muscleGroup = "CHEST", workoutType = "GYM", difficulty = "INTERMEDIATE", durationSeconds = null, reps = 10, sets = 3),
            ExerciseEntity(id = 16, name = "Caminadora", description = "Cardio en estado estable.", muscleGroup = "CARDIO", workoutType = "GYM", difficulty = "BEGINNER", durationSeconds = 1200, reps = null, sets = 1)
        )
        exerciseDao.insertAll(exercises)
    }

    private suspend fun seedRoutines() {
        // Rutinas CASA
        val homeBeginnerRoutineId = routineDao.insertRoutine(
            RoutineEntity(
                id = 1,
                name = "Arranque matutino",
                description = "Rutina rápida de cuerpo completo para empezar el día con energía.",
                workoutType = "HOME",
                difficulty = "BEGINNER",
                durationMinutes = 20
            )
        )
        routineExerciseDao.insertAll(listOf(
            RoutineExerciseEntity(routineId = homeBeginnerRoutineId, exerciseId = 6, sets = 3, reps = null, durationSeconds = 45, restSeconds = 30, order = 1),
            RoutineExerciseEntity(routineId = homeBeginnerRoutineId, exerciseId = 1, sets = 3, reps = 15, durationSeconds = null, restSeconds = 45, order = 2),
            RoutineExerciseEntity(routineId = homeBeginnerRoutineId, exerciseId = 2, sets = 3, reps = 20, durationSeconds = null, restSeconds = 45, order = 3),
            RoutineExerciseEntity(routineId = homeBeginnerRoutineId, exerciseId = 3, sets = 3, reps = null, durationSeconds = 45, restSeconds = 30, order = 4)
        ))

        val homeIntermediateRoutineId = routineDao.insertRoutine(
            RoutineEntity(
                id = 2,
                name = "HIIT en casa",
                description = "Entrenamiento de alta intensidad por intervalos, sin equipamiento.",
                workoutType = "HOME",
                difficulty = "INTERMEDIATE",
                durationMinutes = 30
            )
        )
        routineExerciseDao.insertAll(listOf(
            RoutineExerciseEntity(routineId = homeIntermediateRoutineId, exerciseId = 4, sets = 4, reps = 10, durationSeconds = null, restSeconds = 30, order = 1),
            RoutineExerciseEntity(routineId = homeIntermediateRoutineId, exerciseId = 5, sets = 4, reps = null, durationSeconds = 30, restSeconds = 30, order = 2),
            RoutineExerciseEntity(routineId = homeIntermediateRoutineId, exerciseId = 7, sets = 3, reps = 12, durationSeconds = null, restSeconds = 40, order = 3),
            RoutineExerciseEntity(routineId = homeIntermediateRoutineId, exerciseId = 8, sets = 3, reps = 10, durationSeconds = null, restSeconds = 45, order = 4)
        ))

        val homeAdvancedRoutineId = routineDao.insertRoutine(
            RoutineEntity(
                id = 3,
                name = "Quema total",
                description = "Reto avanzado de peso corporal para atletas experimentados.",
                workoutType = "HOME",
                difficulty = "ADVANCED",
                durationMinutes = 45
            )
        )
        routineExerciseDao.insertAll(listOf(
            RoutineExerciseEntity(routineId = homeAdvancedRoutineId, exerciseId = 4, sets = 5, reps = 15, durationSeconds = null, restSeconds = 20, order = 1),
            RoutineExerciseEntity(routineId = homeAdvancedRoutineId, exerciseId = 1, sets = 5, reps = 20, durationSeconds = null, restSeconds = 30, order = 2),
            RoutineExerciseEntity(routineId = homeAdvancedRoutineId, exerciseId = 5, sets = 4, reps = null, durationSeconds = 45, restSeconds = 20, order = 3),
            RoutineExerciseEntity(routineId = homeAdvancedRoutineId, exerciseId = 3, sets = 4, reps = null, durationSeconds = 60, restSeconds = 30, order = 4)
        ))

        // Rutinas GIMNASIO
        val gymBeginnerRoutineId = routineDao.insertRoutine(
            RoutineEntity(
                id = 4,
                name = "Primer día de gym",
                description = "Introducción perfecta a las máquinas y pesos libres del gimnasio.",
                workoutType = "GYM",
                difficulty = "BEGINNER",
                durationMinutes = 45
            )
        )
        routineExerciseDao.insertAll(listOf(
            RoutineExerciseEntity(routineId = gymBeginnerRoutineId, exerciseId = 16, sets = 1, reps = null, durationSeconds = 600, restSeconds = 60, order = 1),
            RoutineExerciseEntity(routineId = gymBeginnerRoutineId, exerciseId = 12, sets = 3, reps = 15, durationSeconds = null, restSeconds = 60, order = 2),
            RoutineExerciseEntity(routineId = gymBeginnerRoutineId, exerciseId = 11, sets = 3, reps = 12, durationSeconds = null, restSeconds = 60, order = 3),
            RoutineExerciseEntity(routineId = gymBeginnerRoutineId, exerciseId = 14, sets = 3, reps = 12, durationSeconds = null, restSeconds = 60, order = 4)
        ))

        val gymIntermediateRoutineId = routineDao.insertRoutine(
            RoutineEntity(
                id = 5,
                name = "Día de empuje",
                description = "Sesión enfocada en pecho, hombros y tríceps.",
                workoutType = "GYM",
                difficulty = "INTERMEDIATE",
                durationMinutes = 60
            )
        )
        routineExerciseDao.insertAll(listOf(
            RoutineExerciseEntity(routineId = gymIntermediateRoutineId, exerciseId = 9, sets = 4, reps = 10, durationSeconds = null, restSeconds = 90, order = 1),
            RoutineExerciseEntity(routineId = gymIntermediateRoutineId, exerciseId = 15, sets = 3, reps = 10, durationSeconds = null, restSeconds = 75, order = 2),
            RoutineExerciseEntity(routineId = gymIntermediateRoutineId, exerciseId = 13, sets = 4, reps = 8, durationSeconds = null, restSeconds = 90, order = 3)
        ))

        val gymAdvancedRoutineId = routineDao.insertRoutine(
            RoutineEntity(
                id = 6,
                name = "Jalón de potencia",
                description = "Sesión pesada de espalda y bíceps para ganar fuerza.",
                workoutType = "GYM",
                difficulty = "ADVANCED",
                durationMinutes = 75
            )
        )
        routineExerciseDao.insertAll(listOf(
            RoutineExerciseEntity(routineId = gymAdvancedRoutineId, exerciseId = 10, sets = 5, reps = 5, durationSeconds = null, restSeconds = 180, order = 1),
            RoutineExerciseEntity(routineId = gymAdvancedRoutineId, exerciseId = 11, sets = 4, reps = 10, durationSeconds = null, restSeconds = 90, order = 2),
            RoutineExerciseEntity(routineId = gymAdvancedRoutineId, exerciseId = 14, sets = 4, reps = 10, durationSeconds = null, restSeconds = 90, order = 3)
        ))
    }

    private suspend fun seedTestSession() {
        val session = WorkoutSessionEntity(
            id = 1,
            routineId = 1,
            startedAt = LocalDateTime.now().minusDays(1).toString(),
            completedAt = LocalDateTime.now().minusDays(1).plusMinutes(20).toString(),
            durationMinutes = 20,
            completed = true
        )
        sessionDao.insertSession(session)
    }
}