package ru.gold.ordance.board.core.persistence.heir;

import org.springframework.stereotype.Repository;
import ru.gold.ordance.board.core.entity.Advertisement;
import ru.gold.ordance.board.core.persistence.SearchByNameRepository;

import javax.persistence.NamedQuery;
import java.util.List;

@Repository
public interface AdvertisementRepository extends SearchByNameRepository<Advertisement> {
    List<Advertisement> findAdvertisementsBySubcategory_Category_Name(String name);
    List<Advertisement> findAdvertisementsByLocality_Region_Name(String name);
    List<Advertisement> findAdvertisementsBySubcategory_Category_NameAndLocality_Region_Name(String categoryName, String regionName);
}
