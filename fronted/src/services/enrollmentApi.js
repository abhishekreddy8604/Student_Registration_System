import {
  delete_request,
    get_request,
    post_request
  } from "./requests";

// Get all enrollments
const getEnrollments = (params) => {
    return new Promise((resolve, reject) => {
      get_request("enrollment/", params)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  };
// Add an enrollment
const addEnrollment = (data) => {
    return new Promise((resolve, reject) => {
      post_request("enrollment/enroll", data)
        .then((response) => {
          resolve(response.data);
        })
        .catch((error) => {
          reject(error);
        });
    });
  }
// Drop an enrollment
const dropEnrollment = (student_id,class_id) => {
  return new Promise((resolve, reject) => {
    delete_request(`enrollment/drop/${student_id}/${class_id}`)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        reject(error);
      });
  });
}

export { getEnrollments, addEnrollment, dropEnrollment };