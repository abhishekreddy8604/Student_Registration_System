import {
    get_request,
  } from "./requests";

  
// Get all logs
const getLogs = (params) => {
    return new Promise((resolve, reject) => {
      get_request("logs/", params)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };


export { getLogs };