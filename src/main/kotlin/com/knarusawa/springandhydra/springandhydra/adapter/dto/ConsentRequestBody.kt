package com.knarusawa.springandhydra.springandhydra.adapter.dto

data class ConsentRequestBody(
        val consentChallenge: String?,
        val scopes: List<String>?
)
