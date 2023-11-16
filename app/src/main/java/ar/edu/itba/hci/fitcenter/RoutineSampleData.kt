package ar.edu.itba.hci.fitcenter

import ar.edu.itba.hci.fitcenter.api.Models

object RoutineSampleData {
    // Sample routine data

    val sportsRoutines: List<Models.FullRoutine> = listOf(
        Models.FullRoutine(
            id = 1,
            name = "Beginner Full Body",
            detail = "The perfect workout to discover working out",
            date = 20231114,
            score = 5,
            isPublic = true,
            difficulty = Models.Difficulty.Beginner,
            category = Models.FullCategory(3, "Full Body", "Target all major muscle groups"),
            user = Models.PublicUser(
                1,
                "fitstarter",
                Models.Gender.Other,
                "avatar_url_4",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 2,
            name = "Cardio Blast",
            detail = "No description provided",
            date = 20231115,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.Intermediate,
            category = Models.FullCategory(5, "Cardio", "Boost your cardiovascular endurance"),
            user = Models.PublicUser(
                2,
                "cardiokid",
                Models.Gender.Male,
                "avatar_url_5",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 3,
            name = "Legs of Steel",
            detail = "No description provided",
            date = 20231116,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.Intermediate,
            category = Models.FullCategory(4, "Legs", "Focus on leg strength and endurance"),
            user = Models.PublicUser(
                3,
                "legdaylover",
                Models.Gender.Female,
                "avatar_url_6",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 4,
            name = "Upper Body Burnout",
            detail = "No description provided",
            date = 20231117,
            score = 3,
            isPublic = true,
            difficulty = Models.Difficulty.Advanced,
            category = Models.FullCategory(2, "Upper Body", "Target your chest, arms, and back"),
            user = Models.PublicUser(
                4,
                "armdaywarrior",
                Models.Gender.Male,
                "avatar_url_7",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 5,
            name = "Quick Flexibility Flow",
            detail = "No description provided",
            date = 20231118,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.Beginner,
            category = Models.FullCategory(6, "Flexibility", "Improve your range of motion"),
            user = Models.PublicUser(
                5,
                "flexibleflow",
                Models.Gender.Female,
                "avatar_url_8",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 6,
            name = "Stress Relief Yoga",
            detail = "Relaxing yoga and streches to feel better at the end of a long day",
            date = 20231119,
            score = 5,
            isPublic = true,
            difficulty = Models.Difficulty.Beginner,
            category = Models.FullCategory(7, "Yoga", "Relax your mind and body"),
            user = Models.PublicUser(
                6,
                "yogalover",
                Models.Gender.Female,
                "avatar_url_9",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 7,
            name = "Balance and Core Stability",
            detail = "No description provided",
            date = 20231120,
            score = 3,
            isPublic = true,
            difficulty = Models.Difficulty.Intermediate,
            category = Models.FullCategory(1, "Core", "Strengthen your core muscles"),
            user = Models.PublicUser(
                7,
                "corechamp",
                Models.Gender.Male,
                "avatar_url_10",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 8,
            name = "Quick Cardio Circuit",
            detail = "No description provided",
            date = 20231121,
            score = 4,
            isPublic = true,
            difficulty = Models.Difficulty.Intermediate,
            category = Models.FullCategory(5, "Cardio", "Boost your heart rate"),
            user = Models.PublicUser(
                8,
                "cardiobeast",
                Models.Gender.Male,
                "avatar_url_11",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 9,
            name = "Quick Office Stretch",
            detail = "No description provided",
            date = 20231122,
            score = 3,
            isPublic = true,
            difficulty = Models.Difficulty.Beginner,
            category = Models.FullCategory(8, "Stretching", "Relieve tension from desk work"),
            user = Models.PublicUser(
                9,
                "deskstretcher",
                Models.Gender.Female,
                "avatar_url_12",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        Models.FullRoutine(
            id = 10,
            name = "Quick Strength Boost",
            detail = "No description provided",
            date = 20231123,
            score = 2,
            isPublic = true,
            difficulty = Models.Difficulty.Beginner,
            category = Models.FullCategory(2, "Strength", "Build overall body strength"),
            user = Models.PublicUser(
                10,
                "strengthstarter",
                Models.Gender.Male,
                "avatar_url_13",
                20231114,
                20231115
            ),
            metadata = Any()
        )
    )
}