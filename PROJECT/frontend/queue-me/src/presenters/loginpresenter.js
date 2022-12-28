import { useRef } from "react";
import Login from "../views/page/login";

export default function LoginPresenter (){
    
    const username = useRef()
    const password = useRef()

    const login = async () => {
        //Loggin call
        console.log("log in func called")
        console.log("username: " + username.current.value);
    }
    //usernameRef, passRef, login
    return <Login usernameRef={username} passRef={password} login={login}/>
}