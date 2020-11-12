import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import React, { Component } from 'react';
import Countdown from "react-countdown";
import { getSessionCookie, initializeCookie } from "./Session.js";
class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {pollID: "0"}
    }

    componentDidMount() {
        if (getSessionCookie() === "anonymous" || getSessionCookie() == undefined){
            initializeCookie()
        }
    }
    
    handleNextPage = (pollID, view) => {
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            console.log("data: ", data)
            if (pollID == 0)
                return
            if (data == ""){
                alert("Entered poll ID is invalid")
                return
            }
            const jsonResponse = JSON.parse(data)
            const active = jsonResponse["active"]
            if (!active) {
                alert("The poll is no longer active")
                return
            }
            if (view)
                this.props.history.push("polls/view/" + this.state.pollID)
            else
                this.props.history.push("polls/vote/" + this.state.pollID)
                

    
            
        })
        const URL = 'http://localhost:8080/polls/' + pollID

        xhr.open('GET', URL)
        xhr.send(URL)
    }
    
    userIsLoggedIn = () => {
        if (getSessionCookie().username == "anonymous")
            return false
        console.log("user is logged in")
        return true 
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
                    bgcolor="#424242" 
                    style = {{ width:"100%",
                               height:"7vh",
                               position:"absolute"   ,
                               bottom:"0vh",
                                  
                            }}
                >

                </Box>
                <Box
                    bgcolor="primary.main" 
                    display="flex"
                    boxShadow={30}
                    justifyContent="center"
                    alignItems="center"
                    height = "20vh"
                    width = "60vh"
                    style = {{ border: '2px solid',
                            position:"absolute"   ,
                 }}

                >
                    <TextField 
                        id="outlined-basic" 
                        label="Enter Poll ID" 
                        variant="standard" 
                        inputProps={{style: { textAlign: 'center', fontSize: 50}}}
                        InputLabelProps={{style: {textAlign: 'center', fontSize: 50}}}
                        onChange = {e => {this.setState({ pollID: e.target.value});}}
                    />
                </Box>,

                <Button 
                    variant="contained"
                    color = "secondary"
                    // href = {"polls/vote/" + this.state.pollID}
                    onMouseDown = {e => {this.handleNextPage(this.state.pollID, false)}}
                    elevation={20}
                    style = {{ width:"29vh",
                               right: "1vh",
                               position:"relative"   ,
                               top:"14vh",     
                            }}
                >Participate
                </Button>
                <Button 
                    variant="contained"
                    color = "secondary"
                    // href = {"polls/view/" + this.state.pollID}
                    onMouseDown = {e => {this.handleNextPage(this.state.pollID, true)}}
                    elevation={20}
                    style = {{ width:"29vh",
                               left: "1vh",
                               position:"relative"   ,
                               top:"14vh",     
                            }}
                >View
                </Button>
    
            </Box>,
            <Countdown 
                        style = {{ 
                            width:"207vh",
                            top:"50vh",
                            right: "50vh",
                            color: 'white',
                            position:"absolute",
                        }}
                        date={Date.now() + 100000} />,
            <Grid
                container
                direction="row"
                justify="center"
                display="flex"
                alignItems="flex-end"
            >
                <Grid item>

                </Grid>
                <Grid item>
                    {this.userIsLoggedIn() 
                    && <Button 
                        variant="contained"
                        color = "secondary"
                        hidden = {this.userIsLoggedIn()}
                        href = {"/users/" + getSessionCookie().username }
                        style = {{ width:"20vh",
                                position:"absolute",
                                right:"5vh",
                                top:"95vh"
                                }}
                    >My Page
                    </Button>}
                    {!this.userIsLoggedIn() &&
                        <Button 
                        variant="contained"
                        color = "secondary"
                        hidden = {!this.userIsLoggedIn()}
                        href = "/login"
                        style = {{ width:"20vh",
                                position:"absolute",
                                right:"5vh",
                                top:"95vh"
                                }}
                    >Login
                    </Button>}
                </Grid>
                <Grid item>
                {     !this.userIsLoggedIn() && 
              <Button 
                        variant="contained"
                        color = "secondary"
                        hidden = {!this.userIsLoggedIn()}
                        href = "/register"
                        style = {{ width:"20vh",
                                position:"absolute",
                                right:"30vh",
                                top:"95vh"
                                }}
                            
                    >
                        Register
                    </Button>}
                </Grid>

            </Grid>
         </div>

        );
    }
}

export default MainPage;