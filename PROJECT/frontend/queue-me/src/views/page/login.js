export default function Login({usernameRef, passRef, login}){
    return (
        <div className="login">
            <form>
                <p>Username: <input type="text" ref={usernameRef}/></p>
                <p>Password: <input type="password" ref={passRef}/></p>
                <p> <input type="submit" value="Login" onClick={login}/> </p>
            </form>
        </div>
    )
}