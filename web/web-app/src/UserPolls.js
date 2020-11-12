import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormGroup from '@material-ui/core/FormGroup';
import Paper from '@material-ui/core/Paper';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import React, { Component } from "react";
import TextField from '@material-ui/core/TextField';
import { deleteSessionCookie, getSessionCookie } from "./Session.js";

class UserPolls extends Component {
    constructor(props) {
        super(props);
        this.state = {participatedPolls : [],
                      createdPolls : [],
                      isLoading : true,
                      checked : false
                        }
    }

    getUserPollsParticipated = (username) => {
        const xhr = new XMLHttpRequest()
        xhr.addEventListener('load', () => {

            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            console.log("participatedPolls: ", jsonResponse)
            this.state.participatedPolls = jsonResponse
            this.setState({isLoading :false})
            
        })
        
        // const URL = 'http://localhost:8080/users/' + username
        const URL = 'http://localhost:8080/test/' + getSessionCookie().username
        xhr.open('GET', URL)
        
        xhr.send(URL)
    }

    getUserPollsCreated = (username) => {
        const xhr = new XMLHttpRequest()
        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            console.log("jsonResponse: ", jsonResponse)
            this.state.createdPolls = jsonResponse
            this.setState({isLoading :false})
            
        })
        const URL = 'http://localhost:8080/polls?username=' + getSessionCookie().username
        // const URL = 'http://localhost:8080/users/' + username
        xhr.open('GET', URL)
        xhr.send(URL)
    }


    componentDidMount() {
        this.getUserPollsParticipated(this.props.match.params.username)
        this.getUserPollsCreated(this.props.match.params.username)

    }
    getUserPollParticiaptedAsRows = () => {
        const rows = []
        for (const entry of this.state.participatedPolls) {
            rows.push(this.createData(
                        entry.id,
                        entry.question,
                        entry.public,
                        entry.alternative1,
                        entry.alternative2,
                        entry.active
            ))
        }
        return rows
    }

    sendCloseRequest = (e) => {

        const xhr = new XMLHttpRequest()

        const URL = 'http://localhost:8080/polls/' + this.state.pollIDClose
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');
        //create JSON string reqeust
        const jsonString = JSON.stringify({closePoll: "True"})
        console.log("sending ", jsonString)
        // send the request
        xhr.send(jsonString)

    }

    getUserPollsCreatedAsRows = () => {
        const rows = []
        if (this.state.createdPolls != undefined) {
            for (const entry of this.state.createdPolls) {
                rows.push(this.createData(
                            entry.id,
                            entry.question,
                            entry.public,
                            entry.alternative1,
                            entry.alternative2,
                            entry.active
                ))
            }
        }

        return rows

    }



    handleCheckbox = (e) => {
        if (this.state.checked === true)
            this.setState({checked : false})
        else 
            this.setState({checked : true})

    }
    createData = (pollID, question, isPublic, optionA, optionB, active) => {
        return { pollID, question, isPublic, optionA, optionB, active };
      }
      
    renderTable = () => {
        var rows = []
        if (this.state.checked)
            rows = this.getUserPollParticiaptedAsRows()
        else 
            rows = this.getUserPollsCreatedAsRows()
        return ( 
        <TableContainer component={Paper} style={{ width: "90%",
                                                   top: "20vh",
                                                   right: "45vh", 
                                                   position: "relative"
                                                    }}>
            <Table aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell
                        > <h3>Poll ID</h3></TableCell>
                        <TableCell align="right"><h3>Question</h3></TableCell>
                        <TableCell align="right"><h3>Active</h3></TableCell>
                        <TableCell align="right"><h3>Public</h3></TableCell>
                        <TableCell align="right"><h3>Option A</h3></TableCell>
                        <TableCell align="right"><h3>Option B</h3></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                {rows.map((row) => (
                    <TableRow
                    onMouseDown = {e =>{ this.sendCloseRequest(e)}}
                    onMouseDown = {e =>{ this.props.history.push("../../edit/" + row.pollID)}}
                    key={row.pollID}>
                    <TableCell component="th" scope="row">
                        {row.pollID}
                    </TableCell>
                    <TableCell align="right">{row.question}</TableCell>
                    <TableCell
                    align="right">{row.active.toString()}</TableCell>
                    <TableCell align="right">{row.isPublic.toString()}</TableCell>
                    <TableCell align="right">{row.optionA}</TableCell>
                    <TableCell align="right">{row.optionB}</TableCell>
                    </TableRow>
                ))}
                </TableBody>
            </Table>
            </TableContainer> 
        );
    }
    render() {

        {
            if (getSessionCookie().username == this.props.match.params.username) {
                return (
                    <div>
                    <Box
                        bgcolor="primary.dark" 
                        display="flex"
                        justifyContent="flex-start"
                        alignItems="flex-start"
                        minHeight="100vh"
                    >
                    <FormGroup row>
                        <FormControlLabel
                            style = {{ 
                            position:"relative"   ,
                            top:"12vh",   
                            left: "60vh",
                            color: "white"  
                            }}
                            control={<Checkbox style = {{color: "white"  }}checked = {this.state.checked} onChange={e => {this.handleCheckbox(e);}} name="checkedA" />}
                            label="Participated Polls"
                        />
                    </FormGroup>
                        <Typography variant="h4"
                        style = {{ top:"12vh",
                                    left:"25vh",
                                    position:"relative",
                                    color: "white",
                                    width: "30vh"
                         }}
                        >
                            My polls
                        </Typography>  
                        <TextField 
                            id="outlined-basic" 
                            label="ID" 
                            variant="standard" 
                            style = {{ 
                                width:"10vh",
                                position:"relative"   ,
                                left: "67vh",
                                top:"11vh",
                                color: "white"   
                            }}
                            onChange = {e => {this.setState({ pollIDClose: e.target.value});}}
                        />
        
                        <Button 
                        variant="contained"
                        color = "secondary"
                        onMouseDown = {e =>{ this.props.history.push("../../create")}}
                        style = {{ width:"20vh",
                                position:"relative"   ,
                                top:"12vh",   
                                left: "102vh"  
                                }}
                        >New poll
                        </Button>
                        <Button 
                        variant="contained"
                        color = "secondary"
                        onMouseDown = {e =>{this.sendCloseRequest()}}
                        style = {{ width:"23vh",
                                position:"relative"   ,
                                top:"12vh",   
                                left: "56vh"  
                                }}
                        >Close a poll
                        </Button>
                        <Button 
                        variant="contained"
                        color = "secondary"
                        onMouseDown = {e =>{deleteSessionCookie(); this.props.history.push("/")}}
                        style = {{ width:"20vh",
                                position:"relative"   ,
                                top:"12vh",   
                                left: "57vh"  
                                }}
                        >Log out
                        </Button>
                        {this.renderTable()}

         
                    </Box>,
                   
                 </div>
        
                );
            } else {
                {alert("Unauthorized access, redirecting to main page...")}
                this.props.history.push("/");
                return (null);
            }
        }

    }
}


export default UserPolls;
