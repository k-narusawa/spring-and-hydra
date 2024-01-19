package com.knarusawa.springandhydra.springandhydra.adapter.gateway.db.dao

import com.knarusawa.springandhydra.springandhydra.adapter.gateway.db.record.UserRecord
import org.springframework.data.repository.CrudRepository

interface UserDao : CrudRepository<UserRecord, String> {
    fun findByUsername(username: String): UserRecord?
}