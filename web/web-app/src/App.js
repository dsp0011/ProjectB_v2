import React, { useEffect, useState, useContext } from "react";
import './App.css';
import MainPage from './MainPage.js'
import Login from './Login.js';
import Register from './Register.js';
import PollParticipate from './PollParticipate.js';
import PollCreate from './PollCreate.js';
import ViewPoll from './ViewPoll.js';
import EditPoll from './EditPoll.js';
import UserPolls from './UserPolls.js'
import { SessionContext, getSessionCookie, setSessionCookie } from "./Session.js";
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

function App() {
  const [session] = useState(getSessionCookie());

  return (
    <SessionContext.Provider value={session}>
      <Router >
        <Switch>
              <Route exact path='/' component={MainPage} />
              <Route path='/register' component={Register} />
              <Route path='/login' component={Login} />
              <Route path='/polls/view/:pollID' component={ViewPoll} />
              <Route path='/users/:username/' component={UserPolls} />
              <Route path='/polls/vote/:pollID' component={PollParticipate}/>
              <Route path='/create/' component={PollCreate}/>
              <Route path='/edit/:pollID' component={EditPoll}/>

              
          </Switch>
      </Router>
    </SessionContext.Provider>

  );
}

export default App; 
