package com.kmozcan1.lyricquizapp.domain.mapper

/**
 * Interface for mapping the repository models into domain models.
 * Used for getting rid of unnecessary information.
 *
 * Created by Kadir Mert Özcan on 17-Dec-20.
 */
interface Mapper<in RepositoryModel, out DomainModel> {
    fun map(repositoryModel: RepositoryModel): DomainModel
}