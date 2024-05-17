import {
    get_request,
    post_request,
    delete_request,
  } from "./requests";
  
  // post the resources
  const addCourse = (data) => {
    return new Promise((resolve, reject) => {
      post_request("courses/", data)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
  
  // delete the resources based on the Dept_code and Course_code
  const deleteCourse = (dept_code,course_code) => {
    return new Promise((resolve, reject) => {
      delete_request(`courses/${dept_code}/${course_code}`)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
  
  // get all the resources by filters
  const getCourses = (params) => {
    return new Promise((resolve, reject) => {
      get_request("courses/", params)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
  export {
    getCourses,
    addCourse,
    deleteCourse
  };
  