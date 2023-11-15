package ar.edu.itba.hci.fitcenter



object SampleData {
    //Sample Exercise data

    val initialExercises: List<ApiModels.Exercise> = listOf(
        ApiModels.Exercise(
            id = 1,
            title = "Break",
            description = "Take a break!",
            type = null,
            equipment = emptyList(),
            image = "/res/drawable/break.jpg",
            favorite = false,
            bodyArea = null,
        ),
        ApiModels.Exercise(
            id = 1234567890,
            title = "Burpees",
            description = "Burpees are a full-body exercise that combines a squat, push-up, and jump. Begin standing, squat down, kick feet back, perform a push-up, return to squat, and explosively jump up.",
            type = "Cardio",
            equipment = listOf("Exercise Mat"),
            image = "/res/drawable/burpees.jpg",
            favorite = false,
            bodyArea = "Legs",
        ),
        ApiModels.Exercise(
            id = 1234567891,
            title = "Crunches",
            description = "Crunches are an abdominal exercise that involves lying on your back, bending your knees, and lifting your upper body off the ground. They target your core muscles.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/crunches.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567892,
            title = "Squats",
            description = "Squats are a lower-body exercise that involves bending your knees and hips while keeping your back straight. They target your quadriceps, hamstrings, and glutes.",
            type = "Strength",
            equipment = listOf("Barbell"),
            image = "/res/drawable/squats.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567893,
            title = "Push-ups",
            description = "Push-ups are a bodyweight exercise that targets your chest, shoulders, and triceps. Start in a plank position, then lower and raise your body by bending your elbows.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/pushups.jpg",
            favorite = false,
            bodyArea = "Chest"
        ),
        ApiModels.Exercise(
            id = 1234567894,
            title = "Lunges",
            description = "Lunges are a lower-body exercise that involves stepping forward and bending both knees to create a lunge position. They target your quadriceps and glutes.",
            type = "Strength",
            equipment = listOf("Dumbbells"),
            image = "/res/drawable/lunges.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567895,
            title = "Plank",
            description = "The plank is a core exercise that involves holding a push-up position with your body in a straight line. It targets your core muscles.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/plank.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567896,
            title = "Jumping Jacks",
            description = "Jumping jacks are a cardio exercise that involves jumping and spreading your legs and arms apart, then returning to the starting position. They elevate your heart rate.",
            type = "Cardio",
            equipment = emptyList(),
            image = "/res/drawable/jumping.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567897,
            title = "Mountain Climbers",
            description = "Mountain climbers are a cardio exercise that simulates climbing. Start in a push-up position and alternate bringing your knees toward your chest.",
            type = "Cardio",
            equipment = emptyList(),
            image = "/res/drawable/mountains.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567898,
            title = "Bicycle Crunches",
            description = "Bicycle crunches engage your core as you lie on your back, lift your legs, and mimic pedaling a bicycle while twisting to touch your right elbow to your left knee and vice versa.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/bicycle_crunches.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567899,
            title = "Dumbbell Squats",
            description = "Dumbbell squats are a variation of squats that involve using dumbbells. Hold a dumbbell in each hand at your sides while performing squats to increase resistance.",
            type = "Strength",
            equipment = listOf("Dumbbells"),
            image = "/res/drawable/dumbbell_squats.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567900,
            title = "Push-up Variations",
            description = "Push-ups have many variations, such as diamond push-ups, tricep push-ups, and wide push-ups. Each variation focuses on different muscles and provides a unique challenge.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/pushup_variations.jpg",
            favorite = false,
            bodyArea = "Chest"
        ),
        ApiModels.Exercise(
            id = 1234567901,
            title = "Jump Squats",
            description = "Jump squats are an advanced version of squats. Start with a squat and then perform an explosive jump from the squat position. Land softly and repeat.",
            type = "Plyometrics",
            equipment = emptyList(),
            image = "/res/drawable/jump_squats.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567902,
            title = "Plank with Leg Lift",
            description = "Plank with leg lift is an advanced core exercise. Perform a standard plank and alternate lifting one leg while keeping your back straight.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/plank_leg_lift.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567903,
            title = "High Knees",
            description = "High knees are a high-intensity cardiovascular exercise that involves running in place while lifting your knees toward your chest with each step.",
            type = "Cardio",
            equipment = emptyList(),
            image = "/res/drawable/high_knees.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567904,
            title = "Box Jumps",
            description = "Box jumps are a plyometric exercise that involves jumping onto a box or platform. This exercise improves leg strength and power.",
            type = "Plyometrics",
            equipment = emptyList(),
            image = "/res/drawable/box_jumps.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567905,
            title = "Russian Twists",
            description = "Russian twists are a core exercise where you sit on the floor, lift your legs, and twist your torso from side to side, reaching to touch the ground with your hands.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/russian_twists.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567906,
            title = "Plank with Arm Raise",
            description = "The plank with arm raise is a core exercise that begins in a standard plank position. Lift one arm at a time while keeping your body stable and parallel to the ground.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/plank_arm_raise.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567907,
            title = "Side Plank",
            description = "The side plank is a core exercise that involves lying on your side with your elbow directly under your shoulder. Lift your hips and maintain a straight line from head to heels.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/side_plank.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567908,
            title = "Wall Sits",
            description = "Wall sits are a leg exercise that require you to lean against a wall with your knees bent at a 90-degree angle. Hold this position for as long as possible to build leg strength.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/wall_sits.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567909,
            title = "Superman",
            description = "The Superman exercise is performed lying face down with your arms and legs extended. Lift your arms, chest, and legs off the ground, squeezing your lower back and glutes.",
            type = "Strength",
            equipment = emptyList(),
            image = "/res/drawable/superman.jpg",
            favorite = false,
            bodyArea = "Back"
        ),
        ApiModels.Exercise(
            id = 1234567911,
            title = "Static Stretching",
            description = "Static stretching involves holding a stretch position for a period of time without moving. Perform static stretches for various upper body muscle groups, such as neck muscles, shoulders, and back.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/static_stretching.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567912,
            title = "Child's Pose",
            description = "Child's pose is a yoga stretch that relaxes and stretches your lower back, hips, and thighs. Kneel with your big toes touching and sit back on your heels, extending your arms forward.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/childs_pose.jpg",
            favorite = false,
            bodyArea = "Back"
        ),
        ApiModels.Exercise(
            id = 1234567913,
            title = "Seated Forward Bend",
            description = "The seated forward bend is a yoga stretch that targets the hamstrings and lower back. Sit with your legs extended, and reach for your toes while keeping your back straight.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/seated_forward_bend.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567914,
            title = "Cobra Stretch",
            description = "The cobra stretch is a yoga pose that opens the chest and stretches the abs. Lie on your stomach, hands under shoulders, and lift your upper body while keeping your pelvis down.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/cobra_stretch.jpg",
            favorite = false,
            bodyArea = "Chest"
        ),
        ApiModels.Exercise(
            id = 1234567915,
            title = "Triceps Stretch",
            description = "The triceps stretch helps relieve tension in the triceps muscles. Raise one arm overhead and bend your elbow, reaching down your back with your opposite hand to gently pull on your bent elbow.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/triceps_stretch.jpg",
            favorite = false,
            bodyArea = "Arms"
        ),
        ApiModels.Exercise(
            id = 1234567916,
            title = "Neck Rolls",
            description = "Neck rolls help release tension in the neck and shoulders. Gently roll your neck in a circular motion, both clockwise and counterclockwise.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/neck_rolls.jpg",
            favorite = false,
            bodyArea = null
        ),
        ApiModels.Exercise(
            id = 1234567917,
            title = "Hip Flexor Stretch",
            description = "The hip flexor stretch helps relieve tension in the hip flexor muscles. Kneel on one knee and push your hips forward, feeling the stretch in your hip area.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/hip_flexor_stretch.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567918,
            title = "Butterfly Stretch",
            description = "The butterfly stretch is a seated stretch that opens up the groin and inner thighs. Sit with the soles of your feet together and gently push your knees toward the ground.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/butterfly_stretch.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567919,
            title = "Ankle Circles",
            description = "Ankle circles help improve ankle mobility and flexibility. Lift one foot off the ground and rotate your ankle in circular motions, both clockwise and counterclockwise.",
            type = "Flexibility",
            equipment = emptyList(),
            image = "/res/drawable/ankle_circles.jpg",
            favorite = false,
            bodyArea = "Legs"
        ),
        ApiModels.Exercise(
            id = 1234567920,
            title = "Deep Breathing",
            description = "Deep breathing exercises can help calm your body and reduce stress. Inhale deeply through your nose, hold your breath for a few seconds, and exhale slowly through your mouth. Repeat several times.",
            type = "Balance",
            equipment = emptyList(),
            image = "/res/drawable/deep_breathing.jpg",
            favorite = false,
            bodyArea = null,
        ),
        )

    // Sample routine data

    val sportsRoutines: List<ApiModels.FullRoutine> = listOf(
        ApiModels.FullRoutine(
            id = 1,
            name = "Beginner Full Body",
            detail = listOf(
                initialExercises[1], // Burpees
                initialExercises[2], // Crunches
                initialExercises[3], // Squats
                initialExercises[4]  // Push-ups
            ),
            date = 20231114,
            score = 80,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Beginner,
            category = ApiModels.FullCategory(3, "Full Body", "Target all major muscle groups"),
            user = ApiModels.PublicUser(
                1,
                "fitstarter",
                ApiModels.Gender.Other,
                "avatar_url_4",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 2,
            name = "Cardio Blast",
            detail = listOf(
                initialExercises[6], // Plank
                initialExercises[7], // Jumping Jacks
                initialExercises[8], // Mountain Climbers
                initialExercises[17] // High Knees
            ),
            date = 20231115,
            score = 90,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Intermediate,
            category = ApiModels.FullCategory(5, "Cardio", "Boost your cardiovascular endurance"),
            user = ApiModels.PublicUser(
                2,
                "cardiokid",
                ApiModels.Gender.Male,
                "avatar_url_5",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 3,
            name = "Legs of Steel",
            detail = listOf(
                initialExercises[12], // Lunges
                initialExercises[9],  // Dumbbell Squats
                initialExercises[15], // Box Jumps
                initialExercises[8]   // Wall Sits
            ),
            date = 20231116,
            score = 85,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Intermediate,
            category = ApiModels.FullCategory(4, "Legs", "Focus on leg strength and endurance"),
            user = ApiModels.PublicUser(
                3,
                "legdaylover",
                ApiModels.Gender.Female,
                "avatar_url_6",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 4,
            name = "Upper Body Burnout",
            detail = listOf(
                initialExercises[4],  // Push-ups
                initialExercises[15], // Triceps Stretch
                initialExercises[14], // Cobra Stretch
                initialExercises[16]  // Plank with Arm Raise
            ),
            date = 20231117,
            score = 88,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Advanced,
            category = ApiModels.FullCategory(2, "Upper Body", "Target your chest, arms, and back"),
            user = ApiModels.PublicUser(
                4,
                "armdaywarrior",
                ApiModels.Gender.Male,
                "avatar_url_7",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 5,
            name = "Quick Flexibility Flow",
            detail = listOf(
                initialExercises[18], // Static Stretching
                initialExercises[20], // Child's Pose
                initialExercises[21], // Seated Forward Bend
                initialExercises[14]  // Cobra Stretch
            ),
            date = 20231118,
            score = 86,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Beginner,
            category = ApiModels.FullCategory(6, "Flexibility", "Improve your range of motion"),
            user = ApiModels.PublicUser(
                5,
                "flexibleflow",
                ApiModels.Gender.Female,
                "avatar_url_8",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 6,
            name = "Stress Relief Yoga",
            detail = listOf(
                initialExercises[20], // Deep Breathing
                initialExercises[21], // Child's Pose
                initialExercises[14], // Cobra Stretch
                initialExercises[16]  // Neck Rolls
            ),
            date = 20231119,
            score = 92,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Beginner,
            category = ApiModels.FullCategory(7, "Yoga", "Relax your mind and body"),
            user = ApiModels.PublicUser(
                6,
                "yogalover",
                ApiModels.Gender.Female,
                "avatar_url_9",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 7,
            name = "Balance and Core Stability",
            detail = listOf(
                initialExercises[16], // Plank with Leg Lift
                initialExercises[17], // Side Plank
                initialExercises[8],  // Wall Sits
                initialExercises[18]  // Superman
            ),
            date = 20231120,
            score = 87,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Intermediate,
            category = ApiModels.FullCategory(1, "Core", "Strengthen your core muscles"),
            user = ApiModels.PublicUser(
                7,
                "corechamp",
                ApiModels.Gender.Male,
                "avatar_url_10",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 8,
            name = "Quick Cardio Circuit",
            detail = listOf(
                initialExercises[6],  // Jumping Jacks
                initialExercises[16], // High Knees
                initialExercises[7],  // Mountain Climbers
                initialExercises[21]  // Jump Squats
            ),
            date = 20231121,
            score = 89,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Intermediate,
            category = ApiModels.FullCategory(5, "Cardio", "Boost your heart rate"),
            user = ApiModels.PublicUser(
                8,
                "cardiobeast",
                ApiModels.Gender.Male,
                "avatar_url_11",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 9,
            name = "Quick Office Stretch",
            detail = listOf(
                initialExercises[16], // Neck Rolls
                initialExercises[19], // Ankle Circles
                initialExercises[18], // Static Stretching
                initialExercises[0]   // Break
            ),
            date = 20231122,
            score = 84,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Beginner,
            category = ApiModels.FullCategory(8, "Stretching", "Relieve tension from desk work"),
            user = ApiModels.PublicUser(
                9,
                "deskstretcher",
                ApiModels.Gender.Female,
                "avatar_url_12",
                20231114,
                20231115
            ),
            metadata = Any()
        ),
        ApiModels.FullRoutine(
            id = 10,
            name = "Quick Strength Boost",
            detail = listOf(
                initialExercises[4],  // Push-ups
                initialExercises[3],  // Squats
                initialExercises[12], // Lunges
                initialExercises[0]   // Break
            ),
            date = 20231123,
            score = 86,
            isPublic = true,
            difficulty = ApiModels.Difficulty.Beginner,
            category = ApiModels.FullCategory(2, "Strength", "Build overall body strength"),
            user = ApiModels.PublicUser(
                10,
                "strengthstarter",
                ApiModels.Gender.Male,
                "avatar_url_13",
                20231114,
                20231115
            ),
            metadata = Any()
        )
    )

}



    /*
    // Sample conversation data
    val conversationSample = listOf(
        ComposeTutorial.Message(
            "Lexi",
            "Test...Test...Test..."
        ),
        ComposeTutorial.Message(
            "Lexi",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        ComposeTutorial.Message(
            "Lexi",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Searching for alternatives to XML layouts..."
        ),
        ComposeTutorial.Message(
            "Lexi",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim()
        ),
        ComposeTutorial.Message(
            "Lexi",
            "It's available from API 21+ :)"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Android Studio next version's name is Arctic Fox"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Previews are also interactive after enabling the experimental setting"
        ),
        ComposeTutorial.Message(
            "Lexi",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
    */

