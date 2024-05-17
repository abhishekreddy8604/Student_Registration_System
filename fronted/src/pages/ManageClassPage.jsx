import { useState, useEffect,useContext} from "react";
import { NavLink } from "react-router-dom";
import {
  Typography,
  Button,
  IconButton,
  Grid,
  Autocomplete,
  TextField,
} from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

import AlertDialog from "../components/Dialog";
import CustomizedTables from "../components/Table";
import { snankBarContext } from "../context/snackBarContext";

import { getClasses, deleteClass } from "../services/classesApi";
import { StyledTableRow , StyledTableCell} from "../components/Table";

import ClassDialog from "../components/ClassDialog";
// Column Heading for the Table
const columnHeading = [
    "Sr No", "Class Id" , "Department Code", "Course Number", "Section", "Year", "Semester", "Limit", "ClassSize", "Room No.", "Actions"
]

function ManageClassPage() {

  // State to store the data
  const [data, setData] = useState([]);
  const [error, setError] = useState(null);

  const [showAlertDialog, setShowAlertDialog] = useState(false);
  const [deleteClassId, setDeleteClassId] = useState(null);
  // State to show the course dialog
  const [showCourseDialog, setShowCourseDialog] = useState(false);

  const { displaySnackBar } = useContext(snankBarContext);

  // Fetch the data from the API
  useEffect(() => {
    getClasses()
      .then((response) => setData(response.data))
      .catch((error) => setError(error.data));
  }, []);

  // Function to handle the delete button click
  const handleDeleteResourceBtnClick = (class_obj) => {
    setShowAlertDialog(true);
    setDeleteClassId(class_obj.classid);
  };

  // Function to handle the close of the alert dialog
  const handleAlertDialogClose = (value) => {
    if (value === "YES") {
      // Call the delete API
        deleteClass(deleteClassId)
        .then((response) => {
          let res = [...data];
          const index =  data.findIndex((item) => (item.classid === response.data.classid ));
          console.log("index" , index)
          res.splice(
             index,
              1
            );
          setData(res);
          // Show the snackbar
          displaySnackBar(true, response.message, { backgroundColor: "green", color: "white" });
        })
        .catch((error) => {
            console.log(error);
            displaySnackBar(true, error.response.data.message, { backgroundColor: "red", color: "white" });
        });
    }
    setShowAlertDialog(false);
  };
  // Function to handle the add class
  const handleClassAdd = (class_obj) => {
      setData((prev_data) => { 
        return [class_obj, ...prev_data];
      })
  }
  //  Render the data
  const renderedClassData = data.map((item,index) => {
    return (
      <StyledTableRow key={item.classid}>
        <StyledTableCell>{index + 1}</StyledTableCell>
        <StyledTableCell>{item.classid}</StyledTableCell>
        <StyledTableCell>{item.dept_code}</StyledTableCell>
        <StyledTableCell>{item.courseID}</StyledTableCell>
        <StyledTableCell>{item.sect}</StyledTableCell>
        <StyledTableCell>{item.year}</StyledTableCell>
        <StyledTableCell>{item.semester}</StyledTableCell>
        <StyledTableCell>{item.limit}</StyledTableCell>
        <StyledTableCell>{item.classSize}</StyledTableCell>
        <StyledTableCell>{item.room}</StyledTableCell>
        <StyledTableCell>{<>
          <IconButton
            color="error"
            onClick={() => {
              handleDeleteResourceBtnClick(item);
            }}
            edge="end"
          >
            <DeleteIcon />
          </IconButton>
        </>}</StyledTableCell>
      </StyledTableRow>
    )
  })
  // Return the JSX
  return (
    <>
      <Grid container>
        <Grid container sx={{ marginY: "10px"}}>
          <Grid item xs={12} md="auto">
          <Typography variant="h6" sx={{ fontWeight: "bold" , marginRight : 2, my : 2 , width: 200 , borderRadius : "30px", mt : 1}}>Total Classes: { data.length}</Typography>
            </Grid>
          <Grid item sx={{ marginLeft: "auto" }}>
            <Button
              variant="contained"
              onClick={() => setShowCourseDialog(true)}
              sx={{ mt: 1 }}
            >
              + Add Class
            </Button>
          </Grid>
        </Grid>
        <AlertDialog
          open={showAlertDialog}
          handleCancel={handleAlertDialogClose}
          message="Do you really want to delete these Class? This process cannot be undone."
        />
        
        <ClassDialog 
          open={showCourseDialog}
          handleClose={() => setShowCourseDialog(false)}
          handleClassAdd={handleClassAdd}
        />
      </Grid>
      <CustomizedTables columns={columnHeading} Data={renderedClassData} />
    </>
  );
}

export default ManageClassPage;
