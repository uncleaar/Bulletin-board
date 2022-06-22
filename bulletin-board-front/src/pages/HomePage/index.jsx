import React, { useState, useEffect, useCallback } from "react";
import { ModalProvider } from "../../context/ModalContext";
import { getAds, getPhotos, getLocalities } from "../../requests/ad";
import { aggregateAds } from "../../utils/global";
import DefaultLayout from "../../layouts/DefaultLayout";
import Card from "../../components/Card";

const HomePage = () => {
  const [ads, setAds] = useState([]);
  const [photo, setPhoto] = useState([]);
  const [locality, setLocality] = useState([]);
  const [aggregatedAds, setAggregatedAds] = useState([]);

  const fetchAds = useCallback(async () => {
    const { data } = await getAds();
    setAds(data.advertisementList);
  }, []);

  const fetchPhotos = useCallback(async () => {
    const { data } = await getPhotos();
    setPhoto(data.photoList);
  }, []);

  const fetchLocalities = useCallback(async () => {
    const { data } = await getLocalities();
    setLocality(data.localityList);
  }, []);

  useEffect(() => {
    fetchAds();
  }, [fetchAds]);

  useEffect(() => {
    fetchPhotos();
  }, [fetchPhotos]);

  useEffect(() => {
    fetchLocalities();
  }, [fetchLocalities]);

  useEffect(() => {
    if (ads && photo && locality) {
      setAggregatedAds(aggregateAds(ads, photo, locality));
    }
  }, [ads, photo, locality]);

  console.log("aggregatedAds", aggregatedAds);

  return (
    <ModalProvider>
      <DefaultLayout>
        <div className="container">
          <div className="cards">
            {aggregatedAds?.map((ad) => (
              <Card
                name={ad.name}
                price={ad.price}
                date={ad.createDate}
                image={ad.photo?.urn}
                locality={ad.locality?.name}
                key={ad.entityId}
              />
            ))}
          </div>
        </div>
      </DefaultLayout>
    </ModalProvider>
  );
};

export default HomePage;
