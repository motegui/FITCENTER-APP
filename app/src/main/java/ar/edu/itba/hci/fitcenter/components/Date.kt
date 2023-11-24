package ar.edu.itba.hci.fitcenter.components

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    val date = Date(timestamp)
    return dateFormat.format(date)
}

fun main() {
    val timestamp = 1602646871112L
    val formattedDate = formatDate(timestamp)
    println("Timestamp $timestamp converted to date: $formattedDate")
}