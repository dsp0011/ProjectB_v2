import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';
import { createMuiTheme, ThemeProvider } from "@material-ui/core";
import { BrowserRouter } from 'react-router-dom'

const theme = createMuiTheme({
  palette: {
      primary: {
        light: '#fffffb',
        main: '#d7ccc8',
        dark: '#a69b97',
        contrastText: '#fff',
      },
      secondary: {
        light: '#be9c91',
        main: '#8d6e63',
        dark: '#5f4339',
        contrastText: '#000',
      },
    },
  });

ReactDOM.render(
    <ThemeProvider theme={theme}>
      <App />
    </ThemeProvider>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
