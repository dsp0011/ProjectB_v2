import React, { Component, useEffect, useState, useContext } from "react";
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';
import { SessionContext, getSessionCookie, setSessionCookie, updateSessionCookie } from "./Session.js";


class Login extends Component {
    static context = SessionContext;

    updateSessionUsername = (username) => {
        console.log("username: ", username)
    }
 

    componentDidMount() {

        const wow = 
            {
              anchor: "right",
              direction: "column",
            }
        setSessionCookie(wow)
        console.log("woooow", getSessionCookie())
        updateSessionCookie("anchor", "noooooooooooooooooooo")
        console.log("woooow2", getSessionCookie())

    }

    handleLogin = () => {
        this.matchUser()
    }
    // Cgecj

    checkCredentials = (username) => {

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
                        
                        onChange = {e => {this.updateSessionUsername(e.target.value)}}
                        style = {{ top:"17vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}
                    />
                    <TextField 
                        id="outlined-basic" 
                        label="Passowrd" 
                        variant="filled" 
                        inputProps={{style: { textAlign: 'left', fontSize: 30}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 30}}}
                        onChange = {e => {this.setState({ pollID: e.target.value});}}
                        style = {{ top:"22vh",
                                    position:"relative",
                                    left: "20%",
                                    width: "35vh" }}

                    />
                   
                </Box>,
                <Button 
                    variant="contained"
                    color = "secondary"
                    onClick = {e => {alert(this.state.pollID)}} // send HTTP request here
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