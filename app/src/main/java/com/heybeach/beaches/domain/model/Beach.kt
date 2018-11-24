package com.heybeach.beaches.domain.model

data class Beach(val id: String, val name: String, val url: String, val width: Int, val height: Int) {

    fun ratio() = height * 1.0f / width
}