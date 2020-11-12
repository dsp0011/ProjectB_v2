import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React, { Component } from 'react';
import { Link } from "react-router-dom";
import { getSessionCookie } from './Session';
class PollParticipate extends Component {



    constructor(props) {
        super(props);
        this.state = {question: "0",
                      optionA: "0",
                      optionB: "0",
                      timeLimit: "0",
                      public: false}
    }

  
    sendVoteUpdate = (alternative) => {
        const xhr = new XMLHttpRequest()
        const URL = 'http://localhost:8080/votes/' + this.props.match.params.pollID

        xhr.open('PUT', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');

        var jsonString = "";
        (alternative === 1) ?  jsonString = JSON.stringify( {alternative1: 1}) : jsonString = JSON.stringify( {alternative2: 1})

        xhr.send(jsonString)

    }

    sendUserPollVotedUpdate = () => {
        if (getSessionCookie() === "anonymous")
            return;
        const xhr = new XMLHttpRequest()
        const pollData = this.state.poll
        pollData.public = this.state.public
        const URL = 'http://localhost:8080/users/participatePoll/' + getSessionCookie().username
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Content-Type', 'application/json');
        //create JSON string request
        const jsonString = JSON.stringify(this.makePollJSON())
        // send the request
        xhr.send(jsonString)
    }

    makePollJSON = () => {
        return (
            {
                pollID : this.state.pollID



                /*
                question : this.state.question,
                pollID : this.state.pollID,
                alternative1 :this.state.optionA,
                alternative2 :this.state.optionB,
                creator:getSessionCookie().username,
                timeLimit : this.state.timeLimit,
                public : this.state.public
                 */
            }
        )

    }

    getPollData = (pollID) => {
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            this.setState({question: jsonResponse["question"],
                        optionA: jsonResponse["alternative1"],
                        optionB: jsonResponse["alternative2"],
                        timeLimit: jsonResponse["timeLimit"],
                        public: jsonResponse["public"],
                        poll : jsonResponse,
                        pollID : jsonResponse["id"]
                    })
        })
        const URL = 'http://localhost:8080/polls/' + pollID

        xhr.open('GET', URL)
        // send the request
        xhr.send(URL)
    }

    componentDidMount() {
        this.getPollData(this.props.match.params.pollID); 
    }

    userCanAccessPoll = () => {
        return this.state.public
               || !this.state.public &&  getSessionCookie() !== "anonymous"
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
                                background: 'linear-gradient(to right bottom, #d7ccc8, #a69b97)'

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
                            defaultValue={this.state.question}
                            value= {this.state.question}
                            variant="filled" 
                            inputProps={{ readOnly: true,style: { textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'center', fontSize: 35}}}
                            onChange = {e => {this.setState({ pollID: e.target.value});}}
                            style = {{ top:"14vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "50vh" }}
                        />
                        <TextField 
                            id="outlined-basic" 
                            defaultValue={this.state.optionA}
                            value = {this.state.optionA}
                            variant="outlined" 
                            inputProps={{readOnly: true, style: { textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'center', fontSize: 20}}}
                            onChange = {e => {this.setState({ pollID: e.target.value});}}
                            style = {{ top:"19vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "35vh" }}
                        />
                        <TextField 
                            id="outlined-basic" 
                            defaultValue={this.state.optionB}
                            value = {this.state.optionB}
                            variant="outlined" 
                            inputProps={{readOnly: true, style: {textAlign: 'left', fontSize: 20}}}
                            InputLabelProps={{style: {textAlign: 'left', fontSize: 20}}}
                            onChange = {e => {this.setState({ pollID: e.target.value});}}
                            style = {{ top:"22vh",
                                        position:"relative",
                                        left: "10%",
                                        width: "35vh" }}
    
                        />
                       
                    </Box>,
                    <Button 
                        component={Link}
                        variant="contained"
                        color = "secondary"
                        to = {"../view/" + this.props.match.params.pollID}
                        onClick = {e => {this.sendVoteUpdate(1); this.sendUserPollVotedUpdate()}}
                        style = {{ width:"27vh",
                                   right: "-9vh",
                                   position:"relative"   ,
                                   top:"25vh",     
                                }}
                    >Vote Option A
                    </Button>
                    <Button 
                        component={Link}
                        variant="contained"
                        color = "secondary"
                        to = {"../view/" + this.props.match.params.pollID}
                        onClick = {e => {this.sendVoteUpdate(2); this.sendUserPollVotedUpdate()}}
                        style = {{ width:"27vh",
                                   left: "10vh",
                                   position:"relative"   ,
                                   top:"25vh",     
                                }}
                    >Vote Option B
                    </Button>
                    <Typography variant="h6"
                            style = {{ top:"16vh",
                                       right: "42vh",
                                        position:"relative",
                             }}
                        >
                        Time Remaining:  {this.state.timeLimit}
                        </Typography>
    
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

export default PollParticipate;