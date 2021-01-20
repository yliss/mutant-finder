package com.mercadolibre.services.mutantfinder.mappers;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import static com.mercadolibre.services.mutantfinder.utils.StringMessages.STATISTICS_FROM_HUMAN_TABLE_INVALID;

@Mapper(componentModel = "spring")
public interface HumanClassificationMapper {
    @Mapping(target = "ratio", qualifiedByName = "calculateRatio")
    HumanClassificationStatistics mapEntityToModel(HumanClassificationStatisticsTemp entity);

    @Named("calculateRatio")
    default float calculateRatio(HumanClassificationStatisticsTemp entity){
        if (entity == null) {
            throw new InvalidDataException(STATISTICS_FROM_HUMAN_TABLE_INVALID);
        }
        final float humanCount = entity.getCountHumanDNA();
        final float mutantCount = entity.getCountMutantDNA();

        if(mutantCount <= 0) {
            return 0;
        } else {
            return mutantCount / humanCount ;
        }
    }
}
