package lu.lusis.demo;

import com.vaadin.flow.spring.annotation.SpringComponent;
import lu.lusis.demo.backend.data.Position;
import lu.lusis.demo.backend.data.User;
import lu.lusis.demo.backend.repository.PositionRepository;
import lu.lusis.demo.backend.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.time.LocalDate;

/**
 * Génére les données pour la démo
 */
@SpringComponent
public class DataGenerator {


    private Logger logger = LoggerFactory.getLogger(DataGenerator.class);

    private static final String digits = "0123456789";
    private static SecureRandom rnd = new SecureRandom();

    private final UserRepository userRepository;

    private final PositionRepository positionRepository;

    @Value( "${custom.userSize}" )
    private int userSize;

    public DataGenerator(UserRepository userRepository, PositionRepository positionRepository) {
        this.userRepository = userRepository;
        this.positionRepository = positionRepository;
    }

    @PostConstruct
    private void init(){
        logger.info("DataGenerator init()");
        if (userRepository.count() == 0) {
            logger.warn("User empty - Generate users");
            // add some data in db
            for (int i = 0; i < userSize; i++) {
                userRepository.save(new User("Name " + i, " Firstname " + i, generateRandomRegisterNumber(), LocalDate.now()));
            }
        }

        generatePositions();
    }
    private String generateRandomRegisterNumber(){
        return randomString(13);
    }

    private String randomString( int len ){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( digits.charAt( rnd.nextInt(digits.length()) ) );
        return sb.toString();
    }

    private void generatePositions() {
        if (positionRepository.count() == 0) {
            positionRepository.save(new Position("Position 1",6.138007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 2",6.1386081455871135,49.587828424497694));
            positionRepository.save(new Position("Position 3",6.167183927672795,49.55142846806457));
            positionRepository.save(new Position("Position 4",6.188007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 5",6.238007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 6",6.288007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 7",6.338007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 8",6.388007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 9",6.438007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 10",6.488007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 11",6.538007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 12",6.588007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 13",6.65007330767778,49.60996909775758));
            positionRepository.save(new Position("Position 14",6.808007330767778,49.60996909775758));


            positionRepository.save(new Position("A Position 1",6.138007330767778,49.60996909775758));
            positionRepository.save(new Position("A Position 2",6.1386081455871135,49.587828424497694));
            positionRepository.save(new Position("A Position 3",6.167183927672795,49.55142846806457));
            positionRepository.save(new Position("A Position 4",6.188007330767778,49.70996909775758));
            positionRepository.save(new Position("A Position 5",6.238007330767778,49.80996909775758));
            positionRepository.save(new Position("A Position 6",6.288007330767778,49.90996909775758));
            positionRepository.save(new Position("A Position 7",6.338007330767778,50.0996909775758));
            positionRepository.save(new Position("A Position 8",6.388007330767778,50.10996909775758));
            positionRepository.save(new Position("A Position 9",6.438007330767778,50.20996909775758));
            positionRepository.save(new Position("A Position 10",6.488007330767778,50.30996909775758));
            positionRepository.save(new Position("A Position 11",6.538007330767778,50.40996909775758));
            positionRepository.save(new Position("A Position 12",6.588007330767778,50.50996909775758));
            positionRepository.save(new Position("A Position 13",6.65007330767778,50.60996909775758));
            positionRepository.save(new Position("A Position 14",6.808007330767778,50.70996909775758));





        }

    }
}