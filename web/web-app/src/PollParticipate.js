import React, { Component } from 'react';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';
import PropTypes from 'prop-types';
import {Link, LinkProps} from "react-router-dom";
class PollParticipate extends Component {



    constructor(props) {
        super(props);
        this.state = {question: "0",
                      optionA: "0",
                      optionB: "0",
                      timeLimit: "0"}
    }

  
    sendVoteUpdate = (alternative) => {
        const xhr = new XMLHttpRequest()

        const URL = 'http://localhost:8080/votes/' + this.props.match.params.pollID

        console.log("url", )
        xhr.open('PUT', URL)
        xhr.setRequestHeader('Access-Control-Allow-Origin', '*')
        xhr.setRequestHeader('Content-Type', 'application/json');

        //create JSON string reqeust
        console.log("state", this.state)
        var jsonString = "";
        (alternative == 1) ?  jsonString = JSON.stringify( {alternative1: 1}) : jsonString = JSON.stringify( {alternative2: 1})


        console.log("jsonString", jsonString)
        // send the request
        xhr.send(jsonString)

    }
    getPollData = (pollID) => {
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            this.setState({question: jsonResponse["question"],
                        optionA: jsonResponse["alternative1"],
                        optionB: jsonResponse["alternative2"],
                        timeLimit: jsonResponse["timeLimit"]
                    })
            
        })
        const URL = 'http://localhost:8080/polls/' + pollID

        console.log("url", URL)
        xhr.open('GET', URL)
        // send the request
        xhr.send(URL)
    }

    componentDidMount() {
        console.log("props,", this.props)
        const data = this.getPollData(this.props.match.params.pollID); 
        for (const item in data) {
            console.log("item,", item)
        }
    }

    render() {
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
                    onClick = {e => {this.sendVoteUpdate(1)}} // send HTTP request here
                    style = {{ width:"27vh",
                               right: "-7vh",
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
                    onClick = {e => {this.sendVoteUpdate(2)}} // send HTTP request here
                    style = {{ width:"27vh",
                               left: "9vh",
                               position:"relative"   ,
                               top:"25vh",     
                            }}
                >Vote Option B
                </Button>
                <Typography variant="h6"
                        style = {{ top:"16vh",
                                   right: "44vh",
                                    position:"relative",
                         }}
                    >
                    Time Remaining:  {this.state.timeLimit}
                    </Typography>

            </Box>,
           
         </div>

        );
    }
}

PollParticipate.defaultProps = {
    pollID : "0000"
}

PollParticipate.propTypes = {
    pollID : PropTypes.string
}


export default PollParticipate;