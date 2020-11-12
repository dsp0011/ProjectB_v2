import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import React, { Component } from "react";
import { getSessionCookie, SessionContext, updateSessionCookie } from "./Session.js";

class Login extends Component {
    static context = SessionContext;

    handleLogin = () => {
        const username = getSessionCookie().username
        const password = getSessionCookie().password
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            if (data != "") {
                const jsonResponse = JSON.parse(data)
                const actualPassword = jsonResponse.password
                this.props.history.push("/users/" +username); 
            }
            else {
                alert("Invalid information, try again")
            }
            
        })
        const URL = 'http://localhost:8080/users/' + username

        xhr.open('GET', URL)
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
                        type = "password" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {updateSessionCookie("password", e.target.value);}} // TODO do not store this in the session cookie
                        style = {{ top:"22vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                   
                </Box>,
                <Button 
                    variant="contained"
                    color = "secondary"
                    onClick = {e => {this.handleLogin();}} 
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