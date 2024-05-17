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
import EditIcon from "@mui/icons-material/Edit";
import DeleteIcon from "@mui/icons-material/Delete";

import AlertDialog from "../components/Dialog";
import CustomizedTables from "../components/Table";
import { snankBarContext } from "../context/snackBarContext";

import { resultOptions, typeOptions, columnHeading } from "../constants";

import { getStudents, deleteStudent } from "../services/resourceApi";
import StudentDialog from "../components/StudentDialog";
import { StyledTableRow , StyledTableCell} from "../components/Table";

function ManageStudentPage() {
  const [data, setData] = useState([]);
  const [error, setError] = useState(null);

  const [showAlertDialog, setShowAlertDialog] = useState(false);
  const [deleteStudentId, setDeleteStudentId] = useState(null);



  const [showStudentDialog, setShowStudentDialog] = useState(false);

  const { displaySnackBar } = useContext(snankBarContext);
  // Fetch the data from the API
  useEffect(() => {
    console.log("called")
    getStudents()
      .then((response) => setData(response.data))
      .catch((error) => setError(error.data));
  }, []);

  // Function to handle the delete button click
  const handleDeleteResourceBtnClick = (student) => {
    setShowAlertDialog(true);
    setDeleteStudentId(student.bNumber);
  };
  // Function to handle the close of the alert dialog
  const handleAlertDialogClose = (value) => {
    if (value === "YES") {
      // Call the delete student API
      deleteStudent(deleteStudentId)
        .then((response) => {
          // Remove the student from the data
          let res = [...data];
          res.splice(
            data.findIndex((item) => item.bNumber === deleteStudentId),
            1
          );
          setData([...res]);
          // Show the snackbar
          displaySnackBar(true, response.message, { backgroundColor: "green", color: "white" });
        })
        .catch((error) => {
          console.log(error);
        });
    }
    setShowAlertDialog(false);
  };

  const handleAddStudent = (student) => {
      console.log(student)
      setData((prev_data) => { 
        return [student, ...prev_data];
      })
  }
  // Render the data
  const resourceData = data.map((student) => {
    return {
      bNumber: student.bNumber,
      firstName: student.firstName,
      lastName: student.lastName,
      studentLevel: student.studentLevel,
      gpa: student.gpa,
      birthDate : student.birthDate,
      email : student.email,
      action_delete: (
        <>
          <IconButton
            color="error"
            onClick={() => {
              handleDeleteResourceBtnClick(student);
            }}
            edge="end"
          >
            <DeleteIcon />
          </IconButton>
        </>
      ),
    };
  });
  // Render the data
  const renderedStudentData = resourceData.map((row,index) => (
    <StyledTableRow key={row.bNumber}> 
      <StyledTableCell>{index + 1}</StyledTableCell>
      <StyledTableCell>{row.bNumber}</StyledTableCell>
      <StyledTableCell>{row.firstName}</StyledTableCell>
      <StyledTableCell>{row.lastName}</StyledTableCell>
      <StyledTableCell>{row.studentLevel}</StyledTableCell>
      <StyledTableCell>{row.gpa}</StyledTableCell>
      <StyledTableCell>{row.email}</StyledTableCell>
      <StyledTableCell>{row.birthDate}</StyledTableCell>
      <StyledTableCell>{row.action_delete}</StyledTableCell>
      </StyledTableRow>
  ))

  return (
    <>
      <Grid container>
        <Grid container sx={{ marginY: "10px"}}>
          <Grid item xs={12} md="auto">
          <Typography variant="h6" sx={{ fontWeight: "bold" , marginRight : 2, my : 2 , width: 200 , borderRadius : "30px", mt : 1}}>Total Student: { data.length}</Typography>
            </Grid>
          <Grid item sx={{ marginLeft: "auto" }}>
            <Button
              variant="contained"
              // disableElevation
            
              // LinkComponent={NavLink}
              // to="/resource/add"
              // state={{
              //   title: "Manage Resource",
              //   result: resultSelection,
              //   type: typeSelection,
              // }}
              onClick={() => setShowStudentDialog(true)}
              sx={{ mt: 1 }}
            >
              + Add Student
            </Button>
          </Grid>
        </Grid>
        <AlertDialog
          open={showAlertDialog}
          handleCancel={handleAlertDialogClose}
          message="Do you really want to delete these Student? This process cannot be undone."
        />
        
        <StudentDialog 
          open={showStudentDialog}
          handleClose={() => setShowStudentDialog(false)}
          handleAddStudent={handleAddStudent}
        />
      </Grid>
      <CustomizedTables columns={columnHeading} Data={renderedStudentData} />
    </>
  );
}

export default ManageStudentPage;
