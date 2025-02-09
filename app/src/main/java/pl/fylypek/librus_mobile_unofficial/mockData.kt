package pl.fylypek.librus_mobile_unofficial

val mockSemesters = listOf(
    Semester(
        name = "Semestr 1",
        subjects = listOf(
            Subject(
                name = "Matematyka",
                grades = listOf(5, 4, 3, 5)
            ),
            Subject(
                name = "Język polski",
                grades = listOf(4, 4, 5)
            )
        )
    ),
    Semester(
        name = "Semestr 2",
        subjects = listOf(
            Subject(
                name = "Fizyka",
                grades = listOf(3, 5, 3, 4)
            ),
            Subject(
                name = "Informatyka",
                grades = listOf(5, 5, 5)
            )
        )
    )
)
