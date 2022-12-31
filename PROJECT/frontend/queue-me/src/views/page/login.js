export default function Login({usernameRef, passRef, clicker, debug}){
    return (
        <div className="login">

                <p>Username: <input type="text" ref={usernameRef}/></p>
                <p>Password: <input type="password" ref={passRef}/></p>
                <p> <input type="submit" value="Login" onClick={e => clicker()}/> </p>

            <p>debug: {JSON.stringify(debug)}  </p>
        </div>
    )
}