package playground.jpa;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import playground.jpa.entity.PetFood;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
class PetFoodRepositoryTest {
    @Autowired
    PetFoodRepository petFoodRepository;

    @DisplayName("N + 1 쿼리")
    @Test
    @Transactional
    void N1query() {
        List<PetFood> pefFoods = petFoodRepository.findPetFoodsByBrandName("브랜드1");
        pefFoods.forEach(pf -> log.info(pf.toString()));
    }

    @Test
    @Transactional
    void fetchJoinTest() {
        List<PetFood> petFoods = petFoodRepository.findPetFoodsByBrandNameWithFetchJoin("브랜드1");
        petFoods.forEach(pf -> log.info(pf.toString()));

    }

}