package com.gymmate.app.domain.usecase.profile

import com.gymmate.app.domain.model.UserProfile
import com.gymmate.app.domain.repository.UserProfileRepository
import javax.inject.Inject

class SaveUserProfileUseCase @Inject constructor(
    private val repository: UserProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile) =
        repository.saveUserProfile(profile)
}