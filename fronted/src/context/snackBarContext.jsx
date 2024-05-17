import { createContext } from "react";
import { useState } from "react";

const snankBarContext = createContext(null);
// Context to display the snackbar
const SnackBarProvider = ({ children }) => {
  const [open, setOpen] = useState(false);
  const [message, setMessage] = useState("");
  const [sx, setSx] = useState(null);
  //  Function to display the snackbar
  const displaySnackBar = (open, message, sx = {}) => {
    setOpen(open);
    setMessage(message);
    setSx(sx);
  };
  // Context value
  const contextValue = {
    open,
    setOpen,
    message,
    sx,
    displaySnackBar,
  };
  // Return the provider
  return (
    <snankBarContext.Provider value={contextValue}>
      {children}
    </snankBarContext.Provider>
  );
};

export default SnackBarProvider;
export { snankBarContext };
