import { RouterProvider } from "react-router-dom";
import { ThemeProvider } from "@emotion/react";

import router from "./routers/router";
import SnackBarProvider from "./context/snackBarContext";
import AlertSnackbar from "./components/SnackBar";
import theme from "./Themes";

function App() {

  // Return the router provider with the router and the theme provider with the theme
  return (
    <ThemeProvider theme={theme}>
      <SnackBarProvider>
        <RouterProvider router={router} />
        <AlertSnackbar />
      </SnackBarProvider>
    </ThemeProvider>
  );
}

export default App;
  