package com.heybeach.main.data

import com.heybeach.beaches.ui.BeachesFragment
import com.heybeach.profile.ProfileFragment

val BEACHES = BeachesFragment::class.java.canonicalName!!
val PROFILE = ProfileFragment::class.java.canonicalName!!

class MainModel {

    val fragmentNames =
        arrayListOf(BEACHES, PROFILE)

    var selectedFragmentName = fragmentNames[0]

}