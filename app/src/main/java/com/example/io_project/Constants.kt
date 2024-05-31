package com.example.io_project

object Constants {
    //App
    const val TAG = "AppTag"

    //Collection References
    const val USERS = "users"

    //User fields
    const val DISPLAY_NAME = "displayName"
    const val EMAIL = "email"
    const val PHOTO_URL = "photoUrl"
    const val CREATED_AT = "createdAt"

    //Names
    const val SIGN_IN_REQUEST = "signInRequest"
    const val SIGN_UP_REQUEST = "signUpRequest"

    //Buttons
    const val SIGN_IN_WITH_GOOGLE = "Sign in with Google"
    const val SIGN_OUT = "Sign-out"
    const val REVOKE_ACCESS = "Revoke Access"

    //Screens
    const val AUTH_SCREEN = "auth_screen"
    const val ADD_ACTIVITY_DIALOG = "add_activity_dialog"
    const val ADD_GROUP_DIALOG = "add_group_dialog"
    const val ADD_FRIEND_DIALOG = "add_friend_dialog"
    const val PROFILE_SCREEN = "profile_screen"
    const val GOALS_SCREEN = "goals_screen"
    const val TASKS_SCREEN = "tasks_screen"
    const val SOCIAL_SCREEN = "social_screen"
    const val GROUP_SCREEN = "group_screen"
    const val HOME_SCREEN = "home_screen"
    const val CALENDAR_SCREEN = "calendar_screen"
    const val ARCHIVE_SCREEN = "archive_screen"
    const val STATS_SCREEN = "stats_screen"
    const val SIGN_IN_SCREEN = "sign_in_screen"
    // TODO: Pozamieniac stringi w kodzie na consty

    //Values
    const val SPLASH_DELAY: Long = 500

    //Messages
    const val REVOKE_ACCESS_MESSAGE = "You need to re-authenticate before revoking the access."
}