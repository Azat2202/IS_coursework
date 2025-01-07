import React from "react";
import {Route, Routes} from "react-router-dom";
import {LandingPage} from "./views/Landing";
import {ErrorPage} from "./views/ErrorPage";
import PrivateRoute from "./containers/PrivateRoute";

export const App: React.FC = () => {
    return (
        <Routes>
            <Route path={"/"} element={<LandingPage/>}/>
            <Route path={"/dots"} element={<PrivateRoute><div></div></PrivateRoute>}/>
            <Route path={"/*"} element={<ErrorPage/>}/>
        </Routes>
    );
};