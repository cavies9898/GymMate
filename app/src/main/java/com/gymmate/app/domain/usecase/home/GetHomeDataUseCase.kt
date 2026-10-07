package com.gymmate.app.domain.usecase.home

import com.gymmate.app.domain.model.Routine
import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.model.WorkoutSession
import com.gymmate.app.domain.model.WorkoutType
import com.gymmate.app.domain.repository.RoutineRepository
import com.gymmate.app.domain.repository.UserProfileRepository
import com.gymmate.app.domain.repository.WorkoutSessionRepository
import com.gymmate.app.domain.usecase.workout.CalculateStreakUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class HomeData(
    val userName: String,
    val completedSessions: Int,
    val activeStreak: Int,
    val featuredRoutines: List<Routine>,
    val lastSession: WorkoutSession?
)

class GetHomeDataUseCase @Inject constructor(
    private val routineRepository: RoutineRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val userProfileRepository: UserProfileRepository,
    private val calculateStreakUseCase: CalculateStreakUseCase
) {
    operator fun invoke(selectedWorkoutType: WorkoutType): Flow<HomeData> =
        combine(
            routineRepository.getAllRoutines(),
            workoutSessionRepository.getAllSessions(),
            userProfileRepository.getUserProfile()
        ) { routines: List<Routine>, sessions: List<WorkoutSession>, profile: UserProfile? ->
            val completedSessions = sessions.filter { it.completed }
            val streak = calculateStreakUseCase(completedSessions)
            val lastSession = completedSessions.maxByOrNull { it.startedAt }
            val featured = routines.take(6)

            HomeData(
                userName = profile?.name ?: "Atleta",
                completedSessions = completedSessions.size,
                activeStreak = streak,
                featuredRoutines = featured,
                lastSession = lastSession
            )
        }
}