import React, { Component } from 'react';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';

class Register extends Component {
    constructor(props) {
        super(props);
        this.state = {}
    }

    sendRegisterRequest = () => {
        // create a new XMLHttpRequest
        const xhr = new XMLHttpRequest()

        // get a callback when the server responds
        xhr.addEventListener('load', () => {
        // update the state of the component with the result here
            console.log(xhr.responseText) 
        })
        // open the request with the verb and the url
        xhr.open('POST', 'http://localhost:8080/users')
        xhr.setRequestHeader('Content-Type', 'application/json');

        //create JSON string reqeust
        console.log("state", this.state)
        const jsonString = JSON.stringify( {
                            "userName": this.state.username,
                            "password": this.state.password,
                            "firstName": this.state.firstName,
                            "lastName": this.state.lastName,
                            "repeatPassword" : this.state.repeatPassword

        })
        console.log("jsonString", jsonString)
        // send the request
        xhr.send(jsonString)
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
                    height = "70vh"
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
                        Register
                    </Typography>
                    <TextField 
                        id="outlined-basic" 
                        label="Name" 
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'center', fontSize: 30}}}
                        onChange = {e => {this.setState({ firstName: e.target.value});}}
                        style = {{ top:"17vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}
                    />
                    <TextField 
                        id="outlined-basic" 
                        label="Last name" 
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {this.setState({ lastName: e.target.value});}}
                        style = {{ top:"19vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                    <TextField
                        required 
                        id="outlined-required" 
                        label="Username" 
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {this.setState({ username: e.target.value});}}
                        style = {{ top:"21vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                    <TextField 
                        required
                        id="outlined-password-required" 
                        label="Password" 
                        type = "password"
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {this.setState({ password: e.target.value});}}
                        style = {{ top:"23vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                    <TextField 
                        required
                        id="outlined-password-required" 
                        label="Repeat password" 
                        variant="filled" 
                        type = "password"
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {this.setState({ repeatPassword: e.target.value});}} 
                        style = {{ top:"25vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                </Box>,
                <Button 
                    variant="contained"
                    color = "secondary"
                    href = "login"
                    onClick = {e => {this.sendRegisterRequest()}} // send HTTP request here
                    style = {{ width:"60vh",
                               position:"relative"   ,
                               top:"39vh",     
                            }}
                >Register
                </Button>

            </Box>,
           
         </div>

        );
    }
}

export default Register;