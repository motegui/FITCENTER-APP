package ar.edu.itba.hci.fitcenter

import ar.edu.itba.hci.fitcenter.api.Models

object Placeholder {
    val emptyUser = Models.FullUser(
        id = 0,
        username = "",
        firstName = "",
        lastName = "",
        gender = Models.Gender.Other,
        birthdate = 0,
        email = "",
        phone = "",
        avatarUrl = "",
        metadata = null,
        date = 0,
        lastActivity = 0,
        verified = true
    )
}
