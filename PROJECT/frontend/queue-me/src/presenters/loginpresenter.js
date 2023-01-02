import { useEffect, useRef, useState } from "react";
import Login from "../views/page/login";
import { getURL, getPostOptions } from "../util/apihelpers";
import User from "../models/user";

export default function LoginPresenter (){
    
    const username = useRef();
    const password = useRef();
    
    const refBeenMounted = useRef(false);
    const [debug, setdebug] = useState("nothing");
    const [click, setClick] = useState(false);
    //const [auth, setAuth] = useState
    
    const login = async () => {
        const retURL = getURL('login/');
        const options = getPostOptions({
            username: username.current.value,
            password: password.current.value
        });

        try {
            const resp = await fetch(retURL, options);
            const data = await resp.json();
            return data;
        } catch (err){
            console.log(err);
        }
    }

    useEffect (() => {
        async function log(){
            const data = await login();
            setdebug(data);
            if (data.status === "success"){
                const userData = data.user;
                const user = new User();
                user.authenticate(userData.username, userData.adminstatus);
                const userProps = user.getProps();
                localStorage.setItem ('user', JSON.stringify(userProps));
            }
        };
        if(refBeenMounted.current) log();
        else refBeenMounted.current = true;
    },[click]);

    //usernameRef, passRef, login
    return <Login usernameRef={username} passRef={password} clicker={() => setClick(!click)} debug={debug}/>
}