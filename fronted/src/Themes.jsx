import { createTheme } from "@mui/material/styles";
 const theme = createTheme({
    palette: {
      primary: {
        light: "#00ffff",
        main: "#609874",
        dark: "#30964b",
        contrastText: "#fff",
      },
      secondary: {
        light: "#ff7961",
        main: "#f44336",
        dark: "#ba000d",
        contrastText: "#000",
      },
    },
    typography: {
      allVariants: {
        fontFamily: "Poppins",
        textTransform: "none",
        fontSize: 18,
      },
    },
  });

export default theme;