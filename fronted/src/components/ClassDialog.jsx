import {
    Button,
    DialogActions,
    DialogContent,
    DialogContentText,
    DialogTitle,
    TextField,
    Modal,
    Box,
    Stack,
  } from "@mui/material";

// import { Stack } from "@mui/system";
import { snankBarContext } from "../context/snackBarContext";

import { addClass } from "../services/classesApi";
import { useForm } from 'react-hook-form';
import { useContext } from "react";

  export default function ClassDialog({ open,handleClose,handleClassAdd }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm();
    // displaySnackBar function from snackBarContext
  const { displaySnackBar } = useContext(snankBarContext);
    const onSubmit = (data) => {
      // Call the addClass API
        addClass(data).then((response) => {
            // Call the handleClassAdd function to update the state
            handleClassAdd(response.data);
            displaySnackBar(true, response.message, { backgroundColor: "green", "color": "white"});
            reset();
            // Close the dialog
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
        <DialogTitle id="alert-dialog-title">Add Class</DialogTitle>
            <Box component="form">
            <Stack spacing={2} direction={"column"} >
                <TextField  {...register('classid')} type="text" placeholder="Enter Class Id" label="Class Id" variant="outlined" size="small"/>
                <TextField  {...register('dept_code')} type="text" placeholder="Enter Department Name" label="Department Name" variant="outlined" size="small"/>
                <TextField  {...register('courseID')} type="number" placeholder="Enter Course Code" label="Course Code" variant="outlined" size="small"/>
                <TextField  {...register('sect')} type="number" placeholder="Enter Section" label="Section" variant="outlined" size="small"/>
                <TextField  {...register('year')} type="number" placeholder="Enter Year" label="Year" variant="outlined" size="small"/>
                <TextField  {...register('semester')} type="text" placeholder="Enter Semester" label="Semester" variant="outlined" size="small"/>
                <TextField  {...register('limit')} type="number" placeholder="Enter Limit" label="Limit" variant="outlined" size="small"/>
                <TextField  {...register('classSize')} type="number" placeholder="Enter Class Size" label="Class Size" variant="outlined" size="small"/>
                <TextField  {...register('room')} type="text" placeholder="Enter Room" label="Room" variant="outlined" size="small"/>
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
                Add Class
     </Button>
           </DialogActions>
                </Box>
        </Box>
        </Modal>
    );
  }
  