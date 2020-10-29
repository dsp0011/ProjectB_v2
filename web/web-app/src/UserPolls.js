import React, { Component, useEffect, useState, useContext } from "react";
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';
import PropTypes from 'prop-types';
import { SessionContext, getSessionCookie, deleteSessionCookie, updateSessionCookie } from "./Session.js";
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import FormGroup from '@material-ui/core/FormGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';

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
        console.log("username: ", username)
        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            console.log("jsonResponse, ", jsonResponse.pollsVotedOn)
            this.state.participatedPolls = jsonResponse.pollsVotedOn
            console.log("state: ", this.state)
            this.setState({isLoading :false})
            
        })
        const URL = 'http://localhost:8080/users/' + username
        console.log("URL: ", URL)
        xhr.open('GET', URL)
        xhr.send(URL)
    }

    getUserPollsCreated = (username) => {
        const xhr = new XMLHttpRequest()
        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            console.log("jsonResponse, ", jsonResponse.pollsCreated)
            this.state.createdPolls = jsonResponse.pollsCreated
            console.log("state: ", this.state)
            this.setState({isLoading :false})
            
        })
        const URL = 'http://localhost:8080/users/' + username
        console.log("URL: ", URL)
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
            console.log("entry: ", entry)
            rows.push(this.createData(
                        entry.id,
                        entry.question,
                        entry.public,
                        entry.alternative1,
                        entry.alternative2
            ))
        }
        return rows

    }

    getUserPollsCreatedAsRows = () => {
        const rows = []
        console.log( "this.state.createdPolls ", this.state.createdPolls)
        if (this.state.createdPolls != undefined) {
            for (const entry of this.state.createdPolls) {
                console.log("entry: ", entry)
                rows.push(this.createData(
                            entry.id,
                            entry.question,
                            entry.public,
                            entry.alternative1,
                            entry.alternative2
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
    createData = (pollID, question, isPublic, optionA, optionB) => {
        return { pollID, question, isPublic, optionA, optionB };
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
                        <TableCell> <h3>Poll ID</h3></TableCell>
                        <TableCell align="right"><h3>Question</h3></TableCell>
                        <TableCell align="right"><h3>Public</h3></TableCell>
                        <TableCell align="right"><h3>Option A</h3></TableCell>
                        <TableCell align="right"><h3>Option B</h3></TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                {rows.map((row) => (
                    <TableRow key={row.pollID}>
                    <TableCell component="th" scope="row">
                        {row.pollID}
                    </TableCell>
                    <TableCell align="right">{row.question}</TableCell>
                    <TableCell align="right">{row.isPublic}</TableCell>
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
                        control={<Checkbox checked = {this.state.checked} onChange={e => {this.handleCheckbox(e);}} name="checkedA" />}
                        label="Created Polls"
                    />
                    </FormGroup>
                        <Typography variant="h4"
                        style = {{ top:"10vh",
                                    left:"15vh",
                                    position:"relative",
                                    color: "white"
                         }}
                        >
                            My polls
                        </Typography>               
                        <Button 
                        variant="contained"
                        color = "secondary"
                        onMouseDown = {e =>{ this.props.history.push("../../create")}}
                        style = {{ width:"30vh",
                                position:"relative"   ,
                                top:"10vh",   
                                left: "121vh"  
                                }}
                        >Create a new poll
                        </Button>
                        <Button 
                        variant="contained"
                        color = "secondary"
                        onMouseDown = {e =>{deleteSessionCookie(); this.props.history.push("/")}}
                        style = {{ width:"30vh",
                                position:"relative"   ,
                                top:"10vh",   
                                left: "62vh"  
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
