package ar.edu.itba.hci.fitcenter

object ApiModels {
    enum class Gender(val value: String) { Male("male"), Female("female"), Other("other") }
    data class User (
        val username: String,
        val password: String,
        val firstName: String?,
        val lastName: String?,
        val gender: Gender?,
        val birthdate: Int?,
        val email: String,
        val phone: String?,
        val avatarUrl: String?,
        val metadata: Any?
    )

    data class FullUser (
        val id: Int,
        val username: String,
        val password: String,
        val firstName: String,
        val lastName: String,
        val gender: Gender,
        val birthdate: Int,
        val email: String,
        val phone: String,
        val avatarUrl: String,
        val metadata: Any,
        val date: Int,
        val lastActivity: Int,
        val verified: Boolean
    )

    data class PublicUser (
        val id: Int,
        val username: String,
        val gender: Gender,
        val avatarUrl: String,
        val date: Int,
        val lastActivity: Int
    )

    data class SearchResult<T> (
        val totalCount: Int,
        val orderBy: String,
        val direction: String,
        val content: List<T>,
        val size: Int,
        val page: Int,
        val isLastPage: Boolean
    )

    enum class Difficulty(val value: String) {
        Rookie("rookie"),
        Beginner("beginner"),
        Intermediate("intermediate"),
        Advanced("advanced"),
        Expert("expert")
    }
    data class FullRoutine(
        val id: Int,
        val name: String,
        val detail: List<Exercise>,
        val date: Int,
        val score: Int,
        val isPublic: Boolean,
        val difficulty: Difficulty,
        val category: FullCategory,
        val user: PublicUser,
        val metadata: Any
    )

    data class FullCategory (
        val id: Int,
        val name: String,
        val detail: String
    )

    data class Exercise(
        val id: Int,
        val title: String,
        val description: String,
        val type: String?,
        val equipment: List<String>,
        val image: String,
        val favorite: Boolean,
        val bodyArea: String?
    )
}
