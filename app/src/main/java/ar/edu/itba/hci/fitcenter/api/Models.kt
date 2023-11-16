package ar.edu.itba.hci.fitcenter.api

/**
 * API Models from the API's swagger documentation
 * THE STRUCTURE OF THESE OBJECTS CANNOT BE CHANGED.
 * If you add new objects, make sure they reflect exactly what the documentation says they will be.
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
    )

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
