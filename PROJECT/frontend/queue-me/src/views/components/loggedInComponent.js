
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
                </tbody>
            </table>
            <p><input type="submit" value="logout" onClick={e => logout()} /> </p>
        </div>
    );
}