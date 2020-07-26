import React, {Component} from 'react';
import GrailsApp from "./GrailsApp";
import UsersBuyVouchers from "./usersView/UsersBuyVouchers";
import UsersExchangeVouchers from "./usersView/UsersExchangeVouchers";
import BusinessProfile from "./usersView/BusinessProfile"
import SignInPage from "./usersView/SignInPage";
import SignUpPage from "./usersView/SignUpPage";
import ForgotPasswordPage from "./usersView/ForgotPasswordPage";
import {
  BrowserRouter as Router,
  Switch,
  Route
} from "react-router-dom";
import ClientProfile from './usersView/ClientProfile';

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
                    <Route path="/business/profile">
                        <BusinessProfile />
                    </Route>
                    <Route path="/users/profile">
                        <ClientProfile />
                    </Route>
                    <Route path="/users/comprar">
                        <UsersBuyVouchers />
                    </Route>
                    <Route path="/users/canjear">
                        <UsersExchangeVouchers />
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
