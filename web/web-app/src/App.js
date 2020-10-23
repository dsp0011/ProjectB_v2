import React, { useEffect, useState, useContext } from "react";
import './App.css';
import MainPage from './MainPage.js'
import Login from './Login.js';
import Register from './Register.js';
import PollParticipate from './PollParticipate.js';
import ViewPoll from './ViewPoll.js';
import PollSearch from './PollSearch.js';
import UserPolls from './UserPolls.js'
import { SessionContext, getSessionCookie, setSessionCookie } from "./Session.js";
import { BrowserRouter as Router, Switch, Route, Link } from 'react-router-dom';

function App() {
  const [session, setSession] = useState(getSessionCookie());
  useEffect(
    () => {
      setSession(getSessionCookie());
    },
    [session]
  );
  return (
    <SessionContext.Provider value={session}>
      <Router >
        <Switch>
              <Route exact path='/' component={MainPage} />
              <Route path='/register' component={Register} />
              <Route path='/login' component={Login} />
              <Route path='/polls/search' component={PollSearch} />
              <Route path='/polls/view/:pollID' component={ViewPoll} />
              <Route path='/polls/:username' component={UserPolls} />
              <Route path='/polls/vote/:pollID' component={PollParticipate}/>
          </Switch>
      </Router>
    </SessionContext.Provider>

  );
}

export default App; 
