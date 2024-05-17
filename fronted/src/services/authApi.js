import { post_request } from "./requests";

// post the resources
const login = (data) => {
  return new Promise((resolve, reject) => {
    post_request("admin/login", data)
      .then((response) => {
        resolve(response.data);
      })
      .catch((error) => {
        reject(error);
      });
  });
};

export { login };
