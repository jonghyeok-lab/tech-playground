package playground;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import playground.entity.PetFood;

import java.util.List;

public interface PetFoodRepository extends JpaRepository<PetFood, Long> {


    List<PetFood> findPetFoodsByBrandName(String brandName);

    @Query(value = """
            SELECT pf
            FROM PetFood pf
            JOIN FETCH pf.brand b
            JOIN FETCH pf.keyword k
            WHERE b.name = :brandName
            """)
    List<PetFood> findPetFoodsByBrandNameWithFetchJoin(String brandName);
}
