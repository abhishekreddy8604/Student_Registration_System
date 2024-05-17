import {
  createBrowserRouter,
  createRoutesFromElements,
  Route,
} from "react-router-dom";

// pages
import ManageStudentPage from "../pages/ManageResourcePage";
import ViewLogPage from "../pages/ViewLogPage";
import ViewEnrollmentPage from "../pages/ViewEnrollmentPage";
import ManageCoursePage from "../pages/ManageCoursePage";
import ManageClassPage from "../pages/ManageClassPage";
// layout
import Resource from "../layout/resource";

// Create a router with the routes
// The router is wrapped in the BrowserRouter
const router = createBrowserRouter(
  createRoutesFromElements(
    <Route path="/" element={<Resource />}>
        <Route index element={<ManageStudentPage />} />
        <Route path="course" element={<ManageCoursePage />}/>
        <Route path="viewlogs" element={<ViewLogPage />}/>
        <Route path="manageclass" element={<ManageClassPage />}/>
        <Route path="viewenrollments" element={<ViewEnrollmentPage />}/>
    </Route>
  )
);

export default router;
