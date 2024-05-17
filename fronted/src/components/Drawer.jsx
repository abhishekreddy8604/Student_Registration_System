import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import {
  Box,
  Button,
  Drawer,
  CssBaseline,
  Toolbar,
  List,
  Typography,
  Divider,
  IconButton,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
} from "@mui/material";
import ManageAccountsIcon from '@mui/icons-material/ManageAccounts';
import MenuIcon from "@mui/icons-material/Menu";
import useMediaQuery from "@mui/material/useMediaQuery";
import AppBar from "./layout/AppBar";
import Main from "./layout/Main";
import DrawerHeader from "./layout/DrawerHeader";
import PeopleAltIcon from '@mui/icons-material/PeopleAlt';
import { useLocation } from 'react-router-dom';
import VisibilityIcon from '@mui/icons-material/Visibility';
const drawerWidth = 240;

export default function CustomDrawer({ children }) {
  const [open, setOpen] = useState(true);
  const [appBarLabel, setAppBarLabel] = useState("Manage Student");
  const location = useLocation();
  const { pathname } = location;
  // Set the app bar label based on the path
  useEffect(() => {
    if (pathname === "/") {
      setAppBarLabel("Manage Student");
    } else if (pathname === "/manageclass") {
      setAppBarLabel("Manage Classes");
    } else if (pathname === "/course") {
      setAppBarLabel("Manage Course");
    } else if (pathname === "/viewlogs") {
      setAppBarLabel("Logs");
    } else if (pathname === "/viewenrollments") {
      setAppBarLabel("Enrollments");
    }
  },[])
  // Function to handle the drawer open and close
  const matches = useMediaQuery("(max-width:600px)");
  const navigate = useNavigate();
  const handleDrawer = () => {
    setOpen(!open);
  };
  // List of items in the drawer
  const drawerItemList = [
    {
      title: "Manage Student",
      icon: <ManageAccountsIcon />,
      onClick: () => {
        setAppBarLabel("Manage Student");
        if (matches) {
          setOpen(false);
        }
        navigate("/");
      },
    },
    {
      title: "Manage Classes",
      icon: <ManageAccountsIcon />,
      onClick: () => {
        setAppBarLabel("Manage Classes");
        if (matches) {
          setOpen(false);
        }
        navigate("/manageclass");
      },
    },
    {
      title: "Manage Course",
      icon: <ManageAccountsIcon />,
      onClick: () => {
        setAppBarLabel("Manage Course");
        if (matches) {
          setOpen(false);
        }
        navigate("/course");
      },
    },
    {
      title: "View Enrollments",
      icon: <ManageAccountsIcon />,
      onClick: () => {
        setAppBarLabel("Enrollments");
        if (matches) {
          setOpen(false);
        }
        navigate("/viewenrollments");
      },
    },
    {
      title: "View Logs",
      icon: <VisibilityIcon />,
      onClick: () => {
        setAppBarLabel("Logs");
        if (matches) {
          setOpen(false);
        }
        navigate("/viewlogs");
      },
    }
  ];
  // Return the drawer
  return (
    <Box sx={{ display: "flex" }}>
      <CssBaseline />
      <AppBar
        position="fixed"
        open={open}
        sx={{ color: "black", backgroundColor: "white" }}
        elevation={0}
      >
        <Toolbar>
          <IconButton
            color="inherit"
            aria-label="open drawer"
            onClick={handleDrawer}
            edge="start"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" noWrap component="div" sx={{ flexGrow: 1 }}>
            {appBarLabel}
          </Typography>
        </Toolbar>
      </AppBar>
      <Drawer
        PaperProps={{
          sx: {
            backgroundColor: "primary.main",
          },
        }}
        sx={{
          width: drawerWidth,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: {
              xs: "100vw",
              md: drawerWidth,
            },
            boxSizing: "border-box",
          },
        }}
        variant="persistent"
        anchor="left"
        open={open}
      >
        <Box
          sx={{
            m: 2,
            display: "flex",
            flexDirection: "column",
            alignItems: "center",
            justifyContent: "center",
          }}
        >
          <PeopleAltIcon sx={{ color : "white"}} fontSize="large" />
          <Typography
            variant="h1"
            sx={{
              mt: 2,
              fontWeight: "700",
              fontSize: "12px",
              letterSpacing: "0.12em",
            }}
            color="white"
            align="center"
          >
            Student Registration System
          </Typography>
        </Box>

        <Divider />
        <List sx={{ color: "white" }}>
          {drawerItemList.map((item) => (
            <ListItem
              key={item.title}
              disablePadding
              component={Button}
              onClick={item.onClick}
            >
              <ListItemButton>
                <ListItemIcon sx={{ color: "white" }}>{item.icon}</ListItemIcon>
                <ListItemText
                  disableTypography
                  primary={item.title}
                  sx={{
                    color: "white",
                    fontWeight: "700",
                    fontSize: "12px",
                    letterSpacing: "0.12em",
                  }}
                />
              </ListItemButton>
            </ListItem>
          ))}
        </List>
      </Drawer>

      <Main
        open={open}
        sx={{
          backgroundColor: "#F4FAF5",
          height: "100vh",
          ...(open && matches && { display: "none" }),
        }}
      >
        <DrawerHeader />
        {children}
      </Main>
    </Box>
  );
}
