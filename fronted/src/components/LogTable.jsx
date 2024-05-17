import { styled } from '@mui/material/styles';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell, { tableCellClasses } from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';

// Customized Table Cell
const StyledTableCell = styled(TableCell)(({ theme }) => ({
  [`&.${tableCellClasses.head}`]: {
    backgroundColor: theme.palette.common.black,
    color: theme.palette.common.white,
  },
  [`&.${tableCellClasses.body}`]: {
    fontSize: 14,
  },
}));
// Customized Table Row
const StyledTableRow = styled(TableRow)(({ theme }) => ({
  '&:nth-of-type(odd)': {
    backgroundColor: theme.palette.action.hover,
  },
  // hide last border
  '&:last-child td, &:last-child th': {
    border: 0,
  },
}));

// LogTable Component
export default function LogTable({columns, data}) {
  console.log(data)
  return (
    <TableContainer component={Paper} sx={{ maxWidth: {
      md : "100%",
      sm : "100%",
      xs : "350px"
    }}}>
      <Table aria-label="customized table">
        <TableHead>
          <TableRow>
            {
                columns.map((item) => {
                    return <StyledTableCell key={item}>{item}&nbsp;</StyledTableCell>
                })
            }
          </TableRow>
        </TableHead>
        <TableBody>
          {data.map((row,index) => (
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
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    
  );
}