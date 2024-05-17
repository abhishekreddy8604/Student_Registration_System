import { Navigate, Outlet } from "react-router-dom";
import CustomDrawer from "../components/Drawer";

export default function Resource() {
  const auth = true;
  return auth ? (
    <CustomDrawer>
      <Outlet />
    </CustomDrawer>
  ) : (
    <Navigate to="/" replace />
  );
}
