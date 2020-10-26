import React, { Component } from 'react';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';
import PropTypes from 'prop-types';
import {Link, LinkProps} from "react-router-dom";
import { getSessionCookie } from './Session';
class PollCreate extends Component {



    constructor(props) {
        super(props);
        this.state = {question: "0",
                      optionA: "0",
                      optionB: "0",
                      timeLimit: "0",
                      public: false}
    }

    // this.addUserCreatedPoll(); this.createPoll()
    componentDidMount() {
        console.log("mounted cookie ", getSessionCookie())
    }
    
    addUserCreatedPoll = () => {
        const xhr = new XMLHttpRequest()
        const pollData = this.makePollJSON()
        console.log("session cookie, addUserCreatedPoll ", getSessionCookie())
        const URL = 'http://localhost:8080/users/' + getSessionCookie().username
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');
        console.log("URL: ", URL)
        //create JSON string reqeust
        const jsonString = JSON.stringify( {pollsCreated: [pollData]})
        console.log("jsonString", jsonString )
        // send the request
        xhr.send(jsonString)
    }
    makePollJSON = () => {

        return (
            {
                question : this.state.question,
                alternative1 :this.state.optionA,
                alternative2 :this.state.optionB,
                // creator : {userName: getSessionCookie().username},
                creator: null,
                timeLimit : this.state.timeLimit,
                isPublic : false,
                vote : {
                    alternative1 : "0",
                    alternative2 : "0"
                }
            }
        )

    }

    createPoll = () => {

        const xhr = new XMLHttpRequest()

        const pollData = this.makePollJSON()
        console.log("pollData")
        const URL = 'http://localhost:8080/polls/'
        xhr.open('POST', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');
        //create JSON string reqeust
        const jsonString = JSON.stringify(pollData)
        // send the request
        xhr.send(jsonString)
    }

    sendUserPollCreateedUpdate = () => {
        if (getSessionCookie() == "anonymous")
            return;
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            console.log(xhr.data)

        })
        const pollData = this.state.poll 
        console.log("pollData")
        const URL = 'http://localhost:8080/users/' + getSessionCookie().username
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');
        console.log("poll data", pollData)
        //create JSON string reqeust
        const jsonString = JSON.stringify( {pollsVotedOn: [pollData]})
        // send the request
        xhr.send(jsonString)
    }

    // TODO FIX BUG username resets 
    componentDidMount() {
        
        console.log("woooooooooow", getSessionCookie())
    }

    userCanAccessPoll = () => {
        console.log("getSessionCookie(), ", getSessionCookie())
        // return (getSessionCookie() !== "anonymous")
        return true
    }
   
    render() {
        if (this.userCanAccessPoll()) {
            return (
                <div>
                <Box
                    bgcolor="primary.dark" 
                    display="flex"
                    justifyContent="center"
                    alignItems="center"
                    minHeight="100vh"
        
                >
     
                    <Box
                        bgcolor="primary.main" 
                        justifyContent="center"
                        alignItems="flex-top"
                        height = "60vh"
                        width = "60vh"
                        style = {{ border: '3px solid',
                                position:"absolute"   ,
                     }}
    
                    >
                        <Box
                        bgcolor="secondary.dark" 
                        justifyContent="center"
                        alignItems="flex-top"
                        height = "10vh"
                        width = "60vh"
                        style = {{ 
                                position:"absolute",
                                borderBottom: '3px solid',
                                background: 'linear-gradient(to right bottom, #00363a, #6d6d6d)'
    
                     }}
    
                    ></Box>
                        <Typography variant="h4"
                            style = {{ top:"3vh",
                                        left:"2vh",
                                        position:"absolute",
                             }}
                        >
                        Poll   {this.props.match.params.pollID }
                        </Typography>
    
                        <TextField 
                            id="outlined-basic" 
                            label="Question" 
                            variant="filled" 
                            inputProps={{ style: { textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'center', fontSize: 35}}}
                            onChange = {e => {this.setState({ question: e.target.value});}}
                            style = {{ top:"14vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "50vh" }}
                        />
                        <TextField 
                            id="outlined-basic" 
                            label="Option A" 
                            variant="outlined" 
                            inputProps={{ style: { textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'center', fontSize: 20}}}
                            onChange = {e => {this.setState({ optionA: e.target.value});}}
                            style = {{ top:"19vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "35vh" }}
                        />
                        <TextField 
                            id="outlined-basic" 
                            label="Option B" 
                            variant="outlined" 
                            inputProps={{ style: {textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'left', fontSize: 20}}}
                            onChange = {e => {this.setState({ optionB: e.target.value});}}
                            style = {{ top:"22vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "35vh" }}
    
                        />
                        <TextField 
                            id="outlined-basic" 
                            label="Time limit" 
                            variant="outlined" 
                            inputProps={{ style: { textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'center', fontSize: 20}}}
                            onChange = {e => {this.setState({ timeLimit: e.target.value});}}
                            style = {{ top:"25vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "35vh" }}
                        />
                       
                    </Box>,
                    <Button 
                        component={Link}
                        variant="contained"
                        color = "secondary"
                        // to = {"../users/" + getSessionCookie().username}
                        onClick = {e => {this.createPoll(); this.addUserCreatedPoll();}} // send HTTP request here
                        style = {{ width:"27vh",
                                   position:"relative"   ,
                                   top:"25vh",     
                                }}
                    >Publish poll
                    </Button>
 

                </Box>,
               
             </div>
    
            );
        } else {
            {alert("Unauthorized access, redirecting to main page...")}
            return (null);

        }
       
    }
}


export default PollCreate;