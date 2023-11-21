package ar.edu.itba.hci.fitcenter.components

import java.time.LocalDate
import java.time.format.DateTimeFormatter

fun formatDate(dateValue: Long): String {
    val date = LocalDate.parse(dateValue.toString(), DateTimeFormatter.BASIC_ISO_DATE)
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy") // You can adjust the pattern as needed
    return date.format(formatter)
}