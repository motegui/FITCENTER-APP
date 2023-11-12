package ar.edu.itba.hci.fitcenter



object SampleData {
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
}
