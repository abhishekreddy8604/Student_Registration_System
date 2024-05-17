import {
    get_request,
    post_request,
    delete_request,
  } from "./requests";
  
  // post the resources
  const addClass = (data) => {
    return new Promise((resolve, reject) => {
      post_request("classes/", data)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
  
  // delete the resources based on the Dept_code and Course_code
  const deleteClass = (class_id) => {
    return new Promise((resolve, reject) => {
      delete_request(`classes/${class_id}`)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
  
  // get all the resources by filters
  const getClasses = (params) => {
    return new Promise((resolve, reject) => {
      get_request("classes/", params)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
  export {
    getClasses,
    addClass,
    deleteClass
  };
  