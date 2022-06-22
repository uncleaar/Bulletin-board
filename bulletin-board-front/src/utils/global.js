export const aggregateAds = (ads, photos, localities) => {
  const aggregatedAds = ads?.map((ad) => {
    const adPhoto = photos?.find(
      (photo) => photo.entityId === ad.entityId && photo
    );
    const adLocality = localities?.find(
      (locality) => locality.entityId === ad.localityId && locality
    );
    return { ...ad, photo: adPhoto, locality: adLocality };
  });
  return aggregatedAds;
};
