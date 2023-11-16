package ar.edu.itba.hci.fitcenter.api

/**
 * API models from the swagger docs
 * THE STRUCTURE OF THESE @Serializable data classES ARE DETERMINED BY THE API AND CANNOT BE CHANGED.
 * If you add new @Serializable data classes from the API, make sure they exactly reflect the documentation.
 * http://localhost:8080/docs/#/
 */
object Models {
    enum class Gender(val value: String) { Male("male"), Female("female"), Other("other") }
    enum class ExerciseType(val value: String){Exercise("exercise"), Rest("rest")}
    data class User (
        var username: String,
        var password: String,
        var firstName: String?,
        var lastName: String?,
        var gender: Gender?,
        var birthdate: Int?,
        var email: String,
        var phone: String?,
        var avatarUrl: String?,
        var metadata: Any?
    )

    data class FullUser (
        var id: Int,
        var username: String,
        var password: String,
        var firstName: String,
        var lastName: String,
        var gender: Gender,
        var birthdate: Int,
        var email: String,
        var phone: String,
        var avatarUrl: String,
        var metadata: Any,
        var date: Int,
        var lastActivity: Int,
        var verified: Boolean
    )

    data class PublicUser (
        var id: Int,
        var username: String,
        var gender: Gender,
        var avatarUrl: String,
        var date: Int,
        var lastActivity: Int
    )

    data class Credentials (
        var username: String,
        var password: String
    )

    data class AuthenticationToken (
        var token: String
    )

    data class SearchResult<T> (
        var totalCount: Int,
        var orderBy: String,
        var direction: String,
        var content: List<T>,
        var size: Int,
        var page: Int,
        var isLastPage: Boolean
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
        val detail: String,
        val date: Int,
        val score: Int,
        val isPublic: Boolean,
        val difficulty: Difficulty,
        val category: FullCategory,
        val user: PublicUser,
        val metadata: Any
    ){
        val isFavorite: Boolean
            get() {
                if (metadata is FavoriteMetadata) {
                    return metadata.isFavorite
                }
                return false
            }
    }

    data class FavoriteMetadata(val isFavorite: Boolean)

    data class FullCategory (
        var id: Int,
        var name: String,
        var detail: String
    )

    data class FullExercise(
        val id: String,
        val name: String,
        val detail: String,
        val type: ExerciseType,
        val duration: Int,
        val date: Int,
        val metadata: Any?
    )
}
