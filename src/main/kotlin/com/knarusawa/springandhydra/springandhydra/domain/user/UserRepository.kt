package com.knarusawa.springandhydra.springandhydra.domain.user

interface UserRepository {
    fun save(user: User)
    fun findByUsername(username: Username): User?
}