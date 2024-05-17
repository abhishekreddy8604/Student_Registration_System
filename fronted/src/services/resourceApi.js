import {
  get_request,
  post_request,
  delete_request,
  put_request,
} from "./requests";

// post the resources
const addStudent = (data) => {
  return new Promise((resolve, reject) => {
    post_request("students/", data)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        reject(error);
      });
  });
};

// update the resources 
// const updateResource = (id, data) => {
//   return new Promise((resolve, reject) => {
//     put_request(`admin/resources/${id}`, data)
//       .then((response) => {
//         resolve(response.data);
//       })
//       .catch((error) => {
//         reject(error);
//       });
//   });
// };

// delete the resources based on the id
const deleteStudent = (id) => {
  return new Promise((resolve, reject) => {
    delete_request(`students/${id}`)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        reject(error);
      });
  });
};

// get all the resources by filters
const getStudents = (params) => {
  return new Promise((resolve, reject) => {
    get_request("students/", params)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        reject(error);
      });
  });
};
// const getStudentById = (params) => {
//   return new Promise((resolve, reject) => {
//     get_request("students/", params)
//       .then((response) => {
//         resolve(response.data);
//       })
//       .catch((error) => {  
//         reject(error);
//       });
//   });
// };

// get the resources by id
// const getResourceById = (id) => {
//   return new Promise((resolve, reject) => {
//     get_request(`admin/resources/${id}`)
//       .then((response) => {
//         resolve(response.data);
//       })
//       .catch((error) => {
//         reject(error);
//       });
//   });
// };

export {
  addStudent,
  // updateResource,
  // getResourceById,
  getStudents,
  deleteStudent,
};
