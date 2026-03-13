package com.gymmate.app.domain.usecase.profile

import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    operator fun invoke(): Flow<UserProfile?> = repository.getUserProfile()
}