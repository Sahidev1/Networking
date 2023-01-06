
export default function LoggedInComponent ({navigator,username, role, logout}){
    return (
        <div className="logcomp">
            <table className="credtable">
                <tbody>
                    <tr>
                        <td>Username: {username}</td>
                    </tr>
                    <tr>
                        <td>Role: {role} </td>
                    </tr>
                </tbody>
            </table>
            <div className="menu">
                <input id={window.location.pathname !== '/'?"":"hide"} type="submit" value="Home" onClick={e => navigator()}/>
                <input className="logoutbtn" type="submit" value="logout" onClick={e => logout()} /> 
            </div>
        </div>
    );
}