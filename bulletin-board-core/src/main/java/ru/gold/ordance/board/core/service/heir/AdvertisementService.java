package ru.gold.ordance.board.core.service.heir;

import ru.gold.ordance.board.core.entity.Advertisement;
import ru.gold.ordance.board.core.service.SearchAllByNameService;

import java.util.List;

public interface AdvertisementService extends SearchAllByNameService<Advertisement> {
    List<Advertisement> findByCategoryName(String name);
    List<Advertisement> findByRegionName(String name);
    List<Advertisement> findByCategoryNameAndRegionName(String categoryName, String regionName);
}
