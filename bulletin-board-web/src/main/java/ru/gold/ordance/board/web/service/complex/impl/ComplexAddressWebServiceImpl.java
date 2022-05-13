package ru.gold.ordance.board.web.service.complex.impl;

import org.springframework.stereotype.Service;
import ru.gold.ordance.board.core.entity.*;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRq;
import ru.gold.ordance.board.web.api.complex.ComplexAddressUpdateRs;
import ru.gold.ordance.board.core.service.heir.*;
import ru.gold.ordance.board.web.service.complex.ComplexAddressWebService;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ComplexAddressWebServiceImpl implements ComplexAddressWebService {
    private final RegionService regionService;

    private final LocalityService localityService;

    private final StreetService streetService;

    private final LnkLocalityStreetService lnkLocalityStreetService;

    private final AddressService addressService;

    public ComplexAddressWebServiceImpl(RegionService regionService,
                                        LocalityService localityService,
                                        StreetService streetService,
                                        LnkLocalityStreetService lnkLocalityStreetService,
                                        AddressService addressService) {
        this.regionService = regionService;
        this.localityService = localityService;
        this.streetService = streetService;
        this.lnkLocalityStreetService = lnkLocalityStreetService;
        this.addressService = addressService;
    }

    @Override
    public ComplexAddressUpdateRs update(ComplexAddressUpdateRq rq) {
        AtomicLong regionId = new AtomicLong();
        AtomicLong streetId = new AtomicLong();
        AtomicLong localityId = new AtomicLong();

        Optional<Region> foundRegion = regionService.findByName(rq.getRegionName());
        foundRegion.ifPresentOrElse(r -> regionId.set(r.getId()), () ->
                regionId.set(regionService.update(Region.builder()
                    .name(rq.getRegionName())
                    .build())
                    .get()
                    .getId()));

        Optional<Locality> foundLocality = localityService.findByName(rq.getLocalityName());
        foundLocality.ifPresentOrElse(l -> localityId.set(l.getId()), () ->
                localityId.set(localityService.update(Locality.builder()
                        .name(rq.getLocalityName())
                        .region(Region.builder()
                                .id(regionId.get())
                                .build())
                        .build())
                        .get()
                        .getId()));

        Optional<Street> foundStreet = streetService.findByName(rq.getStreetName());
        foundStreet.ifPresentOrElse(s -> streetId.set(s.getId()), () ->
                streetId.set(streetService.update(Street.builder()
                        .name(rq.getStreetName())
                        .build())
                        .get()
                        .getId()));

        Optional<LnkLocalityStreet> foundLnk = lnkLocalityStreetService.findByLocalityAndStreet(
                Locality.builder()
                        .id(localityId.get())
                        .build(),
                Street.builder()
                        .id(streetId.get())
                        .build());
        if (foundLnk.isEmpty()) {
            lnkLocalityStreetService.update(LnkLocalityStreet.builder()
                   .locality(Locality.builder()
                           .id(localityId.get())
                           .build())
                   .street(Street.builder()
                           .id(streetId.get())
                           .build())
                    .build());
        }

        try {
            addressService.update(Address.builder()
                    .locality(Locality.builder()
                            .id(localityId.get())
                            .build())
                    .street(Street.builder()
                            .id(streetId.get())
                            .build())
                    .houseNumber(rq.getHouseNumber())
                    .build());
        } catch (Exception ignored) {}

        return ComplexAddressUpdateRs.success(regionId.get(), localityId.get(), streetId.get());
    }
}
