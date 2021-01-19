
package com.mercadolibre.services.mutantfinder.services.impl;

import com.mercadolibre.services.mutantfinder.data.entities.HumanClassificationStatisticsTemp;
import com.mercadolibre.services.mutantfinder.data.entities.HumanEntity;
import com.mercadolibre.services.mutantfinder.data.repositories.HumanRepository;
import com.mercadolibre.services.mutantfinder.exceptions.InvalidDataException;
import com.mercadolibre.services.mutantfinder.mappers.HumanClassificationMapper;
import com.mercadolibre.services.mutantfinder.models.HumanClassificationStatistics;
import com.mercadolibre.services.mutantfinder.services.DNAFinderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DNAFinderServiceImpl implements DNAFinderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DNAFinderServiceImpl.class);
    private final HumanRepository humanRepository;
    private final HumanClassificationMapper humanClassificationMapper;

    @Autowired
    public DNAFinderServiceImpl(HumanRepository humanRepository, HumanClassificationMapper humanClassificationMapper) {
        this.humanRepository = humanRepository;
        this.humanClassificationMapper = humanClassificationMapper;
    }

    @Override
    public boolean isMutant(String[] dnaArray) {
        LOGGER.info("Starting isMutant method inside service");
        if(dnaArray == null || dnaArray.length <= 1 ||  isNotValidateArraySize(dnaArray)) {
            LOGGER.error("The DNA parameter is not valid");
            throw new InvalidDataException("The DNA parameter is not valid");
        }
     
        final boolean result = evaluateDnaIsMutant(dnaArray);
        HumanEntity humanEntity = new HumanEntity();
        humanEntity.setHashCode(simpleHash(dnaArray));
        humanEntity.setDna(String.join("",dnaArray));
        humanEntity.setIsMutant(result);

        humanRepository.save(humanEntity);

        LOGGER.info("Finishing isMutant method inside service");
        return result;
    }

	@Override
    public HumanClassificationStatistics retrieveStatistics() {
        LOGGER.info("Starting retrieveStatistics method");
        LOGGER.info("Calling the db to retrieve the statistics");
        final HumanClassificationStatisticsTemp humanClassificationStatisticsTemp =
                humanRepository.retrieveStatistics();

        LOGGER.info("Mapping the entity to the model");
        final HumanClassificationStatistics humanClassificationStatistics = humanClassificationMapper
                .mapEntityToModel(humanClassificationStatisticsTemp);

        LOGGER.info("Finishing the retrieveStatistics method");
        return humanClassificationStatistics;
    }

    private long simpleHash(final String[] dnaArray) {
        int hash = 7;
        for (int i = 0; i < dnaArray.length; i++) {
            String row = dnaArray[i];
            for (int j = 0; j < row.length(); j++) {
                hash = hash*31 + row.charAt(j);
            }
        }

        return hash;
    }
    
    private boolean evaluateDnaIsMutant(String[] dnaIn) {
    	String[][] dnaArray = new String[dnaIn.length][dnaIn.length];
		int size = dnaIn.length;

		for (int posY = 0; posY < size; posY++) {

			dnaArray = addLineToDnaArray(dnaIn, dnaArray, posY);
			int nSecuences = 0;
			int stepX = 0;
			int stepY = 0;
			int initialX = 0;
			/*
			 * stepHorizontal
			 */

			do {
				if (dnaArray[posY][stepX].equals(dnaArray[posY][stepX + 1]))
					nSecuences++;
				else
					nSecuences = 0;

				stepX++;
			} while (stepX + 1 < size && nSecuences < 3);
			if (nSecuences == 3) return true;
			

			/*
			 * stepDiagonal
			 */

			if (posY >= 3) {
				nSecuences = 0;
				stepX = 0;
				initialX = 0;
				stepY = posY;
				do {
					if (dnaArray[stepY][stepX].equals(dnaArray[stepY - 1][stepX + 1])) {
						nSecuences++;
						stepX++;
						stepY--;
					} else {
						initialX++;
						nSecuences = 0;
						stepX = initialX;
						stepY = posY;
					}
				} while (stepX + 1 < size && stepY - 1 < size && nSecuences < 3);
				if (nSecuences == 3) return true;

				/*
				 * stepVertical
				 */
				

				nSecuences = 0;
				stepX = 0;
				stepY = posY;
				do {
					if (dnaArray[posY][stepX].equals(dnaArray[stepY - 1][stepX])) {
						nSecuences++;
						stepY--;
					} else {
						nSecuences = 0;
						stepY = posY;
						stepX++;
					}

				} while (stepX < size && stepY - 1 < size && nSecuences < 3);
				if (nSecuences == 3) return true;

				/*
				 * step Inverted Diagonal
				 */
			
				nSecuences = 0;
				stepX = 3;
				initialX = 3;
				stepY = posY;
				do {
					if (dnaArray[stepY][stepX].equals(dnaArray[stepY - 1][stepX - 1])) {
						nSecuences++;
						stepX--;
						stepY--;
					} else {
						initialX++;
						nSecuences = 0;
						stepX = initialX;
						stepY = posY;
					}
				} while (stepX  < size && stepY - 1 < size && nSecuences < 3);
				if (nSecuences == 3) return true;
			}
		}
		return false;
	}

	private static String[][] addLineToDnaArray(String[] dnaIn, String[][] dnaArray, int posY) {
		String[] line = dnaIn[posY].split("");
		for (int posX = 0; posX < line.length; posX++)
			dnaArray[posY][posX] = line[posX];
		return dnaArray;
	}

	private boolean isNotValidateArraySize(String[] dnaArray) {
		return (dnaArray[0].length() != dnaArray.length);
	}
}
