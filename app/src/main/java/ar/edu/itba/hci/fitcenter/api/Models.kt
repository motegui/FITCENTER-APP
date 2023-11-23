package ar.edu.itba.hci.fitcenter.api


import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
//import kotlinx.serialization.json.JsonPrimitive

/**
 * API models from the swagger docs
 * THE STRUCTURE OF THESE CLASSES ARE DETERMINED BY THE API AND CANNOT BE CHANGED.
 * If you add a property to a class's metadata, it must be done through getters and setters.
 * If you add new classes from the API, make sure they exactly reflect the documentation.
 * http://localhost:8080/docs/#/
 */
object Models {
    @Serializable data class ErrorMessage (
        val message: String
    )

    @Serializable data class Error (
        val code: Int,
        val description: String,
        val details: List<ErrorMessage>? = null
    )

    enum class Gender {
        male,
        female,
        other
    }
    enum class ExerciseType {
        exercise,
        rest
    }
    @Serializable class FullUser (
        val id: Long,
        val username: String,
        val firstName: String,
        val lastName: String,
        val gender: Gender?,
        val birthdate: Long?,
        val email: String,
        val phone: String?,
        val avatarUrl: String?,
        val metadata: JsonObject?,
        val date: Long,
        val lastActivity: Long,
        val verified: Boolean
    )

    @Serializable class PublicUser (
        val id: Long,
        val username: String,
        val gender: Gender,
        val avatarUrl: String,
        val date: Long,
        val lastActivity: Long
    )

    @Serializable class Credentials (
        val username: String,
        val password: String
    )

    @Serializable class AuthenticationToken (
        val token: String
    )

    enum class Difficulty {
        rookie,
        beginner,
        intermediate,
        advanced,
        expert
    }
    @Serializable open class FullRoutine (
        val id: Long,
        val name: String,
        val detail: String,
        val date: Long,
        val score: Int,
        val isPublic: Boolean,
        val difficulty: Difficulty,
        val category: FullCategory?,
        val user: PublicUser?,
        val metadata: JsonObject?
    ) {
        val isFavorite: Boolean
            get() {
                if (metadata == null) return false
                return metadata.contains("isFavorite")
            }

    }

//    private val exampleFullRoutine = FullRoutine(
//        id = 1234,
//        name = "bruh",
//        detail = "",
//        date = 0,
//        score = 0,
//        isPublic = false,
//        difficulty = Difficulty.Rookie,
//        category = FullCategory(id=1, name="ortg", detail=""),
//        user = PublicUser(id=2, username="garlic", gender=Gender.Male, avatarUrl="", date=0, lastActivity=0),
//        metadata = JsonObject(content = mapOf(
//            "isFavorite" to JsonPrimitive(true)
//        ))
//    )
//    private val isFavorite = exampleFullRoutine.isFavorite

//    @Serializable class FavoriteMetadata (
//        val isFavorite: Boolean
//    )

    @Serializable class FullCategory (
        val id: Long,
        val name: String,
        val detail: String
    )

    @Serializable class FullExercise (
        val id: Long,
        val name: String,
        val detail: String,
        val type: ExerciseType,
        val duration: Int,
        val date: Long,
        val metadata: JsonObject?
    )

    enum class CycleType {
        warmup,
        exercise,
        cooldown
    }
    @Serializable open class FullCycle (
        val id: Long,
        val name: String,
        val detail: String,
        val type: CycleType,
        val order: Int,
        val repetitions: Long,
        val metadata: JsonObject?
    )

    @Serializable open class Cycles (
        val totalCount: Long,
        val orderBy: String,
        val direction: String,
        val content: List<FullCycle>,
        val size: Long,
        val page: Long,
        val isLastPage: Boolean
    )

    @Serializable class FullCycleExercise (
        val order: Int,
        val duration: Int,
        val repetitions: Int,
        val exercise: FullExercise
    )

    @Serializable enum class Direction {
        asc,
        desc

    }
    @Serializable class SearchResult<T> (
        val totalCount: Int,
        val orderBy: String,
        val direction: Direction,
        val content: List<T>,
        val size: Int,
        val page: Int,
        val isLastPage: Boolean
    )

    // Internal Cycle object that also holds its respective cycleExercises
    class MegaCycle constructor(
        cycle: FullCycle,
        val cycleExercises: List<FullCycleExercise>
    ): FullCycle(
        cycle.id, cycle.name, cycle.detail, cycle.type, cycle.order, cycle.repetitions,
        cycle.metadata
    ) {
        // https://www.reddit.com/r/Kotlin/comments/lx99rv/asynchronous_initialisation/gpnz2yz/
        companion object {
            suspend operator fun invoke(store: Store, cycle: FullCycle): MegaCycle {
                val cycleExercises = store.fetchCycleExercises(cycle.id)
                return MegaCycle(cycle, cycleExercises)
            }
        }
    }

    // Internal Routine object that also holds its respective cycles and exercises
    class MegaRoutine constructor(
        routine: FullRoutine,
        val megaCycles: List<MegaCycle>
    ) : FullRoutine(routine.id, routine.name, routine.detail, routine.date, routine.score,
        routine.isPublic, routine.difficulty, routine.category, routine.user, routine.metadata
    ) {
        companion object {
            suspend operator fun invoke(store: Store, routine: FullRoutine): MegaRoutine {
                val cycles = store.fetchCycles(routine.id)
                val megaCycles = cycles.map { cycle -> MegaCycle(store, cycle) }
                return MegaRoutine(routine, megaCycles)
            }
        }
    }
}
