import React from 'react';
import './App.css';
import MainPage from './MainPage.js'
import Login from './Login.js';
import Register from './Register.js';
import PollParticipate from './PollParticipate.js';
import ViewPoll from './ViewPoll.js';


import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

function App() {
  return (
    <Router >
      <Switch>
            <Route exact path='/' component={MainPage} />
            <Route path='/register' component={Register} />
            <Route path='/login' component={Login} />
            <Route path='/polls/view/:pollID' component={ViewPoll} />
            <Route path='/polls/vote/:pollID' component={PollParticipate}/>
        </Switch>
    </Router>
  );
}

export default App; 
