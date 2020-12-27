package com.kmozcan1.lyricquizapp.domain.interactor

import androidx.room.rxjava3.EmptyResultSetException
import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.io.IOException
import javax.inject.Inject


/**
 * Created by Kadir Mert Özcan on 20-Dec-20.
 */
class RegisterUserUseCase @Inject constructor(
    private val insertUserToDatabaseUseCase: InsertUserToDatabaseUseCase,
    private val getUserFromDatabaseUseCase: GetUserFromDatabaseUseCase
) : CompletableUseCase<RegisterUserUseCase.Params>() {
    data class Params(val userName: String)

    override fun buildObservable(params: Params?): Completable {
        // Adds users to the database if they aren't registered
        return if (isUsernameValid(params?.userName!!)) {
            getUserFromDatabaseUseCase.buildObservable(
                GetUserFromDatabaseUseCase.Params(params.userName)
            ).onErrorResumeNext { error ->
                if (error is EmptyResultSetException) Single.just("")
                else Single.error(error)
            }.flatMapCompletable { name ->
                if (name == "") {
                    insertUserToDatabaseUseCase.buildObservable(
                        InsertUserToDatabaseUseCase.Params(params.userName)
                    )
                } else {
                    Completable.complete()
                }
            }
        } else {
            Completable.error(IOException("Username cannot be empty"))
        }
    }

    // Can stay here until the login process is more complicated
    private fun isUsernameValid(userName: String): Boolean {
        return userName.isNotBlank()
    }
}