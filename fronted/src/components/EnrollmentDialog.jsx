import {
    Button,
    Dialog,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    TextField,
    Modal,
    Typography,
    Box,
    Stack,
  } from "@mui/material";

// import { Stack } from "@mui/system";
import { snankBarContext } from "../context/snackBarContext";
import { addEnrollment } from "../services/enrollmentApi";
import { addCourse } from "../services/courseApi";
import { useForm } from 'react-hook-form';
import { useContext } from "react";

  export default function EnrollmentDialog({ open,handleClose,handleAddCourse }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm();
    // displaySnackBar function from snackBarContext
  const { displaySnackBar } = useContext(snankBarContext);
  // Function to handle the form submit
    const onSubmit = (data) => {
      // Call the addEnrollment API
        addEnrollment(data).then((response) => {
            let api_response = response.data;
            api_response["score"] = 0
            // Call the handleAddCourse function to update the state
            handleAddCourse(response.data);
            // Display the success message
            displaySnackBar(true, response.message, { backgroundColor: "green", "color": "white"});
            reset();
            handleClose();
        }).catch((error) => {
          // Display the error message
          displaySnackBar(true, error.response.data.message, { backgroundColor: "red", "color": "white"});
          console.log('error');
          console.log(error)
        })

    };
    const style = {
        position: 'absolute',
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        width: 400,
        bgcolor: 'background.paper',
        // border: '2px solid #000',

        boxShadow: 24,
        pt: 2,
        px: 4,
        pb: 3,
      };
      
    return (
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        >
        <Box sx={style} onSubmit={handleSubmit(onSubmit)}>
        <DialogTitle id="alert-dialog-title">Enroll Student to Class</DialogTitle>
            {/* <Typography id="modal-modal-description" sx={{ mt: 2 }}> </Typography> */}
            <Box component="form">

            <Stack spacing={2} direction={"column"} >
                <TextField   {...register('bID')} type="text" placeholder="Enter Student Id" label="Student Id" variant="outlined" size="small"/>
                <TextField {...register('classID')} type="text" placeholder="Enter Class Id" label="Class Id" variant="outlined" size="small"/>
                </Stack>
           
            <DialogActions sx={{ display: "flex", justifyContent: "center" }}>
             <Button onClick={() => {
                reset()
                handleClose()
             }} variant="contained" color="warning" type="button">
           Cancel
         </Button>
             <Button
             type="submit"
             variant="contained"
             autoFocus
             color="primary"
             >
              Enroll
     </Button>
           </DialogActions>
                </Box>
        </Box>
        </Modal>
    );
  }
  