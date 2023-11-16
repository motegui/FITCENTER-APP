package ar.edu.itba.hci.fitcenter.api

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject

/**
 * API models from the swagger docs
 * THE STRUCTURE OF THESE @Serializable data classES ARE DETERMINED BY THE API AND CANNOT BE CHANGED.
 * If you add new @Serializable data classes from the API, make sure they exactly reflect the documentation.
 * http://localhost:8080/docs/#/
 */
object Models {
    enum class Gender(val value: String) { Male("male"), Female("female"), Other("other") }
    enum class ExerciseType(val value: String) { Exercise("exercise"), Rest("rest") }
    @Serializable data class User (
        var username: String,
        var password: String,
        var firstName: String?,
        var lastName: String?,
        var gender: Gender?,
        var birthdate: Int?,
        var email: String,
        var phone: String?,
        var avatarUrl: String?,
        var metadata: JsonObject?
    )

    @Serializable data class FullUser (
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
        var metadata: JsonObject?,
        var date: Int,
        var lastActivity: Int,
        var verified: Boolean
    )

    @Serializable data class PublicUser (
        var id: Int,
        var username: String,
        var gender: Gender,
        var avatarUrl: String,
        var date: Int,
        var lastActivity: Int
    )

    @Serializable data class Credentials (
        var username: String,
        var password: String
    )

    @Serializable data class AuthenticationToken (
        var token: String
    )

    @Serializable data class SearchResult<T> (
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
    @Serializable data class FullRoutine (
        val id: Int,
        val name: String,
        val detail: String,
        val date: Int,
        val score: Int,
        val isPublic: Boolean,
        val difficulty: Difficulty,
        val category: FullCategory,
        val user: PublicUser,
        val metadata: Map<String, JsonElement>?
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

    @Serializable data class FullCategory (
        var id: Int,
        var name: String,
        var detail: String
    )

    @Serializable data class FullExercise (
        var id: String,
        var name: String,
        var detail: String,
        var type: ExerciseType,
        var duration: Int,
        var date: Int,
        var metadata: JsonObject?
    )
    @Serializable data class Exercise (
        var name: String,
        var detail: String,
        var type: ExerciseType,
        var metadata: JsonObject?
    )
}
