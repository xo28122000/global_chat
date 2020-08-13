import * as React from "react";
import { BrowserRouter, Switch, Route } from "react-router-dom";
import Home from "./pages/Home/index";
import Login from "./pages/Login";
import User from './pages/User/index'

export const Routes = ({handleThemeToggle}) => (
  <BrowserRouter>
    <Switch>
      <Route exact={true} path="/home">
        <Home handleThemeToggle={handleThemeToggle}/>
      </Route>
      <Route exact={true} path="/login">
        <Login />
      </Route>
      <Route exact={true} path="/">
        <Login />
      </Route>
      <Route exact={true} path="/user">
        <User/>
      </Route>
    </Switch>
  </BrowserRouter>
);
