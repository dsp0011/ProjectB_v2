import React, { Component, useEffect, useState, useContext } from "react";
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import { Typography } from '@material-ui/core';
import PropTypes from 'prop-types';
import { ResponsivePie } from "@nivo/pie";

class UserPolls extends Component {
    constructor(props) {
        super(props);
        this.state = {participatedPolls : [],
                      createdPolls : []
                        }
    }

    getUserPollsCreated = (username) => {
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {
            const data = xhr.responseText
            const jsonResponse = JSON.parse(data)
            this.state.createdPolls = jsonResponse["pollsCreated"]
            console.log("state: ", this.state)
            
        })
        const URL = 'http://localhost:8080/users/' + username
        xhr.open('GET', URL)
        xhr.send(URL)
    }

    getUserPollsParticipated = (username) => {
        const xhr = new XMLHttpRequest()

        xhr.addEventListener('load', () => {

            
        })
        const URL = 'http://localhost:8080/users/' + username
        xhr.open('GET', URL)
        xhr.send(URL)
    }


    componentDidMount() {
        const username = this.props.match.params.username
        console.log(username)
        this.getUserPollsCreated(username)

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
                bgcolor="primary.light" 
                display="flex"
                justifyContent="center"
                alignItems="center"
                minHeight="100vh"
                >
    
                </Box>,
 
            </Box>,
           
         </div>

        );
    }
}


export default UserPolls;
