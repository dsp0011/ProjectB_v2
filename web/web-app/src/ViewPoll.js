import { Typography } from '@material-ui/core';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { ResponsivePie } from "@nivo/pie";
import React, { Component } from 'react';
import { getSessionCookie } from './Session';

class ViewPoll extends Component {



    constructor(props) {
        super(props);
        this.state = {question: "0",
                      optionA: "0",
                      optionB: "0",
                      timeLimit: "0",
                      optionAVotes: "0",
                      optionBVotes: "0"  
                    }
    }

  
    getPieData = () => {
        const pieData = 
            [
                {
                    "id": this.state.optionA,
                    "label": this.state.optionA,
                    "value": parseInt(this.state.optionAVotes,10),
                    "color": "hsl(289, 70%, 50%)"
                },
                {
                    "id": this.state.optionB,
                    "label": this.state.optionB,
                    "value": parseInt(this.state.optionBVotes,10),
                    "color": "hsl(12, 70%, 50%)"
                }
            ] 

        return pieData;

    }
    getPieLegends = () => {
        const legends = [
            {
              anchor: "right",
              direction: "column",
              justify: false,
              translateX: 140,
              translateY: 100,
              itemsSpacing: 2,
              itemWidth: 100,
              itemHeight: 20,
              itemDirection: "left-to-right",
              itemOpacity: 0.85,
              itemTextColor: "#ffffff",
              symbolSize: 20,
              effects: [
                {
                  on: "hover",
                  style: {
                    itemOpacity: 1
                  }
                }
              ]
            }
          ];

        return legends
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
                        optionAVotes: jsonResponse["vote"]["alternative1"],
                        optionBVotes: jsonResponse["vote"]["alternative2"],
                        pollClosingDate: jsonResponse["pollClosingDate"]
                    })
                
            
        })
        const URL = 'http://localhost:8080/polls/' + pollID

        xhr.open('GET', URL)
        xhr.send(URL)
    }

    componentDidMount() {
        const data = this.getPollData(this.props.match.params.pollID); 

    }


    getExitPath = () => {
        const username = getSessionCookie().username
        if (username === undefined || username === "anonymous")
            return "../../"
        else 
            return ("../../users/" + username)
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
                    height = "80vh"
                    width = "60vh"
                    style = {{ border: '1px solid',
                            position:"absolute"   ,
                            background: 'linear-gradient(to right bottom, #d7ccc8, #a69b97)'

                 }}

                >
                    <Box
                    bgcolor="secondary.main" 
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
                        value = {this.state.question}
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
                        style = {{ top:"17vh",
                                    position:"relative",
                                    left: "10%",
                                    width: "35vh" }}
                    />
                    <TextField 
                        id="outlined-basic" 
                        // defaultValue={this.state.optionB}
                        defaultValue={this.state.optionB}
                        value = {this.state.optionB}
                        variant="outlined" 
                        inputProps={{readOnly: true, style: {textAlign: 'left', fontSize: 20}}}
                        InputLabelProps={{style: {textAlign: 'left', fontSize: 20}}}
                        onChange = {e => {this.setState({ pollID: e.target.value});}}
                        style = {{ top:"18vh",
                                    position:"relative",
                                    left: "10%",
                                    width: "35vh" }}

                    />
                    <Box
                        bgcolor="primary.dark" 
                        justifyContent="center"
                        alignItems="flex-top"
                        height = "35vh"
                        width = "60vh"
                        style = {{ 
                                top:"40vh",
                                position:"absolute"   ,

                    }}

                    ></Box>
                    <ResponsivePie
                        data = {this.getPieData()}
                        width = {parseInt("450", 10)}
                        margin={{ top: 45, right: 80, bottom: 80, left: 80 }} 
                        innerRadius={0.6}
                        borderColor={{ from: 'color', modifiers: [ [ 'darker', 0.2 ] ] }}
                        legends={this.getPieLegends()}
                        
                    >
                    
                    </ResponsivePie>
    
                </Box>,

                <Button 
                    variant="contained"
                    color = "secondary"
                    href = {this.getExitPath()} 
                    style = {{ width:"14vh",
                               left: "20vh",
                               position:"relative"   ,
                               top:"35vh",     
                            }}
                > Exit
                </Button>

            </Box>,
           
         </div>

        );
    }
}


export default ViewPoll;