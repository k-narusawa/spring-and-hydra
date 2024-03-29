package com.knarusawa.springandhydra.springandhydra.adapter.gateway.db

import com.knarusawa.springandhydra.springandhydra.adapter.gateway.db.dao.UserDao
import com.knarusawa.springandhydra.springandhydra.adapter.gateway.db.record.UserRecord
import com.knarusawa.springandhydra.springandhydra.domain.user.User
import com.knarusawa.springandhydra.springandhydra.domain.user.UserRepository
import com.knarusawa.springandhydra.springandhydra.domain.user.Username
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryImpl(
        private val userDao: UserDao
): UserRepository {
    override fun save(user: User) {
        userDao.save(UserRecord(
                userId = user.userId.value(),
                username = user.username.value(),
                password = user.password.value(),
                isAccountLock = user.isAccountLock,
                failedAttempts = user.failedAttempts,
                isDisabled = user.isDisabled,
        ))
    }

    override fun findByUsername(username: Username): User? {
        val user = userDao.findByUsername(username = username.value())
        return user?.let { User.from(it) }
    }
}