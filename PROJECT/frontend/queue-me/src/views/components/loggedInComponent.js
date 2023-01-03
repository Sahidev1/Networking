
export default function LoggedInComponent ({username, role, logout}){
    return (
        <div className="logcomp">
            <table>
                <tbody>
                    <tr>
                        <td>Username: {username}</td>
                    </tr>
                    <tr>
                        <td>Role: {role} </td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="logout" onClick={e => logout()} /> </td>
                    </tr>
                </tbody>
            </table>
        </div>
    );
}