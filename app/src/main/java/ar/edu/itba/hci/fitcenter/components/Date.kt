package ar.edu.itba.hci.fitcenter.components

import java.text.DateFormat.getDateInstance

fun formatDate(unixTimestamp: Long): String {
    val sdf = getDateInstance()
    val date = java.util.Date(unixTimestamp * 1000)
    return sdf.format(date)
}
