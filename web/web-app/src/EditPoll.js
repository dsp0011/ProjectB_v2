import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Checkbox from '@material-ui/core/Checkbox';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormGroup from '@material-ui/core/FormGroup';
import TextField from '@material-ui/core/TextField';
import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { getSessionCookie } from './Session';
class EditPoll extends Component {



    constructor(props) {
        super(props);
        this.state = {question: "0",
                      optionA: "0",
                      optionB: "0",
                      timeLimit: "inf",
                      public: false}
    }
    addUserCreatedPoll = () => {
        const xhr = new XMLHttpRequest()
        const pollData = this.makePollJSON()
        const URL = 'http://localhost:8080/users/createPoll/' + getSessionCookie().username
        
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Content-type', 'application/json')
        
        //create JSON string reqeust
        const jsonString = JSON.stringify(pollData)
        // send the request
        xhr.send(jsonString)
    }

    getPollData = (pollID) => {
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            console.log("jsonResponse", jsonResponse)
            this.setState({question: jsonResponse["question"],
                        optionA: jsonResponse["alternative1"],
                        optionB: jsonResponse["alternative2"],
                        timeLimit: jsonResponse["timeLimit"],
                        optionAVotes: jsonResponse["vote"]["alternative1"],
                        optionBVotes: jsonResponse["vote"]["alternative2"],
                        timeLimit: jsonResponse["timeLimit"],
                        checked: jsonResponse["public"],
                    })
                
            
        })
        const URL = 'http://localhost:8080/polls/' + pollID

        xhr.open('GET', URL)
        xhr.send(URL)
    }


    componentDidMount() {
        this.getPollData(this.props.match.params.pollID);
    }
    

    makePollJSON = () => {
        return (
            {
                question : this.state.question,
                alternative1 :this.state.optionA,
                alternative2 :this.state.optionB, 
                creator:getSessionCookie().username,
                timeLimit : this.state.timeLimit,
                public : this.state.public,
                active: "true",
            }
        )

    }
    handleCheckbox = (e) => {
        if (this.state.checked === true)
            this.setState({public : false})
        else 
            this.setState({public : true})

    }
    updatePoll = () => {

        const xhr = new XMLHttpRequest()

        const pollData = this.makePollJSON()
        const URL = 'http://localhost:8080/polls/' + this.props.match.params.pollID
        console.log("URL", URL)
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');
        //create JSON string reqeust
        const jsonString = JSON.stringify(pollData)
        console.log("sending ", jsonString)
        // send the request
        xhr.send(jsonString)
    }


    userCanAccessPoll = () => {
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
                            defaultValue={this.state.question}
                            value = {this.state.question}
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
                            defaultValue={this.state.optionA}
                            value = {this.state.optionA} 
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
                            defaultValue={this.state.optionB}
                            value = {this.state.optionB} 
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
                            defaultValue={this.state.timeLimit}
                            value = {this.state.timeLimit}  
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
                    <FormGroup row>
                        <FormControlLabel
                            style = {{ 
                            position:"relative"   ,
                            top:"18vh",   
                            left: "32vh",
                            }}
                            control={<Checkbox checked = {this.state.checked} onChange={e => {this.handleCheckbox(e);}} name="checkedA" />}
                            label="Public"
                        />
                    </FormGroup>
                    <Button 
                        component={Link}
                        variant="contained"
                        color = "secondary"
                        // to = {"../users/" + getSessionCookie().username}
                        onClick = {e => {this.updatePoll(); this.addUserCreatedPoll();}} // send HTTP request here
                        style = {{ width:"27vh",
                                   position:"relative"   ,
                                   top:"25vh",
                                   right:"5vh"     
                                }}
                    >Edit
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


export default EditPoll;