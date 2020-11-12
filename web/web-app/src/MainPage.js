import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import TextField from '@material-ui/core/TextField';
import React, { Component } from 'react';
import { getSessionCookie, initializeCookie } from "./Session.js";
class MainPage extends Component {
    constructor(props) {
        super(props);
        this.state = {pollID: "0"}
    }

    componentDidMount() {
        if (getSessionCookie() === "anonymous" || getSessionCookie() === undefined){
            initializeCookie()
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
                    bgcolor="black" 
                    style = {{ width:"100%",
                               height:"7vh",
                               position:"absolute"   ,
                               bottom:"0vh"     
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
                    style = {{ border: '6px solid',
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
                    href = {"polls/vote/" + this.state.pollID}
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
                    href = {"polls/view/" + this.state.pollID}
                    elevation={20}
                    style = {{ width:"29vh",
                               left: "1vh",
                               position:"relative"   ,
                               top:"14vh",     
                            }}
                >View
                </Button>
    
            </Box>,
            
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
                    <Button 
                        variant="contained"
                        color = "secondary"
                        href = "/login"
                        style = {{ width:"20vh",
                                position:"absolute",
                                right:"5vh",
                                top:"95vh"
                                }}
                    >Login
                    </Button>
                </Grid>
                <Grid item>
                    <Button 
                        variant="contained"
                        color = "secondary"
                        href = "/register"
                        style = {{ width:"20vh",
                                position:"absolute",
                                right:"30vh",
                                top:"95vh"
                                }}
                            
                    >
                        Register
                    </Button>
                </Grid>

            </Grid>
         </div>

        );
    }
}

export default MainPage;