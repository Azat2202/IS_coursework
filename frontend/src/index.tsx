import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {App} from "./app";
import {api} from "./store/baseApi";
import {ApiProvider} from '@reduxjs/toolkit/query/react';
import {BrowserRouter} from "react-router-dom";
import './index.css';
import {Provider} from "react-redux";
import store from "./store/store";
import {Toaster} from "react-hot-toast";
import {AuthProvider} from "react-oidc-context";
const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);


const oidcConfig = {
    authority: "http://localhost:8484/realms/bunker",
    client_id: "bunker",
    redirect_uri: "http://localhost:3000/main",
    onSigninCallback: () => {
        window.history.pushState({}, "", "/main");
    }
}

const appConfig = {

}

root.render(
    <React.StrictMode>
        <AuthProvider {...oidcConfig}>
            <Toaster/>
            <ApiProvider api={api}>
                <Provider store={store}>
                    <BrowserRouter>
                        <App/>
                    </BrowserRouter>
                </Provider>
            </ApiProvider>
        </AuthProvider>
    </React.StrictMode>
);

reportWebVitals();