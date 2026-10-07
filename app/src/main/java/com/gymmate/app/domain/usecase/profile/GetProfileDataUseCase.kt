package com.gymmate.app.domain.usecase.profile

import com.gymmate.app.domain.model.DifficultyLevel
import com.gymmate.app.domain.model.FitnessGoal
import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.repository.RoutineRepository
import com.gymmate.app.domain.repository.UserProfileRepository
import com.gymmate.app.domain.repository.WorkoutSessionRepository
import com.gymmate.app.domain.usecase.workout.CalculateStreakUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class ProfileData(
    val profile: UserProfile?,
    val totalSessions: Int,
    val activeStreak: Int,
    val totalMinutes: Int,
    val favoriteRoutineName: String?
)

class GetProfileDataUseCase @Inject constructor(
    private val userProfileRepository: UserProfileRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val routineRepository: RoutineRepository,
    private val calculateStreakUseCase: CalculateStreakUseCase
) {
    operator fun invoke(): Flow<ProfileData> =
        combine(
            userProfileRepository.getUserProfile(),
            workoutSessionRepository.getAllSessions(),
            routineRepository.getAllRoutines()
        ) { profile: UserProfile?, sessions: List<WorkoutSession>, routines: List<Routine> ->
            val completed = sessions.filter { it.completed }
            val totalMinutes = completed.sumOf { it.durationMinutes }
            val streak = calculateStreakUseCase(completed)

            val favoriteRoutineId = completed
                .groupingBy { it.routine.id }
                .eachCount()
                .maxByOrNull { it.value }?.key
            val favoriteRoutineName = routines.find { it.id == favoriteRoutineId }?.name

            ProfileData(
                profile = profile,
                totalSessions = completed.size,
                activeStreak = streak,
                totalMinutes = totalMinutes,
                favoriteRoutineName = favoriteRoutineName
            )
        }
}