
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
import java.util.ArrayList;
import java.util.List;
import static com.mercadolibre.services.mutantfinder.utils.StringMessages.STATISTICS_FROM_HUMAN_TABLE_INVALID;

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

    /**
     * Return boolean value of the DNA array processing, validations on the size
     * array and coherence with the information of the nitrogenous bases (A, T, C,
     * G).
     */
    @Override
    public boolean isMutant(String[] dnaArray) {
        LOGGER.info("Starting isMutant method inside service");
        if (dnaArray == null || dnaArray.length <= 1 ||
                isNotValidateArraySize(dnaArray) || containsNotValidLetters(dnaArray)) {
            LOGGER.error("The DNA parameter is not valid");
            throw new InvalidDataException("The DNA parameter is not valid");
        }

        final boolean result = evaluateDnaIsMutant(dnaArray);
        HumanEntity humanEntity = new HumanEntity();
        humanEntity.setHashCode(simpleHash(dnaArray));
        humanEntity.setDna(String.join("", dnaArray));
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
        final float ratio = calculateRatio(humanClassificationStatisticsTemp);

        LOGGER.info("Mapping the entity to the model");
        final HumanClassificationStatistics humanClassificationStatistics = humanClassificationMapper
                .mapEntityToModel(humanClassificationStatisticsTemp);
        humanClassificationStatistics.setRatio(ratio);

        LOGGER.info("Finishing the retrieveStatistics method");
        return humanClassificationStatistics;
    }

    private float calculateRatio(HumanClassificationStatisticsTemp entity){
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

	private long simpleHash(final String[] dnaArray) {
		int hash = 7;
		for (int i = 0; i < dnaArray.length; i++) {
			String row = dnaArray[i];
			for (int j = 0; j < row.length(); j++) {
				hash = hash * 31 + row.charAt(j);
			}
		}

		return hash;
	}

    /**
     * Return Boolean value of finding or not more than a sequence of four equal
     * letters in any direction: Vertical, horizontal, diagonal or inverted
     * diagonal. The method implementation was inspired by the operation of the
     * floodfill algorithm. Where the area formed by contiguous elements in an array
     * is determined performing the validation of continuous elements to the
     * processed node. The method comprises case management for sequences of letters
     * of 5-6 numbers of letters, where it is not possible to confuse and generate a
     * new sequence with an already created sequence.
     * <p>
     * The search of the sequences is carried out in order and by count of steps
     * given between the processed node.
     *
     * @param dnaIn
     * @return Boolean result
     */
    private boolean evaluateDnaIsMutant(String[] dnaIn) {
        String[][] dnaArray = new String[dnaIn.length][dnaIn.length];
        int size = dnaIn.length;
        int totalNumberOfSequencesFound = 0;

        for (int positionY = 0; positionY < size; positionY++) {
            dnaArray = addLineToDnaArray(dnaIn, dnaArray, positionY);
            int numberOfSequencesFound = 0;
            int positionX = 0;
            int stepX = 0;
            int stepY = 0;
            /**
             * Step Horizontal.
             */
            do {
                if (dnaArray[positionY][stepX].equals(dnaArray[positionY][stepX + 1])) {
                    numberOfSequencesFound++;
                    if (numberOfSequencesFound >= 3) {
                        totalNumberOfSequencesFound++;
                        if (numberOfSequencesFound == 4) {
                            totalNumberOfSequencesFound--;
                            numberOfSequencesFound = 0;
                        }
                    }
                } else {
                    numberOfSequencesFound = 0;
                }
                stepX++;
            } while (stepX + 1 < size && totalNumberOfSequencesFound < 2);
            if (totalNumberOfSequencesFound == 2) return true;

            /*
             * Step Diagonal
             */
            if (positionY >= 3) {
                numberOfSequencesFound = 0;
                stepX = 0;
                positionX = 0;
                stepY = positionY;
                do {
                    if (dnaArray[stepY][stepX].equals(dnaArray[stepY - 1][stepX + 1])) {
                        numberOfSequencesFound++;
                        stepX++;
                        stepY--;
                        if (numberOfSequencesFound >= 3) {
                            totalNumberOfSequencesFound++;
                            if (numberOfSequencesFound == 4) {
                                totalNumberOfSequencesFound--;
                                numberOfSequencesFound = 0;
                                if (totalNumberOfSequencesFound == 2)
                                    totalNumberOfSequencesFound--;
                            }
                        }
                    } else {
                        positionX++;
                        numberOfSequencesFound = 0;
                        stepX = positionX;
                        stepY = positionY;
                    }
                } while (stepX + 1 < size && stepY > 0);
                if (totalNumberOfSequencesFound == 2) return true;

                /*
                 * Steps Vertical
                 */

                numberOfSequencesFound = 0;
                stepX = 0;
                stepY = positionY;
                do {
                    if (dnaArray[positionY][stepX].equals(dnaArray[stepY - 1][stepX])) {
                        numberOfSequencesFound++;
                        stepY--;
                        if (numberOfSequencesFound >= 3) {
                            totalNumberOfSequencesFound++;
                            if (numberOfSequencesFound == 4) {
                                totalNumberOfSequencesFound--;
                                numberOfSequencesFound = 0;
                                if (totalNumberOfSequencesFound == 2)
                                    totalNumberOfSequencesFound--;
                            }
                        }
                    } else {
                        numberOfSequencesFound = 0;
                        stepY = positionY;
                        stepX++;
                    }
                } while (stepX < size && stepY > 0);
                if (totalNumberOfSequencesFound == 2) return true;

                /*
                 * Steps Inverted Diagonal
                 */

                numberOfSequencesFound = 0;
                stepX = 3;
                positionX = 3;
                stepY = positionY;
                do {
                    if (dnaArray[stepY][stepX].equals(dnaArray[stepY - 1][stepX - 1])) {
                        numberOfSequencesFound++;
                        stepX--;
                        stepY--;
                        if (numberOfSequencesFound >= 3) {
                            totalNumberOfSequencesFound++;
                            if (numberOfSequencesFound == 4) {
                                totalNumberOfSequencesFound--;
                                numberOfSequencesFound = 0;
                                if (totalNumberOfSequencesFound == 2)
                                    totalNumberOfSequencesFound--;
                            }
                        }
                    } else {
                        positionX++;
                        numberOfSequencesFound = 0;
                        stepX = positionX;
                        stepY = positionY;
                    }
                } while (stepX < size && stepY > 0);
                if (totalNumberOfSequencesFound == 2) return true;
            }
        }
        return false;
    }


    private static String[][] addLineToDnaArray(String[] dnaIn, String[][] dnaArray, int posY) {
        final String[] line = dnaIn[posY].split("");
        dnaArray[posY] = line;

        return dnaArray;
    }

    /**
     * Evaluation of the size of the array, if the width of the array is equal to
     * the length.
     *
     * @param dnaArray
     * @return
     */
    private boolean isNotValidateArraySize(String[] dnaArray) {
        return (dnaArray[0].length() != dnaArray.length);
    }

    // TODO IMPLEMENTAR

    /**
     * Evaluation of the letters entered in the adn array by Line. There should be
     * no letters other than ATCG.
     *
     * @param dnaLine Array line.
     * @return
     */
    private boolean containsNotValidLetters(String[] dnaArray) {
        final List<Character> validCharacters = new ArrayList<>();
        validCharacters.add('A');
        validCharacters.add('T');
        validCharacters.add('C');
        validCharacters.add('G');

        for (String string : dnaArray) {
            final int sizeString = string.length();
            int numberOfValidCharacters = 0;
            for (int i = 0; i < string.length(); i++) {
                char character = string.charAt(i);
                if (validCharacters.contains(character)) {
                    numberOfValidCharacters += 1;
                } else
					LOGGER.info(character  + "");
            }

            if (numberOfValidCharacters < sizeString)
                return true;
        }

        return false;
    }
}
