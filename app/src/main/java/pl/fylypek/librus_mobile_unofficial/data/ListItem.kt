package pl.fylypek.librus_mobile_unofficial.data

sealed class ListItem {
    data class SemesterHeader(val title: String) : ListItem()
    data class SubjectItem(val subject: Subject) : ListItem()
}
