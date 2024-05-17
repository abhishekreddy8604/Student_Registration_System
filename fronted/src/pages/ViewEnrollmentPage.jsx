import { useState, useEffect,useContext} from "react";
import CustomizedTables from "../components/Table";

import { snankBarContext } from "../context/snackBarContext";
import { StyledTableRow , StyledTableCell} from "../components/Table";
import { getEnrollments ,dropEnrollment } from "../services/enrollmentApi";
import EnrollmentDialog from "../components/EnrollmentDialog";
import DeleteIcon from "@mui/icons-material/Delete";
import AlertDialog from "../components/Dialog";


import {
  Typography,
  Button,
  IconButton,
  Grid,
  Autocomplete,
  TextField,
} from "@mui/material";
// Column Heading for the Table
const columnHeading = [
  "Sr No","Class Id","Student Id","Score","Actions"
]
function ViewEnrollmentPage() {
  const [data, setData] = useState([]);
  const [showEnrollmentDialog, setShowEnrollmentDialog] = useState(false);
  
  const [showAlertDialog, setShowAlertDialog] = useState(false);
  const [dropEnrollmentId, setDropEnrollmentId] = useState(null);

  const { displaySnackBar } = useContext(snankBarContext);
  //  Fetch the data from the API Enrollments
  useEffect(() => {
    console.log("called")
    getEnrollments()
      .then((response) => setData(response.data))
      .catch((error) => console.log(error));
  }, []);

  const handleAlertDialogClose = (value) => {
    if (value === "YES") {
        const [student_id, class_id] = dropEnrollmentId.split(",");
        // Call the delete API dropEnrollment
        dropEnrollment(student_id, class_id)
        .then((response) => {
          // Remove the enrollment from the data
          let res = [...data];
          const index =  data.findIndex((item) => (item.bID === response.data.bID && item.classID === response.data.classID));
          console.log("index" , index)
          res.splice(
             index,
              1
            );
            // Update the state
          setData(res);
          displaySnackBar(true, response.message, { backgroundColor: "green", color: "white" });
        })
        .catch((error) => {
            console.log(error);
            displaySnackBar(true, error.response.data.message, { backgroundColor: "red", color: "white" });
        });
    }
    setShowAlertDialog(false);
  };
    const renderedData = data.map((item,index) => {
    return (
      <StyledTableRow key={item.id}>
        <StyledTableCell>{index + 1}</StyledTableCell>
        <StyledTableCell>{item.classID}</StyledTableCell>
        <StyledTableCell>{item.bID}</StyledTableCell>
        <StyledTableCell>{item.score}</StyledTableCell>
        <StyledTableCell>{<>
          <IconButton
            color="error"
            onClick={() => {
            setShowAlertDialog(true);
            setDropEnrollmentId(`${item.bID},${item.classID}`);
            }}
            edge="end"
          >
            <DeleteIcon />
          </IconButton>
        </>}</StyledTableCell>
      </StyledTableRow>
    )
    })

    return (
    <>
     <Grid container>
        <Grid container sx={{ marginY: "10px"}}>
          <Grid item xs={12} md="auto">
          <Typography variant="h6" sx={{ fontWeight: "bold" , marginRight : 2, my : 2 , width: 200 , borderRadius : "30px", mt : 1}}>Total Courses: { data.length}</Typography>
            </Grid>
          <Grid item sx={{ marginLeft: "auto" }}>
            <Button
              variant="contained"
              onClick={() => setShowEnrollmentDialog(true)}
              sx={{ mt: 1 }}
            >
              + Enroll Student to Class
            </Button>
          </Grid>
        </Grid>
        <AlertDialog
          open={showAlertDialog}
          handleCancel={handleAlertDialogClose}
          message="Do you really want to delete these Course? This process cannot be undone."
        />
        
        <EnrollmentDialog 
          open={showEnrollmentDialog}
          handleClose={() => setShowEnrollmentDialog(false)}
          handleAddCourse={(data) => { 
            setData((prev_data) => { 
              return [data, ...prev_data];
            })
          }}
        />
      </Grid>
      <CustomizedTables columns={columnHeading} Data={renderedData} />
    </>
  );
}

export default ViewEnrollmentPage;
