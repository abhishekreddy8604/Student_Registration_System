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

import { addCourse } from "../services/courseApi";
import { useForm } from 'react-hook-form';
import { useContext } from "react";

  export default function CourseDialog({ open,handleClose,handleAddCourse }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm();
    // displaySnackBar function from snackBarContext
  const { displaySnackBar } = useContext(snankBarContext);
  // Function to handle the form submit
    const onSubmit = (data) => {
      addCourse(data).then((response) => {
        // Call the handleAddCourse function to update the state
            handleAddCourse(response.data);
            // Display the success message
            displaySnackBar(true, response.message, { backgroundColor: "green", "color": "white"});
            reset();
            handleClose();
        }).catch((error) => {
          displaySnackBar(true, error.response.data.message, { backgroundColor: "red", "color": "white"});
          console.log('error');
          console.log(error)
        })

    };
    // Style for the Modal
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
      // Return the Modal
    return (
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        >
        <Box sx={style} onSubmit={handleSubmit(onSubmit)}>
        <DialogTitle id="alert-dialog-title">Add Course</DialogTitle>
            {/* <Typography id="modal-modal-description" sx={{ mt: 2 }}> </Typography> */}
            <Box component="form">

            <Stack spacing={2} direction={"column"} >
                <TextField   {...register('dept_code')} type="text" placeholder="Enter Department Name" label="Department Name" variant="outlined" size="small"/>
                <TextField {...register('courseID')} type="number" placeholder="Enter Course Code" label="Course Code" variant="outlined" size="small"/>
                <TextField {...register('title')} type="text" placeholder="Enter Title" label="Title" variant="outlined"size="small" />
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
                Add Course
     </Button>
           </DialogActions>
                </Box>
        </Box>
        </Modal>
    );
  }
  