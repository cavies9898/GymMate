package com.gymmate.app.core.utils

import com.gymmate.app.domain.model.WorkoutType

fun getRoutineIcon(name: String, workoutType: WorkoutType): String {
    val nameLower = name.lowercase()
    return when {
        nameLower.contains("mañana") || nameLower.contains("matutino") || nameLower.contains("arranque") -> "🌅"
        nameLower.contains("hiit") -> "⚡"
        nameLower.contains("quema") || nameLower.contains("burn") -> "🔥"
        nameLower.contains("cardio") -> "🫀"
        nameLower.contains("yoga") || nameLower.contains("flexibilidad") -> "🧘"
        nameLower.contains("core") || nameLower.contains("abdomen") -> "🎯"
        nameLower.contains("pecho") || nameLower.contains("press") || nameLower.contains("empuje") || nameLower.contains("push") -> "💪"
        nameLower.contains("espalda") || nameLower.contains("jalón") || nameLower.contains("pull") || nameLower.contains("potencia") -> "🏋️"
        nameLower.contains("pierna") || nameLower.contains("sentadilla") || nameLower.contains("leg") -> "🦵"
        nameLower.contains("hombro") || nameLower.contains("militar") || nameLower.contains("shoulder") -> "🔱"
        nameLower.contains("bícep") || nameLower.contains("brazo") || nameLower.contains("curl") -> "💪"
        nameLower.contains("starter") || nameLower.contains("primer") || nameLower.contains("inicio") -> "🏁"
        nameLower.contains("full") || nameLower.contains("completo") || nameLower.contains("total") -> "⚡"
        workoutType == WorkoutType.HOME -> "🏠"
        else -> "🏋️"
    }
}