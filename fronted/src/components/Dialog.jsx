import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
} from "@mui/material";

// Function to display the Alert Dialog
export default function AlertDialog({ open, message, handleCancel }) {
  // Return the Dialog
  return (
    <div>
      <Dialog
        open={open}
        onClose={() => handleCancel(false)}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
        square
      >
        <DialogTitle id="alert-dialog-title">Are you sure?</DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {message}
          </DialogContentText>
        </DialogContent>
        <DialogActions sx={{ display: "flex", justifyContent: "center" }}>
          <Button onClick={() => handleCancel("NO")} variant="contained">
            Cancel
          </Button>
          <Button
            onClick={() => handleCancel("YES")}
            variant="contained"
            autoFocus
            color="error"
          >
            Delete
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
