package ar.edu.itba.hci.fitcenter

import ar.edu.itba.hci.fitcenter.api.Models

object RoutineSampleData {
    // Sample routine data

    val sportsRoutines: List<Models.FullRoutine> = listOf(

        Models.FullRoutine(
                        id = 11,
                        name = "Morning Jog",
                        detail = "A light jog to start the day",
                        date = System.currentTimeMillis(),
                        score = 4,
                        isPublic = true,
                        difficulty = Models.Difficulty.beginner,
                        category = Models.FullCategory(
                            id = 101,
                            name = "Running",
                            detail = "Outdoor"
                        ),
                        user = Models.PublicUser(
                            id = 123,
                            username = "runner123",
                            gender = Models.Gender.male,
                            avatarUrl = "https://example.com/avatar/runner123.jpg",
                            date = System.currentTimeMillis(),
                            lastActivity = System.currentTimeMillis()
                        ),
                        metadata = Models.RoutineMetadata(
                            favorite = true,
                            equipment = listOf("Running Shoes"),
                            category = "Outdoor"
                        )
                    ),
        Models.FullRoutine(
                        id = 12,
                        name = "Bodyweight Workout",
                        detail = "No equipment needed bodyweight exercises",
                        date = System.currentTimeMillis(),
                        score = 5,
                        isPublic = true,
                        difficulty = Models.Difficulty.intermediate,
                        category = Models.FullCategory(
                            id = 102,
                            name = "Bodyweight",
                            detail = "Indoor"
                        ),
                        user = Models.PublicUser(
                            id = 456,
                            username = "fitfanatic",
                            gender = Models.Gender.female,
                            avatarUrl = "https://example.com/avatar/fitfanatic.jpg",
                            date = System.currentTimeMillis(),
                            lastActivity = System.currentTimeMillis()
                        ),
                        metadata = Models.RoutineMetadata(
                            favorite = false,
                            equipment = emptyList(),
                            category = "Indoor"
                        )
                    ),

        Models.FullRoutine(
            id = 1,
            name = "Beginner Full Body",
            detail = "The perfect workout to discover working out",
            date = 20231114,
            score = 5,
            isPublic = true,
            difficulty = Models.Difficulty.beginner,
            category = Models.FullCategory(3, "Full Body", "Target all major muscle groups"),
            user = Models.PublicUser(
                1,
                "fitstarter",
                Models.Gender.other,
                "avatar_url_4",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = emptyList(),
                category = "Strength"
            )
        ),
        Models.FullRoutine(
            id = 2,
            name = "Cardio Blast",
            detail = "No description provided",
            date = 20231115,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.intermediate,
            category = Models.FullCategory(5, "Cardio", "Boost your cardiovascular endurance"),
            user = Models.PublicUser(
                2,
                "cardiokid",
                Models.Gender.male,
                "avatar_url_5",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = emptyList(),
                category = "Cardio"
            )
        ),
        Models.FullRoutine(
            id = 3,
            name = "Legs of Steel",
            detail = "No description provided",
            date = 20231116,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.intermediate,
            category = Models.FullCategory(4, "Legs", "Focus on leg strength and endurance"),
            user = Models.PublicUser(
                3,
                "legdaylover",
                Models.Gender.female,
                "avatar_url_6",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = listOf("Mat"),
                category = "Strength"
            )
        ),
        Models.FullRoutine(
            id = 4,
            name = "Upper Body Burnout",
            detail = "No description provided",
            date = 20231117,
            score = 3,
            isPublic = true,
            difficulty = Models.Difficulty.advanced,
            category = Models.FullCategory(2, "Upper Body", "Target your chest, arms, and back"),
            user = Models.PublicUser(
                4,
                "armdaywarrior",
                Models.Gender.male,
                "avatar_url_7",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = true,
                equipment = listOf("Dumbells"),
                category = "Strength"
            )
        ),
        Models.FullRoutine(
            id = 5,
            name = "Quick Flexibility Flow",
            detail = "No description provided",
            date = 20231118,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.beginner,
            category = Models.FullCategory(6, "Flexibility", "Improve your range of motion"),
            user = Models.PublicUser(
                5,
                "flexibleflow",
                Models.Gender.female,
                "avatar_url_8",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = listOf("Mat"),
                category = "Flexibility"
            )
        ),
        Models.FullRoutine(
            id = 6,
            name = "Stress Relief Yoga",
            detail = "Relaxing yoga and streches to feel better at the end of a long day",
            date = 20231119,
            score = 5,
            isPublic = true,
            difficulty = Models.Difficulty.beginner,
            category = Models.FullCategory(7, "Yoga", "Relax your mind and body"),
            user = Models.PublicUser(
                6,
                "yogalover",
                Models.Gender.female,
                "avatar_url_9",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = emptyList(),
                category = "Flexibility"
            )
        ),
        Models.FullRoutine(
            id = 7,
            name = "Balance and Core Stability",
            detail = "No description provided",
            date = 20231120,
            score = 3,
            isPublic = true,
            difficulty = Models.Difficulty.intermediate,
            category = Models.FullCategory(1, "Core", "Strengthen your core muscles"),
            user = Models.PublicUser(
                7,
                "corechamp",
                Models.Gender.male,
                "avatar_url_10",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = emptyList(),
                category = "Strength"
            )
        ),
        Models.FullRoutine(
            id = 8,
            name = "Quick Cardio Circuit",
            detail = "No description provided",
            date = 20231121,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.intermediate,
            category = Models.FullCategory(5, "Cardio", "Boost your heart rate"),
            user = Models.PublicUser(
                8,
                "cardiobeast",
                Models.Gender.male,
                "avatar_url_11",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(category = "Cardio")
        ),
        Models.FullRoutine(
            id = 9,
            name = "Quick Office Stretch",
            detail = "No description provided",
            date = 20231122,
            score = 3,
            isPublic = true,
            difficulty = Models.Difficulty.beginner,
            category = Models.FullCategory(8, "Stretching", "Relieve tension from desk work"),
            user = Models.PublicUser(
                9,
                "deskstretcher",
                Models.Gender.female,
                "avatar_url_12",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = emptyList(),
                category = "Flexibility"
            )
        ),
        Models.FullRoutine(
            id = 10,
            name = "Quick Strength Boost",
            detail = "No description provided",
            date = 20231123,
            score = 2,
            isPublic = true,
            difficulty = Models.Difficulty.beginner,
            category = Models.FullCategory(2, "Strength", "Build overall body strength"),
            user = Models.PublicUser(
                10,
                "strengthstarter",
                Models.Gender.male,
                "avatar_url_13",
                20231114,
                20231115
            ),
            metadata = Models.RoutineMetadata(
                favorite = false,
                equipment = emptyList(),
                category = "Strength"
            )
        )
    )

    val cyclesRoutine = Models.Cycles(
        totalCount = 4,  // Aquí debes proporcionar el valor adecuado para totalCount
        orderBy = "id",  // Proporciona el valor correcto para orderBy
        direction = "asc",  // Proporciona el valor correcto para direction
        content = listOf(
            Models.FullCycle(
                id = 1,
                name = "Warm Up",
                detail = "Details for Cycle 1",
                type = Models.CycleType.warmup,  // Ajusta según tu enumeración CycleType
                order = 1,
                repetitions = 5,
                metadata = null  // Puedes proporcionar un objeto JsonObject aquí si es necesario
            ),
            Models.FullCycle(
                id = 2,
                name = "Cycle 1",
                detail = "Details for Cycle 1",
                type = Models.CycleType.exercise,  // Ajusta según tu enumeración CycleType
                order = 2,
                repetitions = 1,
                metadata = null  // Puedes proporcionar un objeto JsonObject aquí si es necesario
            ),
            Models.FullCycle(
                id = 3,
                name = "Cycle 2",
                detail = "Details for Cycle 2",
                type = Models.CycleType.exercise,  // Ajusta según tu enumeración CycleType
                order = 3,
                repetitions = 3,
                metadata = null  // Puedes proporcionar un objeto JsonObject aquí si es necesario
            ),
            Models.FullCycle(
                id = 4,
                name = "Cool Down",
                detail = "Details for Cycle 1",
                type = Models.CycleType.cooldown,  // Ajusta según tu enumeración CycleType
                order = 4,
                repetitions = 1,
                metadata = null  // Puedes proporcionar un objeto JsonObject aquí si es necesario
            ),
        ),
        size = 20,  // Proporciona el valor correcto para size
        page = 1,   // Proporciona el valor correcto para page
        isLastPage = false  // Proporciona el valor correcto para isLastPage
    )

    val cylceInfo: List<Models.FullCycleExercise> = listOf(
        Models.FullCycleExercise(
            order = 1,
            duration = 30,
            repetitions = 1,
            exercise = Models.FullExercise(
                id = 1,
                name = "Burpees",
                detail = "",
                type = Models.ExerciseType.exercise,
                duration = 30,
                date = 20231114,
                metadata = null
            )
        ),

        Models.FullCycleExercise(
            order = 2,
            duration = 0,
            repetitions = 5,
            exercise = Models.FullExercise(
                id = 3,
                name = "Jump",
                detail = "",
                type = Models.ExerciseType.exercise,
                duration = 0,
                date = 20231114,
                metadata = null
            )
        ),

        Models.FullCycleExercise(
            order = 3,
            duration = 30,
            repetitions = 0,
            exercise = Models.FullExercise(
                id = 4,
                name = "Break",
                detail = "",
                type = Models.ExerciseType.rest,
                duration = 30,
                date = 20231114,
                metadata = null
            )
        ),

        Models.FullCycleExercise(
            order = 4,
            duration = 30,
            repetitions = 6,
            exercise = Models.FullExercise(
                id = 4,
                name = "Crunches",
                detail = "",
                type = Models.ExerciseType.exercise,
                duration = 30,
                date = 20231114,
                metadata = null
            )
        ),

    )
}
