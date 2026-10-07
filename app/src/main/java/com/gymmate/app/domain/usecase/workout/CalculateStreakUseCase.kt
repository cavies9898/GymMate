package com.gymmate.app.domain.usecase.workout

import com.gymmate.app.domain.model.WorkoutSession
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class CalculateStreakUseCase @Inject constructor() {
    operator fun invoke(sessions: List<WorkoutSession>): Int {
        val completed = sessions.filter { it.completed }
        return calculateStreak(completed.map { it.startedAt })
    }

    private fun calculateStreak(dates: List<LocalDateTime>): Int {
        if (dates.isEmpty()) return 0
        val uniqueDays = dates.map { it.toLocalDate() }.toSortedSet().toList().sortedDescending()
        var streak = 0
        var expected = LocalDate.now()
        for (day in uniqueDays) {
            if (day == expected) {
                streak++
                expected = expected.minusDays(1)
            } else break
        }
        return streak
    }
}