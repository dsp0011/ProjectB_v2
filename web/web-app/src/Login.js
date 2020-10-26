import React, { Component, useEffect, useState, useContext } from "react";
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';
import { SessionContext, getSessionCookie, setSessionCookie, updateSessionCookie } from "./Session.js";
import Alert from '@material-ui/lab/Alert';

class Login extends Component {
    static context = SessionContext;

    componentDidMount() {

    }

    handleLogin = () => {
        const username = getSessionCookie().username
        const password = getSessionCookie().password
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            if (data != "") {
                const jsonResponse = JSON.parse(data)
                const actualPassword = jsonResponse.password
                // console.log("password: ", password,
                //             "\n actual password: ", actualPassword,
                //             "\n username: ", username)
    
            if (password === actualPassword || data == "" )
                this.props.history.push("/users/" +username); 
            }
            else {
                alert("Invalid information, try again")
            }
            
        })
        const URL = 'http://localhost:8080/users/' + username

        xhr.open('GET', URL)
        // send the request
        xhr.send(URL)
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
                    height = "50vh"
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
                        Login
                    </Typography>
                    <TextField 
                        id="outlined-basic" 
                        label="Username" 
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'center', fontSize: 30}}}
                        
                        onChange = {e => {updateSessionCookie("username", e.target.value);}}
                        style = {{ top:"17vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}
                    />
                    <TextField 
                        id="outlined-basic" 
                        label="Password" 
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {updateSessionCookie("password", e.target.value);}}
                        style = {{ top:"22vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                   
                </Box>,
                <Button 
                    variant="contained"
                    color = "secondary"
                    onClick = {e => {this.handleLogin();}} // send HTTP request here
                    style = {{ width:"60vh",
                               position:"relative"   ,
                               top:"30vh",     
                            }}
                >Login
                </Button>

            </Box>,
           
         </div>

        );
    }
}

export default Login;