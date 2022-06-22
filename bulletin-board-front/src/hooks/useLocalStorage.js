export const useLocalStorage = () => {
  const setValueIntoLocalStorage = (key, value) => {
    return localStorage.setItem(key, value);
  };

  const getValueFromLocalStorage = (key) => {
    return localStorage.getItem(key);
  };

  return { setValueIntoLocalStorage, getValueFromLocalStorage };
};
