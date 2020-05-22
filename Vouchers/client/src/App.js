import React, {Component} from 'react';
import GrailsApp from "./GrailsApp";
import UsersView from "./usersView/UsersView";
import SignInPage from "./usersView/SignInPage";
import SignUpPage from "./usersView/SignUpPage";
import ForgotPasswordPage from "./usersView/ForgotPasswordPage";
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";

class App extends Component {
    render() {
        return (
            <Router>
                <Switch>
                    <Route path="/signin">
                        <SignInPage />
                    </Route>
                    <Route path="/signup">
                        <SignUpPage />
                    </Route>
                    <Route path="/forgotpassword">
                        <ForgotPasswordPage />
                    </Route>
                    <Route path="/users">
                        <UsersView />
                    </Route>
                    <Route path="/">
                        <GrailsApp />
                    </Route>
                </Switch>
            </Router>
        );
    }
}

export default App;
