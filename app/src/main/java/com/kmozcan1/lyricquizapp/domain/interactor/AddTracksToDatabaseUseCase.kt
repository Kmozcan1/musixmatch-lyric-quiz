package com.kmozcan1.lyricquizapp.domain.interactor

import com.kmozcan1.lyricquizapp.domain.interactor.base.CompletableUseCase
import com.kmozcan1.lyricquizapp.domain.model.domainmodel.TrackDomainModel
import com.kmozcan1.lyricquizapp.domain.repository.TrackRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

/**
 * Created by Kadir Mert Özcan on 19-Dec-20.
 */
class AddTracksToDatabaseUseCase @Inject constructor(
        private val trackRepository: TrackRepository
        ) : CompletableUseCase<AddTracksToDatabaseUseCase.Params>() {

    data class Params(val trackList: List<TrackDomainModel>)

    // Adds all tasks to completable list and returns onComplete after all sources complete
    override fun buildObservable(params: Params?): Completable {
        val taskList = mutableListOf<Completable>()
        for (track in params!!.trackList) {
            taskList.add(trackRepository.insertTrackToDatabase(track))
        }
        return Completable.merge(taskList)
    }
}
