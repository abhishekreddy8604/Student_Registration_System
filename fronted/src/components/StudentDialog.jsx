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

import {addStudent } from "../services/resourceApi";
import { useForm } from 'react-hook-form';
import { useContext } from "react";

  export default function StudentDialog({ open,handleClose,handleAddStudent }) {
    const { register, handleSubmit, reset, formState: { errors } } = useForm();

  const { displaySnackBar } = useContext(snankBarContext);
    // displaySnackBar(true, "gg boi", { color: "success" });
    const onSubmit = (data) => {
        console.log(data);
        // Call the addStudent API
        addStudent(data).then((response) => {
            console.log(response)
            //  Call the handleAddStudent function to update the state
            handleAddStudent(response.data);
            displaySnackBar(true, response.message, { backgroundColor: "green", "color": "white"});
        handleClose();
        }).catch((error) => {
            displaySnackBar(true, error.response.data.message, { backgroundColor: "red", "color": "white"});
            console.log('error');
            console.log(error)
        })
        // Call API to submit data
        // clear the form
        // reset();

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
      // Return the Modal
    return (
        <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        >
        <Box sx={style} onSubmit={handleSubmit(onSubmit)}>
        <DialogTitle id="alert-dialog-title">Insert Data</DialogTitle>
            {/* <Typography id="modal-modal-description" sx={{ mt: 2 }}> </Typography> */}
            <Box component="form">

            <Stack spacing={2} direction={"column"} >
                <TextField   {...register('bNumber')} type="text" required placeholder="Enter b#" label="Enter b#" variant="outlined" size="small"/>
                <TextField {...register('firstName')} type="text" placeholder="First Name" label="First Name" variant="outlined" size="small"/>
                <TextField {...register('lastName')} type="text" placeholder="LastName" label="LastName Name" variant="outlined"size="small" />
                <TextField  {...register('email')}  type="email" placeholder="Email" label="Email" variant="outlined"size="small" />
                <TextField {...register('studentLevel')} type="text" placeholder="type" label="Student Level" variant="outlined"size="small" />
                <TextField {...register('birthDate')} type="date" required placeholder="birthDate" variant="outlined" size="small"/>
                <TextField {...register('gpa')} type="number" placeholder="gpa" label="gpa" variant="outlined" size="small"  inputProps={{step: 'any'}}/>
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
                Insert Student
     </Button>
           </DialogActions>
                </Box>
        </Box>
        </Modal>
    );

    // return (
    //   <div>
    //     <Dialog
    //       open={open}
    //       onClose={() => handleCancel(false)}
    //       aria-labelledby="alert-dialog-title"
    //       aria-describedby="alert-dialog-description"
    //       square
    //     >
    //       <DialogTitle id="alert-dialog-title">Insert Data</DialogTitle>
    //       <DialogContent>
    //         <DialogContentText id="alert-dialog-description">
    //             <Stack spacing={2} direction={"column"}>
    //             <TextField type="text" placeholder="Enter b#" label="Enter b#" variant="outlined" />
    //             <TextField type="text" placeholder="First Name" label="First Name" variant="outlined" />

    //             </Stack>

    //           {/* <input type="text" placeholder="Enter Name" /> */}
    //         </DialogContentText>
    //       </DialogContent>
    //       <DialogActions sx={{ display: "flex", justifyContent: "center" }}>
    //         <Button onClick={() => handleCancel("NO")} variant="contained">
    //           Cancel
    //         </Button>
    //         <Button
    //           onClick={() => handleCancel("YES")}
    //           variant="contained"
    //           autoFocus
    //           color="error"
    //         >
    //             Insert
    //         </Button>
    //       </DialogActions>
    //     </Dialog>
    //   </div>
    // );
  }
  